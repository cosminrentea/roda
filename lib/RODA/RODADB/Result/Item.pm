use utf8;
package RODA::RODADB::Result::Item;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Item

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

=head1 TABLE: C<item>

=cut

__PACKAGE__->table("item");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Identificatorul item-ului

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 50

Numele item-ului

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
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

=head2 scale

Type: might_have

Related object: L<RODA::RODADB::Result::Scale>

=cut

__PACKAGE__->might_have(
  "scale",
  "RODA::RODADB::Result::Scale",
  { "foreign.itemid" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 selection_variable_items

Type: has_many

Related object: L<RODA::RODADB::Result::SelectionVariableItem>

=cut

__PACKAGE__->has_many(
  "selection_variable_items",
  "RODA::RODADB::Result::SelectionVariableItem",
  { "foreign.item_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 value

Type: might_have

Related object: L<RODA::RODADB::Result::Value>

=cut

__PACKAGE__->might_have(
  "value",
  "RODA::RODADB::Result::Value",
  { "foreign.itemid" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:DM22TLH0JcFGVMLiK3dDhg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
