use utf8;

package RODA::RODADB::ResultSet::PersonRole;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::PersonRole - metode specifice pentru manipularea rolurilor persoanelor in cadrul organizatiilor


=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate corespunzatoare rolurilor persoanelor in cadrul organizatiilor.

=cut

=head1 UTILIZARE

	my %moi = (
	           role => 'manager proiect',                               
               );

	my $orgrelationtype = $roda->dbschema->resultset('PersonRole')
	              ->checkpersonrole( %moi );

=cut

=head1 METODE

=cut

=head2 checkpersonrole

checkpersonrole verifica existenta unui tip de rol intre o persoana si o organizatie in baza de date (pe baza parametrilor furnizati); 
daca exista, returneaza obiectul respectiv, altfel introduce si returneaza obiectul corespunzator. 

Parametrii de intrare:

=over 

=item C<role_id>
- cheia primara a unui tip de rol intre o persoana si o organizatie din tabelul ce contine aceste tipuri de rol (PersonRole)

=item C<role>
- denumirea tipului rolului curent

=back


Criterii de unicitate:

=over

=item
- role 

=back


=cut

sub checkpersonrole {
    my ( $self, %params ) = @_;
    my $personrolers;
    
    if ($params{role} && $params{role} ne '' ) {
    	$personrolers = $self->find({ 'me.name' => lc($params{role})}, );
    	if ($personrolers) {
   			return $personrolers;
    	} else {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        	my $newpersonrolers = $self->create(
                                      		   	{
                                        		 name => lc($params{role}),
                                      		   	}
                                               );
        	$guard->commit;
        	return $newpersonrolers;
    	}
    }  
}
1;
