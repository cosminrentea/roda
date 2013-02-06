use utf8;
package RODA::RODADB::Result::SelectionVariableCard;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::SelectionVariableCard

=head1 DESCRIPTION

Tabel ce stocheaza cartonasele de raspuns asociate anumitor variabile

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

=head1 TABLE: C<selection_variable_card>

=cut

__PACKAGE__->table("selection_variable_card");

=head1 ACCESSORS

=head2 instance_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul instantei ce contine variabile care au cartonase de raspuns

=head2 variable_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul variabilei careia ii sunt asociate cartonase de raspuns in instanta identificata prin atributul instance_id

=head2 item_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul elementului de raspuns din cadrul variabilei referite prin atributul variable_id, pentru care se furnizeaza un cartonas de raspuns

=head2 response_card

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul documentului ce constituie cartonasul de raspuns pentru elementul referit prin atributul item_id corespunzator variabilei identificate prin variable_id (impreuna cu atributul instance_id, refera atributele instance_id si document_id din tabelul instance_documents)

=cut

__PACKAGE__->add_columns(
  "instance_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "variable_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "item_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "response_card",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</variable_id>

=item * L</item_id>

=item * L</instance_id>

=back

=cut

__PACKAGE__->set_primary_key("variable_id", "item_id", "instance_id");

=head1 RELATIONS

=head2 instance_document

Type: belongs_to

Related object: L<RODA::RODADB::Result::InstanceDocument>

=cut

__PACKAGE__->belongs_to(
  "instance_document",
  "RODA::RODADB::Result::InstanceDocument",
  { document_id => "response_card", instance_id => "instance_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 selection_variable_item

Type: belongs_to

Related object: L<RODA::RODADB::Result::SelectionVariableItem>

=cut

__PACKAGE__->belongs_to(
  "selection_variable_item",
  "RODA::RODADB::Result::SelectionVariableItem",
  {
    instance_id => "instance_id",
    item_id     => "item_id",
    variable_id => "variable_id",
  },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:8FF8+KQMmCRp1bZxd9R0MQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
