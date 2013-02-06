use utf8;
package RODA::RODADB::Result::Suffix;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Suffix

=head1 DESCRIPTION

Tabel ce contine sufixele care pot fi adaugate numelor persoanelor

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

=head1 TABLE: C<suffix>

=cut

__PACKAGE__->table("suffix");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'suffix_id_seq'

Codul sufixului

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 50

Denumirea sufixului care poate fi utilizat pentru persoane (Jr, Sr, etc)

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "suffix_id_seq",
  },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 50 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 people

Type: has_many

Related object: L<RODA::RODADB::Result::Person>

=cut

__PACKAGE__->has_many(
  "people",
  "RODA::RODADB::Result::Person",
  { "foreign.suffix_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 16:35:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:AXrZiU9c8wH7bkSShWFzJA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
