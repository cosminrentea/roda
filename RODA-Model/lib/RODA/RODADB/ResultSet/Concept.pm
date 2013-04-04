use utf8;

package RODA::RODADB::ResultSet::Concept;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::Concept - metode specifice prelucrarii conceptelor

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip concept.

=cut

=head1 METODE

=cut

=head2 checkconcept

checkconcept verifica existenta unui concept (preluat prin valori ale parametrilor de intrare) in baza de date; in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce conceptul in baza de date si apoi returneaza obiectul corespunzator. Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.

Parametrii de intrare:

=over 

=item C<concept_id>
- cheia primara a conceptului din tabelul de concepte

=item C<concept>
- denumirea conceptului

=item C<description>
- descrierea conceptului

=back


Criterii de unicitate:

=over

=item
- name (presupunem ca denumirile conceptelor sunt unice)

=back

=cut

sub checkconcept {
   my ( $self, %params ) = @_;

   my $conceptrs = $self->search({name => $params{concept}});
   if ($conceptrs->count == 1) {
   		return $conceptrs->single;
   } 
   
   if ($params{concept} && $params{concept} ne '' ) {
   		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        my $newconceptrs = $self->create(
                                      {
                                        name => $params{concept},
                                        description => $params{description},                                     
                                      }
        );
        $guard->commit;
        return $newconceptrs;
    }
}
1;
