use utf8;

package RODA::RODADB::ResultSet::Topic;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::Topic - metode specifice prelucrarii topicurilor

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip topic.

=cut

=head1 METODE

=cut

=head2 checktopic

checktopic verifica existenta unui topic (preluat prin valori ale parametrilor de intrare) in baza de date; in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce conceptul in baza de date si apoi returneaza obiectul corespunzator. Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.

Parametrii de intrare:

=over 

=item C<topic_id>
- cheia primara a topicului din tabelul de topicuri

=item C<topic>
- denumirea topicului

=item C<parent_topic>
- denumirea topicului parinte

=item C<preferred_synonym_topic>
- denumirea topicului sinonim preferat

=item C<description>
- descrierea topicului

=back


Criterii de unicitate:

=over

=item
- name (presupunem ca denumirile topicurilor sunt unice)

=back

=cut

sub checktopic {
   my ( $self, %params ) = @_;

   my $topicrs = $self->search({name => $params{topic}});
   if ($topicrs->count == 1) {
   		return $topicrs->single;
   } 
   
   #topic parinte
   my $parentTopic_rs = $self->search({name => $params{parent_topic}});;
   
   #Verificare topic sinonim
   my $preferredSynTopic_rs = $self->search({name => $params{preferred_synonym_topic}});;
   
   if ($params{topic} && $params{topic} ne '' ) {
   		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        my $newtopicrs = $self->create(
                                      {
                                        name => $params{topic},
                                        description => $params{description},
                                        parent_topic_id => $parentTopic_rs -> id,
                                        preferred_synonym_topic_id => $preferredSynTopic_rs -> id,
                                      }
        );
        $guard->commit;
        return $newtopicrs;
    }
}
1;
