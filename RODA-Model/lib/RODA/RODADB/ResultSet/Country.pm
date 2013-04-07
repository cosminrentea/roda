use utf8;

package RODA::RODADB::ResultSet::Country;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;


=head1 NUME

RODA::RODADB::ResultSet::Country - metode specifice pentru manipularea informatiilor despre tari

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip country.

=cut

=head1 METODE

=cut

=head2 checkcountry

verifica existenta unei tari in baza de date; in cazul existentei, returneaza obiectul respectiv, altfel, introduce tara in baza de date si returneaza obiectul corespunzator.


Parametrii de intrare:

=over 

=item C<id>
- cheia primara din tabelul de tari (in format ISO 3166_alpha2)

=item C<name>
- numele tarii

=item C<alpha3>
- codul tarii in format de 3 litere (in format ISO 3166_alpha3)

=back

Criterii de unicitate:

=over


=item
- alpha3 (codurile alpha3 sunt unice pe tari)

=item
- name (numele tarilor vor fi unice) 

=back


=cut
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
