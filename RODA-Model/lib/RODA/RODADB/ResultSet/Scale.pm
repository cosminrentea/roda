use utf8;

package RODA::RODADB::ResultSet::Scale;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::Scale - metode specifice prelucrarii elementelor de tip scala corespunzatoare unei variabile de selectie

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate corespunzatoare elementelor de tip scala ale variabilelor de selectie.

=cut

=head1 METODE

=cut

=head2 checkscale

checkscale introduce un element de tip scala corespunzator unei variabile de selectie. Metoda verifica existenta in baza de date a elementelor 
asociate capetelor intervalului de selectie; in cazul existentei, acestea vor fi referite de catre scala curenta; altfel, vor fi introduse elemente 
de selectie noi care vor fi referite de catre scala curenta. 

Parametrii de intrare:

=over 

=item C<item_id>
- cheia primara a elementului de tip scala din tabelul ce contine elementele posibile ale variabilelor de selectie (item)

=item C<min_value>
- valoarea asociata elementului minim al selectiei 

=item C<max_value>
- valoarea asociata elementului maxim al selectiei

=item C<min_item>
- eticheta corespunzatoare elementului minim al selectiei

=item C<max_item>
- eticheta corespunzatoare elementului maxim al selectiei

=item C<units>
- numarul de unitati ale scalei 

=back


Criterii de unicitate:

=over

=item
- N/A (se aplica unicitatea doar la nivelul capetelor intervalului de selectie)

=back

=cut


sub checkscale {
   my ( $self, %params ) = @_;
   
   if ($params{item_id} && $params{item_id} ne '' && $params{min_value} && $params{min_value} ne ''
   												  && $params{max_value} && $params{max_value} ne ''
   												  && $params{units} && $params{units} ne '' ) {
   	  
   		my $minvaluers = $self->result_source->schema()->resultset('Item')
   								->checkitem(item => $params{min_item}, value => $params{min_value});
   		
   		
   		my $maxvaluers = $self->result_source->schema()->resultset('Item')
   								->checkitem(item => $params{max_item}, value => $params{max_value});
   		
   		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        my $newscalers = $self->create(
                                       {
                                      	item_id => $params{item_id},
                                        units => $params{units}, 
                                        minvalue_id => $minvaluers -> get_column('id'),
                                        maxvalue_id => $maxvaluers -> get_column('id'),                       
                                       }
        							  );
        $guard->commit;
        return $newscalers;
    }
}
1;
