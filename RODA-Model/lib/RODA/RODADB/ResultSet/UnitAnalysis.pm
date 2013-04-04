use utf8;

package RODA::RODADB::ResultSet::UnitAnalysis;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::UnitAnalysis - metode specifice prelucrarii unitatilor de analiza

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip unitate de analiza.

=cut

=head1 METODE

=cut

=head2 check_unit_analysis

check_unit_analysis verifica existenta unei unitati de analiza (preluate prin valori ale parametrilor de intrare) in baza de date; in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce unitatea de analiza in baza de date si apoi returneaza obiectul corespunzator. Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.

Parametrii de intrare:

=over 

=item C<unit_analysis_id>
- cheia primara a unitatii din tabelul de unitati de analiza

=item C<name>
- denumirea unitatii de analiza

=item C<description>
- descrierea unitatii de analiza

=back


Criterii de unicitate:

=over

=item
- name (presupunem ca denumirile unitatilor de analiza sunt unice)

=back

=cut


sub check_unit_analysis {
    my ( $self, %params ) = @_;
    my $unit_analysis_rs;
    
    if ($params{name} && $params{name} ne '' ) {
    	$unit_analysis_rs = $self->search({name => $params{name}}, );
    	if ($unit_analysis_rs -> count == 1) {
   			return $unit_analysis_rs -> single;
    	} else {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        	my $newUnitAnalysis_rs = $self->create(
                                      		   	{
                                        		 name => lc($params{name}),
                                        		 description => $params{description},
                                      		   	}
                                               );
        	$guard->commit;
        	return $newUnitAnalysis_rs;
    	}
    }  
}
1;
