use utf8;

package RODA::RODADB::ResultSet::Country;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

sub checkcountry {
    my ( $self, %params ) = @_;

    #sunt trei posibili parametri : nume, alpha3, id. Alpha3 si id sunt unici, numele nu e. Id-ul are in principiu precedenta
    my $countryrs;
    if ( $params{id} && length( $params{id} ) == 2 ) {
        #sa vedem daca exista (lowercase pentru orice eventualitate)
        $countryrs = $self->find( {id => lc($params{id})} );
        if ($countryrs) {
            return $countryrs;
        } else {    #nu am gasit nimic, incercam sa inseram, doar daca avem numele
            if ( $params{name} && length( $params{name} ) > 3  && $params{alpha3} && length( $params{alpha3} ) == 3    ) {
                $countryrs = $self->create(
                                            {
                                              id     => lc($params{id}),
                                              name   => lc($params{name}),
                                              alpha3 => lc($params{alpha3}),
                                            }
                );
                return $countryrs;
            }
        }
    } else {    #daca nu avem id sau e nul, incercam sa vedem daca nu avem alpha3 (putin probabil, dar totusi)
        if ( $params{alpha3} && length( $params{alpha3} ) == 2 ) {
        #alpha3 e unic si el, daca gasim o inregistrare returnam. Nu inseram o inregistrare care contine alpha 3 si nu contine id
              return $self->find( {alpha3 => lc($params{alpha3})} );
        }
    }
    if ( $params{name} && length( $params{name} ) > 3 ) {
             return $self->find({ name => lc($params{name})} );
    }
}
1;
