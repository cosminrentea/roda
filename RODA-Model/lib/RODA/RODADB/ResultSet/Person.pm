use utf8;

package RODA::RODADB::ResultSet::Person;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;



=head1 NUME

RODA::RODADB::ResultSet::Person - metode specifice pentru manipularea inregistrarilor despre persoane

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip persoana.

=cut

=head1 UTILIZARE

    my %moi = (fname => 'Ion',
               mname => 'Vlad',
               lname => 'Popescu',
               prefix => 'domnul',
               addresses => [{
                              country_name => 'Romania',
                              city_name => 'Bucuresti',
                              address1 => 'Str. Sperantei nr. 14',
                              address2 => 'Bloc 10 sc. C,. etaj 1, apt. 97',
                              subdiv_name => 'sector',
                              subdiv_code => '2',
                              postal_code => '0216',
                             },
                             {
                              country_name => 'Romania',
                              city_name => 'Bucuresti',
                              address1 => 'Str. Emisferei nr. 1',
                              address2 => '',
                              subdiv_name => 'sector',
                              subdiv_code => '3',
                              postal_code => '0693',
                             }],
               emails => [{
                           email=>'dummy@example.com', 
                           ismain => '1'
                          },
                          {
                           email => 'dummy2@example.com'
                          },
                          {
                           email => 'dummy3@example.com'
                          }],
               phones => [{
                     	   phone => '074000000', 
                           phone_type => 'mobile'
                     	  },
                          {
                           phone => '0216545454', 
                           phone_type => 'home'
                          }],
               internets => [{
                     		  internet_type => 'blog',
                              internet=>'http://vivi.wordp.ro'
                     		 },
                             {
                              internet_type => 'erepx', 
                              internet => 'http://erepx.com/vivi'
                             },
                             {
                              internet_type => 'facebook', 
                              internet => 'http://www.facebook.com/vivid'
                             }],                       
              );

    my $person = $roda->dbschema->resultset('Person')->checkperson( %moi );

=cut

=head1 METODE

=cut

=head2 checkperson

verifica existenta unei persoane in baza de date, daca exista returneaza obiectul respectiv, daca nu, il introduce si returneaza obiectul corespunzator. Asteapta o 
structura de date sub forma unui hash conform exemplului de mai sus. 

Parametrii de intrare:

=over 

=item C<person_id>
- cheia primara a persoanei din tabelul de persoane

=item C<fname>
- prenumele persoanei

=item C<mname>
- numele din mijloc al persoanei

=item C<lname>
- numele de familie al persoanei

=item C<prefix>
- prefixul persoanei curente. Acesta va fi cautat in tabelul de prefixe pentru persoane; daca nu este gasit, va fi introdus in acest tabel. 


=item C<addresses>
- lista ce contine adresele postale ale persoanei; existenta fiecarei adrese va fi verificata in baza de date, iar in cazul inexistentei va fi introdusa. 
Totodata, va fi inserata si asocierea dintre adresa postala gasita sau inserata si persoana curenta.  

=item C<emails>
- lista ce contine adresele de email ale persoanei; existenta fiecarei adrese de email va fi verificata in baza de date, iar in cazul inexistentei va fi introdusa. 
Totodata, va fi inserata si asocierea dintre adresa de email gasita sau inserata si persoana curenta.

=item C<internets>
- lista ce contine adresele de internet ale persoanei; existenta fiecarei adrese de internet va fi verificata in baza de date, iar in cazul inexistentei va fi introdusa. 
Totodata, va fi inserata si asocierea dintre adresa de internet gasita sau inserata si persoana curenta.

=item C<phones>
- lista ce contine numerele de telefon ale persoanei; existenta fiecarui numar de telefon va fi verificata in baza de date, iar in cazul inexistentei numarul respectiv va fi introdus. 
Totodata, va fi inserata si asocierea dintre numarul de telefon gasit sau inserat si persoana curenta.
  

=back


Criterii de unicitate:

=over

=item
- fname + mname + lname

=back

=cut


sub checkperson {
    my ( $self, %params ) = @_;

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
        my $prefixrs = $self->result_source->schema()->resultset('Prefix')->search( { 'upper(me.name)' => uc($params{prefix}) } )->first;
        if ($prefixrs) {
            $insertpers->{prefix_id} = $prefixrs->id;
        }
    }
    if ( $params{sufix} && $params{sufix} ne '' ) {
        my $suffixrs = $self->result_source->schema()->resultset('Suffix')->search( { 'upper(me.name)' => uc($params{sufix}) } )->first;
        if ($suffixrs) {
            $insertpers->{suffix_id} = $suffixrs->id;
        }
    }
    $insertpers->{fname} = ucfirst( $params{fname});
    $insertpers->{lname} = ucfirst( $params{lname});
    $insertpers->{mname} = ucfirst($params{mname});
    my $guard    = $self->result_source->schema()->txn_scope_guard;
    my $personrs = $self->create($insertpers);
    if ($personrs) {
    	
    	if ($params{addresses} && @{$params{addresses}} > 0) {
        	$personrs->attach_addresses( addresses=>$params{addresses} );
        }

		if ($params{emails} && @{$params{emails}} > 0) {
        	$personrs->attach_emails(emails => $params{emails} );
		}
		
		if ($params{phones} && @{$params{phones}} > 0) {
        	$personrs->attach_phones( phones => $params{phones} );
		}
		
		if ($params{internets} && @{$params{internets}} > 0) {
        	$personrs->attach_internets(internets => $params{internets} );
		}
     }
               
     $guard->commit;
     return $personrs;
   }
    1;
