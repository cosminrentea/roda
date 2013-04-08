use utf8;

package RODA::RODADB::ResultSet::Groups;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::Groups - metode specifice prelucrarii grupurilor de utilizatori

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip grup de utilizatori.

=cut

=head1 METODE

=cut

=head2 checkgroup

checkgroup verifica existenta unui grup (preluat prin valori ale parametrilor de intrare) in baza de date; in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce grupul in baza de date si apoi returneaza obiectul corespunzator. Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.

Parametrii de intrare:

=over 

=item C<id>
- cheia primara a grupului

=item C<name>
- denumirea grupului

=back


Criterii de unicitate:

=over

=item
- name

=back

=cut

sub checkgroup {
   # TODO
}

=head2 addgroup

addgroup adauga un grup de utilizatori (preluat prin valori ale parametrilor de intrare) in baza de date.

Parametrii de intrare:

=over 

=item C<name>
- denumirea grupului


=back

=cut

sub addgroup {
   # TODO
}
1;
