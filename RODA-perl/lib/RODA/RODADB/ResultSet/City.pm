use utf8;

package RODA::RODADB::ResultSet::City;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

sub checkcity {
    my ( $self, %params ) = @_;
    #deocamdata putem identifica unic un oras doar prin id sau city_code + ccode_name, e suficient pentru initializarea bazei de date
    if ( $params{id} && $params{id} ne '' ) {
        return $self->find( { id => $params{id}, } );
    }
    if ( $params{city_code} && $params{city_code} ne '' && $params{city_code_name} && $params{city_code_name} ne '' ) {
        my $cityrs = $self->find(
                                  {
                                    city_code  => lc( $params{city_code} ),
                                    city_code_name => lc( $params{city_code_name} ),
                                  }
        );
        return $cityrs if ($cityrs);
    }

if ($params{name} && $params{name} ne '' 
            && (    $params{country_name} && $params{country_name} ne ''
              || $params{country_id} && $params{country_id} ne '' )
    ) {
    my $countryid;
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
      my $cityrs = $self->search({country_id => $countryid, name => lc($params{name})});
      if ($cityrs->count == 1) {
          return $cityrs->single;
      } 
    }


    #la insert, trebuie sa verificam ca avem urmatoarele (obligatoriu)
    # nume
    # country_id sau contry_name
    # apoi, avem asa:
    # city_code si city_code_sup nu pot fi setate in lipsa lui city_code_system
    # city_type nu poate fi setat in lipsa lui city_type_system
    if (
            $params{name}
         && $params{name} ne ''
         && (    $params{country_name} && $params{country_name} ne ''
              || $params{country_id} && $params{country_id} ne '' )
      )
    {
        my $city_code;
        my $city_code_name;
        my $city_code_sup;
        if ( $params{city_code_name} && $params{city_code_name} ne '' && $params{city_code} && $params{city_code} ne '' ) {
            $city_code      = lc( $params{city_code} );
            $city_code_name = lc( $params{city_code_name} );
            $city_code_sup  = lc( $params{city_code_sup} );
        } else {
            warn "Orasul $params{name} va fi inserat fara coduri: city_code = $params{city_code}, ccode_name = $params{ccode_name}";
        }
        my $city_type;
        my $city_type_system;
        if ( $params{city_type} && $params{city_type} ne '' && $params{city_type_system} && $params{city_type_system} ne '' ) {
            $city_type        = lc( $params{city_type} );
            $city_type_system = lc( $params{city_type_system} );
        } else {
            warn "Orasul $params{name} va fi inserat fara coduri: city_type = $params{city_type}, city_type_system = $params{city_type_system}";
        }
        my $guard = $self->result_source->schema()->txn_scope_guard;
        my $newcityrs = $self->create(
                                       {
                                         name          => lc( $params{name} ),
                                         city_code     => $city_code,
                                         prefix        => lc( $params{prefix} ),
                                         city_code_name    => $city_code_name,
                                         city_code_sup => $city_code_sup,
                                         city_type     => $city_type,
                                         city_type_system  => $city_type_system,
                                         country_id => $params{country_id},
                                       }
        );
        $guard->commit;
        return $newcityrs;
    }
}
1;
