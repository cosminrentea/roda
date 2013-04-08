use utf8;

package RODA::RODADB::ResultSet::CmsPageType;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::CmsPageType - metode specifice prelucrarii tipurilor paginilor de CMS

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate corespunzatoare tipurilor de pagini din sistemul CMS.

=cut

=head1 METODE

=cut

=head2 checkpagetype

checkpagetype verifica existenta unui tip de pagina (preluat prin valori ale parametrilor de intrare) in baza de date; in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce tipul de pagina in baza de date si apoi returneaza obiectul corespunzator. Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.

Parametrii de intrare:

=over 

=item C<page_type_id>
- cheia primara a tipului din tabelul de tipuri de pagini

=item C<name>
- denumirea tipului de pagina

=back

Criterii de unicitate:

=over

=item
- name (presupunem ca denumirile tipurilor de pagini sunt unice)

=back

=cut


sub checkpagetype {
   # TODO
}
1;
