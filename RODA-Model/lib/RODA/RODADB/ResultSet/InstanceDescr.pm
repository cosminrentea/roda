use utf8;

package RODA::RODADB::ResultSet::InstanceDescr;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

=head1 NUME

RODA::RODADB::ResultSet::InstanceDescr
=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE



=cut

sub checkinstancedescr {
    my ( $self, %params ) = @_;
    my $langId;

	#Verificare lang_id; daca nu exista, este inserat mai intai in tabelul lang
    if ( $params{lang} && $params{lang} ne '' ) {
        my $langrs = $self->result_source->schema()->resultset('Lang')->checklangname(name => lc($params{lang}));
        if ($langrs) {
        	$langId = $langrs->id;
        }
    }

	if ($params{title} && $params{title} ne '') {
		#Adaugarea unei descrieri a unei instante din baza de date are loc doar daca aceasta nu exista deja.
		#Presupunem ca o descriere a unei instante este unic identificata prin atributul title. 
		
    	my $instancedescrexist = $self->result_source->schema()->resultset('InstanceDescr')
    							->search(
                                         {
                                           'lower(me.title)' => lc($params{title}),
       									 }
                                        );

    	if ( $instancedescrexist->count == 1 ) {
    		return $instancedescrexist -> first;
    	}

		#Daca descrierea instantei nu a fost gasita, o inseram. 
    	my $insertinstancedescr;
   
    	$insertinstancedescr->{lang_id} = $langId;
    	$insertinstancedescr->{instance_id} = $params{instance_id};
  	    $insertinstancedescr->{title} = ucfirst($params{title});
  	    $insertinstancedescr->{weighting} = $params{weighting};
  	    $insertinstancedescr->{research_instrument} = $params{research_instrument};
  	    $insertinstancedescr->{scope} = $params{scope};
  	    $insertinstancedescr->{universe} = $params{universe};
  	    $insertinstancedescr->{abstract} = $params{abstract};
  
    	my $guard = $self->result_source->schema()->txn_scope_guard;
    
    	my $instancedescrrs = $self->create($insertinstancedescr);
    
        $guard->commit;
         
    	return $instancedescrrs;
	}
}
1;
