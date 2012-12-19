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

=head2 coll_mod_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul tipului de model de colectare a datelor asociat instantei identificate prin atributul instance_id

=cut

__PACKAGE__->add_columns(
  "instance_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "coll_mod_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</instance_id>

=item * L</coll_mod_id>

=back

=cut

__PACKAGE__->set_primary_key("instance_id", "coll_mod_id");

=head1 RELATIONS

=head2 coll_mod

Type: belongs_to

Related object: L<RODA::RODADB::Result::CollectionModelType>

=cut

__PACKAGE__->belongs_to(
  "coll_mod",
  "RODA::RODADB::Result::CollectionModelType",
  { id => "coll_mod_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 instance

Type: belongs_to

Related object: L<RODA::RODADB::Result::Methodology>

=cut

__PACKAGE__->belongs_to(
  "instance",
  "RODA::RODADB::Result::Methodology",
  { instance_id => "instance_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:/J87uVU+RM+yUAExDhNzHA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
