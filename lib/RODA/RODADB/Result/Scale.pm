use utf8;
package RODA::RODADB::Result::Scale;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Scale

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

=head1 TABLE: C<scale>

=cut

__PACKAGE__->table("scale");

=head1 ACCESSORS

=head2 itemid

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 minvalueid

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 maxvalueid

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 units

  data_type: 'smallint'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "itemid",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "minvalueid",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "maxvalueid",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "units",
  { data_type => "smallint", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</itemid>

=back

=cut

__PACKAGE__->set_primary_key("itemid");

=head1 RELATIONS

=head2 itemid

Type: belongs_to

Related object: L<RODA::RODADB::Result::Item>

=cut

__PACKAGE__->belongs_to(
  "itemid",
  "RODA::RODADB::Result::Item",
  { id => "itemid" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 maxvalueid

Type: belongs_to

Related object: L<RODA::RODADB::Result::Value>

=cut

__PACKAGE__->belongs_to(
  "maxvalueid",
  "RODA::RODADB::Result::Value",
  { itemid => "maxvalueid" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 minvalueid

Type: belongs_to

Related object: L<RODA::RODADB::Result::Value>

=cut

__PACKAGE__->belongs_to(
  "minvalueid",
  "RODA::RODADB::Result::Value",
  { itemid => "minvalueid" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:xptbuzitIHFVJoNmaKsC5w


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
