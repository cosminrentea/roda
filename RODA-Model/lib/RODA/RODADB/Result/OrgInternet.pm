use utf8;
package RODA::RODADB::Result::OrgInternet;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::OrgInternet

=head1 DESCRIPTION

Tabel de legatura pentru relatia de tip NxM intre "Org" si "Internet"

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

=head1 TABLE: C<org_internet>

=cut

__PACKAGE__->table("org_internet");

=head1 ACCESSORS

=head2 org_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 internet_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 main

  data_type: 'boolean'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "org_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "internet_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "main",
  { data_type => "boolean", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</org_id>

=item * L</internet_id>

=back

=cut

__PACKAGE__->set_primary_key("org_id", "internet_id");

=head1 RELATIONS

=head2 internet

Type: belongs_to

Related object: L<RODA::RODADB::Result::Internet>

=cut

__PACKAGE__->belongs_to(
  "internet",
  "RODA::RODADB::Result::Internet",
  { id => "internet_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 org

Type: belongs_to

Related object: L<RODA::RODADB::Result::Org>

=cut

__PACKAGE__->belongs_to(
  "org",
  "RODA::RODADB::Result::Org",
  { id => "org_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:xnOX6Wxnl4uM/oTrKsM8gw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
