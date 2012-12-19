use utf8;
package RODA::RODADB::Result::CollectionModelType;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::CollectionModelType - Tabel ce contine tipurile modelelor de colectare a datelor

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

=head1 TABLE: C<collection_model_type>

=cut

__PACKAGE__->table("collection_model_type");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul tipului modelului de colectare a datelor

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 200

Denumirea tipului metodei de colectare a datelor

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 200 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 meth_coll_types

Type: has_many

Related object: L<RODA::RODADB::Result::MethCollType>

=cut

__PACKAGE__->has_many(
  "meth_coll_types",
  "RODA::RODADB::Result::MethCollType",
  { "foreign.coll_mod_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:OCSc6ih5iG2wbDm13es8Yw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
