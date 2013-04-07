use utf8;

package RODA::RODADB::ResultSet::Prefix;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::Prefix - metode specifice pentru manipularea prefixelor persoanelor

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip prefix.

=cut

=head1 UTILIZARE

  my $prefix = $roda->dbschema->resultset('Prefix')
                            ->checkprefix( 
                                          id => '12', 
                                          name => 'Doamna' 
                                         );

  my $prefix = $roda->dbschema->resultset('Prefix')
                            ->checkprefixname( 
                                              name => 'Domnul' 
                                             );

=cut


=head1 METODE

=cut

=head2 checkprefix

Primeste ca parametri de intrare id-ul si numele prefixului; daca gaseste in tabel o intrare corespunzatoare, returneaza obiectul respectiv, altfel, creeaza intrarea si returneaza obiectul atasat.

=cut

sub checkprefix {
    my ( $self, %params ) = @_;

    #daca avem parametri corecti
    if ( $params{id} && $params{name} ) {
        my $insl = $self->find_or_create(
                                          {
                                             id   => lc($params{id}),
                                             name => lc($params{name}),
                                          },
                                          { key => 'primary' }
        );
        return $insl;
    }

    #daca nu avem decat id-ul sau numele, vedem ce facem in continuare
}

=head2 checkprefixname

Primeste ca parametru de intrare numele prefixului; daca gaseste in tabel o intrare corespunzatoare, returneaza obiectul respectiv, altfel, creeaza intrarea si returneaza obiectul atasat.

=cut


1;
