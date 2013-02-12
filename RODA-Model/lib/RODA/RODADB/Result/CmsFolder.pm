use utf8;
package RODA::RODADB::Result::CmsFolder;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::CmsFolder

=head1 DESCRIPTION

Tabel pentru stocarea informatiilor despre folder-ele din sistemul CMS al aplicatiei

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

__PACKAGE__->load_components(qw( Tree::AdjacencyList ));

=head1 TABLE: C<cms_folder>

=cut

__PACKAGE__->table("cms_folder");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'cms_folder_id_seq'

Codul unui folder din sistemul cms al aplicatiei

=head2 name

  data_type: 'text'
  is_nullable: 0

Denumirea unui folder din sistemul CMS al aplicatiei

=head2 parent_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

Codul folder-ului parinte al folder-ului curent (refera atributul id din acelasi tabel)

=head2 description

  data_type: 'text'
  is_nullable: 1

Descrierea unui folder din sistemul CMS al aplicatiei

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "cms_folder_id_seq",
  },
  "name",
  { data_type => "text", is_nullable => 0 },
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
__PACKAGE__->parent_column('parent_id');
#__PACKAGE__->repair_tree( 1 );

=head1 RELATIONS

=head2 cms_files

Type: has_many

Related object: L<RODA::RODADB::Result::CmsFile>

=cut

__PACKAGE__->has_many(
  "cms_files",
  "RODA::RODADB::Result::CmsFile",
  { "foreign.cms_folder_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 cms_folders

Type: has_many

Related object: L<RODA::RODADB::Result::CmsFolder>

=cut

__PACKAGE__->has_many(
  "cms_folders",
  "RODA::RODADB::Result::CmsFolder",
  { "foreign.parent_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 parent

Type: belongs_to

Related object: L<RODA::RODADB::Result::CmsFolder>

=cut

#__PACKAGE__->belongs_to(
#  "parent",
#  "RODA::RODADB::Result::CmsFolder",
#  { id => "parent_id" },
#  {
#    is_deferrable => 0,
#    join_type     => "LEFT",
#    on_delete     => "NO ACTION",
#    on_update     => "NO ACTION",
#  },
#);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:VY5nYUuEiiKQjcCVfoCypw


# You can replace this text with custom code or comments, and it will be preserved on regeneration

__PACKAGE__->meta->make_immutable;
1;
