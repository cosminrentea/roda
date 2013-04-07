use utf8;

package RODA::RODADB::ResultSet::Instance;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

=head1 NUME

RODA::RODADB::ResultSet::Instance - metode specifice pentru manipularea inregistrarilor despre instante

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip instanta.

=cut

=head1 UTILIZARE

	my $dtf = $roda->dbschema->storage->datetime_parser;
	
	my %moi = (datestart => $dtf -> format_datetime(
	                DateTime->new(year => 2012, 
	                              month => 12, 
	                              day => 1)),
	           study_id => '12',
               insertion_status => 1,
               added_by => 1,
               added => $dtf -> format_datetime(
                    DateTime->now),
               version => '1',
               raw_data => 'false',
               raw_metadata => 'false',
               unit_analysis => 'individ',
               time_meth => 'met temp 1',  
               orgs => [{
               	         id =>'1', 
               	         assoc_name=>'producator'
                        },
                        {
                         id => '2', 
                         assoc_name => 'investigator principal'
                        }], 
               persons => [{
               	            id =>'1', 
               	            assoc_name=>'finantator'
                           }],  
               variables => [{
               	              label => 'Categorie de varsta', 
               	              type => 1, 
               	              order_in_instance => 2, 
               				  type_edited_text => 0, 
               				  type_edited_number => 0, 
               				  type_selection => 1,
                             },
                             {
                              label => 'Mediu', 
                              type => 1, 
                              order_in_instance => 1, 
                              type_edited_text => 0, 
                              type_edited_number => 0, 
                              type_selection => 1,
                              other_statistics => [{
                              	   name => 'medie', 
                              	   value => 100, 
                              	   description => 'valoare medie'},] 	
                             }],
               forms => [{
               	          order_in_instance => 2, 
	                      operator => {fname => 'Ion', 
	                                   lname => 'Popescu'},	
	                      edited_text_vars => [{
	                                    variable_id => '16', 
	                                    text => 'raspuns la var 16'
	                                    }],
	                      selection_vars => [{
	                                    variable_id => '107', 
	                                    item_id => '70'
	                                    }]					  
                         }],   	                                     
              );

	my $instance = $roda->dbschema->resultset('Instance')
	                   ->checkinstance( %moi );
	
=cut

=head1 METODE

=cut

=head2 checkinstance

checkinstance verifica existenta unei instante (preluata prin valori ale parametrilor de intrare sau combinatii ale acestora), verifica daca acesta exista in baza de date; in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce instanta in baza de date si apoi returneaza obiectul corespunzator. Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.
 Parametrii de intrare formeaza o structura de date sub forma unui hash conform exemplului de mai sus. 

Parametrii de intrare:

=over 

=item C<instance_id>
- cheia primara a instantei din tabelul de instante

=item C<study_id>
- cheia primara a studiului din care face parte instanta

=item C<datestart>
- data de inceput a desfasurarii instantei curente

=item C<dateend>
- data de final a desfasurarii instantei curente

=item C<added_by>
- cheia primara a utilizatorului caruia ii apartine instanta curenta

=item C<added>
- data si timpul la care a fost adaugata instanta curenta in baza de date

=item C<version>
- versiunea instantei

=item C<unit_analysis>
- denumirea unitatii de analiza specifice instantei curente; daca unitatea respectiva nu exista, va fi introdusa in baza de date.

=item C<time_meth>
- denumirea metodei temporale care se aplica instantei curente; daca metoda temporala respectiva nu exista, va fi introdusa in baza de date.

=item C<insertion_status>
- pasul din wizardul de introducere a metadatelor; atunci cand introducerea se face prin wizard, fiecare pas trebuie salvat in baza de date.

=item C<raw_data>
- parametru boolean ce indica daca datele sunt in forma digitizata (true) sau in forma de fisiere procesabile/editabile (false)

=item C<raw_metadata>
- parametru boolean ce indica daca metadatele sunt in forma digitizata (true) sau in forma de fisiere procesabile/editabile (false)

=item C<orgs>
- lista de organizatii aflate in relatie cu instanta curenta; un element al acestei liste contine codul unei organizatii si codul asocierii care exista cu organizatia respectiva. O organizatie poate fi specificata atat prin codul sau, cat si prin informatiile complete ale acesteia. La randul ei, daca organizatia nu exista, va fi introdusa in baza de date.

=item C<persons>
- lista de persoane aflate in relatie cu instanta curenta; un element al acestei liste contine codul unei persoane si codul asocierii care exista cu persoana respectiva. O persoana poate fi specificata atat prin codul sau, cat si prin informatiile complete ale acesteia. La randul ei, daca persoana nu exista, va fi introdusa in baza de date.

=item C<topics>
- lista de topicuri asociate instantei curente; un element al acestei liste contine codul unui topic. Un topic poate fi specificat atat prin codul sau, cat si prin denumirea sa. La randul lui, daca topicul nu exista, va fi introdus in baza de date.

=item C<keywords>
- lista de cuvinte cheie asociate instantei curente; un element al acestei liste contine codul unui cuvant cheie. Un cuvant cheie poate fi specificat atat prin codul sau, cat si prin denumirea sa. La randul lui, daca topicul nu exista, va fi introdus in baza de date.

=item C<variables>
- lista de variabile asociate instantei curente; un element al acestei liste contine informatiile specifice unei variabile. La randul ei, variabila va fi introdusa in baza de date.

=item C<forms>
- lista de formulare asociate instantei curente; un element al acestei liste contine informatiile specifice unui formular. La randul lui, formularul va fi introdus in baza de date.

=back


Criterii de unicitate:

=over

=item
- name + parent_id (doua subcataloage ale unui catalog parinte nu pot avea acelasi nume)

=back


=cut

sub checkinstance {
    my ( $self, %params ) = @_;


#Verificarea unicitatii unei instante se realizeaza dupa atributele (study_id, datestart)    
    
    my $instanceexist = $self->result_source->schema()->resultset('Instance')->search(
                                                                                   {
                                                                                     study_id => $params{study_id},
                                                                                     datestart => $params{datestart},                                                                                     
                                                                                   } );

    if ( $instanceexist->count == 1 ) {
        return $instanceexist->first;
    }
    
    my $insertinstance;
  
   	if ( $params{unit_analysis} && $params{unit_analysis} ne '' ) {   		
        my $unit_analysis_rs = $self->result_source->schema()->resultset('UnitAnalysis')->check_unit_analysis(name => $params{unit_analysis});
        if ($unit_analysis_rs) {
            $insertinstance->{unit_analysis_id} = $unit_analysis_rs->id;
        }
    }
    
    if ( $params{time_meth} && $params{time_meth} ne '' ) {
        my $time_meth_rs = $self->result_source->schema()->resultset('TimeMethType')->check_time_meth(name => $params{time_meth});
        if ($time_meth_rs) {
            $insertinstance->{time_meth_id} = $time_meth_rs->id;
        }
    }
   
    $insertinstance->{study_id} = $params{study_id};
    $insertinstance->{datestart} = $params{datestart};
    $insertinstance->{dateend} =  $params{dateend};
    $insertinstance->{version} = $params{version};
    $insertinstance->{insertion_status} = $params{insertion_status};
    $insertinstance->{raw_data} = $params{raw_data};
    $insertinstance->{raw_metadata} = $params{raw_metadata};
    $insertinstance->{added_by} = $params{added_by};
    $insertinstance->{added} = $params{added};

        
    my $guard   = $self->result_source->schema()->txn_scope_guard;
    my $instancers = $self->create($insertinstance);
    
    if ($instancers) {
    	
    	if ($params{orgs} && @{$params{orgs}} > 0) {
        	$instancers->attach_organizations( orgs=>$params{orgs} );
        }
        
        if ($params{persons} && @{$params{persons}} > 0) {
        	$instancers->attach_persons( persons=>$params{persons} );
        }
        
        if ($params{topics} && @{$params{topics}} > 0) {
        	$instancers->attach_topics( topics=>$params{topics} );
        }
        
        if ($params{keywords} && @{$params{keywords}} > 0) {
        	$instancers->attach_keywords( keywords=>$params{keywords} );
        }
        
        if ($params{variables} && @{$params{variables}} > 0) {
        	$instancers->attach_variables( variables=>$params{variables} );
        }
        
        if ($params{forms} && @{$params{forms}} > 0) {
        	$instancers->attach_forms( forms=>$params{forms} );
        }
    }
               
    $guard->commit;
    return $instancers;
}

1;
