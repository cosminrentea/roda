use utf8;

package RODA::RODADB::ResultSet::FileAcl;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::FileAcl - metode specifice pentru manipularea listelor de depturi (acl) asociate fisierelor

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip acl asociat unui fisier.

=cut

=head1 METODE

=cut

=head2 check_file_acl

verifica existenta unui acl asociat unui fisier in baza de date, daca exista returneaza obiectul respectiv, daca nu, il introduce si returneaza obiectul corespunzator.


Parametrii de intrare:

=over 

=item C<file_id>
- cheia primara a fisierului caruia ii este asociat un acl

=item C<aro_id>
- cheia primara a obiectului caruia ii este acordat dreptul respectiv

=item C<aro_type>
- tipul obiectului caruia ii este acordat dreptul respectiv

=item C<read>
- parametru boolean, ce specifica daca este acordat drept de citire asupra fisierului

=item C<update>
- parametru boolean, ce specifica daca este acordat drept de modificare asupra fisierului

=item C<delete>
- parametru boolean, ce specifica daca este acordat drept de stergere asupra fisierului

=back

Criterii de unicitate:

=over

=item
- N/A  

=back

=cut


sub check_file_acl {
    # TODO
}
1;
