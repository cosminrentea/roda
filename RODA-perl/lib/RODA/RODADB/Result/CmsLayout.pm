use utf8;
package RODA::RODADB::Result::CmsLayout;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::CmsLayout - Tabel care stocheaza layout-uri pentru paginile din cms

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
  is_nullable: 0

Codul unui layout pentru o pagina de CMS 

=head2 name

  data_type: 'varchar'
  is_nullable: 1
  size: 150

Denumirea unui layout pentru o pagina de CMS

=head2 layout_group

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul grupului din care face parte un layout de CMS (refera atributul id din tabelul cms_layout_group)

=head2 layout_content

  data_type: 'text'
  is_nullable: 0

Continutul unui layout pentru o pagina de CMS

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "name",
  { data_type => "varchar", is_nullable => 1, size => 150 },
  "layout_group",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
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

=head2 cms_pages

Type: has_many

Related object: L<RODA::RODADB::Result::CmsPage>

=cut

__PACKAGE__->has_many(
  "cms_pages",
  "RODA::RODADB::Result::CmsPage",
  { "foreign.layout" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 layout_group

Type: belongs_to

Related object: L<RODA::RODADB::Result::CmsLayoutGroup>

=cut

__PACKAGE__->belongs_to(
  "layout_group",
  "RODA::RODADB::Result::CmsLayoutGroup",
  { id => "layout_group" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:Eb3Xx+N7nvC5c8ioHMkE1g


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
