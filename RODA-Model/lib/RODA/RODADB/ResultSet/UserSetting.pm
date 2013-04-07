use utf8;

package RODA::RODADB::ResultSet::UserSetting;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::UserSetting - metode specifice pentru manipularea setarilor utilizatorilor

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip setare a unui utilizator.

=cut

=head1 METODE

=cut

=head2 check_user_setting

verifica existenta unei setari utilizator in baza de date, daca exista returneaza obiectul respectiv, daca nu, il introduce si returneaza obiectul corespunzator


Parametrii de intrare:

=over 

=item C<name>
- numele setarii

=item C<description>
- descrierea setarii utilizator

=item C<predefined_values>
- valorile predefinite ale setarii

=item C<default_values>
- valorile implicite ale setarii

=back

Criterii de unicitate:

=over

=item
- name 

=back

=cut


sub check_user_setting {
    # TODO
}
1;
