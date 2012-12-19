use utf8;
package RODA::RODADB::Result::StudyOrg;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::StudyOrg

=head1 DESCRIPTION

Tabel asociativ ce implementeaza relatia many-to-many intre studiu si organizatie; aici sunt stocate toate organizatiile care au legatura cu studiul, finantator, realizator, arhivar, etc.

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

=head1 TABLE: C<study_org>

=cut

__PACKAGE__->table("study_org");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul asocierii dintre o organizatie si un studiu

=head2 org_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul organizatiei care se afla in relatia specificata prin atributul assoctype_id cu studiul identificat prin study_id (refera atributul id al tabelului org)

=head2 study_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul studiului care se afla in relatia specificata prin atributul assoctype_id cu organizatia identificata prin org_id (refera atributul id al tabelului study)

=head2 assoctype_id

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

Codul tipului de asociere existent intre studiul identificat prin study_id si organizatia referita prin org_id (refera atributul id din tabelul study_org_assoc)

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "org_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "study_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "assoctype_id",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 assoctype

Type: belongs_to

Related object: L<RODA::RODADB::Result::StudyOrgAssoc>

=cut

__PACKAGE__->belongs_to(
  "assoctype",
  "RODA::RODADB::Result::StudyOrgAssoc",
  { id => "assoctype_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 org

Type: belongs_to

Related object: L<RODA::RODADB::Result::Org>

=cut

__PACKAGE__->belongs_to(
  "org",
  "RODA::RODADB::Result::Org",
  { id => "org_id" },
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

=head2 study_org_acls

Type: has_many

Related object: L<RODA::RODADB::Result::StudyOrgAcl>

=cut

__PACKAGE__->has_many(
  "study_org_acls",
  "RODA::RODADB::Result::StudyOrgAcl",
  { "foreign.study_org_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:cMqQnLglMSwTinkjHcjxAA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
