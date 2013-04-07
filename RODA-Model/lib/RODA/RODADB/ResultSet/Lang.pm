use utf8;

package RODA::RODADB::ResultSet::Lang;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::Lang - metode specifice pentru manipularea inregistrarilor despre limba

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare pentru manipularea inregistrarilor despre limba.

=cut



=head1 METODE

=cut

=head2 checklanguage

verifica existenta unei limbi dupa nume si id. Utila in special pentru import si initializarea bazei de date, vrem sa ne asiguram ca limba are acelasi id indiferent de cate ori este refacuta baza de date in timpul dezvoltarii.

Parametrii de intrare:

=over 

=item C<id>
- cheia primara a limbii din tabelul de limbi

=item C<name>
- denumirea limbii

=back


Criterii de unicitate:

=over

=item
- name

=back

=cut

sub checklanguage {
    my ( $self, %params ) = @_;

    #daca avem parametri corecti
    if ( $params{id} && $params{nume} ) {
        my $insl = $self->find_or_create(
                                          {
                                             id   => lc($params{id}),
                                             name => lc($params{nume}),
                                          },
                                          { key => 'primary' }
        );
        return $insl;
    }

    #daca nu avem decat id-ul sau numele, vedem ce facem in continuare
}

#Verificarea unei limbi, daca avem numele sau.

=head2 checklangname

verifica existenta unei limbi dupa nume. Numele limbii ar trebui sa fie unic. Daca exista, returneaza obiectul respectiv, daca nu, il introduce si returneaza obiectul

Parametrii de intrare:

=over 

=item C<name>
- denumirea limbii

=back


Criterii de unicitate:

=over

=item
- name

=back

=cut


sub checklangname {
    my ( $self, %params ) = @_;
    my $langrs;
    
    if ($params{name} && $params{name} ne '' ) {
    	$langrs = $self->find({ 'lower(me.name)' => lc($params{name})}, );
    	if ($langrs) {
   			return $langrs;
    	} else {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        	my $newlangrs = $self->create(
                                      	   {
                                      	   	  id => substr(lc($params{name}), 1, 2),
                                              name => lc($params{name}),
                                      	   }
                                         );
        	$guard->commit;
        	return $langrs;
    	}
    }  
}
1;
