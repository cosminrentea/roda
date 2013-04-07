use utf8;

package RODA::RODADB::ResultSet::PropertyName;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::PropertyName - metode specifice pentru manipularea numelor proprietatilor asociate unui fisier

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip nume al unei proprietati ce poate fi asociata unui fisier.

=cut

=head1 METODE

=cut

=head2 check_property_name

verifica existenta unui nume de proprietate, daca exista returneaza obiectul respectiv, daca nu, il introduce si returneaza obiectul corespunzator.


Parametrii de intrare:

=over 

=item C<property_id>
- cheia primara a unui nume de proprietate

=item C<name>
- numele proprietatii curente

=item C<description>
- descrierea proprietatii curente

=back

Criterii de unicitate:

=over

=item
- name 

=back

=cut


sub check_property_name {
    # TODO
}
1;
