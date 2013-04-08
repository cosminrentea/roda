use utf8;

package RODA::RODADB::ResultSet::CmsPage;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::CmsPage - metode specifice prelucrarii informatiilor corespunzatoare paginilor din sistemul CMS

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip pagina din sistemul CMS.

=cut

=head1 METODE

=cut

=head2 addpage

addpage adauga o noua pagina de layout in baza de date.

Parametrii de intrare:

=over 

=item C<name>
- denumirea 

=item C<content>
- continutul

=item C<layout>
- layoutul din care face parte pagina

=item C<page_type>
- tipul paginii

=item C<visible>
- parametru boolean, ce specifica daca pagina este vizibila sau nu

=item C<navigable>
- parametru boolean, ce specifica daca pagina este navigabila sau nu

=item C<owner>
- proprietarul paginii

=back


=cut

sub addpage {
  # TODO
}

1;
