use utf8;

package RODA::RODADB::ResultSet::StudyDescr;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

=head1 NUME

RODA::RODADB::ResultSet::StudyDescr - metode specifice pentru manipularea descrierilor de studii

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip descriere de studiu.

=cut

=head1 METODE

=cut

=head2 checkstudydescr

checkstudydescr verifica existenta unei descrieri de studiu (preluat prin valori ale parametrilor de intrare), verifica daca aceasta exista in baza de date; in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce descrierea in baza de date si apoi returneaza obiectul corespunzator. Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.

Parametrii de intrare:

=over 

=item C<study_id>
- cheia primara a studiului pentru care este furnizata descrierea curenta

=item C<lang>
- limba in care este furnizata descrierea curenta a studiului; daca limba nu exista, ea va fi adaugata in baza de date

=item C<title_type>
- tipul titlului studiului in descrierea curenta

=item C<title>
- titlul studiului in descrierea curenta

=item C<abstract>
- rezumatul studiului

=item C<grant_details>
- detaliile asociate grantului in cadrul caruia se desfasoara studiul curent


=back


Criterii de unicitate:

=over

=item
- title_type + title + lang_id + datestart  

=back

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
