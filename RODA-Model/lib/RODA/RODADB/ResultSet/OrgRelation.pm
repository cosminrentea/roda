use utf8;

package RODA::RODADB::ResultSet::OrgRelation;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

=head1 NUME

RODA::RODADB::ResultSet::OrgRelation - metode specifice pentru manipularea relatiilor dintre organizatii

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip relatie dintre organizatii.

=cut

=head1 UTILIZARE

	my $dtf = $roda->dbschema->storage->datetime_parser;
	my %moi = (org_1_id => 1,
               org_2_id => 2,
               relation_type => 'afiliere',
               datestart => $dtf -> format_datetime(
               				DateTime->new(year => 2002, 
               				              month => 10, 
               				              day => 1)),
               details => 'Institutie afiliata universitatii'                   
               );

	my $orgrelation = $roda->dbschema->resultset('OrgRelation')
	                             ->checkorgrelation( %moi );

=cut

=head1 METODE

=cut

=head2 checkorgrelation

checkorgrelation verifica existenta unei organizatii in baza de date (pe baza parametrilor furnizati); daca exista, returneaza obiectul respectiv, altfel introduce si returneaza obiectul corespunzator. Parametrii de intrare formeaza o 
structura de date sub forma unui hash conform exemplului de mai sus. 

Parametrii de intrare:

=over 

=item C<org_1_id>
- cheia primara a unei organizatii din tabelul de organizatii, implicate in relatia curenta

=item C<org_1_id>
- cheia primara a celeilalte organizatii din tabelul de organizatii, implicate in relatia curenta

=item C<relation_type>
- tipul relatiei curente; existenta acestuia este verificata in baza de date (in tabelul OrgRelationType), iar in cazul inexistentei tipul relatiei este inserat in tabelul respectiv.

=item C<datestart>
- data de inceput a relatiei dintre cele doua organizatii

=item C<dateend>
- data de final a relatiei dintre cele doua organizatii

=item C<details>
- detalii referitoare la relatia curenta dintre cele doua organizatii 

=back


Criterii de unicitate:

=over

=item
- org_1_id + org_2_id + relation_type + datestart + dateend 

=back


=cut

sub checkorgrelation {
    my ( $self, %params ) = @_;
    my $relationId;

    #Verificare relation_type; daca nu exista, este inserat mai intai in tabelul org_relation_type
    if ( $params{relation_type} && $params{relation_type} ne '' ) {
        my $relationtypers = $self->result_source->schema()->resultset('OrgRelationType')->checkorgrelationtype(relation_type => $params{relation_type});
        if ($relationtypers) {
        	$relationId = $relationtypers->id;
        }
    }

	
	if ($relationId && $relationId ne '') {
		#Adaugarea unei asocieri intre doua organizatii in baza de date are loc doar daca aceasta nu exista deja.  
    	my $orgrelationexist = $self->result_source->schema()->resultset('OrgRelation')->search(
                                                                            	{
                                                                            	'me.org_1_id' =>  $params{org_1_id},
                                                                            	'me.org_2_id' => $params{org_2_id},
                                                                            	'me.org_relation_type_id' => $relationId,  
                                                                            	'me.datestart' => $params{datestart},
                                                                            	'me.dateend' => $params{dateend},
                                                                            	#'me.details' => $params{details},                                                                          
                                                                            	});


    	if ( $orgrelationexist->count == 1 ) {
        	return $orgrelationexist->first;
    	}

		#Daca asocierea nu a fost gasita, o inseram. 
    	my $insertorgrelation;
   
    
    	$insertorgrelation->{org_1_id} = $params{org_1_id};
    	$insertorgrelation->{org_2_id} = $params{org_2_id};
    	$insertorgrelation->{org_relation_type_id} = $relationId;
    	$insertorgrelation->{datestart} = $params{datestart};
    	$insertorgrelation->{dateend} = $params{dateend};
    	$insertorgrelation->{details} = $params{details};
  
    	my $guard = $self->result_source->schema()->txn_scope_guard;
    
    	my $orgrelationrs = $self->create($insertorgrelation);
    
        $guard->commit;
         
    	return $orgrelationrs;
	}
}
1;
