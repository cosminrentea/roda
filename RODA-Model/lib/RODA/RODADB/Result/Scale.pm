use utf8;
package RODA::RODADB::Result::Scale;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Scale - Tabel ce stocheaza elementele de tip scala ale variabilelor de selectie.

=cut

use strict;
use warnings;

use Moose;
use MooseX::NonMoose;
use MooseX::MarkAsMethods autoclean => 1;
extends 'DBIx::Class::Core';

=head1 COMPONENTE UTILIZATE

=over 4

=item * L<DBIx::Class::InflateColumn::DateTime>

=back

=cut

__PACKAGE__->load_components("InflateColumn::DateTime");

=head1 TABLE: C<scale>

=cut

__PACKAGE__->table("scale");

=head1 ACCESSORS

=head2 item_id

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

Codul item-ului

=head2 minvalue_id

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

Codul valorii minime (refera atributul itemId din tabelul value) 

=head2 maxvalue_id

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

Codul valorii maxime (refera atributul itemId din tabelul value) 

=head2 units

  data_type: 'smallint'
  is_nullable: 0

Unitatea scalei respective

=cut

__PACKAGE__->add_columns(
  "item_id",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
  "minvalue_id",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
  "maxvalue_id",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
  "units",
  { data_type => "smallint", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</item_id>

=back

=cut

__PACKAGE__->set_primary_key("item_id");

=head1 RELATII

=head2 item

Type: belongs_to

Related object: L<RODA::RODADB::Result::Item>

=cut

__PACKAGE__->belongs_to(
  "item",
  "RODA::RODADB::Result::Item",
  { id => "item_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 maxvalue

Type: belongs_to

Related object: L<RODA::RODADB::Result::Value>

=cut

__PACKAGE__->belongs_to(
  "maxvalue",
  "RODA::RODADB::Result::Value",
  { item_id => "maxvalue_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 minvalue

Type: belongs_to

Related object: L<RODA::RODADB::Result::Value>

=cut

__PACKAGE__->belongs_to(
  "minvalue",
  "RODA::RODADB::Result::Value",
  { item_id => "minvalue_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:YX13C9dMSyCVpz/V8nEoog


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;

1;
