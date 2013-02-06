use utf8;
package RODA::RODADB::Result::FormSelectionVar;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::FormSelectionVar

=head1 DESCRIPTION

Tabel ce inregistreaza raspunsurile la variabilele de selectie (pentru care exista optiuni de raspuns) 

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

=head1 TABLE: C<form_selection_var>

=cut

__PACKAGE__->table("form_selection_var");

=head1 ACCESSORS

=head2 form_id

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

Codul unui formular completat in cadrul instantei identificate prin atributul instance_id

=head2 variable_id

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

Codul unei variabile din cadrul instantei identificate prin atributul instance_id

=head2 item_id

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

Identificatorul elementului selectat ca raspuns pentru variabila identificata prin atributul variable_id din instanta referita prin atributul instance_id, asociat formularului specificat prin atributul form_id

=head2 order_of_items_in_response

  data_type: 'integer'
  is_nullable: 1

Numarul de ordine al elementului corespunzator variabilei in cadrul raspunsului (relevant pentru raspunsuri care accepta o selectie multipla, in care ordinea este importanta)

=cut

__PACKAGE__->add_columns(
  "form_id",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
  "variable_id",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
  "item_id",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
  "order_of_items_in_response",
  { data_type => "integer", is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</form_id>

=item * L</variable_id>

=item * L</item_id>

=back

=cut

__PACKAGE__->set_primary_key("form_id", "variable_id", "item_id");

=head1 RELATIONS

=head2 form

Type: belongs_to

Related object: L<RODA::RODADB::Result::Form>

=cut

__PACKAGE__->belongs_to(
  "form",
  "RODA::RODADB::Result::Form",
  { id => "form_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 selection_variable_item

Type: belongs_to

Related object: L<RODA::RODADB::Result::SelectionVariableItem>

=cut

__PACKAGE__->belongs_to(
  "selection_variable_item",
  "RODA::RODADB::Result::SelectionVariableItem",
  { item_id => "item_id", variable_id => "variable_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:Lfrtv3Y0guFQXt1XHzuxnA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
