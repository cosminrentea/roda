use utf8;
package RODA::RODADB::Result::EditedVariable;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::EditedVariable

=head1 DESCRIPTION

Tabel ce contine variabile de tip editat (cu raspuns completat de chestionat)

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

=head1 TABLE: C<edited_variable>

=cut

__PACKAGE__->table("edited_variable");

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

=cut

__PACKAGE__->add_columns(
  "instance_id",
  { data_type => "integer", is_nullable => 0 },
  "variable_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</variable_id>

=item * L</instance_id>

=back

=cut

__PACKAGE__->set_primary_key("variable_id", "instance_id");

=head1 RELATIONS

=head2 form_edited_number_vars

Type: has_many

Related object: L<RODA::RODADB::Result::FormEditedNumberVar>

=cut

__PACKAGE__->has_many(
  "form_edited_number_vars",
  "RODA::RODADB::Result::FormEditedNumberVar",
  {
    "foreign.instance_id" => "self.instance_id",
    "foreign.variable_id" => "self.variable_id",
  },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 form_edited_text_vars

Type: has_many

Related object: L<RODA::RODADB::Result::FormEditedTextVar>

=cut

__PACKAGE__->has_many(
  "form_edited_text_vars",
  "RODA::RODADB::Result::FormEditedTextVar",
  {
    "foreign.instance_id" => "self.instance_id",
    "foreign.variable_id" => "self.variable_id",
  },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 other_statistics

Type: has_many

Related object: L<RODA::RODADB::Result::OtherStatistic>

=cut

__PACKAGE__->has_many(
  "other_statistics",
  "RODA::RODADB::Result::OtherStatistic",
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
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:aWEIuXiTMsEizm97spQs+w


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
