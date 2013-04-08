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

=back


=cut

sub addlayout {
  # TODO
}
1;
