use utf8;

package RODA::RODADB::ResultSet::Sourcestudy;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

=head1 NUME

RODA::RODADB::ResultSet::Sourcestudy  - metode specifice pentru manipularea inregistrarilor despre studiile care pot fi furnizate de catre o sursa (organizatie). 

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip studiu furnizat de catre o sursa.

=cut

=head1 METODE

=cut

=head2 check_source_study

check_source_study introduce un studiu (preluat prin valori ale parametrilor de intrare) 
in baza de date si returneaza obiectul corespunzator.  

Parametrii de intrare:

=over 

=item C<source_study_id>
- cheia primara a studiului din tabelul de studii furnizate de catre surse

=item C<name>
- numele studiului

=item C<details>
- detalii asupra studiului curent

=item C<type>
- tipul (starea) studiului curent

=item C<org>
- organizatia care furnizeaza studiul curent

=back


Criterii de unicitate:

=over

=item
- N/A

=back


=cut

sub check_source_study {
   # TODO
}

1;
