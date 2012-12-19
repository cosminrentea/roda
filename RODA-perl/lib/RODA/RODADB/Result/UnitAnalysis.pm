use utf8;
package RODA::RODADB::Result::UnitAnalysis;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::UnitAnalysis

=head1 DESCRIPTION

Tabel care stocheaza unitatea de analiza (individ, gospodarie, etc)

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

=head1 TABLE: C<unit_analysis>

=cut

__PACKAGE__->table("unit_analysis");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul unitatii de analiza

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Denumirea unitatii de analiza (individ, familie, organizatie, gospodarie etc.)

=head2 description

  data_type: 'text'
  is_nullable: 1

Descrierea unitatii de analiza

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 100 },
  "description",
  { data_type => "text", is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 instances

Type: has_many

Related object: L<RODA::RODADB::Result::Instance>

=cut

__PACKAGE__->has_many(
  "instances",
  "RODA::RODADB::Result::Instance",
  { "foreign.unit_analysis_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:K970W/VnvGesoRc50Ac0jQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
