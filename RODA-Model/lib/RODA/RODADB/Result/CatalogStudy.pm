use utf8;
package RODA::RODADB::Result::CatalogStudy;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::CatalogStudy

=head1 DESCRIPTION

Tabel ce contine asocierile dintre cataloage si studii (implementeaza relatia many-to-many intre catalog si study)

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

=head1 TABLE: C<catalog_study>

=cut

__PACKAGE__->table("catalog_study");

=head1 ACCESSORS

=head2 catalog_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul unui catalog din care face parte studiul referit prin atributul study_id (refera atributul id din tabelul catalog)

=head2 study_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul studiului asociat catalogului referit prin atributul catalog_id (refera atributul id din tabelul study)

=head2 added

  data_type: 'timestamp'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "catalog_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "study_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "added",
  { data_type => "timestamp", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</catalog_id>

=item * L</study_id>

=back

=cut

__PACKAGE__->set_primary_key("catalog_id", "study_id");

=head1 RELATIONS

=head2 catalog

Type: belongs_to

Related object: L<RODA::RODADB::Result::Catalog>

=cut

__PACKAGE__->belongs_to(
  "catalog",
  "RODA::RODADB::Result::Catalog",
  { id => "catalog_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 study

Type: belongs_to

Related object: L<RODA::RODADB::Result::Study>

=cut

__PACKAGE__->belongs_to(
  "study",
  "RODA::RODADB::Result::Study",
  { id => "study_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:fqOOBFX+XFD3MBm2qJuaFg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
