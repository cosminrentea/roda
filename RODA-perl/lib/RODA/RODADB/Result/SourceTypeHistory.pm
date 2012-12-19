use utf8;
package RODA::RODADB::Result::SourceTypeHistory;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::SourceTypeHistory

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

=head1 TABLE: C<source_type_history>

=cut

__PACKAGE__->table("source_type_history");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

=head2 datestart

  data_type: 'timestamp'
  is_nullable: 0

=head2 dateend

  data_type: 'timestamp'
  is_nullable: 0

=head2 added_by

  data_type: 'integer'
  is_nullable: 0

=head2 org_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "datestart",
  { data_type => "timestamp", is_nullable => 0 },
  "dateend",
  { data_type => "timestamp", is_nullable => 0 },
  "added_by",
  { data_type => "integer", is_nullable => 0 },
  "org_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 org

Type: belongs_to

Related object: L<RODA::RODADB::Result::Source>

=cut

__PACKAGE__->belongs_to(
  "org",
  "RODA::RODADB::Result::Source",
  { org_id => "org_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:LpCnhZpPUzPcFk22Fur8dQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
