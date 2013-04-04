use utf8;

package RODA::RODADB::ResultSet::InstancePersonAssoc;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::InstancePersonAssoc - metode specifice pentru manipularea tipurilor de asociere intre persoane si instante

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip asociere intre instanta si persoana.

=cut

=head1 METODE

=cut

=head2 checkassoctype

checkassoctype verifica existenta unui tip de asociere intre persoana si instanta (preluat prin valori ale parametrilor de intrare), verifica daca acesta exista in baza de date; in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce conceptul in baza de date si apoi returneaza obiectul corespunzator. Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.

Parametrii de intrare:

=over 

=item C<assoc_id>
- cheia primara a tipului de asociere in tabelul instance_person_assoc

=item C<assoc_name>
- denumirea tipului de asociere intre instanta si persoana

=item C<assoc_description>
- descrierea tipului de asociere intre instanta si persoana

=back

Criterii de unicitate:

=over

=item
- assoc_name (presupunem ca denumirile tipurilor de asociere sunt unice)

=back


=cut

sub checkassoctype {
    my ( $self, %params ) = @_;
    my $assoctypers;
    
    # Verificarea unicitatii unui tip de asociere intre instanta si persoana 
    # se realizeaza dupa numele tipului de asociere.
    
    if ($params{assoc_name} && $params{assoc_name} ne '' ) {
    	$assoctypers = $self->search({assoc_name => $params{assoc_name}});
    	if ($assoctypers -> count == 1) {
   			return $assoctypers -> single;
    	} else {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        	my $newassoctypers = $self->create(
                                      		   	{
                                        		 assoc_name => $params{assoc_name},
                                        		 assoc_description => $params{assoc_description},
                                      		   	}
                                               );
        	$guard->commit;
        	return $newassoctypers;
    	}
    }  
}
1;
