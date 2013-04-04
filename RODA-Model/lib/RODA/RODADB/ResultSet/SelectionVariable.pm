use utf8;

package RODA::RODADB::ResultSet::SelectionVariable;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::SelectionVariable - metode specifice prelucrarii variabilelor de tip selectie

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip variabila de selectie.

=cut

=head1 METODE

=cut

=head2 check_selection_variable

check_selection_variable adauga informatiile asociate unei variabile de selectie in tabelul corespunzator din baza de date (selection_variable). 
O inregistrare din acest tabel corespunde unei inregistrari din tabelul ce contine toate variabilele (variable). 
 Verificarea existentei unei variabile in acest tabel se realizeaza doar prin intermediul parametrului ce furnizeaza cheia primara (variable_id).

Parametrii de intrare:

=over 

=item C<variable_id>
- cheia primara a variabilei de selectie; totodata, este cheie externa, referind cheia primara a unei variabile 
din tabelul ce contine toate variabilele din baza de date (variable)

=item C<item>
- numele elementului corespunzator unei variabile de selectie

=item C<min_count>
- numarul minim de elemente ce trebuie selectate in cadrul unui raspuns asociat acestei variabile

=item C<max_count>
- numarul maxim de elemente ce trebuie selectate in cadrul unui raspuns asociat acestei variabile 

=item C<items>
- lista elementelor ce pot fi selectate pentru variabila de selectie curenta. 
Fiecare element din aceasta lista este verificat din punct de vedere al existentei sale in baza de date; in cazul inexistentei unui element, 
acesta este introdus in tabelul corespunzator (item). Totodata, este adaugata si asocierea dintre variabila de selectie si elementul respectiv 
(tabelul selection_variable_item).  


=back


Criterii de unicitate:

=over

=item
- N/A 

=back

=cut


sub check_selection_variable {
   my ( $self, %params ) = @_;
   
   my $guard = $self->result_source->schema()->txn_scope_guard;
 
   my $new_selection_variable_rs = $self->create({
                                   			      variable_id => $params{variable_id},
          						   			      min_count => $params{min_count},
          						   			      max_count => $params{max_count},
                                  				 }
                                 				);
                                 				
   if ($new_selection_variable_rs) {
    	if ($params{items} && @{$params{items}} > 0) {
        	$new_selection_variable_rs->attach_items( items=>$params{items} );
   		}
   }	                                 				
   $guard->commit;
   return $new_selection_variable_rs;
}
1;
