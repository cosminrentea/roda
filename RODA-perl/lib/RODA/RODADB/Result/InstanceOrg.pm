use utf8;
package RODA::RODADB::Result::InstanceOrg;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::InstanceOrg

=head1 DESCRIPTION

Tabel ce implementeaza relatia many-to-many intre instanta si organizatie

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

=head1 TABLE: C<instance_org>

=cut

__PACKAGE__->table("instance_org");

=head1 ACCESSORS

=head2 org_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul organizatiei care se afla in relatia specificata prin atributul assoc_type_id cu instanta identificata prin atributul instance_id (refera atributul id al tabelului org)

=head2 instance_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul instantei care se afla in relatia specificata prin atributul assoc_type_id cu organizatia identificata prin atributul org_id (refera atributul id al tabelului instance)

=head2 assoc_type_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul tipului de asociere corespunzator relatiei dintre organizatia identificata prin atributul org_id si instanta specificata prin atributul instance_id (refera atributul id al tabelului instance_org_assoc)

=head2 assoc_details

  data_type: 'text'
  is_nullable: 1

Detaliile asocierii dintre  organizatia identificata prin atributul org_id si instanta specificata prin atributul instance_id

=head2 citation

  data_type: 'text'
  is_nullable: 0

Modalitatea de citare in cadrul instantei identificate prin atributul instance_id realizate de catre organizatia referita prin atributul org_id

=cut

__PACKAGE__->add_columns(
  "org_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "instance_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "assoc_type_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "assoc_details",
  { data_type => "text", is_nullable => 1 },
  "citation",
  { data_type => "text", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</org_id>

=item * L</instance_id>

=back

=cut

__PACKAGE__->set_primary_key("org_id", "instance_id");

=head1 RELATIONS

=head2 assoc_type

Type: belongs_to

Related object: L<RODA::RODADB::Result::InstanceOrgAssoc>

=cut

__PACKAGE__->belongs_to(
  "assoc_type",
  "RODA::RODADB::Result::InstanceOrgAssoc",
  { id => "assoc_type_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 instance

Type: belongs_to

Related object: L<RODA::RODADB::Result::Instance>

=cut

__PACKAGE__->belongs_to(
  "instance",
  "RODA::RODADB::Result::Instance",
  { id => "instance_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 org

Type: belongs_to

Related object: L<RODA::RODADB::Result::Org>

=cut

__PACKAGE__->belongs_to(
  "org",
  "RODA::RODADB::Result::Org",
  { id => "org_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:LyL1sHVDLW6SxrqNxvsPuA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
