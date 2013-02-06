use utf8;
package RODA::RODADB::Result::Form;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Form

=head1 DESCRIPTION

Tabel pentru informatiile legate de un chestionarele aplicate (un rand reprezinta o anumita fisa completata pe teren cu raspunsuri)

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

=head1 TABLE: C<form>

=cut

__PACKAGE__->table("form");

=head1 ACCESSORS

=head2 id

  data_type: 'bigint'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'form_id_seq'

=head2 instance_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 order_in_instance

  data_type: 'integer'
  is_nullable: 0

=head2 operator_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

=head2 operator_notes

  data_type: 'text'
  is_nullable: 1

=head2 fill_time

  data_type: 'timestamp'
  is_nullable: 1

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "bigint",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "form_id_seq",
  },
  "instance_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "order_in_instance",
  { data_type => "integer", is_nullable => 0 },
  "operator_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
  "operator_notes",
  { data_type => "text", is_nullable => 1 },
  "fill_time",
  { data_type => "timestamp", is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 UNIQUE CONSTRAINTS

=head2 C<form_instance_id_Idx>

=over 4

=item * L</instance_id>

=item * L</order_in_instance>

=back

=cut

__PACKAGE__->add_unique_constraint("form_instance_id_Idx", ["instance_id", "order_in_instance"]);

=head1 RELATIONS

=head2 form_edited_number_vars

Type: has_many

Related object: L<RODA::RODADB::Result::FormEditedNumberVar>

=cut

__PACKAGE__->has_many(
  "form_edited_number_vars",
  "RODA::RODADB::Result::FormEditedNumberVar",
  { "foreign.form_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 form_edited_text_vars

Type: has_many

Related object: L<RODA::RODADB::Result::FormEditedTextVar>

=cut

__PACKAGE__->has_many(
  "form_edited_text_vars",
  "RODA::RODADB::Result::FormEditedTextVar",
  { "foreign.form_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 form_selection_vars

Type: has_many

Related object: L<RODA::RODADB::Result::FormSelectionVar>

=cut

__PACKAGE__->has_many(
  "form_selection_vars",
  "RODA::RODADB::Result::FormSelectionVar",
  { "foreign.form_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

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

=head2 operator

Type: belongs_to

Related object: L<RODA::RODADB::Result::Person>

=cut

__PACKAGE__->belongs_to(
  "operator",
  "RODA::RODADB::Result::Person",
  { id => "operator_id" },
  {
    is_deferrable => 0,
    join_type     => "LEFT",
    on_delete     => "NO ACTION",
    on_update     => "NO ACTION",
  },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:0JBblajr6LhJASw1J1CVmg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
