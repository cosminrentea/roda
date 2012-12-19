use utf8;
package RODA::RODADB::Result::OtherStatistic;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::OtherStatistic - Tabel ce contine statistici specifice variabilelor editate

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

=head1 TABLE: C<other_statistics>

=cut

__PACKAGE__->table("other_statistics");

=head1 ACCESSORS

=head2 instance_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Identificatorul instantei 

=head2 variable_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul variabilei pentru care sunt stocate statistici

=head2 statistic_name

  data_type: 'varchar'
  is_nullable: 0
  size: 30

Denumirea statisticii

=head2 statistic_value

  data_type: 'real'
  is_nullable: 0

Valoarea statisticii

=cut

__PACKAGE__->add_columns(
  "instance_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "variable_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "statistic_name",
  { data_type => "varchar", is_nullable => 0, size => 30 },
  "statistic_value",
  { data_type => "real", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</instance_id>

=item * L</variable_id>

=back

=cut

__PACKAGE__->set_primary_key("instance_id", "variable_id");

=head1 RELATIONS

=head2 edited_variable

Type: belongs_to

Related object: L<RODA::RODADB::Result::EditedVariable>

=cut

__PACKAGE__->belongs_to(
  "edited_variable",
  "RODA::RODADB::Result::EditedVariable",
  { instance_id => "instance_id", variable_id => "variable_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:Zv7SEJ8sNkCtYtlMTqBVug


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
