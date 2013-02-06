use utf8;
package RODA::RODADB::Result::VariableVargroup;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::VariableVargroup

=head1 DESCRIPTION

Tabel ce asociaza variabile unor grupuri in cadrul instantelor

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

=head1 TABLE: C<variable_vargroup>

=cut

__PACKAGE__->table("variable_vargroup");

=head1 ACCESSORS

=head2 variable_id

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

Codul variabilei in cadrul instantei instance_id

=head2 vargroup_id

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

Codul grupului caruia ii apartine variabila variable_id din instanta instance_id

=cut

__PACKAGE__->add_columns(
  "variable_id",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
  "vargroup_id",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</vargroup_id>

=item * L</variable_id>

=back

=cut

__PACKAGE__->set_primary_key("vargroup_id", "variable_id");

=head1 RELATIONS

=head2 vargroup

Type: belongs_to

Related object: L<RODA::RODADB::Result::Vargroup>

=cut

__PACKAGE__->belongs_to(
  "vargroup",
  "RODA::RODADB::Result::Vargroup",
  { id => "vargroup_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 variable

Type: belongs_to

Related object: L<RODA::RODADB::Result::Variable>

=cut

__PACKAGE__->belongs_to(
  "variable",
  "RODA::RODADB::Result::Variable",
  { id => "variable_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:lhp9gf97y3fgkKelgXGs/g


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
