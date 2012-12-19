use utf8;
package RODA::RODADB::Result::Document;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Document

=head1 DESCRIPTION

Tabel ce contine documentele asociate oricarei entitati din baza de date

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

=head1 TABLE: C<documents>

=cut

__PACKAGE__->table("documents");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul documentului

=head2 title

  data_type: 'varchar'
  is_nullable: 0
  size: 250

Titlul documentului

=head2 description

  data_type: 'text'
  is_nullable: 1

Descrierea documentului

=head2 type_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul tipului documentului (refera atributul id al tabelului document_type)

=head2 filename

  data_type: 'varchar'
  is_nullable: 0
  size: 200

Numele fisierului asociat documentului

=head2 mimetype

  data_type: 'varchar'
  is_nullable: 0
  size: 50

Tipul mime al fisierului

=head2 filesize

  data_type: 'integer'
  is_nullable: 0

Dimensiunea fisierului (specificata in kilobytes)

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "title",
  { data_type => "varchar", is_nullable => 0, size => 250 },
  "description",
  { data_type => "text", is_nullable => 1 },
  "type_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "filename",
  { data_type => "varchar", is_nullable => 0, size => 200 },
  "mimetype",
  { data_type => "varchar", is_nullable => 0, size => 50 },
  "filesize",
  { data_type => "integer", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 documents_acls

Type: has_many

Related object: L<RODA::RODADB::Result::DocumentsAcl>

=cut

__PACKAGE__->has_many(
  "documents_acls",
  "RODA::RODADB::Result::DocumentsAcl",
  { "foreign.document_id" => "self.id" },
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

=head2 study_documents

Type: has_many

Related object: L<RODA::RODADB::Result::StudyDocument>

=cut

__PACKAGE__->has_many(
  "study_documents",
  "RODA::RODADB::Result::StudyDocument",
  { "foreign.document_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 type

Type: belongs_to

Related object: L<RODA::RODADB::Result::DocumentType>

=cut

__PACKAGE__->belongs_to(
  "type",
  "RODA::RODADB::Result::DocumentType",
  { id => "type_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:PQx7TwSPcmArdyyqiwyrUQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
