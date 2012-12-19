use utf8;
package RODA::RODADB::Result::StudyPerson;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::StudyPerson

=head1 DESCRIPTION

Tabel ce implementeaza relatia many-to-many intre studiu si persoane (realizare, proiectare chestionar, etc.)

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

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul asocierii intre o persoana si un studiu

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

=head2 asoctype_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul tipului de asociere existent intre persoana identificata prin atributul person_id si studiul referit prin atributul study_id

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "person_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "study_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "asoctype_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 asoctype

Type: belongs_to

Related object: L<RODA::RODADB::Result::StudyPersonAsoc>

=cut

__PACKAGE__->belongs_to(
  "asoctype",
  "RODA::RODADB::Result::StudyPersonAsoc",
  { id => "asoctype_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 person

Type: belongs_to

Related object: L<RODA::RODADB::Result::Person>

=cut

__PACKAGE__->belongs_to(
  "person",
  "RODA::RODADB::Result::Person",
  { id => "person_id" },
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

=head2 study_person_acls

Type: has_many

Related object: L<RODA::RODADB::Result::StudyPersonAcl>

=cut

__PACKAGE__->has_many(
  "study_person_acls",
  "RODA::RODADB::Result::StudyPersonAcl",
  { "foreign.study_person_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:nZYACXj+ZftlzuPU9KOJjA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
