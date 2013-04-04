use utf8;

package RODA::RODADB::ResultSet::TimeMethType;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::TimeMethType - metode specifice prelucrarii metodelor temporale

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip metoda temporala.

=cut

=head1 METODE

=cut

=head2 check_time_meth

check_time_meth verifica existenta unei metode temporale (preluate prin valori ale parametrilor de intrare) in baza de date; in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce metoda temporala in baza de date si apoi returneaza obiectul corespunzator. Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.

Parametrii de intrare:

=over 

=item C<time_meth_id>
- cheia primara a metodei din tabelul de metode temporale

=item C<name>
- denumirea metodei temporale

=item C<description>
- descrierea metodei temporale

=back


Criterii de unicitate:

=over

=item
- name (presupunem ca denumirile metodelor temporale sunt unice)

=back

=cut


sub check_time_meth {
    my ( $self, %params ) = @_;
    my $time_meth_rs;
    
    if ($params{name} && $params{name} ne '' ) {
    	$time_meth_rs = $self->find({ 'lower(me.name)' => lc($params{name})}, );
    	if ($time_meth_rs) {
   			return $time_meth_rs;
    	} else {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        	my $newTimeMeth_rs = $self->create(
                                      		   	{
                                        		 name => lc($params{name}),
                                        		 description => $params{description},
                                      		   	}
                                               );
        	$guard->commit;
        	return $newTimeMeth_rs;
    	}
    }  
}
1;
