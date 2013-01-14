use utf8;

package RODA::RODADB::ResultSet::Person;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

sub checkperson {
    my ( $self, %params ) = @_;
    print Dumper(%params);

#aici adaugam persoane. Evident, prima chestie e sa vedem daca nu exista deja
#problema cea mai mare aici e ca numele si prenumele nu identifica unic persoana. Ca sa putem decide automat daca persoana este deja in baza de date
# avem nevoie de mai mult.  Emailul poate fi identificator unic dar poate sa nu fie (office@institutie.ro sau altele), telefonul de tip mobil ar putea fi
#mergem cu urmatoarele ipoteze - speram sa nu existe o persoana cu aceleasi nume si prenume si cu acelasi email si cu acelasi telefon
#verificarea nu o putem face decat daca datele contin emailuri

#deocamdata o verificare simpla, care va trebui inlocuita cu ceva mai serios

    my $personexist = $self->result_source->schema()->resultset('Person')->search(
                                                                                   {
                                                                                     'upper(me.lname)' => uc( $params{lname} ),
                                                                                     'upper(me.fname)' => uc( $params{fname} ),
                                                                                     'upper(me.mname)' => uc( $params{mname} ),
                                                                                   } );


    if ( $personexist->count == 1 ) {
        return $personexist->first;
    }

    #daca nu am returnat pana acum, inseram. Inseram mai intai persoana
    # Sa vedem mai intai prefixele si sufixele
    my $insertpers;
    if ( $params{prefix} && $params{prefix} ne '' ) {
        my $prefixrs = $self->result_source->schema()->resultset('Prefix')->search( { 'upper(me.name)' => $params{prefix} } )->first;
        if ($prefixrs) {
            $insertpers->{prefix} = $prefixrs->id;
        }
    }
    if ( $params{sufix} && $params{sufix} ne '' ) {
        my $suffixrs = $self->result_source->schema()->resultset('Suffix')->search( { 'upper(me.name)' => $params{suffix} } )->first;
        if ($suffixrs) {
            $insertpers->{suffix} = $suffixrs->id;
        }
    }
    $insertpers->{fname} = ucfirst( $params{fname});
          $insertpers->{lname} = ucfirst( $params{lname});
              $insertpers->{mname} = ucfirst($params{mname});
                  my $guard    = $self->result_source->schema()->txn_scope_guard;
                  my $personrs = $self->create($insertpers);
                  if ($personrs) {

                    if (@{$params{addresses}} > 0) {
                            $personrs->attach_addresses( addresses=>$params{addresses} );
                    }

                    $personrs->attach_emails(emails => $params{emails} );
                    $personrs->attach_phones( phones => $params{phones} );
                    $personrs->attach_internets(internets => $params{internets} );
                }
               
                $guard->commit;
                  return $personrs;
            }
            1;
