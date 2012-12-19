use utf8;
package RODA::RODADB::Result::News;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::News

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

=head1 TABLE: C<news>

=cut

__PACKAGE__->table("news");

=head1 ACCESSORS

=head2 news_id

  data_type: 'integer'
  is_nullable: 0

=head2 titlu

  data_type: 'varchar'
  is_nullable: 0
  size: 200

=head2 content

  data_type: 'text'
  is_nullable: 1

=head2 added_by

  data_type: 'integer'
  is_nullable: 0

=head2 added

  data_type: 'timestamp'
  is_nullable: 0

=head2 visible

  data_type: 'boolean'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "news_id",
  { data_type => "integer", is_nullable => 0 },
  "titlu",
  { data_type => "varchar", is_nullable => 0, size => 200 },
  "content",
  { data_type => "text", is_nullable => 1 },
  "added_by",
  { data_type => "integer", is_nullable => 0 },
  "added",
  { data_type => "timestamp", is_nullable => 0 },
  "visible",
  { data_type => "boolean", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</news_id>

=back

=cut

__PACKAGE__->set_primary_key("news_id");


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:0EQMrk0CB3qioMgT0xKxUQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
