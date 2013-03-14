use utf8;

package RODA::RODADB::ResultSet::UnitAnalysis;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

sub check_unit_analysis {
    my ( $self, %params ) = @_;
    my $unit_analysis_rs;
    
    if ($params{name} && $params{name} ne '' ) {
    	$unit_analysis_rs = $self->search({name => $params{name}}, );
    	if ($unit_analysis_rs -> count == 1) {
   			return $unit_analysis_rs -> single;
    	} else {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        	my $newUnitAnalysis_rs = $self->create(
                                      		   	{
                                        		 name => lc($params{name}),
                                        		 description => $params{description},
                                      		   	}
                                               );
        	$guard->commit;
        	return $newUnitAnalysis_rs;
    	}
    }  
}
1;
