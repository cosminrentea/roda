use utf8;

package RODA::RODADB::ResultSet::Internet;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::Internet

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip internet.

=cut

=head1 METODE

=cut

=head2 checkinternet

checkinternet verifica existenta unei adrese internet (preluata prin valori ale parametrilor de intrare), verifica daca acesta exista in baza de date; in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce conceptul in baza de date si apoi returneaza obiectul corespunzator. Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.

Parametrii de intrare:

=over 

=item C<internet>
- cheia primara a adresei de internet

=item C<internet_type>
- denumirea tipului adresei internet

=item C<assoc_description>
- descrierea tipului de asociere intre instanta si persoana

=back

Criterii de unicitate:

=over

=item
- internet + internet_type 

=back

=cut

sub checkinternet {
    my ( $self, %params ) = @_;

   my $internetrs = $self->search({internet => $params{internet}, internet_type => $params{internet_type}});
   if ($internetrs->count == 1) {
   		return $internetrs->single;
   } 
   
   if ($params{internet} && $params{internet} ne '' ) {
   		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        my $newinternetrs = $self->create(
                                      {
                                        internet => $params{internet},
                                        internet_type => $params{internet_type},
                                      }
        );
        $guard->commit;
        return $newinternetrs;
    }
}
1;
