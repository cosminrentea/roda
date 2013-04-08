use utf8;

package RODA::RODADB::ResultSet::CmsFolder;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::CmsFolder - metode specifice prelucrarii folderelor din sistemul CMS al aplicatiei

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip folder din sistemul CMS.

=cut

=head1 METODE

=cut

=head2 check_cms_folder

check_cms_folder verifica existenta unui folder (preluat prin valori ale parametrilor de intrare) in baza de date; in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce folderul in baza de date si apoi returneaza obiectul corespunzator. Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.

Parametrii de intrare:

=over 

=item C<id>
- cheia primara a folderului

=item C<name>
- denumirea folderului

=item C<parent>
- folderul parinte

=item C<description>
- descrierea folderului

=back


Criterii de unicitate:

=over

=item
- name + parent

=back

=cut

sub check_cms_folder {
   # TODO
}
1;
