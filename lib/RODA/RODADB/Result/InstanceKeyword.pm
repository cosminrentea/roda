use utf8;
package RODA::RODADB::Result::InstanceKeyword;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::InstanceKeyword

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

=head1 TABLE: C<instance_keywords>

=cut

__PACKAGE__->table("instance_keywords");

=head1 ACCESSORS

=head2 keywords_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 instance_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 added

  data_type: 'timestamp'
  is_nullable: 0

=head2 added_by

  data_type: 'integer'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "keywords_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "instance_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "added",
  { data_type => "timestamp", is_nullable => 0 },
  "added_by",
  { data_type => "integer", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</keywords_id>

=back

=cut

__PACKAGE__->set_primary_key("keywords_id");

=head1 RELATIONS

=head2 instance

Type: belongs_to

Related object: L<RODA::RODADB::Result::Instance>

=cut

__PACKAGE__->belongs_to(
  "instance",
  "RODA::RODADB::Result::Instance",
  { id => "instance_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 keyword

Type: belongs_to

Related object: L<RODA::RODADB::Result::Keyword>

=cut

__PACKAGE__->belongs_to(
  "keyword",
  "RODA::RODADB::Result::Keyword",
  { keywords_id => "keywords_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:OQ5sqJmcn9unm+cU2qUBmw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
