use utf8;
package RODA::RODADB::Result::Value;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Value

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

=head1 TABLE: C<value>

=cut

__PACKAGE__->table("value");

=head1 ACCESSORS

=head2 itemid

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 value

  data_type: 'integer'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "itemid",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "value",
  { data_type => "integer", is_nullable => 0 },
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

=head2 scale_maxvalueids

Type: has_many

Related object: L<RODA::RODADB::Result::Scale>

=cut

__PACKAGE__->has_many(
  "scale_maxvalueids",
  "RODA::RODADB::Result::Scale",
  { "foreign.maxvalueid" => "self.itemid" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 scale_minvalueids

Type: has_many

Related object: L<RODA::RODADB::Result::Scale>

=cut

__PACKAGE__->has_many(
  "scale_minvalueids",
  "RODA::RODADB::Result::Scale",
  { "foreign.minvalueid" => "self.itemid" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:wKR0IB/j/54EsVclmtNwYA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
