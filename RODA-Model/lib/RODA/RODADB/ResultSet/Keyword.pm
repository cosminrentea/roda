use utf8;

package RODA::RODADB::ResultSet::Keyword;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::Keyword - metode specifice prelucrarii cuvintelor cheie

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip cuvant cheie.

=cut

=head1 METODE

=cut

=head2 checkkeyword

checkkeyword verifica existenta unui cuvant cheie (preluat prin valori ale parametrilor de intrare) in baza de date; in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce cuvantul cheie in baza de date si apoi returneaza obiectul corespunzator. Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.

Parametrii de intrare:

=over 

=item C<keyword_id>
- cheia primara a cuvantului cheie din tabelul de cuvinte cheie

=item C<keyword>
- denumirea cuvantului cheie

=back


Criterii de unicitate:

=over

=item
- keyword (presupunem ca denumirile cuvintelor cheie sunt unice)

=back

=cut

sub checkkeyword {
   my ( $self, %params ) = @_;

   my $keywordrs = $self->search({name => $params{keyword}});
   if ($keywordrs->count == 1) {
   		return $keywordrs->single;
   } 
   
   if ($params{keyword} && $params{keyword} ne '' ) {
   		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        my $newkeywordrs = $self->create(
                                      {
                                        name => $params{keyword},                        
                                      }
        );
        $guard->commit;
        return $newkeywordrs;
    }
}
1;
