use utf8;
package RODA::RODADB::Result::PersonLink;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::PersonLink

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

=head1 TABLE: C<person_links>

=cut

__PACKAGE__->table("person_links");

=head1 ACCESSORS

=head2 person_links_id

  data_type: 'integer'
  is_nullable: 0

=head2 person

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 user

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 simscore

  data_type: 'numeric'
  is_nullable: 0
  size: [10,2]

=head2 namescore

  data_type: 'numeric'
  is_nullable: 0
  size: [10,2]

=head2 emailscore

  data_type: 'numeric'
  is_nullable: 0
  size: [10,2]

=head2 status

  data_type: 'integer'
  is_nullable: 0

0 - pending, 1 - confirmed, 9 - infirmed

=head2 status_by

  data_type: 'integer'
  is_nullable: 0

=head2 ststus_time

  data_type: 'timestamp'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "person_links_id",
  { data_type => "integer", is_nullable => 0 },
  "person",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "user",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "simscore",
  { data_type => "numeric", is_nullable => 0, size => [10, 2] },
  "namescore",
  { data_type => "numeric", is_nullable => 0, size => [10, 2] },
  "emailscore",
  { data_type => "numeric", is_nullable => 0, size => [10, 2] },
  "status",
  { data_type => "integer", is_nullable => 0 },
  "status_by",
  { data_type => "integer", is_nullable => 0 },
  "ststus_time",
  { data_type => "timestamp", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</person_links_id>

=back

=cut

__PACKAGE__->set_primary_key("person_links_id");

=head1 RELATIONS

=head2 person

Type: belongs_to

Related object: L<RODA::RODADB::Result::Person>

=cut

__PACKAGE__->belongs_to(
  "person",
  "RODA::RODADB::Result::Person",
  { id => "person" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 user

Type: belongs_to

Related object: L<RODA::RODADB::Result::User>

=cut

__PACKAGE__->belongs_to(
  "user",
  "RODA::RODADB::Result::User",
  { id => "user" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:Dgk8jwFEGc3Rv7ggbqfuSg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
