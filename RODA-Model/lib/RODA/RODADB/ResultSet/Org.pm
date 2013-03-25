use utf8;

package RODA::RODADB::ResultSet::Org;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

=head1 NUME

RODA::RODADB::ResultSet::Org

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE



=cut

sub checkorg {
    my ( $self, %params ) = @_;

#Adaugarea unei organizatii in baza de date are loc doar daca aceasta nu exista deja.  
#Presupunem ca denumirea completa a unei organizatii o identifica unic pe aceasta.

    my $orgexist = $self->result_source->schema()->resultset('Org')->search(
                                                                            {
                                                                            'upper(me.fullname)' => uc( $params{fullname} ),                                                                          
                                                                            });


    if ( $orgexist->count == 1 ) {
        return $orgexist->first;
    }

    #Daca organizatia nu a fost gasita, o inseram. 
    #Inseram mai intai organizatia, iar apoi informatiile asociate acesteia (email-uri, telefoane etc.)
    my $insertorg;
    if ( $params{prefix} && $params{prefix} ne '' ) {
        my $prefixrs = $self->result_source->schema()->resultset('OrgPrefix')->checkorgprefix(prefix => $params{prefix});
        if ($prefixrs) {
            $insertorg->{org_prefix_id} = $prefixrs->id;
        }
    }
    if ( $params{sufix} && $params{sufix} ne '' ) {
        my $suffixrs = $self->result_source->schema()->resultset('OrgSufix')->checkorgsufix(sufix => $params{sufix});
        if ($suffixrs) {
            $insertorg->{org_sufix_id} = $suffixrs->id;
        }
    }
    $insertorg->{name} = ucfirst( $params{name});
    $insertorg->{fullname} = ucfirst( $params{fullname});
  
    my $guard = $self->result_source->schema()->txn_scope_guard;
    my $orgrs = $self->create($insertorg);
    if ($orgrs) {
    	if (@{$params{addresses}} > 0) {
        	$orgrs->attach_addresses( addresses=>$params{addresses} );
        }

		if (@{$params{emails}} > 0) {
    		$orgrs->attach_emails(emails => $params{emails} );
		}
		
		if (@{$params{phones}} > 0) {
    		$orgrs->attach_phones( phones => $params{phones} );
		}
		
		if (@{$params{internets}} > 0) {
    		$orgrs->attach_internets(internets => $params{internets} );
		}
    }
               
    $guard->commit;
    return $orgrs;
}
1;
