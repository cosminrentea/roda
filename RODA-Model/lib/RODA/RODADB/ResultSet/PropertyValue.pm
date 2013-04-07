use utf8;

package RODA::RODADB::ResultSet::PropertyValue;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::PropertyValue - metode specifice pentru manipularea valorilor proprietatilor asociate unui fisier

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip valoare a unei proprietati ce poate fi asociata unui fisier.

=cut

=head1 METODE

=cut

=head2 check_property_value

verifica existenta unui valori a unei proprietati in tabel; daca exista, returneaza obiectul respectiv, altfel, il introduce si returneaza obiectul corespunzator.


Parametrii de intrare:

=over 

=item C<value_id>
- cheia primara a unei valori asociate unei proprietati

=item C<value>
- valoarea unei proprietati

=back

Criterii de unicitate:

=over

=item
- value 

=back

=cut


sub check_property_value {
    # TODO
}
1;
