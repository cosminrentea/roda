use utf8;

package RODA::RODADB::ResultSet::Study;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

sub checkstudy {
    my ( $self, %params ) = @_;


#Verificarea ca un studiu exista deja o facem pentru StudyDescr
#Deocamdata, metoda realizeaza doar inserarea unui studiu (la nivelul informatiilor din tabelul study)    
    
    my $insertstudy;
   
    $insertstudy->{datestart} = $params{datestart};
    $insertstudy->{dateend} =  $params{dateend};
    $insertstudy->{insertion_status} = $params{insertion_status};
    $insertstudy->{added_by} = $params{added_by};
    $insertstudy->{added} = $params{added};
    $insertstudy->{can_digitize} = $params{can_digitize};
    $insertstudy->{can_use_anonymous} = $params{can_use_anonymous};
    
    my $guard   = $self->result_source->schema()->txn_scope_guard;
    my $studyrs = $self->create($insertstudy);
    
    if ($studyrs) {
    	
    	if (@{$params{orgs}} > 0) {
        	$studyrs->attach_organizations( orgs=>$params{orgs} );
        }
        
        if (@{$params{persons}} > 0) {
        	$studyrs->attach_persons( persons=>$params{persons} );
        }
    }
               
    $guard->commit;
    return $studyrs;
}

1;
