use utf8;

package RODA::RODADB::ResultSet::UserProfile;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::UserProfile - metode specifice pentru manipularea profilurilor utilizatorilor

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip profil al unui utilizator.

=cut

=head1 METODE

=cut

=head2 check_user_profile

verifica existenta unui profil utilizator in baza de date, daca exista returneaza obiectul respectiv, daca nu, il introduce si returneaza obiectul corespunzator


Parametrii de intrare:

=over 

=item C<fname>
- prenumele utilizatorului

=item C<lname>
- numele de familie al utilizatorului

=item C<email>
- adresa de email a utilizatorului

=back

Criterii de unicitate:

=over

=item
- fname + lname + email 

=back

=cut


sub check_user_profile {
    # TODO
}
1;
