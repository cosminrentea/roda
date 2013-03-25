use utf8;

package RODA::RODADB::ResultSet::OrgSufix;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::OrgSufix

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE



=cut

sub checkorgsufix {
    my ( $self, %params ) = @_;

    my $orgsufixrs = $self->find({ 'lower(me.name)' => $params{sufix}}, );
    if ($orgsufixrs) {
   		return $orgsufixrs;
    } 
   
    if ($params{sufix} && $params{sufix} ne '' ) {
   		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        my $neworgsufixrs = $self->create(
                                      {
                                        name => lc($params{sufix}),
                                      }
        );
        $guard->commit;
        return $neworgsufixrs;
    }
   
}
1;
