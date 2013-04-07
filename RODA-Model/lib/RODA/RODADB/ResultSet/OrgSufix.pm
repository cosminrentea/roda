use utf8;

package RODA::RODADB::ResultSet::OrgSufix;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::OrgSufix - metode specifice pentru manipularea sufixelor organizatiilor

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip suix al organizatiilor.

=cut

=head1 UTILIZARE

  my $sufix = $roda->dbschema->resultset('OrgSufix')
                           ->checkorgsufix( 
                                           id => '200', 
                                           name => 'SRL' 
                                          );

  my $sufix = $roda->dbschema->resultset('OrgSufix')
                           ->checkorgsufixname( 
                                               name => 'SRL' 
                                              );

=cut


=head1 METODE

=cut

=head2 checkorgsufix

Primeste ca parametri de intrare id-ul si numele prefixului, daca gaseste in tabel o intrare corespunzatoare, returneaza obiectul respectiv, daca nu, creaza intrarea si returneaza obiectul atasat

Parametrii de intrare:

=over 

=item C<id>
- cheia primara a sufixului de organizatie din tabelul corespunzator acestor sufixe

=item C<name>
- denumirea sufixului de organizatie


=back

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

=head2 checkorgsufixname

Primeste ca parametru de intrare numele sufixului de organizatie, daca gaseste in tabel o intrare corespunzatoare, returneaza obiectul respectiv, daca nu, creaza intrarea si returneaza obiectul atasat

=cut


1;
