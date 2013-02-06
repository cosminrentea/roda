use utf8;
package RODA::RODADB::Result::MethCollType;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::MethCollType

=head1 DESCRIPTION

Tabel ce stocheaza tipurile modelelor de colectare a datelor utilizate in cadrul unei instante

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

=head1 TABLE: C<meth_coll_type>

=cut

__PACKAGE__->table("meth_coll_type");

=head1 ACCESSORS

=head2 instance_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul instantei pentru care se asociaza un model de colectare a datelor

=head2 collection_model_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul tipului de model de colectare a datelor asociat instantei identificate prin atributul instance_id

=cut

__PACKAGE__->add_columns(
  "instance_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "collection_model_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</instance_id>

=item * L</collection_model_id>

=back

=cut

__PACKAGE__->set_primary_key("instance_id", "collection_model_id");

=head1 RELATIONS

=head2 collection_model

Type: belongs_to

Related object: L<RODA::RODADB::Result::CollectionModelType>

=cut

__PACKAGE__->belongs_to(
  "collection_model",
  "RODA::RODADB::Result::CollectionModelType",
  { id => "collection_model_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 instance

Type: belongs_to

Related object: L<RODA::RODADB::Result::Instance>

=cut

__PACKAGE__->belongs_to(
  "instance",
  "RODA::RODADB::Result::Instance",
  { id => "instance_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:vexHZaiyCEo2Ccgo+sfsog


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
