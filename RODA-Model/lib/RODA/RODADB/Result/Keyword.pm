use utf8;
package RODA::RODADB::Result::Keyword;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Keyword

=head1 DESCRIPTION

Tabel ce contine cuvintele cheie ale studiilor si instantelor din baza de date

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

=head1 TABLE: C<keyword>

=cut

__PACKAGE__->table("keyword");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'keyword_id_seq'

Codul cuvantului cheie

=head2 name

  data_type: 'text'
  is_nullable: 0

Cuvantul cheie

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "keyword_id_seq",
  },
  "name",
  { data_type => "text", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 instance_keywords

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceKeyword>

=cut

__PACKAGE__->has_many(
  "instance_keywords",
  "RODA::RODADB::Result::InstanceKeyword",
  { "foreign.keyword_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_keywords

Type: has_many

Related object: L<RODA::RODADB::Result::StudyKeyword>

=cut

__PACKAGE__->has_many(
  "study_keywords",
  "RODA::RODADB::Result::StudyKeyword",
  { "foreign.keyword_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 16:35:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:AT+Hxo5HV1zJY2hrgxYMOQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
