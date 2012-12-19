use utf8;
package RODA::RODADB::Result::SelectionVariableItem;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::SelectionVariableItem

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

=head2 instance_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul instantei

=head2 variable_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul unei variabile ce apare in cadrul instantei identificate prin atributul instance_id

=head2 item_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul unui item de raspuns pentru variabila referita prin atributul variable_id

=head2 order

  data_type: 'smallint'
  is_nullable: 0

Numarul de ordine al item-ului referit prin atributul item_id in cadrul variabilei identificate prin atributul variable_id

=cut

__PACKAGE__->add_columns(
  "instance_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "variable_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "item_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "order",
  { data_type => "smallint", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</variable_id>

=item * L</item_id>

=item * L</instance_id>

=back

=cut

__PACKAGE__->set_primary_key("variable_id", "item_id", "instance_id");

=head1 RELATIONS

=head2 form_selection_vars

Type: has_many

Related object: L<RODA::RODADB::Result::FormSelectionVar>

=cut

__PACKAGE__->has_many(
  "form_selection_vars",
  "RODA::RODADB::Result::FormSelectionVar",
  {
    "foreign.instance_id" => "self.instance_id",
    "foreign.item_id"     => "self.item_id",
    "foreign.variable_id" => "self.variable_id",
  },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 frequencies

Type: has_many

Related object: L<RODA::RODADB::Result::Frequency>

=cut

__PACKAGE__->has_many(
  "frequencies",
  "RODA::RODADB::Result::Frequency",
  {
    "foreign.instance_id" => "self.instance_id",
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
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 selection_variable

Type: belongs_to

Related object: L<RODA::RODADB::Result::SelectionVariable>

=cut

__PACKAGE__->belongs_to(
  "selection_variable",
  "RODA::RODADB::Result::SelectionVariable",
  { instance_id => "instance_id", variable_id => "variable_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 selection_variable_cards

Type: has_many

Related object: L<RODA::RODADB::Result::SelectionVariableCard>

=cut

__PACKAGE__->has_many(
  "selection_variable_cards",
  "RODA::RODADB::Result::SelectionVariableCard",
  {
    "foreign.instance_id" => "self.instance_id",
    "foreign.item_id"     => "self.item_id",
    "foreign.variable_id" => "self.variable_id",
  },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:JyT8jgl6o3pLFbw7JOUrIg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
