use utf8;
package RODA::RODADB::Result::CmsLayoutGroup;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NUME

RODA::RODADB::Result::Groups - Tabel ce stocheaza grupurile de utilizatori

=cut

use strict;
use warnings;

use Moose;
use MooseX::NonMoose;
use MooseX::MarkAsMethods autoclean => 1;
extends 'DBIx::Class::Core';

=head1 COMPONENTE UTILIZATE

=over 4

=item * L<DBIx::Class::InflateColumn::DateTime>

=back

=cut

__PACKAGE__->load_components("InflateColumn::DateTime");

=head1 TABLE: C<groups>

=cut

__PACKAGE__->table("groups");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'groups_id_seq'

Codul unui grup de utilizatori

=head2 group_name

  data_type: 'varchar'
  is_nullable: 0
  size: 64

Denumirea unui grup de utilizatori

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "cms_layout_group_id_seq",
  },
  "group_name",
  { data_type => "varchar", is_nullable => 0, size => 64 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATII

=head2 groups

Type: has_many

Related object: L<RODA::RODADB::Result::Groups>

=cut

__PACKAGE__->has_many(
  "groups",
  "RODA::RODADB::Result::Groups",
  { "foreign.parent_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 group_members

Type: has_many

Related object: L<RODA::RODADB::Result::GroupMembers>

=cut

__PACKAGE__->has_many(
  "group_members",
  "RODA::RODADB::Result::GroupMembers",
  { "foreign.groups_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:Nfi42llbww5F7iLXvW5byQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
