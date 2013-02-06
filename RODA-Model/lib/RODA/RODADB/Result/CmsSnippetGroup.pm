use utf8;
package RODA::RODADB::Result::CmsSnippetGroup;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::CmsSnippetGroup

=head1 DESCRIPTION

Tabel care stocheaza grupuri de snippeturi utilizate in aplicatie

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

=head1 TABLE: C<cms_snippet_group>

=cut

__PACKAGE__->table("cms_snippet_group");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'cms_snippet_group_id_seq'

Codul unui grup de snippet-uri din sistemul CMS al aplicatiei

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 200

Denumirea unui grup de snippet-uri din sistemul CMS al aplicatiei

=head2 parent_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

Codul grupului de snippet-uri care este parintele grupului curent (refera atributul id din acelasi tabel)

=head2 description

  data_type: 'text'
  is_nullable: 1

Descrierea grupului de snippet-uri 

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "cms_snippet_group_id_seq",
  },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 200 },
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

=head1 RELATIONS

=head2 cms_snippet_groups

Type: has_many

Related object: L<RODA::RODADB::Result::CmsSnippetGroup>

=cut

__PACKAGE__->has_many(
  "cms_snippet_groups",
  "RODA::RODADB::Result::CmsSnippetGroup",
  { "foreign.parent_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 cms_snippets

Type: has_many

Related object: L<RODA::RODADB::Result::CmsSnippet>

=cut

__PACKAGE__->has_many(
  "cms_snippets",
  "RODA::RODADB::Result::CmsSnippet",
  { "foreign.cms_snippet_group_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 parent

Type: belongs_to

Related object: L<RODA::RODADB::Result::CmsSnippetGroup>

=cut

__PACKAGE__->belongs_to(
  "parent",
  "RODA::RODADB::Result::CmsSnippetGroup",
  { id => "parent_id" },
  {
    is_deferrable => 0,
    join_type     => "LEFT",
    on_delete     => "NO ACTION",
    on_update     => "NO ACTION",
  },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:a5+aDmZr3/weYPR9zHP+sw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
