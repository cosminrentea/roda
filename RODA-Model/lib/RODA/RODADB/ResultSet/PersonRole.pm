use utf8;

package RODA::RODADB::ResultSet::PersonRole;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

sub checkpersonrole {
    my ( $self, %params ) = @_;
    my $personrolers;
    
    if ($params{role} && $params{role} ne '' ) {
    	$personrolers = $self->find({ 'me.name' => lc($params{role})}, );
    	if ($personrolers) {
   			return $personrolers;
    	} else {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        	my $newpersonrolers = $self->create(
                                      		   	{
                                        		 name => lc($params{role}),
                                      		   	}
                                               );
        	$guard->commit;
        	return $newpersonrolers;
    	}
    }  
}
1;
