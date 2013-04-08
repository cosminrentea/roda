use utf8;

package RODA::RODADB::ResultSet::CmsSnippetGroup;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::CmsSnippetGroup - metode specifice prelucrarii grupurilor de snippet din CMS

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip grup de snippet.

=cut

=head1 METODE

=cut

=head2 checksnippetgroup

checksnippetgroup verifica existenta unui grup (preluat prin valori ale parametrilor de intrare) in baza de date; in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce grupul in baza de date si apoi returneaza obiectul corespunzator. Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.

Parametrii de intrare:

=over 

=item C<id>
- cheia primara a grupului

=item C<name>
- denumirea grupului

=item C<description>
- descrierea grupului

=item C<parent>
- parintele grupului


=back


Criterii de unicitate:

=over

=item
- name + parent

=back

=cut

sub checksnippetgroup {
   # TODO
}

=head2 addsnippetgroup

addsnippetgroup adauga un grup (preluat prin valori ale parametrilor de intrare) in baza de date.

Parametrii de intrare:

=over 

=item C<name>
- denumirea grupului

=item C<description>
- descrierea grupului

=item C<parent>
- parintele grupului


=back


Criterii de unicitate:

=over

=item
- name + parent

=back

=cut

sub addsnippetgroup {
   # TODO
}

1;
