use utf8;
package RODA::RODADB::Result::User;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::User - Tabel ce contine utilizatorii aplicatiei (bazei de date)

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

=head1 TABLE: C<users>

=cut

__PACKAGE__->table("users");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul utilizatorului

=head2 credential_provider

  data_type: 'varchar'
  is_nullable: 0
  size: 20

Furnizorul de informatii de acces pentru utilizatorul respectiv

=head2 username

  data_type: 'varchar'
  is_nullable: 0
  size: 50

=head2 password

  data_type: 'varchar'
  is_nullable: 0
  size: 50

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "credential_provider",
  { data_type => "varchar", is_nullable => 0, size => 20 },
  "username",
  { data_type => "varchar", is_nullable => 0, size => 50 },
  "password",
  { data_type => "varchar", is_nullable => 0, size => 50 },
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

=head2 catalog_studies

Type: has_many

Related object: L<RODA::RODADB::Result::CatalogStudy>

=cut

__PACKAGE__->has_many(
  "catalog_studies",
  "RODA::RODADB::Result::CatalogStudy",
  { "foreign.added_by" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 cms_pages

Type: has_many

Related object: L<RODA::RODADB::Result::CmsPage>

=cut

__PACKAGE__->has_many(
  "cms_pages",
  "RODA::RODADB::Result::CmsPage",
  { "foreign.owner" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 person_links

Type: has_many

Related object: L<RODA::RODADB::Result::PersonLink>

=cut

__PACKAGE__->has_many(
  "person_links",
  "RODA::RODADB::Result::PersonLink",
  { "foreign.user" => "self.id" },
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

=head2 user_messages

Type: has_many

Related object: L<RODA::RODADB::Result::UserMessage>

=cut

__PACKAGE__->has_many(
  "user_messages",
  "RODA::RODADB::Result::UserMessage",
  { "foreign.user_id" => "self.id" },
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


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:57:38
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:8Rq3FZN52hihbVz2yjZ6pw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
