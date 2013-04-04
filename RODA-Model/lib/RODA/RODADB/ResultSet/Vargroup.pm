use utf8;

package RODA::RODADB::ResultSet::Vargroup;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::Vargroup - metode specifice prelucrarii valorilor grupurilor de variabile

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip grup de variabile.

=cut

=head1 METODE

=cut

=head2 checkvargroup

checkvargroup verifica existenta grupului de variabile (preluat prin valori ale parametrilor de intrare) in baza de date. In caz ca exista, obiectul respectiv este returnat; altfel, 
grupul de variabile este introdus in baza de date si obiectul corespunzator acestuia este returnat.


Parametrii de intrare:

=over 

=item C<vargroup_id>
- cheia primara a grupului din tabelul de grupuri de variabile (vargroup)

=item C<vargroup>
- denumirea grupului de variabile

=back


Criterii de unicitate:

=over

=item
- vargroup (presupunem ca numele grupurilor de variabile sunt unice)

=back

=cut


sub checkvargroup {
   my ( $self, %params ) = @_;

   my $vargrouprs = $self->search({name => $params{vargroup}});
   if ($vargrouprs->count == 1) {
   		return $vargrouprs->single;
   } 
   
   if ($params{vargroup} && $params{vargroup} ne '' ) {
   		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        my $newvargrouprs = $self->create(
                                      {
                                        name => $params{vargroup},                                                                 
                                      }
        );
        $guard->commit;
        return $newvargrouprs;
    }
}
1;
