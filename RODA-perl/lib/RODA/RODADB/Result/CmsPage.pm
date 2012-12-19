use utf8;
package RODA::RODADB::Result::CmsPage;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::CmsPage - Tabel care stocheaza paginile din sistemul CMS

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

=head1 TABLE: C<cms_page>

=cut

__PACKAGE__->table("cms_page");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul unei pagini din sistemul CMS

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 150

Denumirea unei pagini din sistemul CMS

=head2 layout

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul layout-ului corespunzator paginii din sistemul CMS (refera atributul id din tabelul cms_layout)

=head2 page_type

  data_type: 'integer'
  is_nullable: 0

Codul tipului unei pagini din sistemul CMS

=head2 visible

  data_type: 'boolean'
  default_value: true
  is_nullable: 0

Atribut a carui valoare este true daca pagina din sistemul CMS este vizibila (valoarea implicita a acestui atribut este true)

=head2 navigable

  data_type: 'boolean'
  default_value: true
  is_nullable: 0

Atribut a carui valoare este true daca pagina din sistemul CMS este navigabila (valoarea implicita a acestui atribut este true)

=head2 owner

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul utilizatorului care detine pagina din sistemul CMS (refera atributul id din tabelul users)

=head2 url

  data_type: 'varchar'
  is_nullable: 0
  size: 200

URL-ul paginii din sistemul CMS

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 150 },
  "layout",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "page_type",
  { data_type => "integer", is_nullable => 0 },
  "visible",
  { data_type => "boolean", default_value => \"true", is_nullable => 0 },
  "navigable",
  { data_type => "boolean", default_value => \"true", is_nullable => 0 },
  "owner",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "url",
  { data_type => "varchar", is_nullable => 0, size => 200 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 cms_page_contents

Type: has_many

Related object: L<RODA::RODADB::Result::CmsPageContent>

=cut

__PACKAGE__->has_many(
  "cms_page_contents",
  "RODA::RODADB::Result::CmsPageContent",
  { "foreign.page" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 layout

Type: belongs_to

Related object: L<RODA::RODADB::Result::CmsLayout>

=cut

__PACKAGE__->belongs_to(
  "layout",
  "RODA::RODADB::Result::CmsLayout",
  { id => "layout" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 owner

Type: belongs_to

Related object: L<RODA::RODADB::Result::User>

=cut

__PACKAGE__->belongs_to(
  "owner",
  "RODA::RODADB::Result::User",
  { id => "owner" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:7HqI54iKBXDp2AP+lS7ehw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
