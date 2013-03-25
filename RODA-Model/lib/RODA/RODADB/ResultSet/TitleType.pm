use utf8;

package RODA::RODADB::ResultSet::TitleType;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::TitleType

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE



=cut


sub checktitletype {
    my ( $self, %params ) = @_;
    my $titletypers;
    
    if ($params{name} && $params{name} ne '' ) {
    	$titletypers = $self->find({ 'lower(me.name)' => lc($params{name})}, );
    	if ($titletypers) {
   			return $titletypers;
    	} else {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        	my $newtitletypers = $self->create(
                                      		   	{
                                        		 name => lc($params{name}),
                                      		   	}
                                               );
        	$guard->commit;
        	return $titletypers;
    	}
    }  
}
1;
