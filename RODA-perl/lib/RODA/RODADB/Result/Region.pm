use utf8;
package RODA::RODADB::Result::Region;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Region

=head1 DESCRIPTION

Tabel care contine regiunile corespunzatoare tarilor referite in model

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

=head1 TABLE: C<region>

=cut

__PACKAGE__->table("region");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Identificatorul regiunii

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 150

Numele regiunii

=head2 rtype_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Tipul regiunii (refera atributul id din tabelul region_type)

=head2 region_code

  data_type: 'varchar'
  is_nullable: 1
  size: 20

=head2 rcode_name

  data_type: 'varchar'
  is_nullable: 1
  size: 20

=head2 country_id

  data_type: 'char'
  is_foreign_key: 1
  is_nullable: 0
  size: 2

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 150 },
  "rtype_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "region_code",
  { data_type => "varchar", is_nullable => 1, size => 20 },
  "rcode_name",
  { data_type => "varchar", is_nullable => 1, size => 20 },
  "country_id",
  { data_type => "char", is_foreign_key => 1, is_nullable => 0, size => 2 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 country

Type: belongs_to

Related object: L<RODA::RODADB::Result::Country>

=cut

__PACKAGE__->belongs_to(
  "country",
  "RODA::RODADB::Result::Country",
  { id => "country_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 region_cities

Type: has_many

Related object: L<RODA::RODADB::Result::RegionCity>

=cut

__PACKAGE__->has_many(
  "region_cities",
  "RODA::RODADB::Result::RegionCity",
  { "foreign.region_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 rtype

Type: belongs_to

Related object: L<RODA::RODADB::Result::RegionType>

=cut

__PACKAGE__->belongs_to(
  "rtype",
  "RODA::RODADB::Result::RegionType",
  { id => "rtype_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2013-01-03 00:25:46
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:HQUmkyDRapwimxR4CfSZkw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
