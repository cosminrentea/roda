use utf8;
package RODA::RODADB::Result::UserSettingValue;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::UserSettingValue - Tabel care stocheaza valorile setarilor utilizatorului

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

=head1 TABLE: C<user_setting_value>

=cut

__PACKAGE__->table("user_setting_value");

=head1 ACCESSORS

=head2 user_setting_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul setarii corespunzatoare unui utilizator (refera atributul id al tabelului user_settings)

=head2 user_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul utilizatorului caruia ii apartine setarea (refera atributul id al tabelului users)

=head2 value

  data_type: 'text'
  is_nullable: 0

Valoarea setarii respective

=cut

__PACKAGE__->add_columns(
  "user_setting_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "user_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "value",
  { data_type => "text", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</user_setting_id>

=item * L</user_id>

=back

=cut

__PACKAGE__->set_primary_key("user_setting_id", "user_id");

=head1 RELATIONS

=head2 user

Type: belongs_to

Related object: L<RODA::RODADB::Result::User>

=cut

__PACKAGE__->belongs_to(
  "user",
  "RODA::RODADB::Result::User",
  { id => "user_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 user_setting

Type: belongs_to

Related object: L<RODA::RODADB::Result::UserSetting>

=cut

__PACKAGE__->belongs_to(
  "user_setting",
  "RODA::RODADB::Result::UserSetting",
  { id => "user_setting_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:AzPiGgg8kLzalNXILDit2g


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
