use utf8;

package RODA::RODADB::ResultSet::SelectionVariableItem;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::SelectionVariableItem - metode specifice prelucrarii asocierilor dintre variabilele de tip selectie si elementele corespunzatoare lor

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip asociere intre o variabila de selectie si un element corespunzator ei.

=cut

=head1 METODE

=cut

=head2 check_selection_variable_item

check_selection_variable_item realizeaza cautarea unei asocieri dintre o variabila si un item; 
 va fi apelata doar din partea de chestionar. Se poate presupune ca metadatele au fost definite anterior, 
 iar asocierile dintre variabilele de selectie si elementele lor sunt cunoscute. 
 Daca asocierea este gasita, functia returneaza obiectul corespunzator. 

Parametrii de intrare:

=over 

=item C<variable_id>
- cheia primara a unei variabilei de selectie din tabelul selection_variable

=item C<item_id>
- cheia primara a unui element din tabelul item, corespunzator unei variabile de selectie



=back


Criterii de unicitate:

=over

=item
- N/A 

=back

=cut


sub check_selection_variable_item {
   my ( $self, %params ) = @_;
 
   if ( $params{variable_id} && $params{variable_id} ne '' && $params{item_id} && $params{item_id} ne '' ) {
        my $selection_variable_item_rs = $self->find(
                                  				     {
                                    				  item_id  => $params{item_id},
                                    				  variable_id => $params{variable_id},
                                  					 }
        											);
        return $selection_variable_item_rs if ($selection_variable_item_rs);
    }
    
}
1;
