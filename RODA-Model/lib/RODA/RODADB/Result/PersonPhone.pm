use utf8;
package RODA::RODADB::Result::PersonPhone;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::PersonPhone

=head1 DESCRIPTION

Tabel de legatura pentru relatia de tip NxM intre "Person" si "Phone"

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

=head1 TABLE: C<person_phone>

=cut

__PACKAGE__->table("person_phone");

=head1 ACCESSORS

=head2 person_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 phone_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 main

  data_type: 'boolean'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "person_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "phone_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "main",
  { data_type => "boolean", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</person_id>

=item * L</phone_id>

=back

=cut

__PACKAGE__->set_primary_key("person_id", "phone_id");

=head1 RELATIONS

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

=head2 phone

Type: belongs_to

Related object: L<RODA::RODADB::Result::Phone>

=cut

__PACKAGE__->belongs_to(
  "phone",
  "RODA::RODADB::Result::Phone",
  { id => "phone_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:DbAnnUZ9+5HDkIxaWznlKQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;