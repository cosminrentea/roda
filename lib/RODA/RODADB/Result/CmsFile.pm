use utf8;
package RODA::RODADB::Result::CmsFile;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::CmsFile

=head1 DESCRIPTION

Tabel ce stocheaza informatii despre fisierele din sistemul CMS al aplicatiei

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

=head1 TABLE: C<cms_files>

=cut

__PACKAGE__->table("cms_files");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul unui fisier din sistemul CMS al aplicatiei

=head2 filename

  data_type: 'varchar'
  is_nullable: 0
  size: 200

Numele unui fisier din sistemul CMS al aplicatiei

=head2 label

  data_type: 'varchar'
  is_nullable: 0
  size: 50

Eticheta unui fisier din sistemul CMS al aplicatiei

=head2 folder_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul folder-ului din care face parte fisierul curent (refera atributul id din tabelul folder)

=head2 md5

  data_type: 'varchar'
  is_nullable: 0
  size: 32

Valoarea md5 asociata unui fisier din sistemul CMS al aplicatiei

=head2 mimegroup

  data_type: 'varchar'
  is_nullable: 0
  size: 50

Grupul MIME asociat unui fisier din sistemul CMS al aplicatiei

=head2 mimesubgroup

  data_type: 'varchar'
  is_nullable: 0
  size: 50

Subgrupul MIME asociat unui fisier din sistemul CMS al aplicatiei

=head2 filesize

  data_type: 'integer'
  is_nullable: 0

Dimensiunea unui fisier din sistemul CMS al aplicatiei, exprimata in kilobytes

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "filename",
  { data_type => "varchar", is_nullable => 0, size => 200 },
  "label",
  { data_type => "varchar", is_nullable => 0, size => 50 },
  "folder_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "md5",
  { data_type => "varchar", is_nullable => 0, size => 32 },
  "mimegroup",
  { data_type => "varchar", is_nullable => 0, size => 50 },
  "mimesubgroup",
  { data_type => "varchar", is_nullable => 0, size => 50 },
  "filesize",
  { data_type => "integer", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 folder

Type: belongs_to

Related object: L<RODA::RODADB::Result::CmsFolder>

=cut

__PACKAGE__->belongs_to(
  "folder",
  "RODA::RODADB::Result::CmsFolder",
  { id => "folder_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:IguRvBOjNkb3vgfwG4zaeQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
