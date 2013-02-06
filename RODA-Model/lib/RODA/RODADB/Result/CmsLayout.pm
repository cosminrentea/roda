use utf8;
package RODA::RODADB::Result::CmsLayout;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::CmsLayout

=head1 DESCRIPTION

Tabel care stocheaza layout-uri pentru paginile din sistemul CMS al aplicatiei

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

=head1 TABLE: C<cms_layout>

=cut

__PACKAGE__->table("cms_layout");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'cms_layout_id_seq'

Codul unui layout pentru o pagina de CMS 

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 200

Denumirea unui layout pentru o pagina de CMS

=head2 cms_layout_group_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

Codul grupului din care face parte un layout de CMS (refera atributul id din tabelul cms_layout_group)

=head2 layout_content

  data_type: 'text'
  is_nullable: 0

Continutul unui layout pentru o pagina de CMS

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "cms_layout_id_seq",
  },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 200 },
  "cms_layout_group_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
  "layout_content",
  { data_type => "text", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 cms_layout_group

Type: belongs_to

Related object: L<RODA::RODADB::Result::CmsLayoutGroup>

=cut

__PACKAGE__->belongs_to(
  "cms_layout_group",
  "RODA::RODADB::Result::CmsLayoutGroup",
  { id => "cms_layout_group_id" },
  {
    is_deferrable => 0,
    join_type     => "LEFT",
    on_delete     => "NO ACTION",
    on_update     => "NO ACTION",
  },
);

=head2 cms_pages

Type: has_many

Related object: L<RODA::RODADB::Result::CmsPage>

=cut

__PACKAGE__->has_many(
  "cms_pages",
  "RODA::RODADB::Result::CmsPage",
  { "foreign.cms_layout_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:FsHxj/EHZJvmGVoBYPV2nQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
