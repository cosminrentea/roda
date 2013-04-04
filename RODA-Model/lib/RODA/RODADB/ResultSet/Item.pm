use utf8;

package RODA::RODADB::ResultSet::Item;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::Item - metode specifice prelucrarii elementelor variabilelor de tip selectie si valorilor acestora

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip element al unei variabile de selectie.

=cut

=head1 METODE

=cut

=head2 checkitem

checkitem verifica existenta unui element corespunzator unei variabile de selectie (pe baza parametrilor furnizati); in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce elementul in baza de date si apoi returneaza obiectul respectiv.
Verificarea elementului se realizeaza pe baza criteriului de unicitate (nume item, valoare) daca este furnizat parametrul C<value>; altfel, sunt cautate elementele corespunzatoare capetelor intervalului de selectie 
(care sunt, de asemenea, elemente ce se regasesc in tabelul C<item> din baza de date). 
Daca nu sunt gasite, aceste elemente sunt inserate in tabelul corespunzator. 

Parametrii de intrare:

=over 

=item C<item_id>
- cheia primara a elementului din tabelul ce contine elementele posibile ale variabilelor de selectie

=item C<item>
- numele elementului corespunzator unei variabile de selectie

=item C<value>
- valoarea asociata elementului de selectie curent (in cazul selectiei simple)

=item C<min_value>
- valoarea asociata elementului minim al selectiei (in cazul selectiei de tip scala) 

=item C<max_value>
- valoarea asociata elementului maxim al selectiei (in cazul selectiei de tip scala)

=item C<min_item>
- eticheta corespunzatoare elementului minim al selectiei (in cazul selectiei de tip scala)

=item C<max_item>
- eticheta corespunzatoare elementului maxim al selectiei (in cazul selectiei de tip scala)

=item C<units>
- numarul de unitati ale scalei (in cazul selectiei de tip scala) 

=back


Criterii de unicitate:

=over

=item
- item + value

=back

=cut

sub checkitem {
   my ( $self, %params ) = @_;

   #nu consideram unicitatea dupa nume a unui item, deoarece un item cu acelasi nume poate aparea 
   #in contexte diferite
   
   
   if ($params{item} && $params{item} ne '' ) {
   	
		#Adaugarea unei valori a unui item va fi adaugata in baza de date doar daca nu exista deja
   	    #pentru un item cu acelasi nume .
		#Presupunem ca un item este unic identificat prin: name, value  
		if ($params{value} && $params{value} ne '' ) {
			my $where = 'upper(me.name) = ?';
        	my @bind  = (uc($params{item}));
		
    		my $valueexist = $self->result_source->schema()->resultset('Item')
    							->search_literal($where, @bind,
                                                 {
                                                 	join => 'value',
       												 '+select' => [ 'value.value' ],
                                                     '+as'     => [ 'value' ],                                                                           	                                                                          
                                                 }
                                                );

        
    		if ( $valueexist->count >= 1 ) {
    	    	my $value = $valueexist->first;		
    		 	while( $value && $value ne '') {    
    				if ($value -> get_column('value') eq $params{value}) {
        				return $value;
    				}
    				$value = $valueexist->next;
    			}
    		}   	
		}
		
   		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        my $newitemrs = $self->create(
                                       {
                                        name => $params{item},                        
                                       }
        							 );
        
        $guard->commit;
       
        if ($newitemrs) {
    	
    		if ($params{value} && $params{value} ne '') {
        		$newitemrs->attach_value(value => $params{value});
        	}
        	else {
        		if ($params{min_value} && $params{min_value} ne '' && $params{max_value} && $params{max_value} ne '') {
        			$newitemrs->attach_scale(min_value => $params{min_value},
        									 max_value => $params{max_value},
        									 min_item => $params{min_item},
        									 max_item => $params{max_item},
        									 units => $params{units});
        		}
        	}
        }	
        
       
        return $newitemrs;
    }
}
1;
