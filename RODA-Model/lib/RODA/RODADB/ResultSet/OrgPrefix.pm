use utf8;

package RODA::RODADB::ResultSet::OrgPrefix;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::OrgPrefix

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE



=cut

sub checkorgprefix {
    my ( $self, %params ) = @_;
    my $orgprefixrs;
    
    if ($params{prefix} && $params{prefix} ne '' ) {
    	$orgprefixrs = $self->find({ 'lower(me.name)' => lc($params{prefix})}, );
    	if ($orgprefixrs) {
   			return $orgprefixrs;
    	} else {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        	my $neworgprefixrs = $self->create(
                                      		   	{
                                        		 name => lc($params{prefix}),
                                      		   	}
                                               );
        	$guard->commit;
        	return $neworgprefixrs;
    	}
    }  
}
1;
