use utf8;

package RODA::RODADB::ResultSet::OrgRelationType;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

sub checkorgrelationtype {
    my ( $self, %params ) = @_;
    my $orgrelationrs;
    
    if ($params{relation_type} && $params{relation_type} ne '' ) {
    	$orgrelationrs = $self->find({ 'me.name' => lc($params{relation_type})}, );
    	if ($orgrelationrs) {
   			return $orgrelationrs;
    	} else {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        	my $neworgrelationrs = $self->create(
                                      		   	{
                                        		 name => lc($params{relation_type}),
                                      		   	}
                                               );
        	$guard->commit;
        	return $neworgrelationrs;
    	}
    }  
}
1;
