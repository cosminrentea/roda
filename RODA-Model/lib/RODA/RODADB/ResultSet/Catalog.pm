use utf8;

package RODA::RODADB::ResultSet::Catalog;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

=head1 NUME

RODA::RODADB::ResultSet::Catalog - metode specifice prelucrarii cataloagelor

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip catalog.

=cut

=head1 UTILIZARE

	my %moi = (name => 'Catalog1',
               owner => 1,
               added => $dtf -> 
                    format_datetime(DateTime -> now),                   
               studies => [
                           {
                            id => 1,
                           },
                           {
                            id => 5,
                           }
                          ],                       
	);

	my $catalog = $roda->dbschema->resultset('Catalog')
	                     ->checkcatalog( %moi );

=cut

=head1 METODE

=cut

=head2 checkcatalog

checkcatalog verifica existenta unui catalog (preluat prin combinatii ale parametrilor de intrare), verifica daca acesta exista in baza de date; in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce catalogul in baza de date si apoi returneaza obiectul corespunzator. Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.

Parametrii de intrare:

=over 

=item C<catalog_id>
- cheia primara a catalogului din tabelul de cataloage

=item C<name>
- numele catalogului

=item C<parent_id>
- cheia primara a catalogului parinte, din tabelul de cataloage

=item C<owner>
- cheia primara a utilizatorului caruia ii apartine catalogul curent

=item C<added>
- data si timpul la care a fost adaugat catalogul curent in baza de date

=item C<sequencenr>
- numarul de ordine al catalogului in cadrul parintelui sau

=item C<studies>
- lista de studii care apartin catalogului curent; un element al acestei liste poate contine atat codul unui studiu, cat si informatiile complete ale acestuia

=back


Criterii de unicitate:

=over

=item
- name + parent_id (doua subcataloage ale unui catalog parinte nu pot avea acelasi nume)

=back

=cut

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
