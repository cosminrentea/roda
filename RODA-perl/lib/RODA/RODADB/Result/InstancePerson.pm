use utf8;
package RODA::RODADB::Result::InstancePerson;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::InstancePerson

=head1 DESCRIPTION

Tabel care implementeaza relatia many-to-many intre persoane si instanta; tabelul stocheaza toate relatiile intre instanta si persoana

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

=head1 TABLE: C<instance_person>

=cut

__PACKAGE__->table("instance_person");

=head1 ACCESSORS

=head2 person_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul persoanei aflate in relatia specificata prin atributul assoc_type_id cu instanta identificata prin atributul instance_id (refera atributul id al tabelului person)

=head2 instance_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul instantei care se afla in relatia specificata prin atributul assoc_type_id cu instanta identificata prin atributul instance_id (refera atributul id al tabelului instance)

=head2 assoc_type_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul tipului de asociere care exista intre instanta specificata prin atributul instance_id si persoana identificata prin atributul person_id (refera atributul id al tabelului instance_person_assoc)

=cut

__PACKAGE__->add_columns(
  "person_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "instance_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "assoc_type_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</person_id>

=item * L</instance_id>

=back

=cut

__PACKAGE__->set_primary_key("person_id", "instance_id");

=head1 RELATIONS

=head2 assoc_type

Type: belongs_to

Related object: L<RODA::RODADB::Result::InstancePersonAssoc>

=cut

__PACKAGE__->belongs_to(
  "assoc_type",
  "RODA::RODADB::Result::InstancePersonAssoc",
  { id => "assoc_type_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 instance

Type: belongs_to

Related object: L<RODA::RODADB::Result::Instance>

=cut

__PACKAGE__->belongs_to(
  "instance",
  "RODA::RODADB::Result::Instance",
  { id => "instance_id" },
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


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:KmX6wGMoLxz7uMmHNOGRYQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
