use utf8;
package RODA::RODADB::Result::SettingValue;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::SettingValue - Tabel care contine valorile setarilor aplicatiei

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

=head1 TABLE: C<setting_value>

=cut

__PACKAGE__->table("setting_value");

=head1 ACCESSORS

=head2 setting_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul setarii a carei valoare este inregistrata (refera atributul id din tabelul setting)

=head2 value

  data_type: 'text'
  is_nullable: 0

Valoarea setarii referite prin atributul setting_id

=cut

__PACKAGE__->add_columns(
  "setting_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "value",
  { data_type => "text", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</setting_id>

=back

=cut

__PACKAGE__->set_primary_key("setting_id");

=head1 RELATIONS

=head2 setting

Type: belongs_to

Related object: L<RODA::RODADB::Result::Setting>

=cut

__PACKAGE__->belongs_to(
  "setting",
  "RODA::RODADB::Result::Setting",
  { id => "setting_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:BWWXlbkDJ+BGooM4Lv6VhA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
