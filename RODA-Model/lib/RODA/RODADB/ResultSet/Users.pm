use utf8;

package RODA::RODADB::ResultSet::Users;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::Users - metode specifice pentru manipularea utilizatorilor aplicatiei

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip utilizator al aplicatiei.

=cut

=head1 METODE

=cut

=head2 checkuser

checkuser verifica existenta unui utilizator preluat prin intermediul parametrilor de intrare; daca utilizatorul exista, metoda returneaza obiectul corespunzator, altfel introduce informatiile referitoare la un nou fisier in baza de date. 
Existenta este verificata pe baza cheii primare sau a unicitatii numelui.

Parametrii de intrare:

=over 

=item C<user_id>
- cheia primara a unui utilizator

=item C<username>
- numele (identificatorul) utilizatorului

=item C<password>
- parola utilizatorului

=item C<fname>
- prenumele utilizatorului

=item C<lname>
- numele de familie al utilizatorului

=item C<email>
- adresa de email a utilizatorului

=item C<enabled>
- parametru boolean, ce specifica daca utilzatorul este activat sau nu

=item C<auth_data>
- datele de autentificare ale utilizatorului, conform unui furnizor de informatii de acces

=item C<persons>
- lista de persoane asociate utilizatorului curent. Un element al acestei liste contine si valoarile de similitudine dintre persoana si utilizator. 
Existenta unei persoane este verificata in tabelul de persoane; in cazul inexistentei, persoana este adaugata in tabelul respectiv. 
Totodata, asocierea dintre utilizator si persoana este adaugata in tabelul person_links.

=item C<user_settings>
- lista de setari asociate utilizatorului curent. Un element al acestei liste contine si valoarea asociata setarii. 
Existenta unei setari este verificata in tabelul user_setting; in cazul inexistentei, setarea este adaugata in tabelul respectiv. 
Totodata, asocierea dintre utilizator si setare este adaugata in tabelul user_setting_value.   

=item C<user_in_messages>
- lista de mesaje primite de catre utilizatorul curent

=item C<user_out_messages>
- lista de mesaje trimise de catre utilizatorul curent

=item C<user_authorities>
- lista de autoritati corespunzatoare utilizatorului curent

=back

Criterii de unicitate:

=over

=item
- N/A (cu exceptia celor definite in baza de date)

=back

=cut


sub checkuser {
    # TODO
}

=head2 insert_root_user

insert_root_user introduce in baza de date informatiile principale referitoare la un nou utilizator al aplicatiei. 

Parametrii de intrare:

=over 

=item C<username>
- numele (identificatorul) utilizatorului

=item C<password>
- parola utilizatorului

=item C<fname>
- prenumele utilizatorului

=item C<lname>
- numele de familie al utilizatorului

=item C<email>
- adresa de email a utilizatorului

=item C<enabled>
- parametru boolean, ce specifica daca utilzatorul este activat sau nu

=back

=cut


sub insert_root_user {
    # TODO
}

1;
