use utf8;

package RODA::RODADB::ResultSet::Org;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

=head1 NUME

RODA::RODADB::ResultSet::Org - metode specifice pentru manipularea inregistrarilor despre organizatii

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip organizatie.

=cut

=head1 UTILIZARE

	my %moi = (name => 'ICS',
	       fullname => 'Institute for the Civil Society',      
	       addresses => [{country_name => 'Romania',
	                      city_name => 'Bucuresti',
	                      address1 => 'Str. Academiei nr. 5',
	                      address2 => '',
	                      subdiv_name => 'sector',
	                      subdiv_code => '5',
	                      postal_code => '050001',
	                      },
	                      {country_name => 'Romania',
	                       city_name => 'Bucuresti',
	                       address1 => 'Sos. Panduri nr. 345',
	                       address2 => '',
	                       subdiv_name => 'sector',
	                       subdiv_code => '6',
	                       postal_code => '060677',
	                 }],
	                 emails => [{
	                             email=>'ics@unibuc.ro', 
	                             ismain => '1'
	                            },
	                            {
	                             email => 'ics@gmail.com'
	                            }],
	                 phones => [{
	                             phone => '0777900800', 
	                             phone_type => 'mobile'
	                            },
	                            {
	                             phone => '0213146789'
	                            }],
	                 internets => [{
	                                internet_type => 'blog', 
	                                internet=>'http://ics.greencore.ro'
	                               },
	                               {
	                                internet_type => 'google', 
	                                internet => 'http://google.docs'
	                               }],
	                 persons => [{
	                              fname => 'Vlad', 
	                              mname => 'Ion', 
	                              lname => 'Anton', 
	                              role => 'project manager'
	                 			 },
	                             {
	                              fname => 'Liliana', 
	                              mname => 'Elena', 
	                              lname => 'Marinescu', 
	                              role => 'dezvoltator'
	                             }]                       
	          );


	my $org = $roda->dbschema->resultset('Org')->checkorg( %moi );

=cut

=head1 METODE

=cut

=head2 checkorg

checkorg verifica existenta unei organizatii in baza de date (pe baza parametrilor furnizati); daca exista, returneaza obiectul respectiv, altfel il introduce si returneaza obiectul corespunzator. Parametrii de intrare formeaza o 
structura de date sub forma unui hash conform exemplului de mai sus. 

Parametrii de intrare:

=over 

=item C<org_id>
- cheia primara a organizatiei din tabelul de organizatii

=item C<fullname>
- denumirea completa a organizatiei

=item C<name>
- denumirea abreviata a organizatiei

=item C<prefix>
- prefixul organizatiei curente. Acesta va fi cautat in tabelul de prefixe pentru organizatii; daca nu este gasit, va fi introdus in acest tabel. 

=item C<sufix>
- sufixul organizatiei curente. Acesta va fi cautat in tabelul de sufixe pentru organizatii; daca nu este gasit, va fi introdus in acest tabel.

=item C<addresses>
- lista ce contine adresele postale ale organizatiei; existenta fiecarei adrese va fi verificata in baza de date, iar in cazul inexistentei va fi introdusa. 
Totodata, va fi inserata si asocierea dintre adresa postala gasita sau inserata si organizatia curenta.  

=item C<emails>
- lista ce contine adresele de email ale organizatiei; existenta fiecarei adrese de email va fi verificata in baza de date, iar in cazul inexistentei va fi introdusa. 
Totodata, va fi inserata si asocierea dintre adresa de email gasita sau inserata si organizatia curenta.

=item C<internets>
- lista ce contine adresele de internet ale organizatiei; existenta fiecarei adrese de internet va fi verificata in baza de date, iar in cazul inexistentei va fi introdusa. 
Totodata, va fi inserata si asocierea dintre adresa de internet gasita sau inserata si organizatia curenta.

=item C<phones>
- lista ce contine numerele de telefon ale organizatiei; existenta fiecarui numar de telefon va fi verificata in baza de date, iar in cazul inexistentei numarul respectiv va fi introdus. 
Totodata, va fi inserata si asocierea dintre numarul de telefon gasit sau inserat si organizatia curenta.

=item C<persons>
- lista de persone aflate in relatie cu organizatia curenta;
existenta fiecarei persoane este verificata in baza de date, iar in cazul inexistentei persoanele sunt inserate in tabelul destinat lor. 
O persoana are un role in cadrul organizatiei; de asemenea, acesta este cautat in baza de date si inserat daca nu exista. 
Totodata, in baza de date va fi retinuta asocierea dintre persoana, role si organizatia curenta.    

=back


Criterii de unicitate:

=over

=item
- fullname (presupunem ca denumirile complete ale organizatiilor sunt unice)

=back


=cut

sub checkorg {
    my ( $self, %params ) = @_;

#Adaugarea unei organizatii in baza de date are loc doar daca aceasta nu exista deja.  
#Presupunem ca denumirea completa a unei organizatii o identifica unic pe aceasta.

    my $orgexist = $self->result_source->schema()->resultset('Org')->search(
                                                                            {
                                                                            'upper(me.fullname)' => uc( $params{fullname} ),                                                                          
                                                                            });


    if ( $orgexist->count == 1 ) {
        return $orgexist->first;
    }

    #Daca organizatia nu a fost gasita, o inseram. 
    #Inseram mai intai organizatia, iar apoi informatiile asociate acesteia (email-uri, telefoane etc.)
    my $insertorg;
    if ( $params{prefix} && $params{prefix} ne '' ) {
        my $prefixrs = $self->result_source->schema()->resultset('OrgPrefix')->checkorgprefix(prefix => $params{prefix});
        if ($prefixrs) {
            $insertorg->{org_prefix_id} = $prefixrs->id;
        }
    }
    if ( $params{sufix} && $params{sufix} ne '' ) {
        my $suffixrs = $self->result_source->schema()->resultset('OrgSufix')->checkorgsufix(sufix => $params{sufix});
        if ($suffixrs) {
            $insertorg->{org_sufix_id} = $suffixrs->id;
        }
    }
    $insertorg->{name} = ucfirst( $params{name});
    $insertorg->{fullname} = ucfirst( $params{fullname});
  
    my $guard = $self->result_source->schema()->txn_scope_guard;
    my $orgrs = $self->create($insertorg);
    if ($orgrs) {
    	if ($params{addresses} && @{$params{addresses}} > 0) {
        	$orgrs->attach_addresses( addresses=>$params{addresses} );
        }

		if ($params{emails} && @{$params{emails}} > 0) {
    		$orgrs->attach_emails(emails => $params{emails} );
		}
		
		if ($params{phones} && @{$params{phones}} > 0) {
    		$orgrs->attach_phones( phones => $params{phones} );
		}
		
		if ($params{internets} && @{$params{internets}} > 0) {
    		$orgrs->attach_internets(internets => $params{internets} );
		}
		
		if ($params{persons} && @{$params{persons}} > 0) {
    		$orgrs->attach_persons(persons => $params{persons} );
		}
    }
               
    $guard->commit;
    return $orgrs;
}
1;
