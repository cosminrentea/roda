use utf8;

package RODA::RODADB::ResultSet::Form;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::Form - metode specifice prelucrarii formularelor de raspunsuri

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip formular.

=cut

=head1 METODE

=cut

=head2 checkform

checkform verifica existenta unui formular (daca este furnizata valoarea identificatorului sau) in baza de date; in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce formularul in baza de date si apoi returneaza obiectul corespunzator. 

Parametrii de intrare:

=over 

=item C<form_id>
- cheia primara a formularului din tabelul de formulare de raspunsuri

=item C<instance_id>
- cheia primara a instantei in cadrul careia a fost completat formularul curent

=item C<order_in_instance>
- numarul de ordine al formularului in cadrul instantei

=item C<operator>
- operatorul care a completat formularul curent; acesta poate fi furnizat prin valoarea cheii primare a unei persoane sau prin informatiile minimale (nume si prenume) asociate operatorului. Daca persoana nu exista in baza de date, va fi adaugata cu aceste informatii. 

=item C<operator_notes>
- note, insemnari ale operatorului pe parcursul completarii formularului curent

=item C<edited_text_vars>
- lista de raspunsuri corespunzatoare formularului curent, asociate variabilelor de tip text editat; un element al acestei liste contine codul unei variabile de tip text editat si valoarea (textul) ce reprezinta raspunsul

=item C<edited_number_vars>
- lista de raspunsuri corespunzatoare formularului curent, asociate variabilelor de tip numeric editat; un element al acestei liste contine codul unei variabile de tip numeric editat si valoarea (numarul) ce reprezinta raspunsul

=item C<selection_vars>
- lista de raspunsuri corespunzatoare formularului curent, asociate variabilelor de selectie; un element al acestei liste contine codul unei variabile de tip selectie, codul elementului selectat ce reprezinta raspunsul si (optional) numarul de ordine al acestui element in cadrul raspunsului (relevant in cadrul selectiilor multiple, atunci cand ordinea rapunsurilor este importanta). 

=back


Criterii de unicitate:

=over

=item
- N/A

=back

=cut

sub checkform {
   my ( $self, %params ) = @_;
   
   if ($params{instance_id} && $params{instance_id} ne '' 
   			&& $params{order_in_instance} && $params{order_in_instance} ne '' ) {
   	    
    	my $insertform;
   
    	$insertform->{instance_id} = $params{instance_id};
    	$insertform->{order_in_instance} = $params{order_in_instance};
    	$insertform->{operator_notes} = $params{operator_notes};
    	$insertform->{fill_time} = $params{fill_time};   	
   	  
   	  	my $operator = $params{operator};
 		if ($operator) {  	  
			my $operatorrs = $self->result_source->schema()->resultset('Person')->checkperson(%$operator);
			$insertform -> {operator_id} = $operatorrs -> id;
 		}
 		   	  
   		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        my $newformrs = $self->create($insertform);
       
       if ($params{edited_text_vars} && @{$params{edited_text_vars}} > 0) {
        	$newformrs->attach_edited_text_vars( edited_text_vars=>$params{edited_text_vars} );
        }
        
        if ($params{edited_number_vars} && @{$params{edited_number_vars}} > 0) {
        	$newformrs->attach_edited_number_vars( edited_number_vars=>$params{edited_number_vars} );
        }
       
		if ($params{selection_vars} && @{$params{selection_vars}} > 0) {
        	$newformrs->attach_selection_vars( selection_vars=>$params{selection_vars} );
        }       
       
        $guard->commit;
        return $newformrs;
    }
}
1;
