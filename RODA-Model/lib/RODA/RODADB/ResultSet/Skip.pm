use utf8;

package RODA::RODADB::ResultSet::Skip;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::Skip - metode specifice prelucrarii salturilor intre variabile in cadrul chestionarelor

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip salt intre doua variabile ale unui chestionar.

=cut

=head1 METODE

=cut

=head2 checkskip

checkskip verifica existenta unui salt definit intre doua variabile (preluat prin valori ale parametrilor de intrare) 
in baza de date. In cazul existentei, returneaza obiectul corespunzator; altfel, metoda introduce saltul respectiv 
in baza de date si apoi returneaza obiectul corespunzator. 
Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.

Parametrii de intrare:

=over 

=item C<skip_id>
- cheia primara a saltului din tabelul de salturi intre variabile (skip)

=item C<variable_id>
- cheia primara a variabilei careia de la care porneste saltul curent

=item C<condition>
- conditia conform careia este realizat saltul catre variabila identificata prin parametrul next_variable_id

=item C<next_variable_id>
- cheia primara a variabilei catre care se indreapta saltul curent

=back


Criterii de unicitate:

=over

=item
- variable_id + condition (presupunem ca poate exista un singur salt de la o variabila, dupa o conditie data)
- variable_id + next_variable_id

=back

=cut

sub checkskip {
   my ( $self, %params ) = @_;

   my $skiprs = $self->search({variable_id => $params{variable_id}, condition => $params{condition}});
   if ($skiprs->count == 1) {
   		return $skiprs->single;
   } 
   
   my $guard = $self->result_source->schema()->txn_scope_guard;
 
   my $newskiprs = $self->create({
                                   variable_id => $params{variable_id},
          						   condition => $params{condition},
          						   next_variable_id => $params{next_variable_id},
                                  }
                                 );
   $guard->commit;
   return $newskiprs;
}
1;
