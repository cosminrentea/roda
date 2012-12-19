use utf8;
package RODA::RODADB::Result::RegionCity;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::RegionCity

=head1 DESCRIPTION

Tabel asociativ, ce modeleaza relatia many:many intre orase si tari; se considera ca un oras poate fi cuprins in aria mai multor regiuni.

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

=head1 TABLE: C<region_city>

=cut

__PACKAGE__->table("region_city");

=head1 ACCESSORS

=head2 region_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul regiunii din care face parte un oras

=head2 city_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul orasului asociat regiunii

=cut

__PACKAGE__->add_columns(
  "region_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "city_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</region_id>

=item * L</city_id>

=back

=cut

__PACKAGE__->set_primary_key("region_id", "city_id");

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

=head2 region

Type: belongs_to

Related object: L<RODA::RODADB::Result::Region>

=cut

__PACKAGE__->belongs_to(
  "region",
  "RODA::RODADB::Result::Region",
  { id => "region_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:v/Re8Ioqo24W9U+NI0KVkg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
