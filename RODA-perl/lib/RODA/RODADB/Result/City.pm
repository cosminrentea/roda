use utf8;
package RODA::RODADB::Result::City;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::City - Tabel unic pentru toate referintele la orase

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

=head1 TABLE: C<city>

=cut

__PACKAGE__->table("city");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul orasului

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Numele orasului

=head2 country_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul tarii in care se afla orasul (refera atributul id al tabelului country)

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 100 },
  "country_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 addresses

Type: has_many

Related object: L<RODA::RODADB::Result::Address>

=cut

__PACKAGE__->has_many(
  "addresses",
  "RODA::RODADB::Result::Address",
  { "foreign.city_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

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
  { "foreign.city_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:W2F6EhWP52Q0+qs5I93tLQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
