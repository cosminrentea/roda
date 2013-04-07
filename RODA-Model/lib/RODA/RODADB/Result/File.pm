use utf8;
package RODA::RODADB::Result::File;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NUME

RODA::RODADB::Result::File - Tabel ce contine documentele asociate oricarei entitati din baza de date (studiu sau instanta)

=cut

use strict;
use warnings;

use Moose;
use MooseX::NonMoose;
use MooseX::MarkAsMethods autoclean => 1;
extends 'DBIx::Class::Core';

=head1 COMPONENTE UTILIZATE

=over 4

=item * L<DBIx::Class::InflateColumn::DateTime>

=back

=cut

__PACKAGE__->load_components("InflateColumn::DateTime");

=head1 TABLE: C<file>

=cut

__PACKAGE__->table("file");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'file_id_seq'

Codul documentului

=head2 title

  data_type: 'text'
  is_nullable: 0

Titlul documentului

=head2 description

  data_type: 'text'
  is_nullable: 1

Descrierea documentului

=head2 filetype_id

  data_type: 'integer'
  is_nullable: 1

Codul tipului documentului (refera atributul id al tabelului document_type)

=head2 name

  data_type: 'text'
  is_nullable: 0

Numele fisierului asociat documentului

=head2 size

  data_type: 'bigint'
  is_nullable: 0

Dimensiunea fisierului (specificata in bytes)

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "file_id_seq",
  },
  "title",
  { data_type => "text", is_nullable => 0 },
  "description",
  { data_type => "text", is_nullable => 1 },
  "filetype_id",
  { data_type => "integer", is_nullable => 1 },
  "name",
  { data_type => "text", is_nullable => 0 },
  "size",
  { data_type => "bigint", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATII

=head2 file_acls

Type: has_many

Related object: L<RODA::RODADB::Result::FileAcl>

=cut

__PACKAGE__->has_many(
  "file_acls",
  "RODA::RODADB::Result::FileAcl",
  { "foreign.document_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 file_property_name_values

Type: has_many

Related object: L<RODA::RODADB::Result::FilePropertyNameValue>

=cut

__PACKAGE__->has_many(
  "file_property_name_values",
  "RODA::RODADB::Result::FilePropertyNameValue",
  { "foreign.file_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instance_documents

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceDocument>

=cut

__PACKAGE__->has_many(
  "instance_documents",
  "RODA::RODADB::Result::InstanceDocument",
  { "foreign.document_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 selection_variable_items

Type: has_many

Related object: L<RODA::RODADB::Result::SelectionVariableItem>

=cut

__PACKAGE__->has_many(
  "selection_variable_items",
  "RODA::RODADB::Result::SelectionVariableItem",
  { "foreign.response_card_file_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_files

Type: has_many

Related object: L<RODA::RODADB::Result::StudyFile>

=cut

__PACKAGE__->has_many(
  "study_files",
  "RODA::RODADB::Result::StudyFile",
  { "foreign.file_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 variables

Type: has_many

Related object: L<RODA::RODADB::Result::Variable>

=cut

__PACKAGE__->has_many(
  "variables",
  "RODA::RODADB::Result::Variable",
  { "foreign.file_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instances

Type: many_to_many

Composing rels: L</instance_documents> -> instance

=cut

__PACKAGE__->many_to_many("instances", "instance_documents", "instance");

=head2 studies

Type: many_to_many

Composing rels: L</study_files> -> study

=cut

__PACKAGE__->many_to_many("studies", "study_files", "study");


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:agdYPuOaSijCTDqcZp9wcw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;

=head1 METODE SUPLIMENTARE

=cut

=head2 attach_file_properties

Ataseaza proprietati fisierului curent.

=cut


sub attach_file_properties {
     # TODO
}

=head2 attach_file_acls

Ataseaza o lista de drepturi (acl) fisierului curent. Un element al acestei liste este o structura de tip hash avand urmatoarele chei: aro_id, aro_type, read, update, delete. 

=cut

sub attach_file_acls {
     # TODO
}

1;
