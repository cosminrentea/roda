use utf8;

package RODA::RODADB::ResultSet::Instance;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

sub checkinstance {
    my ( $self, %params ) = @_;


#Verificarea unicitatii unei instante se realizeaza dupa atributele (instance_id, datestart)    
    
    my $instanceexist = $self->result_source->schema()->resultset('Instance')->search(
                                                                                   {
                                                                                     study_id => $params{study_id},
                                                                                     datestart => $params{datestart},                                                                                     
                                                                                   } );

    if ( $instanceexist->count == 1 ) {
        return $instanceexist->first;
    }
    
    my $insertinstance;
  
   	if ( $params{unit_analysis} && $params{unit_analysis} ne '' ) {   		
        my $unit_analysis_rs = $self->result_source->schema()->resultset('UnitAnalysis')->check_unit_analysis(name => $params{unit_analysis});
        if ($unit_analysis_rs) {
            $insertinstance->{unit_analysis_id} = $unit_analysis_rs->id;
        }
    }
    
    if ( $params{time_meth} && $params{time_meth} ne '' ) {
        my $time_meth_rs = $self->result_source->schema()->resultset('TimeMethType')->check_time_meth(name => $params{time_meth});
        if ($time_meth_rs) {
            $insertinstance->{time_meth_id} = $time_meth_rs->id;
        }
    }
   
    $insertinstance->{study_id} = $params{study_id};
    $insertinstance->{datestart} = $params{datestart};
    $insertinstance->{dateend} =  $params{dateend};
    $insertinstance->{version} = $params{version};
    $insertinstance->{insertion_status} = $params{insertion_status};
    $insertinstance->{raw_data} = $params{raw_data};
    $insertinstance->{raw_metadata} = $params{raw_metadata};
    $insertinstance->{added_by} = $params{added_by};
    $insertinstance->{added} = $params{added};

        
    my $guard   = $self->result_source->schema()->txn_scope_guard;
    my $instancers = $self->create($insertinstance);
    
    if ($instancers) {
    	
    	if (@{$params{orgs}} > 0) {
        	$instancers->attach_organizations( orgs=>$params{orgs} );
        }
        
        if (@{$params{persons}} > 0) {
        	$instancers->attach_persons( persons=>$params{persons} );
        }
    }
               
    $guard->commit;
    return $instancers;
}

1;
