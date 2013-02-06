use utf8;
package RODA::RODADB::Result::PersonOrg;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::PersonOrg

=head1 DESCRIPTION

Tabel ce stocheaza asocierile dintre persoane si organizatii (implementeaza relatia many-to-many intre tabelele person si org)

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

=head1 TABLE: C<person_org>

=cut

__PACKAGE__->table("person_org");

=head1 ACCESSORS

=head2 person_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Identificatorul persoanei care lucreaza in organizatie

=head2 org_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Idetificatorul organizatiei

=head2 role_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Identificatorul rolului detinut de persoana in cadrul organizatiei (refera atributul id al tabelului person_role)

=head2 datestart

  data_type: 'timestamp'
  is_nullable: 1

Data de inceput a apartenentei persoanei la organizatie

=head2 dateend

  data_type: 'timestamp'
  is_nullable: 1

Data de final a apartenentei persoanei la organizatie

=cut

__PACKAGE__->add_columns(
  "person_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "org_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "role_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "datestart",
  { data_type => "timestamp", is_nullable => 1 },
  "dateend",
  { data_type => "timestamp", is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</person_id>

=item * L</org_id>

=item * L</role_id>

=back

=cut

__PACKAGE__->set_primary_key("person_id", "org_id", "role_id");

=head1 RELATIONS

=head2 org

Type: belongs_to

Related object: L<RODA::RODADB::Result::Org>

=cut

__PACKAGE__->belongs_to(
  "org",
  "RODA::RODADB::Result::Org",
  { id => "org_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 person

Type: belongs_to

Related object: L<RODA::RODADB::Result::Person>

=cut

__PACKAGE__->belongs_to(
  "person",
  "RODA::RODADB::Result::Person",
  { id => "person_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 role

Type: belongs_to

Related object: L<RODA::RODADB::Result::PersonRole>

=cut

__PACKAGE__->belongs_to(
  "role",
  "RODA::RODADB::Result::PersonRole",
  { id => "role_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:QnuYIpMoLs6uZEVxWdHsvg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
