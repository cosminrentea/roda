use utf8;
package RODA::RODADB::Result::Topic;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Topic

=head1 DESCRIPTION

Tabel ce contine topic-urile ce pot fi asociate unei instante sau unui studiu

=cut

use strict;
use warnings;

use Moose;
use MooseX::NonMoose;
use MooseX::MarkAsMethods autoclean => 1;
extends 'DBIx::Class::Core';

=head1 COMPONENTS LOADED

=over 4

=item * L<DBIx::Class::InflateColumn::DateTime>

=back

=cut

__PACKAGE__->load_components("InflateColumn::DateTime");

=head1 TABLE: C<topic>

=cut

__PACKAGE__->table("topic");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'topic_id_seq'

Codul unui topic ce poate fi asociat unui studiu sau unei instante 

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Numele unui topic ce poate fi asociat unui studiu sau unei instante

=head2 description

  data_type: 'text'
  is_nullable: 1

Descrierea topic-ului ce poate fi asociat unui studiu sau unei instante

=head2 parent_topic_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

Codul topic-ului din dreapta, in ierarhia arorescenta creata pentru a mentine legaturile cu topic-urile referite 

=head2 preferred_synonym_topic_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "topic_id_seq",
  },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 100 },
  "description",
  { data_type => "text", is_nullable => 1 },
  "parent_topic_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
  "preferred_synonym_topic_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 instance_topics

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceTopic>

=cut

__PACKAGE__->has_many(
  "instance_topics",
  "RODA::RODADB::Result::InstanceTopic",
  { "foreign.topic_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 parent_topic

Type: belongs_to

Related object: L<RODA::RODADB::Result::Topic>

=cut

__PACKAGE__->belongs_to(
  "parent_topic",
  "RODA::RODADB::Result::Topic",
  { id => "parent_topic_id" },
  {
    is_deferrable => 0,
    join_type     => "LEFT",
    on_delete     => "NO ACTION",
    on_update     => "NO ACTION",
  },
);

=head2 preferred_synonym_topic

Type: belongs_to

Related object: L<RODA::RODADB::Result::Topic>

=cut

__PACKAGE__->belongs_to(
  "preferred_synonym_topic",
  "RODA::RODADB::Result::Topic",
  { id => "preferred_synonym_topic_id" },
  {
    is_deferrable => 0,
    join_type     => "LEFT",
    on_delete     => "NO ACTION",
    on_update     => "NO ACTION",
  },
);

=head2 study_topics

Type: has_many

Related object: L<RODA::RODADB::Result::StudyTopic>

=cut

__PACKAGE__->has_many(
  "study_topics",
  "RODA::RODADB::Result::StudyTopic",
  { "foreign.topic_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 topic_parent_topics

Type: has_many

Related object: L<RODA::RODADB::Result::Topic>

=cut

__PACKAGE__->has_many(
  "topic_parent_topics",
  "RODA::RODADB::Result::Topic",
  { "foreign.parent_topic_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 topic_preferred_synonym_topics

Type: has_many

Related object: L<RODA::RODADB::Result::Topic>

=cut

__PACKAGE__->has_many(
  "topic_preferred_synonym_topics",
  "RODA::RODADB::Result::Topic",
  { "foreign.preferred_synonym_topic_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 translated_topics

Type: has_many

Related object: L<RODA::RODADB::Result::TranslatedTopic>

=cut

__PACKAGE__->has_many(
  "translated_topics",
  "RODA::RODADB::Result::TranslatedTopic",
  { "foreign.topic_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instances

Type: many_to_many

Composing rels: L</instance_topics> -> instance

=cut

__PACKAGE__->many_to_many("instances", "instance_topics", "instance");

=head2 studies

Type: many_to_many

Composing rels: L</study_topics> -> study

=cut

__PACKAGE__->many_to_many("studies", "study_topics", "study");


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:i6tepGHjAT09UYf1DULjVA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
