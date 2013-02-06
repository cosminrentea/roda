use utf8;
package RODA::RODADB::Result::SamplingProcedure;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::SamplingProcedure

=head1 DESCRIPTION

Tabel pentru procedurile de esantionare. (cf. DDI Codebook:) The type of sample and sample design used to select the survey respondents to represent the population. These may include one or more of the following: no sampling (total universe); quota sample; simple random sample; one-stage stratified or systematic random sample; one-stage cluster sample; multi-stage stratified random sample; quasi-random (e.g. random walk) sample; purposive selection/case studies; volunteer sample; convenience sample. 

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

=head1 TABLE: C<sampling_procedure>

=cut

__PACKAGE__->table("sampling_procedure");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'sampling_procedure_id_seq'

=head2 name

  data_type: 'text'
  is_nullable: 0

=head2 description

  data_type: 'text'
  is_nullable: 1

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "sampling_procedure_id_seq",
  },
  "name",
  { data_type => "text", is_nullable => 0 },
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

=head2 instance_sampling_procedures

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceSamplingProcedure>

=cut

__PACKAGE__->has_many(
  "instance_sampling_procedures",
  "RODA::RODADB::Result::InstanceSamplingProcedure",
  { "foreign.sampling_procedure_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instances

Type: many_to_many

Composing rels: L</instance_sampling_procedures> -> instance

=cut

__PACKAGE__->many_to_many("instances", "instance_sampling_procedures", "instance");


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:DOxCVHNbR1sjjPeNJ6ACfQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
