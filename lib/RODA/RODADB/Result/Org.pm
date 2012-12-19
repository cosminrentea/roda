use utf8;
package RODA::RODADB::Result::Org;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Org - Tabel cu toate organizatiile din baza de date

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

=head1 TABLE: C<org>

=cut

__PACKAGE__->table("org");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul organizatiei

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Denumirea prescurtata a organizatiei (posibil un acronim al acesteia)

=head2 org_prefix_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul prefixului organizatiei (refera atributul id din tabelul org_prefix)

=head2 fullname

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Denumirea completa a organizatiei 

=head2 org_sufix_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul sufixului organizatiei (refera atributul id din tabelul org_sufix)

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 100 },
  "org_prefix_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "fullname",
  { data_type => "varchar", is_nullable => 0, size => 100 },
  "org_sufix_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 emails

Type: has_many

Related object: L<RODA::RODADB::Result::Email>

=cut

__PACKAGE__->has_many(
  "emails",
  "RODA::RODADB::Result::Email",
  { "foreign.entity_type" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instance_orgs

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceOrg>

=cut

__PACKAGE__->has_many(
  "instance_orgs",
  "RODA::RODADB::Result::InstanceOrg",
  { "foreign.org_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 internets

Type: has_many

Related object: L<RODA::RODADB::Result::Internet>

=cut

__PACKAGE__->has_many(
  "internets",
  "RODA::RODADB::Result::Internet",
  { "foreign.entity_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 org_addresses

Type: has_many

Related object: L<RODA::RODADB::Result::OrgAddress>

=cut

__PACKAGE__->has_many(
  "org_addresses",
  "RODA::RODADB::Result::OrgAddress",
  { "foreign.org_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 org_prefix

Type: belongs_to

Related object: L<RODA::RODADB::Result::OrgPrefix>

=cut

__PACKAGE__->belongs_to(
  "org_prefix",
  "RODA::RODADB::Result::OrgPrefix",
  { id => "org_prefix_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 org_relations_org_1s

Type: has_many

Related object: L<RODA::RODADB::Result::OrgRelation>

=cut

__PACKAGE__->has_many(
  "org_relations_org_1s",
  "RODA::RODADB::Result::OrgRelation",
  { "foreign.org_1_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 org_relations_org_2s

Type: has_many

Related object: L<RODA::RODADB::Result::OrgRelation>

=cut

__PACKAGE__->has_many(
  "org_relations_org_2s",
  "RODA::RODADB::Result::OrgRelation",
  { "foreign.org_2_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 org_sufix

Type: belongs_to

Related object: L<RODA::RODADB::Result::OrgSufix>

=cut

__PACKAGE__->belongs_to(
  "org_sufix",
  "RODA::RODADB::Result::OrgSufix",
  { id => "org_sufix_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 person_orgs

Type: has_many

Related object: L<RODA::RODADB::Result::PersonOrg>

=cut

__PACKAGE__->has_many(
  "person_orgs",
  "RODA::RODADB::Result::PersonOrg",
  { "foreign.org_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 source

Type: might_have

Related object: L<RODA::RODADB::Result::Source>

=cut

__PACKAGE__->might_have(
  "source",
  "RODA::RODADB::Result::Source",
  { "foreign.org_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_orgs

Type: has_many

Related object: L<RODA::RODADB::Result::StudyOrg>

=cut

__PACKAGE__->has_many(
  "study_orgs",
  "RODA::RODADB::Result::StudyOrg",
  { "foreign.org_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:UxZgFckBaRHYPpvRMGofoA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
