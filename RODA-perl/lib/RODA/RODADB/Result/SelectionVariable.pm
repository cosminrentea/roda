use utf8;
package RODA::RODADB::Result::SelectionVariable;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::SelectionVariable

=head1 DESCRIPTION

Tabel ce contine informatii despre variabilele de selectie din instante

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

=head1 TABLE: C<selection_variable>

=cut

__PACKAGE__->table("selection_variable");

=head1 ACCESSORS

=head2 instance_id

  data_type: 'integer'
  is_nullable: 0

Codul instantei

=head2 variable_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul unei variabile de selectie din cadrul instantei identificate prin instance_id

=head2 min_count

  data_type: 'smallint'
  is_nullable: 0

Numarul minim de selectii asteptate ca raspuns

=head2 max_count

  data_type: 'smallint'
  is_nullable: 0

Numarul maxim de selectii asteptate ca raspuns

=cut

__PACKAGE__->add_columns(
  "instance_id",
  { data_type => "integer", is_nullable => 0 },
  "variable_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "min_count",
  { data_type => "smallint", is_nullable => 0 },
  "max_count",
  { data_type => "smallint", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</instance_id>

=item * L</variable_id>

=back

=cut

__PACKAGE__->set_primary_key("instance_id", "variable_id");

=head1 RELATIONS

=head2 selection_variable_items

Type: has_many

Related object: L<RODA::RODADB::Result::SelectionVariableItem>

=cut

__PACKAGE__->has_many(
  "selection_variable_items",
  "RODA::RODADB::Result::SelectionVariableItem",
  {
    "foreign.instance_id" => "self.instance_id",
    "foreign.variable_id" => "self.variable_id",
  },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 variable

Type: belongs_to

Related object: L<RODA::RODADB::Result::Variable>

=cut

__PACKAGE__->belongs_to(
  "variable",
  "RODA::RODADB::Result::Variable",
  { id => "variable_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:HDoYscuFyMe0ahsSX7yzIg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
