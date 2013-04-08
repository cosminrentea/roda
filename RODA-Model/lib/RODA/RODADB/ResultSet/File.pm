use utf8;

package RODA::RODADB::ResultSet::File;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::File - metode specifice pentru manipularea fisierelor

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip fisier.

=cut

=head1 METODE

=cut

=head2 addfile

addfile introduce informatiile referitoare la un nou fisier in baza de date. 

Parametrii de intrare:

=over 

=item C<title>
- titlul fisierului

=item C<description>
- descrierea fisierului

=item C<filetype>
- tipul fisierului

=item C<name>
- denumirea fisierului

=item C<size>
- dimensiunea fisierului

=item C<file_acls>
- lista de drepturi (acl) asociate fisierului curent. Un element al acestei liste este o structura de tip hash avand urmatoarele chei: aro_id, aro_type, read, update, delete. 

=back

=cut


sub addfile {
    # TODO
}

=head2 set_file_properties

set_file_properties seteaza proprietatile unui fisier, acestea fiind introduse in baza de date. 

Parametrii de intrare:

=over 

=item C<file_id>
- cheia primara a unui fisier

=item C<file_properties>
- lista proprietatilor fisierului. Existenta unei proprietati este verificata, iar in caz ca nu exista, proprietatea respectiva este mai intai inserata in tabelul property_name. 
Analog sunt verificate valorile proprietatilor si, daca nu exista, sunt inserate in tabelul property_value. 
Totodata, asocierea dintre fisierul curent, o proprietate si valoarea acesteia este adaugata tabelului file_property_name_value.

=back

=cut

sub set_file_properties {
    # TODO
}


1;
