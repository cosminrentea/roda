use utf8;
package RODA::RODADB::Result::RodaUser;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::User - Tabel ce contine utilizatorii aplicatiei

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

=head1 TABLE: C<user>

=cut

__PACKAGE__->table("rodauser");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'user_id_seq'

Codul utilizatorului aplicatiei

=head2 credential_provider

  data_type: 'text'
  is_nullable: 0

Furnizorul de informatii de acces pentru utilizatorul respectiv

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "user_id_seq",
  },
  "credential_provider",
  { data_type => "text", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 audits

Type: has_many

Related object: L<RODA::RODADB::Result::Audit>

=cut

__PACKAGE__->has_many(
  "audits",
  "RODA::RODADB::Result::Audit",
  { "foreign.user_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 auth_data

Type: might_have

Related object: L<RODA::RODADB::Result::AuthData>

=cut

__PACKAGE__->might_have(
  "auth_data",
  "RODA::RODADB::Result::AuthData",
  { "foreign.user_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 catalogs

Type: has_many

Related object: L<RODA::RODADB::Result::Catalog>

=cut

__PACKAGE__->has_many(
  "catalogs",
  "RODA::RODADB::Result::Catalog",
  { "foreign.owner" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 cms_pages

Type: has_many

Related object: L<RODA::RODADB::Result::CmsPage>

=cut

__PACKAGE__->has_many(
  "cms_pages",
  "RODA::RODADB::Result::CmsPage",
  { "foreign.owner_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instance_keywords

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceKeyword>

=cut

__PACKAGE__->has_many(
  "instance_keywords",
  "RODA::RODADB::Result::InstanceKeyword",
  { "foreign.added_by" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instances

Type: has_many

Related object: L<RODA::RODADB::Result::Instance>

=cut

__PACKAGE__->has_many(
  "instances",
  "RODA::RODADB::Result::Instance",
  { "foreign.added_by" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 news

Type: has_many

Related object: L<RODA::RODADB::Result::News>

=cut

__PACKAGE__->has_many(
  "news",
  "RODA::RODADB::Result::News",
  { "foreign.added_by" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 person_links_statuses_by

Type: has_many

Related object: L<RODA::RODADB::Result::PersonLink>

=cut

__PACKAGE__->has_many(
  "person_links_statuses_by",
  "RODA::RODADB::Result::PersonLink",
  { "foreign.status_by" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 person_links_users

Type: has_many

Related object: L<RODA::RODADB::Result::PersonLink>

=cut

__PACKAGE__->has_many(
  "person_links_users",
  "RODA::RODADB::Result::PersonLink",
  { "foreign.user_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 sourcestudy_type_histories

Type: has_many

Related object: L<RODA::RODADB::Result::SourcestudyTypeHistory>

=cut

__PACKAGE__->has_many(
  "sourcestudy_type_histories",
  "RODA::RODADB::Result::SourcestudyTypeHistory",
  { "foreign.added_by" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 sourcetype_histories

Type: has_many

Related object: L<RODA::RODADB::Result::SourcetypeHistory>

=cut

__PACKAGE__->has_many(
  "sourcetype_histories",
  "RODA::RODADB::Result::SourcetypeHistory",
  { "foreign.added_by" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 studies

Type: has_many

Related object: L<RODA::RODADB::Result::Study>

=cut

__PACKAGE__->has_many(
  "studies",
  "RODA::RODADB::Result::Study",
  { "foreign.added_by" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_keywords

Type: has_many

Related object: L<RODA::RODADB::Result::StudyKeyword>

=cut

__PACKAGE__->has_many(
  "study_keywords",
  "RODA::RODADB::Result::StudyKeyword",
  { "foreign.added_by" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 user_auth_logs

Type: has_many

Related object: L<RODA::RODADB::Result::UserAuthLog>

=cut

__PACKAGE__->has_many(
  "user_auth_logs",
  "RODA::RODADB::Result::UserAuthLog",
  { "foreign.user_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 user_message_to_users

Type: has_many

Related object: L<RODA::RODADB::Result::UserMessage>

=cut

__PACKAGE__->has_many(
  "user_message_to_users",
  "RODA::RODADB::Result::UserMessage",
  { "foreign.to_user_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 user_messages_from_user

Type: has_many

Related object: L<RODA::RODADB::Result::UserMessage>

=cut

__PACKAGE__->has_many(
  "user_messages_from_user",
  "RODA::RODADB::Result::UserMessage",
  { "foreign.from_user_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 user_profile

Type: might_have

Related object: L<RODA::RODADB::Result::UserProfile>

=cut

__PACKAGE__->might_have(
  "user_profile",
  "RODA::RODADB::Result::UserProfile",
  { "foreign.user_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 user_roles

Type: has_many

Related object: L<RODA::RODADB::Result::UserRole>

=cut

__PACKAGE__->has_many(
  "user_roles",
  "RODA::RODADB::Result::UserRole",
  { "foreign.user_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 user_setting_values

Type: has_many

Related object: L<RODA::RODADB::Result::UserSettingValue>

=cut

__PACKAGE__->has_many(
  "user_setting_values",
  "RODA::RODADB::Result::UserSettingValue",
  { "foreign.user_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 roles

Type: many_to_many

Composing rels: L</user_roles> -> role

=cut

__PACKAGE__->many_to_many("roles", "user_roles", "role");


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:FIB97wc4g1ay9MD5s6mmvg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
