use utf8;

package RODA::RODADB::ResultSet::Address;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

sub checkaddress {
    my ( $self, %params ) = @_;

   print Dumper(%params);
   #ce inseamna o adresa unica 
   # id (evident)
   # country_id (sau country_name) + postal_code + address1 + address2
   # city_id, country_id, address1, address2, subdiv_code, subdiv_type
   # city_id, country_name, address1, address2, subdiv_code, subdiv_type

    if ( $params{id} && $params{id} ne '' ) {
       return $self->find({ id => $params{id}});
    }

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

        my $addressrs = $self->search({country_id=> $countryid, postal_code => $params{postal_code}, address1 => $params{address1},address2 => $params{address2} });
        if ($addressrs->count == 1) {
            return $addressrs->single;
        } 

#odata ajuns aici, inseram, daca avem tot ce ne trebuie    
#sa vedem cum acceptam o adresa. Trebuie sa contina un identificator unic de oras si tara, linia 1, subdiv_code si subdiv_name impreuna 

if ($params{address1} && $params{address1} ne '' 
            && (    $params{country_name} && $params{country_name} ne ''
              || $params{country_id} && $params{country_id} ne '' )
         && (    $params{city_name} && $params{city_name} ne ''
              || $params{city_id} && $params{city_id} ne '' )
    ) {

    my $guard = $self->result_source->schema()->txn_scope_guard;

     my $countryid;
     my $cityid;
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

        if ( $params{city_name} && $params{city_name} ne '' ) {
            my $cityrs = $self->result_source->schema()->resultset('City')->checkcity( name => lc( $params{city_name} ),  country_id=>$countryid );
            if ($cityrs) {
                $cityid = $cityrs->id;
            } else {
                die "Nu am putut expanda orasul $params{city_name}";
            }
        } else {
            $cityid = $params{city_id};
        }

        my $subdiv_code;
        my $subdiv_name;
        if ( $params{subdiv_code} && $params{subdiv_code} ne '' && $params{subdiv_name} && $params{subdiv_name} ne '' ) {
            $subdiv_code      = lc( $params{subdiv_code} );
            $subdiv_name = lc( $params{subdiv_name} );
        } else {
            warn "adresa $params{address1} va fi inserata fara coduri: subdiv_code = $params{subdiv_code}, subdiv_name = $params{subdiv_name}";
        }

 
        my $newaddressrs = $self->create(
                                      {
                                        city_id        => $cityid,
                                        address1 => $params{address1},
                                        address2 => $params{address2},
                                        subdiv_name  => $subdiv_name,
                                        subdiv_code  => $subdiv_code,
                                        postal_code    => $params{postal_code},
                                        country_id  => $countryid,
                                      }
        );
        $guard->commit;
        return $newaddressrs;

     
     
    }


}
1;
