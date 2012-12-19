use utf8;
package RODA::RODADB::Result::StudyOrgAssoc;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::StudyOrgAssoc

=head1 DESCRIPTION

tipurile de asociere dintre studiu si organizatie (finantare, realizare)

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

=head1 TABLE: C<study_org_assoc>

=cut

__PACKAGE__->table("study_org_assoc");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul unei asocieri care poate exista intre un studiu si o organizatie

=head2 assoc_name

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Denumirea unei asocieri care poate exista intre un studiu si o organizatie (producator, finantator etc.)

=head2 assoc_description

  data_type: 'bigint'
  is_nullable: 1

Descrierea asocierii

=head2 assoc_details

  data_type: 'text'
  is_nullable: 1

Detaliile asocierii

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "assoc_name",
  { data_type => "varchar", is_nullable => 0, size => 100 },
  "assoc_description",
  { data_type => "bigint", is_nullable => 1 },
  "assoc_details",
  { data_type => "text", is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 study_orgs

Type: has_many

Related object: L<RODA::RODADB::Result::StudyOrg>

=cut

__PACKAGE__->has_many(
  "study_orgs",
  "RODA::RODADB::Result::StudyOrg",
  { "foreign.assoctype_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:xs9OqgYXbx+YsZXuARUiKQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
