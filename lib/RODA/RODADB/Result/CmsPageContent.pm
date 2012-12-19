use utf8;
package RODA::RODADB::Result::CmsPageContent;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::CmsPageContent - Tabel pentru stocarea paginilor de tip content

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

=head1 TABLE: C<cms_page_content>

=cut

__PACKAGE__->table("cms_page_content");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul unei pagini de tip content din sistemul CMS al aplicatiei

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 150

Denumirea unei pagini de tip content din sistemul CMS al aplicatiei

=head2 page

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

Codul paginii in sistemul CMS al aplicatiei (refera atributul id din tabelul page)

=head2 content_title

  data_type: 'varchar'
  is_nullable: 0
  size: 250

Titlul paginii de tip content

=head2 content_text

  data_type: 'text'
  is_nullable: 1

Textul content-ului corespunzator paginii

=head2 sqnumber

  data_type: 'integer'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 150 },
  "page",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
  "content_title",
  { data_type => "varchar", is_nullable => 0, size => 250 },
  "content_text",
  { data_type => "text", is_nullable => 1 },
  "sqnumber",
  { data_type => "integer", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 page

Type: belongs_to

Related object: L<RODA::RODADB::Result::CmsPage>

=cut

__PACKAGE__->belongs_to(
  "page",
  "RODA::RODADB::Result::CmsPage",
  { id => "page" },
  {
    is_deferrable => 1,
    join_type     => "LEFT",
    on_delete     => "CASCADE",
    on_update     => "CASCADE",
  },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:Xyhi0Fz/3FaYP7/KKT1+Zw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
