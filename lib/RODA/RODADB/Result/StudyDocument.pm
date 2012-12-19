use utf8;
package RODA::RODADB::Result::StudyDocument;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::StudyDocument

=head1 DESCRIPTION

Tabel ce implementeaza relatia many-to-many intre studiu si documente

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

=head1 TABLE: C<study_documents>

=cut

__PACKAGE__->table("study_documents");

=head1 ACCESSORS

=head2 study_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul studiului pentru care este stocat documentul specificat prin atributul document_id (refera atributul id din tabelul study)

=head2 document_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul unui document care este asociat studiului identificat prin atributul study_id (refera atributul id din tabelul documents)

=cut

__PACKAGE__->add_columns(
  "study_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "document_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</study_id>

=item * L</document_id>

=back

=cut

__PACKAGE__->set_primary_key("study_id", "document_id");

=head1 RELATIONS

=head2 document

Type: belongs_to

Related object: L<RODA::RODADB::Result::Document>

=cut

__PACKAGE__->belongs_to(
  "document",
  "RODA::RODADB::Result::Document",
  { id => "document_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 study

Type: belongs_to

Related object: L<RODA::RODADB::Result::Study>

=cut

__PACKAGE__->belongs_to(
  "study",
  "RODA::RODADB::Result::Study",
  { id => "study_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:7WtoQaixLvz/QQEZaciHdQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
