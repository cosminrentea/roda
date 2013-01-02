use utf8;
package RODA::RODADB::Result::Country;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Country - Tael unic pentru toate referintele la tari

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

=head1 TABLE: C<country>

=cut

__PACKAGE__->table("country");

=head1 ACCESSORS

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Numele tarii

=head2 alpha3

  data_type: 'char'
  is_nullable: 0
  size: 3

=head2 id

  data_type: 'char'
  is_nullable: 0
  size: 2

=cut

__PACKAGE__->add_columns(
  "name",
  { data_type => "varchar", is_nullable => 0, size => 100 },
  "alpha3",
  { data_type => "char", is_nullable => 0, size => 3 },
  "id",
  { data_type => "char", is_nullable => 0, size => 2 },
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
  { "foreign.country_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 cities

Type: has_many

Related object: L<RODA::RODADB::Result::City>

=cut

__PACKAGE__->has_many(
  "cities",
  "RODA::RODADB::Result::City",
  { "foreign.country_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 regions

Type: has_many

Related object: L<RODA::RODADB::Result::Region>

=cut

__PACKAGE__->has_many(
  "regions",
  "RODA::RODADB::Result::Region",
  { "foreign.country_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2013-01-03 00:25:45
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:WF5tt6QyQcT1YAK+oOtNQQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
