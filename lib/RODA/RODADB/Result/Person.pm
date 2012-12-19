use utf8;
package RODA::RODADB::Result::Person;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Person - Tabel unic pentru toate persoanele, oriunde ar fi ele

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

=head1 TABLE: C<person>

=cut

__PACKAGE__->table("person");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul persoanei

=head2 fname

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Prenumele persoanei

=head2 mname

  data_type: 'varchar'
  is_nullable: 1
  size: 100

Numele din mijloc al persoanei

=head2 lname

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Numele de familie al persoanei

=head2 prefix_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

Codul prefixului corespunzator persoanei (refera atributul id din tabelul prefix)

=head2 suffix_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

Codul sufixului

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "fname",
  { data_type => "varchar", is_nullable => 0, size => 100 },
  "mname",
  { data_type => "varchar", is_nullable => 1, size => 100 },
  "lname",
  { data_type => "varchar", is_nullable => 0, size => 100 },
  "prefix_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
  "suffix_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
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
  { "foreign.entity_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instance_people

Type: has_many

Related object: L<RODA::RODADB::Result::InstancePerson>

=cut

__PACKAGE__->has_many(
  "instance_people",
  "RODA::RODADB::Result::InstancePerson",
  { "foreign.person_id" => "self.id" },
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

=head2 person_addresses

Type: has_many

Related object: L<RODA::RODADB::Result::PersonAddress>

=cut

__PACKAGE__->has_many(
  "person_addresses",
  "RODA::RODADB::Result::PersonAddress",
  { "foreign.person_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 person_links

Type: has_many

Related object: L<RODA::RODADB::Result::PersonLink>

=cut

__PACKAGE__->has_many(
  "person_links",
  "RODA::RODADB::Result::PersonLink",
  { "foreign.person" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 person_orgs

Type: has_many

Related object: L<RODA::RODADB::Result::PersonOrg>

=cut

__PACKAGE__->has_many(
  "person_orgs",
  "RODA::RODADB::Result::PersonOrg",
  { "foreign.person_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 prefix

Type: belongs_to

Related object: L<RODA::RODADB::Result::Prefix>

=cut

__PACKAGE__->belongs_to(
  "prefix",
  "RODA::RODADB::Result::Prefix",
  { id => "prefix_id" },
  {
    is_deferrable => 1,
    join_type     => "LEFT",
    on_delete     => "CASCADE",
    on_update     => "CASCADE",
  },
);

=head2 source_contacts

Type: has_many

Related object: L<RODA::RODADB::Result::SourceContact>

=cut

__PACKAGE__->has_many(
  "source_contacts",
  "RODA::RODADB::Result::SourceContact",
  { "foreign.person_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_people

Type: has_many

Related object: L<RODA::RODADB::Result::StudyPerson>

=cut

__PACKAGE__->has_many(
  "study_people",
  "RODA::RODADB::Result::StudyPerson",
  { "foreign.person_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 suffix

Type: belongs_to

Related object: L<RODA::RODADB::Result::Suffix>

=cut

__PACKAGE__->belongs_to(
  "suffix",
  "RODA::RODADB::Result::Suffix",
  { id => "suffix_id" },
  {
    is_deferrable => 1,
    join_type     => "LEFT",
    on_delete     => "CASCADE",
    on_update     => "CASCADE",
  },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:m3dmF0cF8U+70SSWuDsSoA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
