use utf8;
package RODA::RODADB::Result::Skip;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Skip

=head1 DESCRIPTION

Tabel ce contine salturile care pot avea loc de la o variabila la alta

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

=head2 variable_id

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

Identificatorul variabilei

=head2 id

  data_type: 'bigint'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'skip_id_seq'

Identificatorul saltului, corespunzator variabilei referite prin atributul variable_id din cadrul instantei identificate prin atributul instance_id 

=head2 condition

  data_type: 'text'
  is_nullable: 0

Conditia specificata ca text, care in urma evaluarii va confirma saltul

=head2 next_variable_id

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

Identificatorul variabilei la care se va face salt

=cut

__PACKAGE__->add_columns(
  "variable_id",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
  "id",
  {
    data_type         => "bigint",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "skip_id_seq",
  },
  "condition",
  { data_type => "text", is_nullable => 0 },
  "next_variable_id",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 next_variable

Type: belongs_to

Related object: L<RODA::RODADB::Result::Variable>

=cut

__PACKAGE__->belongs_to(
  "next_variable",
  "RODA::RODADB::Result::Variable",
  { id => "next_variable_id" },
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
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:83tKDY76sAO0qtlFDeQAXA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
