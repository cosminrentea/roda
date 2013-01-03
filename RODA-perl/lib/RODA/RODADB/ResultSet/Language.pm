use utf8;

package RODA::RODADB::ResultSet::Language;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

sub checklanguage {
    my ( $self, %params ) = @_;

    #daca avem parametri corecti
    if ( $params{id} && $params{nume} ) {
        my $insl = $self->find_or_create(
                                          {
                                             id   => lc($params{id}),
                                             nume => lc($params{nume}),
                                          },
                                          { key => 'primary' }
        );
        return $insl;
    }

    #daca nu avem decat id-ul sau numele, vedem ce facem in continuare
}
1;
