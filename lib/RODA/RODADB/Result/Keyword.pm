use utf8;
package RODA::RODADB::Result::Keyword;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Keyword

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

=head1 TABLE: C<keywords>

=cut

__PACKAGE__->table("keywords");

=head1 ACCESSORS

=head2 keywords_id

  data_type: 'integer'
  is_nullable: 0

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 150

=cut

__PACKAGE__->add_columns(
  "keywords_id",
  { data_type => "integer", is_nullable => 0 },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 150 },
);

=head1 PRIMARY KEY

=over 4

=item * L</keywords_id>

=back

=cut

__PACKAGE__->set_primary_key("keywords_id");

=head1 RELATIONS

=head2 instance_keyword

Type: might_have

Related object: L<RODA::RODADB::Result::InstanceKeyword>

=cut

__PACKAGE__->might_have(
  "instance_keyword",
  "RODA::RODADB::Result::InstanceKeyword",
  { "foreign.keywords_id" => "self.keywords_id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 studiu_keywords

Type: has_many

Related object: L<RODA::RODADB::Result::StudiuKeyword>

=cut

__PACKAGE__->has_many(
  "studiu_keywords",
  "RODA::RODADB::Result::StudiuKeyword",
  { "foreign.keyword_id" => "self.keywords_id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:RvjcD4g/tAQceVlWI7TJng


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
