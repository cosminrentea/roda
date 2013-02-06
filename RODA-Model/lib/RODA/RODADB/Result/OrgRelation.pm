use utf8;
package RODA::RODADB::Result::OrgRelation;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::OrgRelation

=head1 DESCRIPTION

Tabel ce stocheaza relatiile dintre organizatii (filiala, subsidiare, holding)

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

=head1 TABLE: C<org_relations>

=cut

__PACKAGE__->table("org_relations");

=head1 ACCESSORS

=head2 org_1_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul primei organizatii implicate in relatie

=head2 org_2_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul celei de-a doua organizatii implicate in relatie

=head2 datestart

  data_type: 'timestamp'
  is_nullable: 1

Data de inceput a relatiei specificate prin tipul org_relation_type_id intre organizatiile specificate prin org_1_id si org_2_id

=head2 dateend

  data_type: 'timestamp'
  is_nullable: 1

Data de final a relatiei specificate prin tipul org_relation_type intre organizatiile specificate prin org_1_id si org_2_id

=head2 org_relation_type_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul tipului relatiei demarate la datestart intre organizatiile specificate prin org_1_id si org_2_id 

=head2 details

  data_type: 'text'
  is_nullable: 0

Detaliile relatiei demarate la datestart intre organizatiile specificate prin org_1_id si org_2_id

=cut

__PACKAGE__->add_columns(
  "org_1_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "org_2_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "datestart",
  { data_type => "timestamp", is_nullable => 1 },
  "dateend",
  { data_type => "timestamp", is_nullable => 1 },
  "org_relation_type_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "details",
  { data_type => "text", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</org_1_id>

=item * L</org_2_id>

=item * L</org_relation_type_id>

=back

=cut

__PACKAGE__->set_primary_key("org_1_id", "org_2_id", "org_relation_type_id");

=head1 RELATIONS

=head2 org_1

Type: belongs_to

Related object: L<RODA::RODADB::Result::Org>

=cut

__PACKAGE__->belongs_to(
  "org_1",
  "RODA::RODADB::Result::Org",
  { id => "org_1_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 org_2

Type: belongs_to

Related object: L<RODA::RODADB::Result::Org>

=cut

__PACKAGE__->belongs_to(
  "org_2",
  "RODA::RODADB::Result::Org",
  { id => "org_2_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 org_relation_type

Type: belongs_to

Related object: L<RODA::RODADB::Result::OrgRelationType>

=cut

__PACKAGE__->belongs_to(
  "org_relation_type",
  "RODA::RODADB::Result::OrgRelationType",
  { id => "org_relation_type_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:Zt44rlq7Dot8LoJapFtvTA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
