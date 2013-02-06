use utf8;
package RODA::RODADB::Result::PersonAddress;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::PersonAddress

=head1 DESCRIPTION

Tabel ce contine asocierile intre persoane si adrese (implementeaza relatia many-to-many intre tabelele address si person)

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

=head1 TABLE: C<person_address>

=cut

__PACKAGE__->table("person_address");

=head1 ACCESSORS

=head2 person_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul persoanei pentru care este stocata o asociere cu adresa referita prin atributul address_id (refera atributul id din tabelul person)

=head2 address_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul adresei care este asociata persoanei identificate prin atributul person_id (refera atributul id din tabelul address)

=head2 datestart

  data_type: 'timestamp'
  is_nullable: 1

Data incepand de la care persoana identificata prin atributul person_id a avut adresa referita prin atributul address_id

=head2 dateend

  data_type: 'timestamp'
  is_nullable: 1

Data pana la care persoana identificata prin atributul person_id a avut adresa referita prin atributul address_id

=cut

__PACKAGE__->add_columns(
  "person_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "address_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "datestart",
  { data_type => "timestamp", is_nullable => 1 },
  "dateend",
  { data_type => "timestamp", is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</person_id>

=item * L</address_id>

=back

=cut

__PACKAGE__->set_primary_key("person_id", "address_id");

=head1 RELATIONS

=head2 address

Type: belongs_to

Related object: L<RODA::RODADB::Result::Address>

=cut

__PACKAGE__->belongs_to(
  "address",
  "RODA::RODADB::Result::Address",
  { id => "address_id" },
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


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:LjlOR7o8PKC9UKQFuVnhbA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
