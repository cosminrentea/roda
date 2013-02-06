use utf8;
package RODA::RODADB::Result::InstanceVarGroup;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::InstanceVarGroup - Tabel ce asociaza variabile unor grupuri

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

=head1 TABLE: C<instance_var_group>

=cut

__PACKAGE__->table("instance_var_group");

=head1 ACCESSORS

=head2 instance_id

  data_type: 'integer'
  is_nullable: 0

Identificatorul instantei

=head2 variable_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Identificatorul variabilei

=head2 group_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Identificatorul grupului

=cut

__PACKAGE__->add_columns(
  "instance_id",
  { data_type => "integer", is_nullable => 0 },
  "variable_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "group_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</instance_id>

=item * L</variable_id>

=back

=cut

__PACKAGE__->set_primary_key("instance_id", "variable_id");

=head1 UNIQUE CONSTRAINTS

=head2 C<instance_var_group_instance_id_group_id_Idx>

=over 4

=item * L</instance_id>

=item * L</group_id>

=back

=cut

__PACKAGE__->add_unique_constraint(
  "instance_var_group_instance_id_group_id_Idx",
  ["instance_id", "group_id"],
);

=head1 RELATIONS

=head2 group

Type: belongs_to

Related object: L<RODA::RODADB::Result::VariableGroup>

=cut

__PACKAGE__->belongs_to(
  "group",
  "RODA::RODADB::Result::VariableGroup",
  { id => "group_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
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
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:pAK7OD8cfy/LuHvQgQ6RhA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
