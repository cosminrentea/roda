use utf8;

package RODA::RODADB::ResultSet::CmsSnippet;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::CmsSnippet - metode specifice prelucrarii informatiilor de snippet din sistemul CMS.

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip snippet din sistemul CMS.

=cut

=head1 METODE

=cut

=head2 addsnippet

addsnippet adauga un nou snippet in baza de date.

Parametrii de intrare:

=over 

=item C<name>
- denumirea 

=item C<content>
- continutul

=item C<snippet_group>
- grupul din care face parte snippetul

=back


=cut

sub addsnippet {
  # TODO
}
1;
