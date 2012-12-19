use utf8;
package RODA::RODADB::Result::Variable;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Variable - Tabel care stocheaza variabilele din cadrul instantelor

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

=head1 TABLE: C<variable>

=cut

__PACKAGE__->table("variable");

=head1 ACCESSORS

=head2 instance_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Identificatorul instantei in care este definita variabila

=head2 id

  data_type: 'integer'
  is_nullable: 0

Identificatorul

=head2 label

  data_type: 'varchar'
  is_nullable: 0
  size: 300

Reprezentarea textuala a variabilei (numele)

=head2 type

  data_type: 'smallint'
  is_nullable: 0

Tipul de variabila := constanta a unei enumeratii

=head2 order

  data_type: 'integer'
  is_nullable: 0

Intregul ordinal reprezentand pozitia variabilei in secventa de variabile care definesc instanta

=head2 operator_instructions

  data_type: 'varchar'
  is_nullable: 1
  size: 300

Text care informeaza operatorul ce chestioneaza asupra unor actiuni pe care trebuie sa le faca atunci cand ajunge la variabila aceasta

=head2 file_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Fisierul din care provine variabila (impreuna cu instance_id, refera atributele instance_id, document_id din tabelul instance_documents)

=cut

__PACKAGE__->add_columns(
  "instance_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "id",
  { data_type => "integer", is_nullable => 0 },
  "label",
  { data_type => "varchar", is_nullable => 0, size => 300 },
  "type",
  { data_type => "smallint", is_nullable => 0 },
  "order",
  { data_type => "integer", is_nullable => 0 },
  "operator_instructions",
  { data_type => "varchar", is_nullable => 1, size => 300 },
  "file_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 concept_variables

Type: has_many

Related object: L<RODA::RODADB::Result::ConceptVariable>

=cut

__PACKAGE__->has_many(
  "concept_variables",
  "RODA::RODADB::Result::ConceptVariable",
  { "foreign.variable_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 edited_variables

Type: has_many

Related object: L<RODA::RODADB::Result::EditedVariable>

=cut

__PACKAGE__->has_many(
  "edited_variables",
  "RODA::RODADB::Result::EditedVariable",
  { "foreign.variable_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instance

Type: belongs_to

Related object: L<RODA::RODADB::Result::Instance>

=cut

__PACKAGE__->belongs_to(
  "instance",
  "RODA::RODADB::Result::Instance",
  { id => "instance_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 instance_document

Type: belongs_to

Related object: L<RODA::RODADB::Result::InstanceDocument>

=cut

__PACKAGE__->belongs_to(
  "instance_document",
  "RODA::RODADB::Result::InstanceDocument",
  { document_id => "file_id", instance_id => "instance_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 instance_var_groups

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceVarGroup>

=cut

__PACKAGE__->has_many(
  "instance_var_groups",
  "RODA::RODADB::Result::InstanceVarGroup",
  { "foreign.variable_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 selection_variables

Type: has_many

Related object: L<RODA::RODADB::Result::SelectionVariable>

=cut

__PACKAGE__->has_many(
  "selection_variables",
  "RODA::RODADB::Result::SelectionVariable",
  { "foreign.variable_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 skip_next_variables

Type: has_many

Related object: L<RODA::RODADB::Result::Skip>

=cut

__PACKAGE__->has_many(
  "skip_next_variables",
  "RODA::RODADB::Result::Skip",
  { "foreign.next_variable_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 skip_variables

Type: has_many

Related object: L<RODA::RODADB::Result::Skip>

=cut

__PACKAGE__->has_many(
  "skip_variables",
  "RODA::RODADB::Result::Skip",
  { "foreign.variable_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:lbK4LiBxg8l8maX1cGCPlw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
