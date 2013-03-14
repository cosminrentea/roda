use utf8;

package RODA::RODADB::ResultSet::TimeMethType;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

sub check_time_meth {
    my ( $self, %params ) = @_;
    my $time_meth_rs;
    
    if ($params{name} && $params{name} ne '' ) {
    	$time_meth_rs = $self->find({ 'lower(me.name)' => lc($params{name})}, );
    	if ($time_meth_rs) {
   			return $time_meth_rs;
    	} else {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        	my $newTimeMeth_rs = $self->create(
                                      		   	{
                                        		 name => lc($params{name}),
                                        		 description => $params{description},
                                      		   	}
                                               );
        	$guard->commit;
        	return $newTimeMeth_rs;
    	}
    }  
}
1;
