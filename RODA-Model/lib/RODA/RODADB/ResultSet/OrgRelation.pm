use utf8;

package RODA::RODADB::ResultSet::OrgRelation;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

=head1 NUME

RODA::RODADB::ResultSet::OrgRelation

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE



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
                                                                            	'me.details' => $params{details},                                                                          
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
