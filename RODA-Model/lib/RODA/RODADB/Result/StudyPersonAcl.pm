use utf8;
package RODA::RODADB::Result::StudyPersonAcl;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::StudyPersonAcl

=head1 DESCRIPTION

Tabel ce contine drepturile de acces asupra asocierilor dintre persoanelor si studii

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

=head1 TABLE: C<study_person_acl>

=cut

__PACKAGE__->table("study_person_acl");

=head1 ACCESSORS

=head2 study_person_id

  data_type: 'integer'
  is_auto_increment: 1
  is_foreign_key: 1
  is_nullable: 0
  sequence: 'study_person_acl_study_person_id_seq'

Codul unei asocieri intre un studiu si o persoana (refera atributul id din tabelul study_person)

=head2 aro_id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'study_person_acl_aro_id_seq'

Codul unui obiect care solicita drepturi de acces

=head2 aro_type

  data_type: 'integer'
  is_nullable: 0

Tipul unui obiect care solicita drepturi de acces

=head2 read

  data_type: 'boolean'
  is_nullable: 1

Atribut boolean, a carui valoare este true daca exista drept de citire; altfel, valoarea atributului este false

=head2 update

  accessor: 'column_update'
  data_type: 'boolean'
  is_nullable: 1

Atribut boolean, a carui valoare este true daca exista drept de modificare; altfel, valoarea atributului este false

=head2 delete

  accessor: 'column_delete'
  data_type: 'boolean'
  is_nullable: 1

Atribut boolean, a carui valoare este true daca exista drept de stergere; altfel, valoarea atributului este false

=head2 modacl

  data_type: 'boolean'
  is_nullable: 1

Atribut boolean, a carui valoare este true daca drepturile pot fi modificate; altfel, valoarea atributului este false

=cut

__PACKAGE__->add_columns(
  "study_person_id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_foreign_key    => 1,
    is_nullable       => 0,
    sequence          => "study_person_acl_study_person_id_seq",
  },
  "aro_id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "study_person_acl_aro_id_seq",
  },
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
);

=head1 PRIMARY KEY

=over 4

=item * L</study_person_id>

=item * L</aro_id>

=item * L</aro_type>

=back

=cut

__PACKAGE__->set_primary_key("study_person_id", "aro_id", "aro_type");

=head1 RELATIONS

=head2 study_person

Type: belongs_to

Related object: L<RODA::RODADB::Result::StudyPerson>

=cut

__PACKAGE__->belongs_to(
  "study_person",
  "RODA::RODADB::Result::StudyPerson",
  { id => "study_person_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 16:35:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:4qubio85KHTvXJwxCnZ2VQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
