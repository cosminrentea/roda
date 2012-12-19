use utf8;
package RODA::RODADB::Result::Study;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Study

=head1 DESCRIPTION

Tabel care stocheaza studiile desfasurate, ale caror informatii sunt prezente in baza de date 

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

=head1 TABLE: C<study>

=cut

__PACKAGE__->table("study");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul studiului

=head2 datestart

  data_type: 'timestamp'
  is_nullable: 0

Data de inceput a studiului

=head2 dateend

  data_type: 'timestamp'
  is_nullable: 0

Data de final a studiului

=head2 grant_number

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Numarul proiectului care a finantat studiul

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "datestart",
  { data_type => "timestamp", is_nullable => 0 },
  "dateend",
  { data_type => "timestamp", is_nullable => 0 },
  "grant_number",
  { data_type => "varchar", is_nullable => 0, size => 100 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 catalog_studies

Type: has_many

Related object: L<RODA::RODADB::Result::CatalogStudy>

=cut

__PACKAGE__->has_many(
  "catalog_studies",
  "RODA::RODADB::Result::CatalogStudy",
  { "foreign.study_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instances

Type: has_many

Related object: L<RODA::RODADB::Result::Instance>

=cut

__PACKAGE__->has_many(
  "instances",
  "RODA::RODADB::Result::Instance",
  { "foreign.study_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 studiu_keyword

Type: might_have

Related object: L<RODA::RODADB::Result::StudiuKeyword>

=cut

__PACKAGE__->might_have(
  "studiu_keyword",
  "RODA::RODADB::Result::StudiuKeyword",
  { "foreign.studiu_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_acls

Type: has_many

Related object: L<RODA::RODADB::Result::StudyAcl>

=cut

__PACKAGE__->has_many(
  "study_acls",
  "RODA::RODADB::Result::StudyAcl",
  { "foreign.study_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_descr

Type: might_have

Related object: L<RODA::RODADB::Result::StudyDescr>

=cut

__PACKAGE__->might_have(
  "study_descr",
  "RODA::RODADB::Result::StudyDescr",
  { "foreign.study_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_documents

Type: has_many

Related object: L<RODA::RODADB::Result::StudyDocument>

=cut

__PACKAGE__->has_many(
  "study_documents",
  "RODA::RODADB::Result::StudyDocument",
  { "foreign.study_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_orgs

Type: has_many

Related object: L<RODA::RODADB::Result::StudyOrg>

=cut

__PACKAGE__->has_many(
  "study_orgs",
  "RODA::RODADB::Result::StudyOrg",
  { "foreign.study_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_people

Type: has_many

Related object: L<RODA::RODADB::Result::StudyPerson>

=cut

__PACKAGE__->has_many(
  "study_people",
  "RODA::RODADB::Result::StudyPerson",
  { "foreign.study_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 titles

Type: has_many

Related object: L<RODA::RODADB::Result::Title>

=cut

__PACKAGE__->has_many(
  "titles",
  "RODA::RODADB::Result::Title",
  { "foreign.study_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:Fyavo4EJt5KvF071F4P6Rw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
