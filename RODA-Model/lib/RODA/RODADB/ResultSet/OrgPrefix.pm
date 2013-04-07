use utf8;

package RODA::RODADB::ResultSet::OrgPrefix;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::OrgPrefix - metode specifice pentru manipularea prefixelor organizatiilor

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip prefix al organizatiilor.

=cut

=head1 UTILIZARE

  my $prefix = $roda->dbschema->resultset('OrgPrefix')
                         ->checkorgprefix( 
                                          id => '100', 
                                          name => 'SC' 
                                         );

  my $prefix = $roda->dbschema->resultset('OrgPrefix')
                         ->checkorgprefixname( 
                                              name => 'SC' 
                                             );

=cut


=head1 METODE

=cut

=head2 checkorgprefix

Primeste ca parametri de intrare id-ul si numele prefixului de organizatie, daca gaseste in tabel o intrare corespunzatoare, returneaza obiectul respectiv, daca nu, creaza intrarea si returneaza obiectul atasat.

Parametrii de intrare:

=over 

=item C<id>
- cheia primara a prefixului de organizatie

=item C<name>
- denumirea prefixului de organizatie

=back


Criterii de unicitate:

=over

=item
- name

=back


=cut

sub checkorgprefix {
    my ( $self, %params ) = @_;
    my $orgprefixrs;
    
    if ($params{prefix} && $params{prefix} ne '' ) {
    	$orgprefixrs = $self->find({ 'lower(me.name)' => lc($params{prefix})}, );
    	if ($orgprefixrs) {
   			return $orgprefixrs;
    	} else {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        	my $neworgprefixrs = $self->create(
                                      		   	{
                                        		 name => lc($params{prefix}),
                                      		   	}
                                               );
        	$guard->commit;
        	return $neworgprefixrs;
    	}
    }  
}

=head2 checkorgprefixname

Primeste ca parametru de intrare numele prefixului de organizatie, daca gaseste in tabel o intrare corespunzatoare, returneaza obiectul respectiv, daca nu, creaza intrarea si returneaza obiectul atasat.

Parametrii de intrare:

=over 

=item C<name>
- denumirea prefixului de organizatie

=back


Criterii de unicitate:

=over

=item
- name

=back

=cut

1;
