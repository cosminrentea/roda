use utf8;
package RODA::RODADB::Result::Instance;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Instance - Tabel ce contine informatiile principale ale instantelor

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

=head1 TABLE: C<instance>

=cut

__PACKAGE__->table("instance");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul instantei

=head2 study_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul studiului caruia ii apartine instanta (refera atributul id al tabelului study)

=head2 datestart

  data_type: 'timestamp'
  is_nullable: 0

Data de inceput a instantei

=head2 dateend

  data_type: 'timestamp'
  is_nullable: 0

Data de incheiere a instantei

=head2 unit_analysis_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul unitatii de analiza specifice instantei (refera atributul id al tabelului unit_analysis)

=head2 version

  data_type: 'smallint'
  is_nullable: 0

Versiunea instantei

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "study_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "datestart",
  { data_type => "timestamp", is_nullable => 0 },
  "dateend",
  { data_type => "timestamp", is_nullable => 0 },
  "unit_analysis_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "version",
  { data_type => "smallint", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 instance_acls

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceAcl>

=cut

__PACKAGE__->has_many(
  "instance_acls",
  "RODA::RODADB::Result::InstanceAcl",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instance_descr

Type: might_have

Related object: L<RODA::RODADB::Result::InstanceDescr>

=cut

__PACKAGE__->might_have(
  "instance_descr",
  "RODA::RODADB::Result::InstanceDescr",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instance_documents

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceDocument>

=cut

__PACKAGE__->has_many(
  "instance_documents",
  "RODA::RODADB::Result::InstanceDocument",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instance_keywords

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceKeyword>

=cut

__PACKAGE__->has_many(
  "instance_keywords",
  "RODA::RODADB::Result::InstanceKeyword",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instance_orgs

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceOrg>

=cut

__PACKAGE__->has_many(
  "instance_orgs",
  "RODA::RODADB::Result::InstanceOrg",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instance_people

Type: has_many

Related object: L<RODA::RODADB::Result::InstancePerson>

=cut

__PACKAGE__->has_many(
  "instance_people",
  "RODA::RODADB::Result::InstancePerson",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instance_topics

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceTopic>

=cut

__PACKAGE__->has_many(
  "instance_topics",
  "RODA::RODADB::Result::InstanceTopic",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 methodology

Type: might_have

Related object: L<RODA::RODADB::Result::Methodology>

=cut

__PACKAGE__->might_have(
  "methodology",
  "RODA::RODADB::Result::Methodology",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
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

=head2 unit_analysis

Type: belongs_to

Related object: L<RODA::RODADB::Result::UnitAnalysis>

=cut

__PACKAGE__->belongs_to(
  "unit_analysis",
  "RODA::RODADB::Result::UnitAnalysis",
  { id => "unit_analysis_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 variables

Type: has_many

Related object: L<RODA::RODADB::Result::Variable>

=cut

__PACKAGE__->has_many(
  "variables",
  "RODA::RODADB::Result::Variable",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:4kZZpmsqqykAAsCbEdforQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
