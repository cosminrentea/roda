use utf8;
package RODA::RODADB::Result::StudyFile;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::StudyFile

=head1 DESCRIPTION

Tabel ce contine asocierile dintre studii si documente (implementeaza relatia many-to-many intre tabelele study si documents)

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

=head1 TABLE: C<study_file>

=cut

__PACKAGE__->table("study_file");

=head1 ACCESSORS

=head2 study_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul studiului pentru care este stocat documentul specificat prin atributul document_id (refera atributul id din tabelul study)

=head2 file_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul unui document care este asociat studiului identificat prin atributul study_id (refera atributul id din tabelul documents)

=cut

__PACKAGE__->add_columns(
  "study_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "file_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</study_id>

=item * L</file_id>

=back

=cut

__PACKAGE__->set_primary_key("study_id", "file_id");

=head1 RELATIONS

=head2 file

Type: belongs_to

Related object: L<RODA::RODADB::Result::File>

=cut

__PACKAGE__->belongs_to(
  "file",
  "RODA::RODADB::Result::File",
  { id => "file_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 study

Type: belongs_to

Related object: L<RODA::RODADB::Result::Study>

=cut

__PACKAGE__->belongs_to(
  "study",
  "RODA::RODADB::Result::Study",
  { id => "study_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:JrTBzduubu3N/5eMUotc+Q


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
