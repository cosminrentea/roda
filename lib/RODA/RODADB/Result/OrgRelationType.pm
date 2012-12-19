use utf8;
package RODA::RODADB::Result::OrgRelationType;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::OrgRelationType

=head1 DESCRIPTION

Tabel continand tipurile relatiilor existente intre organizatii

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

=head1 TABLE: C<org_relation_type>

=cut

__PACKAGE__->table("org_relation_type");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul tipului relatiei intre organizatii

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 50

Numele relatiilor intre organizatii

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 50 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 org_relations

Type: has_many

Related object: L<RODA::RODADB::Result::OrgRelation>

=cut

__PACKAGE__->has_many(
  "org_relations",
  "RODA::RODADB::Result::OrgRelation",
  { "foreign.org_relation_type_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:EllfS1vrxq+lez3brg31Zw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
