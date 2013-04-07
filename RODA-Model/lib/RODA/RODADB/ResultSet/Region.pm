use utf8;

package RODA::RODADB::ResultSet::Region;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

=head1 NUME

RODA::RODADB::ResultSet::Region - metode specifice pentru manipularea informatiilor despre regiuni

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip regiune.

=cut

=head1 METODE

=cut

=head2 checkregion

checkregion verifica existenta unei regiuni in baza de date; daca regiunea exista, returneaza obiectul corespunzator, daca nu, il introduce in baza de date si apoi returneaza obiectul corespunzator.

Parametrii de intrare:

=over 

=item C<id>
- cheia primara a regiunii din tabelul region

=item C<name>
- numele regiunii 

=item C<region_code>
- codul regiunii

=item C<rcode_name>
- numele sistemului de codificare a regiunii

=item C<rtype>
- codul tipului regiunii

=item C<rtype_name>
- numele tipului regiunii

=item C<country_name>
- numele tarii in care se gaseste regiunea

=item C<country_id>
- cheia primara a tarii, din tabelul de tari

=back

Criterii de unicitate:

=over

=item
- region_code + rcode_name

=item
- name + rtype

=item
- name + rtype_name

=back

=cut

sub checkregion {
    my ( $self, %params ) = @_;
    #combinatii pentru care stim sigur ca se va returna o singura regiune
    # id
    # region_code + rcode_name (acelasi sistem de codare ar trebui sa asigure coduri unice)
    # name + rtype
    # name + rtype_name (cu expandarea rtype_name)
    #combinatii pentru care trebuie verificata unicitatea pentru a nu avea doua regiuni cu acelasi nume in doua tari diferite
    # name + country
    # name + country_name (cu expandarea lui country_name)
    # nu acceptam query numai dupa numele regiunii, chiar daca e unic, pentru ca nu putem sti exact daca e din tara care trebuie
    #region id e unic
    if ( $params{id} && $params{id} ne '' ) {
        return $self->find( { id => $params{id}, } );
    }

    #region_code si rcode_name sunt index unic, daca le avem pe cele doua si le gasim, returnam rep
    if ( $params{region_code} && $params{region_code} ne '' && $params{name} && $params{name} ne '' ) {
        my $regionrs = $self->find(
                                    {
                                      region_code => lc( $params{region_code} ),
                                      name        => lc( $params{name} ),
                                    }
        );
        return $regionrs if ($regionrs);
    }

    #name si rtype
    if ( $params{name} && $params{name} ne '' && $params{rtype} && $params{rtype} ne '' ) {
        my $regionrs = $self->find(
                                    {
                                      name     => lc( $params{name} ),
                                      rtype_id => $params{rtype},
                                    }
        );
        return $regionrs if ($regionrs);
    }

    #name si rtype_name
    if ( $params{name} && $params{name} ne '' && $params{rtype_name} && $params{rtype_name} ne '' ) {
        my $regtypers = $self->result_source->schema()->resultset('RegionType')->search( { name => lc( $params{region_type_name} ) } )->single;
        my $regionrs = $self->find(
                                    {
                                      name     => lc( $params{name} ),
                                      rtype_id => $regtypers->id,
                                    }
        );
        return $regionrs if ($regionrs);
    }

    #name + country
    if ( $params{name} && $params{name} ne '' && $params{country_id} && $params{country_id} ne '' ) {
        my $ncrs = $self->search(
            {
              name       => lc( $params{name} ),
              country_id => lc( $params{country_id} ),    #country_id e alfanumeric
            }
        );
        if ( $ncrs->count == 1 ) {
            return $ncrs->first;
        } elsif ( $ncrs->count > 1 ) {
            die "Mai multe regiuni cu numele $params{name} in tara cu id-ul $params{country_id}";
        }
    }

    #name + country_name
    if ( $params{name} && $params{name} ne '' && $params{country_name} && $params{country_name} ne '' ) {
        my $countryrs = $self->result_source->schema()->resultset('Country')->checkcountry( name => lc( $params{country_name} ) );
        if ($countryrs) {
            my $ncrs = $self->search(
                                      {
                                        name       => lc( $params{name} ),
                                        country_id => $countryrs->id,
                                      }
            );
            if ( $ncrs->count == 1 ) {
                return $ncrs->first;
            } elsif ( $ncrs->count > 1 ) {
                die "Mai multe regiuni cu numele $params{name} in tara cu id-ul $params{country_id}";
            }
        }
    }

#daca pana acum nu am returnat nimic, ar trebui sa inseram o regiune noua. Nu acceptam o regiune noua decat daca are informatiile:
# nume
# tara (nume sau id)
# tip (nume sau id)
# in plus, poate avea cod si tip de cod. Astea doua merg impreuna, daca nu are decat una dintre ele nu ne place si deocamdata introducem regiunea dar cu null la ambele
#intai sa vedem daca avem tot ce ne trebuie
    if (
            $params{name}
         && $params{name} ne ''
         && (    $params{country_name} && $params{country_name} ne ''
              || $params{country_id} && $params{country_id} ne '' )
         && (    $params{region_type_name} && $params{region_type_name} ne ''
              || $params{region_type_id} && $params{region_type_id} ne '' )
      )
    {

        #intai sa vedem daca avem country_name sau region_type_name, (am mai facut o data verificarea asta dar o mai facem si aici).
        my $guard = $self->result_source->schema()->txn_scope_guard;
        my $countryid;
        my $regtypeid;
        if ( $params{country_name} && $params{country_name} ne '' ) {
            my $countryrs = $self->result_source->schema()->resultset('Country')->checkcountry( name => lc( $params{country_name} ) );
            if ($countryrs) {
                $countryid = $countryrs->id;
            } else {
                die "Nu am putut expanda tara $params{country_name}";
            }
        } else {
            $countryid = $params{country_id};
        }
        if ( $params{region_type_name} && $params{region_type_name} ne '' ) {
            my $regtypers = $self->result_source->schema()->resultset('Regiontype')->search( { name => lc( $params{region_type_name} ) } )->single;
            if ($regtypers) {
                $regtypeid = $regtypers->id;
            } else {
                die "Nu am putut expanda tipul de regiune $params{region_type_name}";
            }
        } else {
            $regtypeid = $params{region_type_id};
        }

        #sa verificam si codurile
        my $region_code;
        my $region_code_name;
        if ( $params{region_code} && $params{region_code} ne '' && $params{rcode_name} && $params{rcode_name} ne '' ) {
            $region_code      = lc( $params{region_code} );
            $region_code_name = lc( $params{rcode_name} );
        } else {
            warn "Regiune $params{name} va fi inserata fara coduri";
        }

        #acum inseram
        my $newregrs = $self->create(
                                      {
                                        name        => lc( $params{name} ),
                                        region_code => $region_code,
                                        region_code_name  => $region_code_name,
                                        regiontype_id    => $regtypeid,
                                        country_id  => $countryid,
                                      }
        );
        $guard->commit;
        return $newregrs;
    } else {
        die
"Combinatie gresita de parametri la insert regiune: name = $params{name}, country_name = $params{country_name}, country_id = $params{country_id}, region_type_name = $params{region_type_name}, region_type_id = $params{region_type_id}";
    }
}
1;
