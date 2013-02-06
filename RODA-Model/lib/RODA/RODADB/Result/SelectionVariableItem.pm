use utf8;
package RODA::RODADB::Result::SelectionVariableItem;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::SelectionVariableItem

=head1 DESCRIPTION

Tabel ce contine elementele variabilelor de selectie din cadrul unei instante

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

=head1 TABLE: C<selection_variable_item>

=cut

__PACKAGE__->table("selection_variable_item");

=head1 ACCESSORS

=head2 variable_id

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

Codul unei variabile ce apare in cadrul instantei identificate prin atributul instance_id

=head2 item_id

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

Codul unui item de raspuns pentru variabila referita prin atributul variable_id

=head2 order_of_item_in_variable

  data_type: 'integer'
  is_nullable: 0

Numarul de ordine al item-ului referit prin atributul item_id in cadrul variabilei identificate prin atributul variable_id

=head2 response_card_file_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

=head2 frequency_value

  data_type: 'real'
  is_nullable: 1

=cut

__PACKAGE__->add_columns(
  "variable_id",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
  "item_id",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
  "order_of_item_in_variable",
  { data_type => "integer", is_nullable => 0 },
  "response_card_file_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
  "frequency_value",
  { data_type => "real", is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</variable_id>

=item * L</item_id>

=back

=cut

__PACKAGE__->set_primary_key("variable_id", "item_id");

=head1 UNIQUE CONSTRAINTS

=head2 C<selection_variable_item_order_Idx>

=over 4

=item * L</order_of_item_in_variable>

=item * L</variable_id>

=back

=cut

__PACKAGE__->add_unique_constraint(
  "selection_variable_item_order_Idx",
  ["order_of_item_in_variable", "variable_id"],
);

=head1 RELATIONS

=head2 form_selection_vars

Type: has_many

Related object: L<RODA::RODADB::Result::FormSelectionVar>

=cut

__PACKAGE__->has_many(
  "form_selection_vars",
  "RODA::RODADB::Result::FormSelectionVar",
  {
    "foreign.item_id"     => "self.item_id",
    "foreign.variable_id" => "self.variable_id",
  },
  { cascade_copy => 0, cascade_delete => 0 },
);

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

=head2 response_card_file

Type: belongs_to

Related object: L<RODA::RODADB::Result::File>

=cut

__PACKAGE__->belongs_to(
  "response_card_file",
  "RODA::RODADB::Result::File",
  { id => "response_card_file_id" },
  {
    is_deferrable => 0,
    join_type     => "LEFT",
    on_delete     => "NO ACTION",
    on_update     => "NO ACTION",
  },
);

=head2 variable

Type: belongs_to

Related object: L<RODA::RODADB::Result::SelectionVariable>

=cut

__PACKAGE__->belongs_to(
  "variable",
  "RODA::RODADB::Result::SelectionVariable",
  { variable_id => "variable_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:dMj3gvnaP5Ya2nnjfzJWUw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
