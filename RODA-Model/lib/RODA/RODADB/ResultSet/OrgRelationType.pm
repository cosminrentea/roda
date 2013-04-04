use utf8;

package RODA::RODADB::ResultSet::OrgRelationType;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::OrgRelationType - metode specifice pentru manipularea tipurilor de relatii dintre organizatii

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate corespunzatoare tipurilor de relatii dintre organizatii.

=cut

=head1 UTILIZARE

	my %moi = (
				relation_type => 'afiliere',                               
               );

	my $orgrelationtype = $roda->dbschema->resultset('OrgRelationType')
	           ->checkorgrelationtype( %moi );

=cut

=head1 METODE

=cut

=head2 checkorgrelationtype

checkorgrelationtype verifica existenta unui tip de relatie intre organizatii in baza de date (pe baza parametrilor furnizati); daca exista, returneaza obiectul respectiv, altfel introduce si returneaza obiectul corespunzator. 

Parametrii de intrare:

=over 

=item C<relation_type_id>
- cheia primara a unui tip de relatie intre organizatii din tabelul ce contine aceste tipuri

=item C<relation_type>
- denumirea tipului relatiei curente

=back


Criterii de unicitate:

=over

=item
- relation_type 

=back



=cut


sub checkorgrelationtype {
    my ( $self, %params ) = @_;
    my $orgrelationrs;
    
    if ($params{relation_type} && $params{relation_type} ne '' ) {
    	$orgrelationrs = $self->find({ 'me.name' => lc($params{relation_type})}, );
    	if ($orgrelationrs) {
   			return $orgrelationrs;
    	} else {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        	my $neworgrelationrs = $self->create(
                                      		   	{
                                        		 name => lc($params{relation_type}),
                                      		   	}
                                               );
        	$guard->commit;
        	return $neworgrelationrs;
    	}
    }  
}
1;
