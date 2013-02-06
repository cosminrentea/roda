use utf8;
package RODA::RODADB::Result::Value;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Value

=head1 DESCRIPTION

Tabel ce stocheaza valorile posibile ale item-urilor variabilelor de selectie

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

=head1 TABLE: C<value>

=cut

__PACKAGE__->table("value");

=head1 ACCESSORS

=head2 item_id

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

Codul elementului unei variabile de selectie

=head2 value

  data_type: 'integer'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "item_id",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
  "value",
  { data_type => "integer", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</item_id>

=back

=cut

__PACKAGE__->set_primary_key("item_id");

=head1 RELATIONS

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

=head2 scale_maxvalues

Type: has_many

Related object: L<RODA::RODADB::Result::Scale>

=cut

__PACKAGE__->has_many(
  "scale_maxvalues",
  "RODA::RODADB::Result::Scale",
  { "foreign.maxvalue_id" => "self.item_id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 scale_minvalues

Type: has_many

Related object: L<RODA::RODADB::Result::Scale>

=cut

__PACKAGE__->has_many(
  "scale_minvalues",
  "RODA::RODADB::Result::Scale",
  { "foreign.minvalue_id" => "self.item_id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:jNy1wB/KtpMkCRW6Qub9Sg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
