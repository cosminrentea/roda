use utf8;
package RODA::RODADB::Result::Lang;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Lang

=head1 DESCRIPTION

Tabel ce contine limbile utilizate pentru unii termeni din baza de date

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

=head1 TABLE: C<lang>

=cut

__PACKAGE__->table("lang");

=head1 ACCESSORS

=head2 id

  data_type: 'char'
  is_nullable: 0
  size: 2

Codul unei limbi ce poate fi utilizata pentru un termen din baza de date

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 50

Denumirea unei limbi

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "char", is_nullable => 0, size => 2 },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 50 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 instance_descrs

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceDescr>

=cut

__PACKAGE__->has_many(
  "instance_descrs",
  "RODA::RODADB::Result::InstanceDescr",
  { "foreign.lang_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_descrs

Type: has_many

Related object: L<RODA::RODADB::Result::StudyDescr>

=cut

__PACKAGE__->has_many(
  "study_descrs",
  "RODA::RODADB::Result::StudyDescr",
  { "foreign.lang_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 translated_topics

Type: has_many

Related object: L<RODA::RODADB::Result::TranslatedTopic>

=cut

__PACKAGE__->has_many(
  "translated_topics",
  "RODA::RODADB::Result::TranslatedTopic",
  { "foreign.lang_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 16:35:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:Wf8G9ooXIch+n+XkuVtgpw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
