use utf8;
package RODA::RODADB::Result::StudyPerson;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::StudyPerson

=head1 DESCRIPTION

Tabel ce contine asocierile dintre intre studiu si persoane: realizare, proiectare chestionar etc. (implementeaza relatia many-to-many intre tabelele person si study)

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

=head1 TABLE: C<study_person>

=cut

__PACKAGE__->table("study_person");

=head1 ACCESSORS

=head2 person_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul persoanei care este in relatia specificata prin atributul asoctype_id cu studiul referit prin atributul study_id (refera atributul id din tabelul person)

=head2 study_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul studiului aflat in relatia identificata prin asoctype_id cu persoana referita prin atributul person_id (refera atributul id din tabelul study)

=head2 assoctype_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul tipului de asociere existent intre persoana identificata prin atributul person_id si studiul referit prin atributul study_id

=head2 assoc_details

  data_type: 'text'
  is_nullable: 1

=cut

__PACKAGE__->add_columns(
  "person_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "study_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "assoctype_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "assoc_details",
  { data_type => "text", is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</person_id>

=item * L</study_id>

=item * L</assoctype_id>

=back

=cut

__PACKAGE__->set_primary_key("person_id", "study_id", "assoctype_id");

=head1 RELATIONS

=head2 assoctype

Type: belongs_to

Related object: L<RODA::RODADB::Result::StudyPersonAssoc>

=cut

__PACKAGE__->belongs_to(
  "assoctype",
  "RODA::RODADB::Result::StudyPersonAssoc",
  { id => "assoctype_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 person

Type: belongs_to

Related object: L<RODA::RODADB::Result::Person>

=cut

__PACKAGE__->belongs_to(
  "person",
  "RODA::RODADB::Result::Person",
  { id => "person_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 study

Type: belongs_to

Related object: L<RODA::RODADB::Result::Study>

=cut

__PACKAGE__->belongs_to(
  "study",
  "RODA::RODADB::Result::Study",
  { id => "study_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:ihq+XuCO55V5gPOGZwpnkg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
