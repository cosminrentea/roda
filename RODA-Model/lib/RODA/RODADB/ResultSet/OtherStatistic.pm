use utf8;

package RODA::RODADB::ResultSet::OtherStatistic;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::OtherStatistic - metode specifice prelucrarii statisticilor asociate variabilelor

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip statistica asociata unei variabile.

=cut

=head1 METODE

=cut

=head2 check_other_statistic

check_other_statistic verifica existenta unei statistici asociate unei variabile (preluata prin valori ale parametrilor de intrare) 
in baza de date. In cazul existentei, returneaza obiectul corespunzator; altfel, metoda introduce statistica respectiva 
in baza de date si apoi returneaza obiectul corespunzator. 
Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.

Parametrii de intrare:

=over 

=item C<statistic_id>
- cheia primara a statisticii din tabelul de statistici asociate variabilelor

=item C<variable_id>
- cheia primara a variabilei careia ii este asociata statistica curenta

=item C<value>
- valoarea statisticii pentru variabila respectiva

=item C<description>
- descrierea statisticii asociate variabilei respective

=back


Criterii de unicitate:

=over

=item
- variable_id + name (presupunem ca o statistica poate aparea o singura data pentru o variabila)

=back

=cut

sub check_other_statistic {
   my ( $self, %params ) = @_;

   my $other_statistic_rs = $self->search({variable_id => $params{variable_id}, name => $params{name}});
   if ($other_statistic_rs->count == 1) {
   		return $other_statistic_rs->single;
   } 
   
   my $guard = $self->result_source->schema()->txn_scope_guard;
 
   my $new_other_statistic_rs = $self->create({
                                   variable_id => $params{variable_id},
          						   name => $params{name},
          						   value => $params{value},
          						   description => $params{description},
                                  }
                                 );
   $guard->commit;
   return $new_other_statistic_rs;
}
1;