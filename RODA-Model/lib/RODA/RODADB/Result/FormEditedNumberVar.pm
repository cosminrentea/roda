use utf8;
package RODA::RODADB::Result::FormEditedNumberVar;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::FormEditedNumberVar

=head1 DESCRIPTION

Tabel ce inregistreaza raspunsurile numerice ale variabilelor de tip editat

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

=head1 TABLE: C<form_edited_number_var>

=cut

__PACKAGE__->table("form_edited_number_var");

=head1 ACCESSORS

=head2 form_id

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

Codul formularului completat in cadrul instantei identificate prin atributul instance_id

=head2 variable_id

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

Codul unei variabile editate de tip numeric in cadrul instantei identificate prin atributul instance_id

=head2 value

  data_type: 'numeric'
  is_nullable: 0
  size: [10,2]

Valoarea numerica furnizata ca raspuns pentru variabila referita prin atributul variable_id din cadrul instantei identificate prin atributul instance_id, asociata formularului specificat prin atributul form_id  

=cut

__PACKAGE__->add_columns(
  "form_id",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
  "variable_id",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
  "value",
  { data_type => "numeric", is_nullable => 0, size => [10, 2] },
);

=head1 PRIMARY KEY

=over 4

=item * L</variable_id>

=item * L</form_id>

=back

=cut

__PACKAGE__->set_primary_key("variable_id", "form_id");

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
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:bEsHI7cnrkuw+kXN7dQvlg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
