use utf8;

package RODA::RODADB::ResultSet::Source;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

=head1 NUME

RODA::RODADB::ResultSet::Source - metode specifice pentru prelucrarea inregistrarilor despre sursele (organizatiile) 
care pot oferi studii pentru partea de colectare.

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip sursa a unui studiu.

=cut

=head1 METODE

=cut

=head2 checksource

checksource verifica existenta unei organizatii in baza de date (pe baza parametrilor furnizati); 
daca exista, returneaza obiectul respectiv, altfel introduce mai intai organizatia si returneaza obiectul corespunzator. 
Cheia primara a organizatiei gasite sau inserate va constitui cheia primara a sursei ce urmeaza a fi introdusa. 
Daca organizatia exista, se verifica daca nu constituia deja o sursa a studiilor. 

Parametrii de intrare:

=over 

=item C<source_id>
- cheia primara a sursei (organizatiei) din tabelul de surse ale studiilor

=item C<fullname>
- denumirea completa a organizatiei

=item C<name>
- denumirea abreviata a organizatiei

=item C<sourcetype>
- denumirea tipului (starii) sursei

=item C<source_contacts>
- lista ce contine persoanele de contact ale sursei respective; existenta fiecarei persoane va fi verificata in baza de date, iar in cazul inexistentei va fi introdusa. 
Un element al acestei iste contine si metoda de contact a persoanei. 

=item C<source_type_history>
- lista ce contine istoricul organizatiei din punct de vedere al starii acesteia (in curs de prospectare, de recuperare a datelor, contactata etc.).
Lista va fi furnizata initial daca se cunosc informatii anterioare asupra evolutiei starii sursei respective; ulterior, liniile vor fi inserate automat pe baza unui declansator atunci cand se modifica starea sursei de studii.

=item C<source_studies>
- lista de studii furnizate de catre sursa curenta    

=back


Criterii de unicitate:

=over

=item
- fullname (criteriul de unicitate pentru organizatii)

=back


=cut

sub checkstudy {
	#TODO
}
1;
