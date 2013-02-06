use utf8;
package RODA::RODADB::Result::Audit;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Audit

=head1 DESCRIPTION

Tabel ce stocheaza informatiile referitoare la auditul bazei de date

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

=head1 TABLE: C<audit>

=cut

__PACKAGE__->table("audit");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'audit_id_seq'

Codul informatiei de audit

=head2 time

  data_type: 'timestamp'
  is_nullable: 0

Timpul la care a avut loc operatia

=head2 operationtype

  data_type: 'smallint'
  is_nullable: 0

Tipul operatiei (inserare, actualizare, stergere)

=head2 tablename

  data_type: 'text'
  is_nullable: 0

Numele tabelului asupra caruia a avut loc operatia

=head2 user_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul utilizatorului care a efectuat operatia (refera atributul id din tabelul users)

=head2 version_number

  data_type: 'integer'
  is_nullable: 0

Numarul versiunii liniei in tabelul din care face parte

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "audit_id_seq",
  },
  "time",
  { data_type => "timestamp", is_nullable => 0 },
  "operationtype",
  { data_type => "smallint", is_nullable => 0 },
  "tablename",
  { data_type => "text", is_nullable => 0 },
  "user_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "version_number",
  { data_type => "integer", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 audit_fields

Type: has_many

Related object: L<RODA::RODADB::Result::AuditField>

=cut

__PACKAGE__->has_many(
  "audit_fields",
  "RODA::RODADB::Result::AuditField",
  { "foreign.audit_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 audit_row_ids

Type: has_many

Related object: L<RODA::RODADB::Result::AuditRowId>

=cut

__PACKAGE__->has_many(
  "audit_row_ids",
  "RODA::RODADB::Result::AuditRowId",
  { "foreign.audit_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 user

Type: belongs_to

Related object: L<RODA::RODADB::Result::User>

=cut

__PACKAGE__->belongs_to(
  "user",
  "RODA::RODADB::Result::User",
  { id => "user_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:RbVAv1F++UmphasG+aEMzg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
