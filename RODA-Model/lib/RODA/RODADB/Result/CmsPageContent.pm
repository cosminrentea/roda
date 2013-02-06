use utf8;
package RODA::RODADB::Result::CmsPageContent;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::CmsPageContent - Tabel pentru stocarea continutului paginilor din aplicatie

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
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'cms_page_content_id_seq'

Codul unei pagini de tip content din aplicatie

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 200

Denumirea continutului

=head2 cms_page_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul paginii in sistemul CMS al aplicatiei (refera atributul id din tabelul page)

=head2 content_title

  data_type: 'varchar'
  is_nullable: 0
  size: 250

Titlul continutului

=head2 content_text

  data_type: 'text'
  is_nullable: 1

Textul continutului

=head2 order_in_page

  data_type: 'integer'
  is_nullable: 0

Numarul de ordine al continutului curent in cadrul paginii

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "cms_page_content_id_seq",
  },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 200 },
  "cms_page_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "content_title",
  { data_type => "varchar", is_nullable => 0, size => 250 },
  "content_text",
  { data_type => "text", is_nullable => 1 },
  "order_in_page",
  { data_type => "integer", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 UNIQUE CONSTRAINTS

=head2 C<cms_page_content_seqnumber_Idx>

=over 4

=item * L</order_in_page>

=back

=cut

__PACKAGE__->add_unique_constraint("cms_page_content_seqnumber_Idx", ["order_in_page"]);

=head1 RELATIONS

=head2 cms_page

Type: belongs_to

Related object: L<RODA::RODADB::Result::CmsPage>

=cut

__PACKAGE__->belongs_to(
  "cms_page",
  "RODA::RODADB::Result::CmsPage",
  { id => "cms_page_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:UZrJDf3tkKMoZthSVKHj9g


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
