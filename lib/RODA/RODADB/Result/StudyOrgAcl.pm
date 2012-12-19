use utf8;
package RODA::RODADB::Result::StudyOrgAcl;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::StudyOrgAcl

=head1 DESCRIPTION

Tabel ce contine drepturile de acces ale unei organizatii asupra unui studiu

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

=head1 TABLE: C<study_org_acl>

=cut

__PACKAGE__->table("study_org_acl");

=head1 ACCESSORS

=head2 study_org_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul asocierii dintre o organizatie si un studiu (refera atributul id din tabelul study_org)

=head2 aro_id

  data_type: 'integer'
  is_nullable: 0

Codul unui obiect care solicita drepturi de acces

=head2 aro_type

  data_type: 'integer'
  is_nullable: 0

Tipul unui obiect care solicita drepturi de acces

=head2 read

  data_type: 'boolean'
  is_nullable: 1

Atribut boolean, a carui valoare este true daca exista drept de citire; false, altfel

=head2 update

  accessor: 'column_update'
  data_type: 'boolean'
  is_nullable: 1

Atribut boolean, a carui valoare este true daca exista drept de modificare; false, altfel

=head2 delete

  accessor: 'column_delete'
  data_type: 'boolean'
  is_nullable: 1

Atribut boolean, a carui valoare este true daca exista drept de stergere; false, altfel

=head2 modacl

  data_type: 'boolean'
  is_nullable: 1

=head2 study_id

  data_type: 'integer'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "study_org_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "aro_id",
  { data_type => "integer", is_nullable => 0 },
  "aro_type",
  { data_type => "integer", is_nullable => 0 },
  "read",
  { data_type => "boolean", is_nullable => 1 },
  "update",
  { accessor => "column_update", data_type => "boolean", is_nullable => 1 },
  "delete",
  { accessor => "column_delete", data_type => "boolean", is_nullable => 1 },
  "modacl",
  { data_type => "boolean", is_nullable => 1 },
  "study_id",
  { data_type => "integer", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</study_org_id>

=item * L</aro_id>

=back

=cut

__PACKAGE__->set_primary_key("study_org_id", "aro_id");

=head1 RELATIONS

=head2 study_org

Type: belongs_to

Related object: L<RODA::RODADB::Result::StudyOrg>

=cut

__PACKAGE__->belongs_to(
  "study_org",
  "RODA::RODADB::Result::StudyOrg",
  { id => "study_org_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:TlZMhj0qUAsBnspxzI6NAg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
