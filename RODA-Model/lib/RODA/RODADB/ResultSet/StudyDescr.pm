use utf8;

package RODA::RODADB::ResultSet::StudyDescr;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

=head1 NUME

RODA::RODADB::ResultSet::StudyDescr

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE



=cut

sub checkstudydescr {
    my ( $self, %params ) = @_;
    my $titleTypeId;
    my $langId;

    #Verificare title_type_id; daca nu exista, este inserat mai intai in tabelul title_type
    if ( $params{title_type} && $params{title_type} ne '' ) {
        my $titletypers = $self->result_source->schema()->resultset('TitleType')->checktitletype(name => lc($params{title_type}));
        if ($titletypers) {
        	$titleTypeId = $titletypers->id;
        }
    }

	#Verificare lang_id; daca nu exista, este inserat mai intai in tabelul lang
    if ( $params{lang} && $params{lang} ne '' ) {
        my $langrs = $self->result_source->schema()->resultset('Lang')->checklangname(name => lc($params{lang}));
        if ($langrs) {
        	$langId = $langrs->id;
        }
    }

	if ($titleTypeId && $titleTypeId ne '' && $langId && $langId ne '') {
		#Adaugarea unei descrieri a unui studiu din baza de date are loc doar daca aceasta nu exista deja.
		#Presupunem ca o descriere a unui studiu este unic identificata prin: title_type_id, title, lang_id, datestart  
		my $where = 'upper(me.title) = ? AND me.title_type_id = ? AND me.lang_id = ?';
        my @bind  = (uc($params{title}), $titleTypeId, $langId );
		
    	my $studydescrexist = $self->result_source->schema()->resultset('StudyDescr')
    							->search_literal($where, @bind,
                                                 {
                                                 	join => 'study',
       												 '+select' => [ 'study.datestart' ],
                                                     '+as'     => [ 'datestart' ],                                                                           	                                                                          
                                                 }
                                                );

    	if ( $studydescrexist->count >= 1 ) {
    		if ($studydescrexist -> first -> get_column('datestart') eq $params{datestart}) {
        		return $studydescrexist -> first;
    		}
    	}

		#Daca descrierea studiului nu a fost gasita, o inseram. 
    	my $insertstudydescr;
   
    	$insertstudydescr->{lang_id} = $langId;
    	$insertstudydescr->{title_type_id} = $titleTypeId;
    	$insertstudydescr->{study_id} = $params{study_id};
  	    $insertstudydescr->{title} = ucfirst($params{title});
  	    $insertstudydescr->{abstract} = $params{abstract};
  	    $insertstudydescr->{grant_details} = $params{grant_details};
  
    	my $guard = $self->result_source->schema()->txn_scope_guard;
    
    	my $studydescrrs = $self->create($insertstudydescr);
    
        $guard->commit;
         
    	return $studydescrrs;
	}
}
1;
