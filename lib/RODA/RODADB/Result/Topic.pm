use utf8;
package RODA::RODADB::Result::Topic;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Topic - Tabel ce contine topic-urile asociate unei instante

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
  is_nullable: 0

Codul unui topic ce poate fi asociat unei instante 

=head2 nume

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Numele unui topic ce poate fi asociat unei instante

=head2 descriere

  data_type: 'text'
  is_nullable: 1

Descrierea topic-ului ce poate fi asociat unei instante

=head2 left_topic

  data_type: 'integer'
  is_nullable: 1

Codul topic-ului din stanga, in ierarhia arorescenta creata pentru a mentine legaturile cu topic-urile referite 

=head2 right_topic

  data_type: 'integer'
  is_nullable: 0

Codul topic-ului din dreapta, in ierarhia arorescenta creata pentru a mentine legaturile cu topic-urile referite 

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "nume",
  { data_type => "varchar", is_nullable => 0, size => 100 },
  "descriere",
  { data_type => "text", is_nullable => 1 },
  "left_topic",
  { data_type => "integer", is_nullable => 1 },
  "right_topic",
  { data_type => "integer", is_nullable => 0 },
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


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:IYd2zFPfpJbK+IbphZaByQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
