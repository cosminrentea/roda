use utf8;

package RODA::RODADB::ResultSet::Study;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

=head1 NUME

RODA::RODADB::ResultSet::Study  - metode specifice pentru manipularea inregistrarilor despre studii

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip studiu.

=cut

=head1 UTILIZARE

	my $dtf = $roda->dbschema->storage->datetime_parser;
	
	my %moi = (datestart => $dtf -> format_datetime(
	                   DateTime->new(year => 2002, 
	                                 month => 10, 
	                                 day => 1)),
               insertion_status => 1,
               added_by => 1,
               added => $dtf -> format_datetime(
                       DateTime->now),
               can_digitize => 'true',
               can_use_anonymous => 'false',  
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
                            assoc_name=>'finantator'}],                          
               );

	my $study = $roda->dbschema->resultset('Study')
	                ->checkstudy( %moi );
	
=cut

=head1 METODE

=cut

=head2 checkstudy

checkstudy introduce un studiu (preluat prin valori ale parametrilor de intrare) 
in baza de date si returneaza obiectul corespunzator. Unicitatea unui studiu este verificata la nivelul descrierii sale (StudyDescr).
 Parametrii de intrare formeaza o structura de date sub forma unui hash conform exemplului de mai sus. 

Parametrii de intrare:

=over 

=item C<study_id>
- cheia primara a studiului din tabelul de studii

=item C<datestart>
- data de inceput a desfasurarii studiului curent

=item C<dateend>
- data de final a desfasurarii studiului curent

=item C<added_by>
- cheia primara a utilizatorului caruia ii apartine studiului curent

=item C<added>
- data si timpul la care a fost adaugata studiul curent in baza de date

=item C<insertion_status>
- pasul din wizardul de introducere a metadatelor; atunci cand introducerea se face prin wizard, fiecare pas trebuie salvat in baza de date.

=item C<orgs>
- lista de organizatii aflate in relatie cu studiul curent; un element al acestei liste contine codul unei organizatii si codul asocierii care exista cu organizatia respectiva. O organizatie poate fi specificata atat prin codul sau, cat si prin informatiile complete ale acesteia. La randul ei, daca organizatia nu exista, va fi introdusa in baza de date.

=item C<persons>
- lista de persoane aflate in relatie cu studiul curent; un element al acestei liste contine codul unei persoane si codul asocierii care exista cu persoana respectiva. O persoana poate fi specificata atat prin codul sau, cat si prin informatiile complete ale acesteia. La randul ei, daca persoana nu exista, va fi introdusa in baza de date.


=back


Criterii de unicitate:

=over

=item
- N/A

=back


=cut

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
