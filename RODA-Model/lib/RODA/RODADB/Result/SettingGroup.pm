use utf8;
package RODA::RODADB::Result::SettingGroup;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::SettingGroup - Tabel care stocheaza grupurile de setari ale aplicatiei

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

=head1 TABLE: C<setting_group>

=cut

__PACKAGE__->table("setting_group");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'setting_group_id_seq'

Codul unui grup de setari ale aplicatiei

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 150

Denumirea unui grup de setari ale aplicatiei

=head2 parent

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul grupului parinte al grupului de setari ale aplicatiei (refera atributul id al tabelului setting_group)

=head2 description

  data_type: 'text'
  is_nullable: 1

Descrierea grupului de setari ale aplicatiei

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "setting_group_id_seq",
  },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 150 },
  "parent",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "description",
  { data_type => "text", is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 parent

Type: belongs_to

Related object: L<RODA::RODADB::Result::SettingGroup>

=cut

__PACKAGE__->belongs_to(
  "parent",
  "RODA::RODADB::Result::SettingGroup",
  { id => "parent" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 setting_groups

Type: has_many

Related object: L<RODA::RODADB::Result::SettingGroup>

=cut

__PACKAGE__->has_many(
  "setting_groups",
  "RODA::RODADB::Result::SettingGroup",
  { "foreign.parent" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 settings

Type: has_many

Related object: L<RODA::RODADB::Result::Setting>

=cut

__PACKAGE__->has_many(
  "settings",
  "RODA::RODADB::Result::Setting",
  { "foreign.setting_group" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 16:35:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:vr4Qy690vqyr3MsHh8FsmQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
