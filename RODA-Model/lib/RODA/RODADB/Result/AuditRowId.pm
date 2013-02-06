use utf8;
package RODA::RODADB::Result::AuditRowId;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::AuditRowId

=head1 DESCRIPTION

Tabel ce stocheaza valorile identificatorilor liniilor pentru care sunt retinute informatii de audit

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

=head1 TABLE: C<audit_row_id>

=cut

__PACKAGE__->table("audit_row_id");

=head1 ACCESSORS

=head2 audit_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul informatiei de audit (refera atributul id din tabelul audit)

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'audit_row_id_id_seq'

Codul coloanei care face parte din cheia primara a tabelului, in cadrul acestei informatii de audit

=head2 column_name

  data_type: 'text'
  is_nullable: 0

Numele coloanei care face parte din identificatorul (cheia primara) a tabelului asupra caruia are loc o operatie LMD

=head2 column_value

  data_type: 'integer'
  is_nullable: 1

Valoarea coloanei column_name pe linia pentru care este inregistrata o informatie de audit

=cut

__PACKAGE__->add_columns(
  "audit_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "audit_row_id_id_seq",
  },
  "column_name",
  { data_type => "text", is_nullable => 0 },
  "column_value",
  { data_type => "integer", is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=item * L</audit_id>

=back

=cut

__PACKAGE__->set_primary_key("id", "audit_id");

=head1 RELATIONS

=head2 audit

Type: belongs_to

Related object: L<RODA::RODADB::Result::Audit>

=cut

__PACKAGE__->belongs_to(
  "audit",
  "RODA::RODADB::Result::Audit",
  { id => "audit_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:cBYrmZ7ATxP0O8PYfdDXxA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
