use utf8;

package RODA::RODADB::ResultSet::CmsLayout;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::CmsLayout - metode specifice prelucrarii informatiilor de layout pentru sistemul CMS

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip layout din sistemul CMS.

=cut

=head1 METODE

=cut

=head2 addlayout

addlayout adauga un nou layout in baza de date.

Parametrii de intrare:

=over 

=item C<name>
- denumirea 

=item C<content>
- continutul

=item C<layout_group>
- grupul din care face parte layoutul

=back


=cut

sub addlayout {
  # TODO
}

=head2 checklayout

checklayout verifica existenta unui layout (preluat prin valori ale parametrilor de intrare) in baza de date; in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce layoutul in baza de date si apoi returneaza obiectul corespunzator. Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.

Parametrii de intrare:

=over 

=item C<id>
- cheia primara a layoutului

=item C<name>
- denumirea 

=item C<layout_group>
- grupul din care face parte layoutul

=item C<content>
- continutul layoutului

=back


Criterii de unicitate:

=over

=item
- name + layout_group

=back

=cut

sub checklayout {
   # TODO
}

1;
