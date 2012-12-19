use utf8;
package RODA::RODADB::Result::Skip;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Skip - Tabel ce defineste salturi de la o variabila la alta

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

=head1 TABLE: C<skip>

=cut

__PACKAGE__->table("skip");

=head1 ACCESSORS

=head2 instance_id

  data_type: 'integer'
  is_nullable: 0

Identificatorul instantei

=head2 variable_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Identificatorul variabilei

=head2 id

  data_type: 'integer'
  is_nullable: 0

Identificatorul saltului, corespunzator variabilei referite prin atributul variable_id din cadrul instantei identificate prin atributul instance_id 

=head2 condition

  data_type: 'text'
  is_nullable: 0

Conditia codificata ca text, care in urma evaluarii va confirma saltul

=head2 next_variable_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Identificatorul variabilei la care se va face salt

=cut

__PACKAGE__->add_columns(
  "instance_id",
  { data_type => "integer", is_nullable => 0 },
  "variable_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "id",
  { data_type => "integer", is_nullable => 0 },
  "condition",
  { data_type => "text", is_nullable => 0 },
  "next_variable_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</variable_id>

=item * L</instance_id>

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("variable_id", "instance_id", "id");

=head1 RELATIONS

=head2 next_variable

Type: belongs_to

Related object: L<RODA::RODADB::Result::Variable>

=cut

__PACKAGE__->belongs_to(
  "next_variable",
  "RODA::RODADB::Result::Variable",
  { id => "next_variable_id" },
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


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:PMD1y68RUDuhG6yZ+ENKag


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
