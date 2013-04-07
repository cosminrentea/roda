use utf8;

package RODA::RODADB::ResultSet::Phone;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::Phone - metode specifice pentru manipularea numerelor de telefon

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de numere de telefon.

=cut

=head1 METODE

=cut

=head2 checkphone

verifica existenta unui numar de telefon in baza de date; daca acesta exista este returnat obiectul respectiv, altfel, metoda il introduce in tabel si returneaza obiectul corespunzator.


Parametrii de intrare:

=over 

=item C<phone>
- numarul de telefon

=item C<phone_type>
- tipul numarului de telefon

=back

Criterii de unicitate:

=over

=item
- phone 

=back

=cut

sub checkphone {
    my ( $self, %params ) = @_;

   my $phoners = $self->search({phone => $params{phone}, phone_type => $params{phone_type}});
   if ($phoners->count == 1) {
   		return $phoners->single;
   } 
   
   if ($params{phone} && $params{phone} ne '' ) {
   		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        my $newphoners = $self->create(
                                      {
                                        phone => $params{phone},
                                        phone_type => $params{phone_type},
                                      }
        );
        $guard->commit;
        return $newphoners;
    }
}
1;
