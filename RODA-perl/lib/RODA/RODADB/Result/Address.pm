use utf8;
package RODA::RODADB::Result::Address;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Address

=head1 DESCRIPTION

Tabel unic pentru toate adresele care se gasesc in baza de date

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

=head1 TABLE: C<address>

=cut

__PACKAGE__->table("address");

=head1 ACCESSORS

=head2 city_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 address1

  data_type: 'varchar'
  is_nullable: 0
  size: 250

=head2 address2

  data_type: 'varchar'
  is_nullable: 0
  size: 250

Codul orasului corespunzator adresei (refera atributul id din tabelul city)

=head2 subdiv_code

  data_type: 'varchar'
  is_nullable: 0
  size: 50

Prima linie continand detaliile adresei (de exemplu, strada, numar, bloc, scara, apartament)

=head2 postal_code

  data_type: 'varchar'
  is_nullable: 0
  size: 50

A doua linie continand detaliile adresei

=head2 country_id

  data_type: 'char'
  is_foreign_key: 1
  is_nullable: 1
  size: 2

Sectorul corespunzator adresei

=head2 subdiv_name

  data_type: 'varchar'
  is_nullable: 1
  size: 3

Codul postal al adresei

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'address_id_seq'

=cut

__PACKAGE__->add_columns(
  "city_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "address1",
  { data_type => "varchar", is_nullable => 0, size => 250 },
  "address2",
  { data_type => "varchar", is_nullable => 0, size => 250 },
  "subdiv_code",
  { data_type => "varchar", is_nullable => 0, size => 50 },
  "postal_code",
  { data_type => "varchar", is_nullable => 0, size => 50 },
  "country_id",
  { data_type => "char", is_foreign_key => 1, is_nullable => 1, size => 2 },
  "subdiv_name",
  { data_type => "varchar", is_nullable => 1, size => 20 },
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "address_id_seq",
  },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 city

Type: belongs_to

Related object: L<RODA::RODADB::Result::City>

=cut

__PACKAGE__->belongs_to(
  "city",
  "RODA::RODADB::Result::City",
  { id => "city_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 country

Type: belongs_to

Related object: L<RODA::RODADB::Result::Country>

=cut

__PACKAGE__->belongs_to(
  "country",
  "RODA::RODADB::Result::Country",
  { id => "country_id" },
  {
    is_deferrable => 1,
    join_type     => "LEFT",
    on_delete     => "CASCADE",
    on_update     => "CASCADE",
  },
);

=head2 org_addresses

Type: has_many

Related object: L<RODA::RODADB::Result::OrgAddress>

=cut

__PACKAGE__->has_many(
  "org_addresses",
  "RODA::RODADB::Result::OrgAddress",
  { "foreign.address_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 person_addresses

Type: has_many

Related object: L<RODA::RODADB::Result::PersonAddress>

=cut

__PACKAGE__->has_many(
  "person_addresses",
  "RODA::RODADB::Result::PersonAddress",
  { "foreign.address_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2013-01-08 23:03:06
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:GEBhPA7a9ERAFYXNKoBZtA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
