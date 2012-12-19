use utf8;
package RODA::RODADB::Result::ConceptVariable;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::ConceptVariable - Tabel ce asociaza variabilelor o multime de concepte

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

=head1 TABLE: C<concept_variable>

=cut

__PACKAGE__->table("concept_variable");

=head1 ACCESSORS

=head2 concept_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Identificatorul conceptului la care se refera variabila identificata prin atributul variable_id

=head2 variable_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Identificatorul variabilei

=cut

__PACKAGE__->add_columns(
  "concept_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "variable_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</concept_id>

=item * L</variable_id>

=back

=cut

__PACKAGE__->set_primary_key("concept_id", "variable_id");

=head1 RELATIONS

=head2 concept

Type: belongs_to

Related object: L<RODA::RODADB::Result::Concept>

=cut

__PACKAGE__->belongs_to(
  "concept",
  "RODA::RODADB::Result::Concept",
  { id => "concept_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 variable

Type: belongs_to

Related object: L<RODA::RODADB::Result::Variable>

=cut

__PACKAGE__->belongs_to(
  "variable",
  "RODA::RODADB::Result::Variable",
  { id => "variable_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:uWj7nVt1Z0g9EMWQ+57dRQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
