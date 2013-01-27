use utf8;
package RODA::RODADB::Result::FileAcl;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::FileAcl - Tabel ce contine drepturile de acces asupra documentelor

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

=head1 TABLE: C<file_acl>

=cut

__PACKAGE__->table("file_acl");

=head1 ACCESSORS

=head2 document_id

  data_type: 'integer'
  is_auto_increment: 1
  is_foreign_key: 1
  is_nullable: 0
  sequence: 'file_acl_document_id_seq'

Codul documentului asupra caruia sunt definite drepturi de acces

=head2 aro_id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'file_acl_aro_id_seq'

Codul obiectului care solicita drepturi de acces

=head2 aro_type

  data_type: 'integer'
  is_nullable: 0

Tipul obiectului care solicita drepturi de acces

=head2 read

  data_type: 'boolean'
  is_nullable: 0

Atribut boolean, ce va avea valoarea true daca exista dreptul de citire; altfel, valoarea atributului va fi false

=head2 update

  accessor: 'column_update'
  data_type: 'boolean'
  is_nullable: 0

Atribut boolean, ce va avea valoarea true daca exista dreptul de modificare; altfel, valoarea atributului va fi false

=head2 delete

  accessor: 'column_delete'
  data_type: 'boolean'
  is_nullable: 0

Atribut boolean, ce va avea valoarea true daca exista dreptul de stergere; altfel, valoarea atributului va fi false

=cut

__PACKAGE__->add_columns(
  "document_id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_foreign_key    => 1,
    is_nullable       => 0,
    sequence          => "file_acl_document_id_seq",
  },
  "aro_id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "file_acl_aro_id_seq",
  },
  "aro_type",
  { data_type => "integer", is_nullable => 0 },
  "read",
  { data_type => "boolean", is_nullable => 0 },
  "update",
  { accessor => "column_update", data_type => "boolean", is_nullable => 0 },
  "delete",
  { accessor => "column_delete", data_type => "boolean", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</document_id>

=item * L</aro_id>

=item * L</aro_type>

=back

=cut

__PACKAGE__->set_primary_key("document_id", "aro_id", "aro_type");

=head1 RELATIONS

=head2 document

Type: belongs_to

Related object: L<RODA::RODADB::Result::File>

=cut

__PACKAGE__->belongs_to(
  "document",
  "RODA::RODADB::Result::File",
  { id => "document_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 16:35:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:hGgnFEJh9ThUs9MUdbqRHw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
