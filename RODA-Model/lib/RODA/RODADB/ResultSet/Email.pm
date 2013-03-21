use utf8;

package RODA::RODADB::ResultSet::Email;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::Email - metode specifice pentru manipularea emailurilor

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip email.

=cut

=head 1 METODE

=cut

=head2 checkemail

verifica existenta unui email in baza de date, daca exista returneaza obiectul respectiv, daca nu, il introduce si returneaza obiectul corespunzator


Paramtetrii de intrare:

=over 

=item C<email>
- adresa de e-mail

=back


=cut


sub checkemail {
    my ( $self, %params ) = @_;

   my $emailrs = $self->search({email => $params{email}});
   if ($emailrs->count == 1) {
   		return $emailrs->single;
   } 
   
   if ($params{email} && $params{email} ne '' ) {
   		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        my $newemailrs = $self->create(
                                      {
                                        email => $params{email},
                                      }
        );
        $guard->commit;
        return $newemailrs;
    }
}
1;
