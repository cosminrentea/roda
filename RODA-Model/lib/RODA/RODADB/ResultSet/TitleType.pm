use utf8;

package RODA::RODADB::ResultSet::TitleType;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::TitleType - metode specifice prelucrarii tipurilor de titluri

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate corespunzatoare tipurilor de titluri.

=cut

=head1 METODE

=cut

=head2 checktitletype

checktitletype verifica existenta unui tip de titlu (preluat prin valori ale parametrilor de intrare) in baza de date; in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce tipul de titlu in baza de date si apoi returneaza obiectul corespunzator. Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.

Parametrii de intrare:

=over 

=item C<title_type_id>
- cheia primara a tipului din tabelul de tipuri de titluri

=item C<name>
- denumirea tipului de titlu


=back


Criterii de unicitate:

=over

=item
- name (presupunem ca denumirile tipurilor de titluri sunt unice)

=back

=cut


sub checktitletype {
    my ( $self, %params ) = @_;
    my $titletypers;
    
    if ($params{name} && $params{name} ne '' ) {
    	$titletypers = $self->find({ 'lower(me.name)' => lc($params{name})}, );
    	if ($titletypers) {
   			return $titletypers;
    	} else {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        	my $newtitletypers = $self->create(
                                      		   	{
                                        		 name => lc($params{name}),
                                      		   	}
                                               );
        	$guard->commit;
        	return $titletypers;
    	}
    }  
}
1;
