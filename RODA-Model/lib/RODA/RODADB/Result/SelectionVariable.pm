use utf8;
package RODA::RODADB::Result::SelectionVariable;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::SelectionVariable - Tabel ce contine informatii despre variabilele de selectie din instante

=cut

use strict;
use warnings;

use Moose;
use MooseX::NonMoose;
use MooseX::MarkAsMethods autoclean => 1;
extends 'DBIx::Class::Core';

=head1 COMPONENTE UTILIZATE

=over 4

=item * L<DBIx::Class::InflateColumn::DateTime>

=back

=cut

__PACKAGE__->load_components("InflateColumn::DateTime");

=head1 TABLE: C<selection_variable>

=cut

__PACKAGE__->table("selection_variable");

=head1 ACCESSORS

=head2 variable_id

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

Codul unei variabile de selectie din cadrul instantei identificate prin instance_id

=head2 min_count

  data_type: 'smallint'
  is_nullable: 0

Numarul minim de selectii asteptate ca raspuns

=head2 max_count

  data_type: 'smallint'
  is_nullable: 0

Numarul maxim de selectii asteptate ca raspuns

=cut

__PACKAGE__->add_columns(
  "variable_id",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
  "min_count",
  { data_type => "smallint", is_nullable => 0 },
  "max_count",
  { data_type => "smallint", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</variable_id>

=back

=cut

__PACKAGE__->set_primary_key("variable_id");

=head1 RELATII

=head2 selection_variable_items

Type: has_many

Related object: L<RODA::RODADB::Result::SelectionVariableItem>

=cut

__PACKAGE__->has_many(
  "selection_variable_items",
  "RODA::RODADB::Result::SelectionVariableItem",
  { "foreign.variable_id" => "self.variable_id" },
  { cascade_copy => 0, cascade_delete => 0 },
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
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:KxULcEulah2/llx1Ffxj6g


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;

=head1 METODE SUPLIMENTARE

=cut

=head2 attach_items

Ataseaza elemente de selectie variabilei curente.

=cut

sub attach_items {
     my ( $self, %params ) = @_;
     foreach my $item (@{$params{items}}) { 
     	my $guard = $self->result_source->schema()->txn_scope_guard;
        my $itemrs = $self->result_source->schema()->resultset('Item')->checkitem(%$item);
             
        if ($itemrs) { 
        	$self->result_source->schema()->resultset('SelectionVariableItem')
        									->find_or_create({
          													  item_id => $itemrs->id,
          													  variable_id => $self->variable_id,
          													  order_of_item_in_variable => $item -> {order_of_item_in_variable},
          													  response_card_file_id => $item -> {response_card_file_id},
          													  frequency_value => $item -> {frequency_value},
         													 },
         													 {
         		 											  key => 'primary',
         													 }
         													);
      		}
      		$guard->commit; 	           
        }
}

1;
