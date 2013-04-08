use utf8;

package RODA::RODADB::ResultSet::CmsLayoutGroup;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::CmsLayoutGroup - metode specifice prelucrarii grupurilot de layout din CMS

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip grup de layout.

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

=item C<description>
- descrierea grupului

=back


Criterii de unicitate:

=over

=item
- name (presupunem ca denumirile grupurilor sunt unice)

=back

=cut

sub checkgroup {
   # TODO
}
1;
