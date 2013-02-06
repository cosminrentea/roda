use utf8;
package RODA::RODADB::Result::CmsSnippet;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::CmsSnippet

=head1 DESCRIPTION

Tabel care stocheaza snippeturile (fragmentele de cod) folosite in aplicatie

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
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'cms_snippet_id_seq'

Codul unui snippet (fragment de cod) utilizat in aplicatie

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 200

Denumirea unui snippet utilizat in aplicatie

=head2 cms_snippet_group_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

Grupul din care face parte snippet-ul curent (refera atributul id al tabelului cms_snippet_group)

=head2 snippet_content

  data_type: 'text'
  is_nullable: 0

Continutul snippet-ului curent

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "cms_snippet_id_seq",
  },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 200 },
  "cms_snippet_group_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
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

=head2 cms_snippet_group

Type: belongs_to

Related object: L<RODA::RODADB::Result::CmsSnippetGroup>

=cut

__PACKAGE__->belongs_to(
  "cms_snippet_group",
  "RODA::RODADB::Result::CmsSnippetGroup",
  { id => "cms_snippet_group_id" },
  {
    is_deferrable => 0,
    join_type     => "LEFT",
    on_delete     => "NO ACTION",
    on_update     => "NO ACTION",
  },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:A8CymKOfdS/1yJFMnkbCiw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
