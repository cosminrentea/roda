use utf8;
package RODA::RODADB::Result::PersonOrg;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::PersonOrg

=head1 DESCRIPTION

tabel asociativ pentru relatia many-to-many intre persoane si organizatii

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
  is_nullable: 0

Data de inceput a apartenentei persoanei la organizatie

=head2 dateend

  data_type: 'timestamp'
  is_nullable: 0

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
  { data_type => "timestamp", is_nullable => 0 },
  "dateend",
  { data_type => "timestamp", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</person_id>

=item * L</org_id>

=back

=cut

__PACKAGE__->set_primary_key("person_id", "org_id");

=head1 RELATIONS

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

=head2 person

Type: belongs_to

Related object: L<RODA::RODADB::Result::Person>

=cut

__PACKAGE__->belongs_to(
  "person",
  "RODA::RODADB::Result::Person",
  { id => "person_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 role

Type: belongs_to

Related object: L<RODA::RODADB::Result::PersonRole>

=cut

__PACKAGE__->belongs_to(
  "role",
  "RODA::RODADB::Result::PersonRole",
  { id => "role_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:jAox8A31ees6PpapyOkFAQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
