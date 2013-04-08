use utf8;

package RODA::RODADB::ResultSet::CmsFile;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::CmsFile - metode specifice prelucrarii informatiilor referitoare la fisierele din sistemul CMS al aplicatiei

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip fisier din sistemul CMS.

=cut

=head1 METODE

=cut

=head2 add_cms_file

add_cms_file adauga un nou fisier in baza de date pentru sistemul CMS al aplicatiei.

Parametrii de intrare:

=over 

=item C<filename>
- denumirea fisierului 

=item C<label>
- eticheta fisierului

=item C<filesize>
- dimensiunea fisierului

=item C<cms_folder>
- folderul din sistemul CMS caruia ii apartine fisierul

=back


=cut

sub add_cms_file {
  # TODO
}

=head2 set_cms_file_properties

set_cms_file_properties seteaza proprietatile unui fisier din sistemul CMS al aplicatiei, acestea fiind introduse in baza de date.

Parametrii de intrare:

=over 

=item C<property>
- proprietatea 

=item C<value>
- valoarea

=back

=cut

sub set_cms_file_properties {
  # TODO
}

1;
