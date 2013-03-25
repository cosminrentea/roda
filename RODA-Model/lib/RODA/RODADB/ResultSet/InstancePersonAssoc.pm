use utf8;

package RODA::RODADB::ResultSet::InstancePersonAssoc;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::InstancePersonAssoc

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE



=cut

sub checkassoctype {
    my ( $self, %params ) = @_;
    my $assoctypers;
    
    # Verificarea unicitatii unui tip de asociere intre instanta si persoana 
    # se realizeaza dupa numele tipului de asociere.
    
    if ($params{assoc_name} && $params{assoc_name} ne '' ) {
    	$assoctypers = $self->search({assoc_name => $params{assoc_name}});
    	if ($assoctypers -> count == 1) {
   			return $assoctypers -> single;
    	} else {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        	my $newassoctypers = $self->create(
                                      		   	{
                                        		 assoc_name => $params{assoc_name},
                                        		 assoc_description => $params{assoc_description},
                                      		   	}
                                               );
        	$guard->commit;
        	return $newassoctypers;
    	}
    }  
}
1;
