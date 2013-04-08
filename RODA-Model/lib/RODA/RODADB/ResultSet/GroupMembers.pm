use utf8;

package RODA::RODADB::ResultSet::GroupMembers;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::GroupMembers - metode specifice prelucrarii informatiilor referitoare la membrii grupurilor de utilizatori

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip membru al unui grup de utilizatori.

=cut

=head1 METODE

=cut

=head2 add_group_member

add_group_member adauga un nou membru al unui grup de utilizatori in baza de date.

Parametrii de intrare:

=over 

=item C<username>
- numele utilizatorului

=item C<group>
- grupul din care face parte membrul

=back


=cut

sub add_group_member {
  # TODO
}

=head2 check_group_member

check_group_member verifica existenta unui membru al unui grup (preluat prin valori ale parametrilor de intrare) in baza de date; in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce membrul in baza de date si apoi returneaza obiectul corespunzator. Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.

Parametrii de intrare:

=over 

=item C<id>
- cheia primara a membrului unui grup de utilizatori

=item C<username>
- numele utilizatorului 

=item C<group>
- grupul din care face parte utilizatorul

=back


Criterii de unicitate:

=over

=item
- username + group

=back

=cut

sub check_group_member {
   # TODO
}

1;
