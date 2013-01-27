use utf8;
package RODA::RODADB::Result::StudyPersonAssoc;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::StudyPersonAssoc

=head1 DESCRIPTION

Tabel ce contine tipurile de asocieri care pot exista intre studiu si persoane

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

=head1 TABLE: C<study_person_assoc>

=cut

__PACKAGE__->table("study_person_assoc");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'study_person_assoc_id_seq'

Codul unei asocieri care poate exista intre un studiu si o persoana

=head2 asoc_name

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Numele unei asocieri care poate exista intre un studiu si o persoana

=head2 asoc_description

  data_type: 'text'
  is_nullable: 1

Descrierea asocierii

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "study_person_assoc_id_seq",
  },
  "asoc_name",
  { data_type => "varchar", is_nullable => 0, size => 100 },
  "asoc_description",
  { data_type => "text", is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 study_people

Type: has_many

Related object: L<RODA::RODADB::Result::StudyPerson>

=cut

__PACKAGE__->has_many(
  "study_people",
  "RODA::RODADB::Result::StudyPerson",
  { "foreign.assoctype_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 16:35:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:1kLIEtwTIgbU/+5PoapxTA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
