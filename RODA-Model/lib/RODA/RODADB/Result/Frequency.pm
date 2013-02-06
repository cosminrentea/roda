use utf8;
package RODA::RODADB::Result::Frequency;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Frequency

=head1 DESCRIPTION

Tabel ce stocheaza frecventele asociate elementelor de raspuns din cadrul variabilelor de selectie

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

=head1 TABLE: C<frequency>

=cut

__PACKAGE__->table("frequency");

=head1 ACCESSORS

=head2 variable_id

  data_type: 'integer'
  is_auto_increment: 1
  is_foreign_key: 1
  is_nullable: 0
  sequence: 'frequency_variable_id_seq'

Identificatorul variabilei

=head2 item_id

  data_type: 'integer'
  is_auto_increment: 1
  is_foreign_key: 1
  is_nullable: 0
  sequence: 'frequency_item_id_seq'

Item-ul din cadrul variabilei pentru care este furnizata valoarea frecventei

=head2 value

  data_type: 'real'
  is_nullable: 0

Valoarea frecventei

=cut

__PACKAGE__->add_columns(
  "variable_id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_foreign_key    => 1,
    is_nullable       => 0,
    sequence          => "frequency_variable_id_seq",
  },
  "item_id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_foreign_key    => 1,
    is_nullable       => 0,
    sequence          => "frequency_item_id_seq",
  },
  "value",
  { data_type => "real", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</variable_id>

=item * L</item_id>

=back

=cut

__PACKAGE__->set_primary_key("variable_id", "item_id");

=head1 RELATIONS

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


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 16:35:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:pM1sDMhEPDwDSVhOAhr18Q


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
