use utf8;

package RODA::RODADB::ResultSet::PersonOrg;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

sub checkpersonorg {
    my ( $self, %params ) = @_;
    my $roleId;

    #Verificare role; daca nu exista, este inserat mai intai in tabelul person_role
    if ( $params{role} && $params{role} ne '' ) {
        my $rolers = $self->result_source->schema()->resultset('PersonRole')->checkpersonrole(role => $params{role});
        if ($rolers) {
        	$roleId = $rolers->id;
        }
    }

	if ($roleId && $roleId ne '') {
		#Adaugarea unei asocieri intre pesoana si organizatie in baza de date are loc doar daca aceasta nu exista deja.  
    	my $personorgexist = $self->result_source->schema()->resultset('PersonOrg')->search(
                                                                            	{
                                                                            	'me.person_id' =>  $params{person_id},
                                                                            	'me.org_id' => $params{org_id},
                                                                            	'me.role_id' => $roleId,                                                                          
                                                                            	});


    	if ( $personorgexist->count == 1 ) {
        	return $personorgexist->first;
    	}

		#Daca asocierea nu a fost gasita, o inseram. 
    	my $insertpersonorg;
   
    
    	$insertpersonorg->{person_id} = $params{person_id};
    	$insertpersonorg->{org_id} = $params{org_id};
    	$insertpersonorg->{role_id} = $roleId;
  
    	my $guard = $self->result_source->schema()->txn_scope_guard;
    
    	my $personorgrs = $self->create($insertpersonorg);
    
        $guard->commit;
         
    	return $personorgrs;
	}
}
1;
