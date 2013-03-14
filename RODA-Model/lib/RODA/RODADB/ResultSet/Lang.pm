use utf8;

package RODA::RODADB::ResultSet::Lang;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

sub checklanguage {
    my ( $self, %params ) = @_;

    #daca avem parametri corecti
    if ( $params{id} && $params{nume} ) {
        my $insl = $self->find_or_create(
                                          {
                                             id   => lc($params{id}),
                                             name => lc($params{nume}),
                                          },
                                          { key => 'primary' }
        );
        return $insl;
    }

    #daca nu avem decat id-ul sau numele, vedem ce facem in continuare
}

#Verificarea unei limbi, daca avem numele sau.
sub checklangname {
    my ( $self, %params ) = @_;
    my $langrs;
    
    if ($params{name} && $params{name} ne '' ) {
    	$langrs = $self->find({ 'lower(me.name)' => lc($params{name})}, );
    	if ($langrs) {
   			return $langrs;
    	} else {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        	my $newlangrs = $self->create(
                                      	   {
                                      	   	  id => substr(lc($params{name}), 1, 2),
                                              name => lc($params{name}),
                                      	   }
                                         );
        	$guard->commit;
        	return $langrs;
    	}
    }  
}
1;
