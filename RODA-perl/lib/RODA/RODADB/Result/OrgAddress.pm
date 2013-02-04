use utf8;
package RODA::RODADB::Result::OrgAddress;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::OrgAddress

=head1 DESCRIPTION

Tabel asociativ ce modeleaza relatia many-to-many intre tabelele org si adresa

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

=head1 TABLE: C<org_address>

=cut

__PACKAGE__->table("org_address");

=head1 ACCESSORS

=head2 org_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul organizatiei care detine o adresa identificata prin atributul address_id (refera atributul id din tabelul org) 

=head2 address_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul adresei detinute de organizatia specificata prin atributul org_id (refera atributul id din tabelul org)

=head2 datestart

  data_type: 'timestamp'
  is_nullable: 0

Data incepand de la care adresa organizatiei referite prin atributul org_id a devenit cea identificata prin atributul address_id

=head2 dateend

  data_type: 'timestamp'
  is_nullable: 1

Data pana la care adresa organizatiei referite prin org_id a fost cea identificata prin address_id

=cut

__PACKAGE__->add_columns(
  "org_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "address_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "datestart",
  { data_type => "timestamp", is_nullable => 0 },
  "dateend",
  { data_type => "timestamp", is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</org_id>

=item * L</address_id>

=back

=cut

__PACKAGE__->set_primary_key("org_id", "address_id");

=head1 RELATIONS

=head2 address

Type: belongs_to

Related object: L<RODA::RODADB::Result::Address>

=cut

__PACKAGE__->belongs_to(
  "address",
  "RODA::RODADB::Result::Address",
  { id => "address_id" },
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
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:etu/rpdgmYU1Ba4tEqEIrA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
