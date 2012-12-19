use utf8;
package RODA::RODADB::Result::CmsSnippet;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::CmsSnippet

=head1 DESCRIPTION

Tabel care stocheaza snippeturile folosite in sistemul CMS al aplicatiei

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

=head1 TABLE: C<cms_snippet>

=cut

__PACKAGE__->table("cms_snippet");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul unui snippet utilizat in sistemul CMS al aplicatiei

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 150

Denumirea unui snippet utilizat in sistemul CMS al aplicatiei

=head2 snippet_group

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Grupul din care face parte snippet-ul curent (refera atributul id al tabelului cms_snippet_group)

=head2 snippet_content

  data_type: 'text'
  is_nullable: 0

Continutul snippet-ului curent

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 150 },
  "snippet_group",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "snippet_content",
  { data_type => "text", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 snippet_group

Type: belongs_to

Related object: L<RODA::RODADB::Result::CmsSnippetGroup>

=cut

__PACKAGE__->belongs_to(
  "snippet_group",
  "RODA::RODADB::Result::CmsSnippetGroup",
  { id => "snippet_group" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:5TdCbgs96PlChFAfmFqPyw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
