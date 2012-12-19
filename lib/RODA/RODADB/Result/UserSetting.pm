use utf8;
package RODA::RODADB::Result::UserSetting;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::UserSetting - Tabel care stocheaza setarile pentru utilizatori

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

=head1 TABLE: C<user_settings>

=cut

__PACKAGE__->table("user_settings");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul unei setari asociate utilizatorilor

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 150

Denumirea setarii

=head2 setting_group

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Grupul de setari din care aceasta face parte (refera atributul id al tabelului user_settings_group)

=head2 description

  data_type: 'text'
  is_nullable: 1

Descrierea setarii

=head2 predefined_values

  data_type: 'text'
  is_nullable: 1

Valorile predefinite ale setarii

=head2 default_value

  data_type: 'text'
  is_nullable: 1

Valoarea implicita a setarii

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 150 },
  "setting_group",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "description",
  { data_type => "text", is_nullable => 1 },
  "predefined_values",
  { data_type => "text", is_nullable => 1 },
  "default_value",
  { data_type => "text", is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 setting_group

Type: belongs_to

Related object: L<RODA::RODADB::Result::UserSettingsGroup>

=cut

__PACKAGE__->belongs_to(
  "setting_group",
  "RODA::RODADB::Result::UserSettingsGroup",
  { id => "setting_group" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 user_setting_values

Type: has_many

Related object: L<RODA::RODADB::Result::UserSettingValue>

=cut

__PACKAGE__->has_many(
  "user_setting_values",
  "RODA::RODADB::Result::UserSettingValue",
  { "foreign.user_setting_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:rKx30GbfH5HVPqFafpTBwA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
