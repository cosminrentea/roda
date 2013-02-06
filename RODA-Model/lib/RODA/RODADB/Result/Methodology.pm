use utf8;
package RODA::RODADB::Result::Methodology;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Methodology

=head1 DESCRIPTION

Tabel care stocheaza informatii aditionale ale instantei, legate de metodologie

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

=head1 TABLE: C<methodology>

=cut

__PACKAGE__->table("methodology");

=head1 ACCESSORS

=head2 instance_id

  data_type: 'integer'
  is_auto_increment: 1
  is_foreign_key: 1
  is_nullable: 0
  sequence: 'methodology_instance_id_seq'

Codul instantei pentru care sunt stocate informatii legate de metodologie

=head2 time_meth_id

  data_type: 'integer'
  is_auto_increment: 1
  is_foreign_key: 1
  is_nullable: 0
  sequence: 'methodology_time_meth_id_seq'

Metoda temporala

=head2 sampling_procedure

  data_type: 'text'
  is_nullable: 0

Procedura de esantionare a instantei

=head2 weighting

  data_type: 'text'
  is_nullable: 0

Metoda de ponderare a variabilelor

=head2 research_instrument

  data_type: 'text'
  is_nullable: 0

Denumirea instrumentului de cercetare utilizat

=head2 scope

  data_type: 'text'
  is_nullable: 0

Domeniul geografic si demografic corespunzator instantei referite prin atributul instance_id

=cut

__PACKAGE__->add_columns(
  "instance_id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_foreign_key    => 1,
    is_nullable       => 0,
    sequence          => "methodology_instance_id_seq",
  },
  "time_meth_id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_foreign_key    => 1,
    is_nullable       => 0,
    sequence          => "methodology_time_meth_id_seq",
  },
  "sampling_procedure",
  { data_type => "text", is_nullable => 0 },
  "weighting",
  { data_type => "text", is_nullable => 0 },
  "research_instrument",
  { data_type => "text", is_nullable => 0 },
  "scope",
  { data_type => "text", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</instance_id>

=back

=cut

__PACKAGE__->set_primary_key("instance_id");

=head1 RELATIONS

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

=head2 meth_coll_types

Type: has_many

Related object: L<RODA::RODADB::Result::MethCollType>

=cut

__PACKAGE__->has_many(
  "meth_coll_types",
  "RODA::RODADB::Result::MethCollType",
  { "foreign.instance_id" => "self.instance_id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 time_meth

Type: belongs_to

Related object: L<RODA::RODADB::Result::TimeMethType>

=cut

__PACKAGE__->belongs_to(
  "time_meth",
  "RODA::RODADB::Result::TimeMethType",
  { id => "time_meth_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 collection_models

Type: many_to_many

Composing rels: L</meth_coll_types> -> collection_model

=cut

__PACKAGE__->many_to_many("collection_models", "meth_coll_types", "collection_model");


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 16:35:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:zNkoacAfW2dHQ1cuvYAEmA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
