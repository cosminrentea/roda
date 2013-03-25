use utf8;

package RODA::RODADB::ResultSet::StudyPersonAssoc;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::StudyPersonAssoc

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE



=cut

sub checkassoctype {
    my ( $self, %params ) = @_;
    my $assoctypers;
    
    # Verificarea unicitatii unui tip de asociere intre studiu si persoana 
    # se realizeaza dupa numele tipului de asociere.
    
    if ($params{assoc_name} && $params{assoc_name} ne '' ) {
    	$assoctypers = $self->search({asoc_name => $params{assoc_name}});
    	if ($assoctypers -> count == 1) {
   			return $assoctypers -> single;
    	} else {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        	my $newassoctypers = $self->create(
                                      		   	{
                                        		 asoc_name => $params{assoc_name},
                                        		 asoc_description => $params{assoc_description},
                                      		   	}
                                               );
        	$guard->commit;
        	return $newassoctypers;
    	}
    }  
}
1;
