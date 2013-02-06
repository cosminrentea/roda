use utf8;
package RODA::RODADB::Result::Setting;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Setting - Tabel care contine setarile aplicatiei

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

=head1 TABLE: C<setting>

=cut

__PACKAGE__->table("setting");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'setting_id_seq'

Codul unei setari a aplicatiei

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 150

Denumirea unei setari a aplicatiei

=head2 setting_group

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Grupul din care setarea face parte (refera atributul id din tabelul setting_group)

=head2 description

  data_type: 'text'
  is_nullable: 1

Descrierea unei setari a aplicatiei

=head2 predefined_values

  data_type: 'text'
  is_nullable: 0

Valori predefinite ale setarii

=head2 default_value

  data_type: 'text'
  is_nullable: 0

Valoarea implicita a setarii

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "setting_id_seq",
  },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 150 },
  "setting_group",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "description",
  { data_type => "text", is_nullable => 1 },
  "predefined_values",
  { data_type => "text", is_nullable => 0 },
  "default_value",
  { data_type => "text", is_nullable => 0 },
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

Related object: L<RODA::RODADB::Result::SettingGroup>

=cut

__PACKAGE__->belongs_to(
  "setting_group",
  "RODA::RODADB::Result::SettingGroup",
  { id => "setting_group" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 setting_value

Type: might_have

Related object: L<RODA::RODADB::Result::SettingValue>

=cut

__PACKAGE__->might_have(
  "setting_value",
  "RODA::RODADB::Result::SettingValue",
  { "foreign.setting_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 16:35:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:lk4lpmyN1c5J4ymZY1RUTg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
