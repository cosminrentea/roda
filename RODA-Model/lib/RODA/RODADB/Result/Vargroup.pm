use utf8;
package RODA::RODADB::Result::Vargroup;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Vargroup

=head1 DESCRIPTION

Tabel pentru definirea gruparilor de variabile (pentru o organizare mai buna a lor)

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

=head1 TABLE: C<vargroup>

=cut

__PACKAGE__->table("vargroup");

=head1 ACCESSORS

=head2 id

  data_type: 'bigint'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'vargroup_id_seq'

Identificatorul

=head2 name

  data_type: 'text'
  is_nullable: 0

Denumirea grupului

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "bigint",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "vargroup_id_seq",
  },
  "name",
  { data_type => "text", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 variable_vargroups

Type: has_many

Related object: L<RODA::RODADB::Result::VariableVargroup>

=cut

__PACKAGE__->has_many(
  "variable_vargroups",
  "RODA::RODADB::Result::VariableVargroup",
  { "foreign.vargroup_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 variables

Type: many_to_many

Composing rels: L</variable_vargroups> -> variable

=cut

__PACKAGE__->many_to_many("variables", "variable_vargroups", "variable");


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:SH7Guzvsy+QNvQm8HfB0bw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
