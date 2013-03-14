use utf8;

package RODA::RODADB::ResultSet::Catalog;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

sub checkcatalog {
    my ( $self, %params ) = @_;

	#Verificarea ca un catalog exista se realizeaza pe baza coloanelor name si parent_id    

 	my $catalogexist = $self->result_source->schema()->resultset('Catalog')->search(
                                                                                   {
                                                                                     'upper(me.name)' => uc( $params{name} ),
                                                                                     'me.parent_id' => $params{parent_id},                                                                                     
                                                                                   } );

    if ( $catalogexist->count == 1 ) {
        return $catalogexist->first;
    }

        
    my $insertcatalog;
   
    $insertcatalog->{name} = ucfirst($params{name});
    $insertcatalog->{parent_id} =  $params{parent_id};
    $insertcatalog->{owner} = $params{owner};
    $insertcatalog->{added} = $params{added};
    $insertcatalog->{sequencenr} = $params{sequencenr};
    $insertcatalog->{description} = $params{description};
    
    my $guard    = $self->result_source->schema()->txn_scope_guard;
    my $catalogrs = $self->create($insertcatalog);
    
    if ($catalogrs) {
    	if (@{$params{studies}} > 0) {
       		$catalogrs->attach_studies( studies=>$params{studies} );
    	}
    }
               
    $guard->commit;
    return $catalogrs;
}
1;
