use utf8;
package RODA::RODADB::Result::ConceptVariable;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::ConceptVariable

=head1 DESCRIPTION

Tabel ce asociaza variabilelor o multime de concepte (implementeaza relatia many-to-many intre tabelele variable si concept)

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

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

Codul conceptului la care se refera variabila identificata prin atributul variable_id, din cadrul instantei instance_id (refera atributul id din tabelul concept)

=head2 variable_id

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

Codul variabilei din instanta instance_id careia ii este asociat conceptul identificat prin atributul concept_id

=cut

__PACKAGE__->add_columns(
  "concept_id",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
  "variable_id",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
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
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 variable

Type: belongs_to

Related object: L<RODA::RODADB::Result::Variable>

=cut

__PACKAGE__->belongs_to(
  "variable",
  "RODA::RODADB::Result::Variable",
  { id => "variable_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:oXSvZrcyn3I9zaSopEDJVw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
