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

__PACKAGE__->load_components(qw /InflateColumn::DateTime +RODA::Components::DBIC::FileStore Core/);


=head1 TABLE: C<cms_file>

=cut

__PACKAGE__->table("cms_file");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'cms_file_id_seq'

Codul unui fisier din sistemul CMS al aplicatiei

=head2 filename

  data_type: 'text'
  is_nullable: 0

Numele unui fisier din sistemul CMS al aplicatiei

=head2 label

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Eticheta (alias-ul) unui fisier din sistemul CMS al aplicatiei

=head2 cms_folder_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

Codul folder-ului din care face parte fisierul curent (refera atributul id din tabelul cms_folder)

=head2 filesize

  data_type: 'bigint'
  is_nullable: 0

Dimensiunea unui fisier din sistemul CMS al aplicatiei, exprimata in bytes

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "cms_file_id_seq",
  },
  "filename",
  { data_type => "text", is_nullable => 0, is_fs_column => 1 },
  "label",
  { data_type => "varchar", is_nullable => 0, size => 100 },
  "cms_folder_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
  "filesize",
  { data_type => "bigint", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 cms_file_property_name_values

Type: has_many

Related object: L<RODA::RODADB::Result::CmsFilePropertyNameValue>

=cut

__PACKAGE__->has_many(
  "cms_file_property_name_values",
  "RODA::RODADB::Result::CmsFilePropertyNameValue",
  { "foreign.cms_file_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 cms_folder

Type: belongs_to

Related object: L<RODA::RODADB::Result::CmsFolder>

=cut

__PACKAGE__->belongs_to(
  "cms_folder",
  "RODA::RODADB::Result::CmsFolder",
  { id => "cms_folder_id" },
  {
    is_deferrable => 0,
    join_type     => "LEFT",
    on_delete     => "NO ACTION",
    on_update     => "NO ACTION",
  },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:tL/08qrLRWEBmOzx0cL0dw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
