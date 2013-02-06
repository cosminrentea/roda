use utf8;
package RODA::RODADB::Result::CmsLayoutGroup;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::CmsLayoutGroup - Tabel ce stocheaza grupurile pentru layout-uri

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

=head1 TABLE: C<cms_layout_group>

=cut

__PACKAGE__->table("cms_layout_group");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'cms_layout_group_id_seq'

Codul unui grup de layout-uri

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 200

Denumirea unui grup de layout-uri

=head2 parent_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

Codul parintelui unui grup de layout-uri (refera atributul id din tabelul curent))

=head2 description

  data_type: 'text'
  is_nullable: 1

Descrierea unui grup de layout-uri

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "cms_layout_group_id_seq",
  },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 200 },
  "parent_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
  "description",
  { data_type => "text", is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 cms_layout_groups

Type: has_many

Related object: L<RODA::RODADB::Result::CmsLayoutGroup>

=cut

__PACKAGE__->has_many(
  "cms_layout_groups",
  "RODA::RODADB::Result::CmsLayoutGroup",
  { "foreign.parent_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 cms_layouts

Type: has_many

Related object: L<RODA::RODADB::Result::CmsLayout>

=cut

__PACKAGE__->has_many(
  "cms_layouts",
  "RODA::RODADB::Result::CmsLayout",
  { "foreign.cms_layout_group_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 parent

Type: belongs_to

Related object: L<RODA::RODADB::Result::CmsLayoutGroup>

=cut

__PACKAGE__->belongs_to(
  "parent",
  "RODA::RODADB::Result::CmsLayoutGroup",
  { id => "parent_id" },
  {
    is_deferrable => 0,
    join_type     => "LEFT",
    on_delete     => "NO ACTION",
    on_update     => "NO ACTION",
  },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:Nfi42llbww5F7iLXvW5byQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
