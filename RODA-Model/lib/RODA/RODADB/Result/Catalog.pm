use utf8;
package RODA::RODADB::Result::Catalog;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Catalog - Tabel ce stocheaza informatii despre cataloagele de studii

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

__PACKAGE__->load_components("Tree::AdjacencyList::Ordered", "InflateColumn::DateTime");

=head1 TABLE: C<catalog>

=cut

__PACKAGE__->table("catalog");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'catalog_id_seq'

Codul catalogului

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 200

Denumirea catalogului

=head2 parent

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

Codul catalogului din care face parte catalogul curent (refera atributul id al tabelului catalog)

=head2 owner

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul utilizatorului care poseda catalogul (refera atributul id din tabelul users)

=head2 added

  data_type: 'timestamp'
  is_nullable: 0

Data si timpul cand a fost adaugat catalogul respectiv

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "catalog_id_seq",
  },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 200 },
  "parent_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
  "owner",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "added",
  { data_type => "timestamp", is_nullable => 0 },
    "description",
  { data_type => "text", is_nullable => 1 },
    "sequencenr",
  {
    data_type         => "integer",
    is_nullable       => 2,
  },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

__PACKAGE__->position_column('sequencenr');
__PACKAGE__->parent_column('parent_id');


=head1 RELATIONS

=head2 catalog_acls

Type: has_many

Related object: L<RODA::RODADB::Result::CatalogAcl>

=cut

__PACKAGE__->has_many(
  "catalog_acls",
  "RODA::RODADB::Result::CatalogAcl",
  { "foreign.catalog_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 catalog_studies

Type: has_many

Related object: L<RODA::RODADB::Result::CatalogStudy>

=cut

__PACKAGE__->has_many(
  "catalog_studies",
  "RODA::RODADB::Result::CatalogStudy",
  { "foreign.catalog_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 catalogs

Type: has_many

Related object: L<RODA::RODADB::Result::Catalog>

=cut

__PACKAGE__->has_many(
  "catalogs",
  "RODA::RODADB::Result::Catalog",
  { "foreign.parent" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 owner

Type: belongs_to

Related object: L<RODA::RODADB::Result::User>

=cut

__PACKAGE__->belongs_to(
  "owner",
  "RODA::RODADB::Result::User",
  { id => "owner" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 parent

Type: belongs_to

Related object: L<RODA::RODADB::Result::Catalog>

=cut

#__PACKAGE__->belongs_to(
#  "parent",
#  "RODA::RODADB::Result::Catalog",
#  { id => "parent" },
#  {
#    is_deferrable => 0,
#    join_type     => "LEFT",
#    on_delete     => "NO ACTION",
#    on_update     => "NO ACTION",
#  },
#);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:b40e+0VaYFFK9nCUDSETLA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
