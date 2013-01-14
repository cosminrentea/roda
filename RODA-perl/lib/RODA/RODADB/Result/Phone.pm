use utf8;
package RODA::RODADB::Result::Phone;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Phone - Tabel ce contine toate numerele de telefon ale entitatilor din baza de date

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

=head1 TABLE: C<phones>

=cut

__PACKAGE__->table("phones");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul numarului de telefon in tabel

=head2 phone

  data_type: 'varchar'
  is_nullable: 0
  size: 50

Sirul de caractere reprezentand numarul de telefon

=head2 phone_type

  data_type: 'varchar'
  is_nullable: 0
  size: 30
  
Tipul numarului de telefon  
  
=head2 entity_type

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Tipul entitatii careia ii este asociat numarul de telefon specificat prin atributul phone

=head2 entity_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul entitatii careia ii  ii este asociat numarul de telefon specificat prin atributul phone

=head2 ismain

  data_type: 'boolean'
  is_nullable: 0

Atribut boolean ce precizeaza daca numarul de telefon este cel principal al entitatii referite prin atributul entity_id

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "phone",
  { data_type => "varchar", is_nullable => 0, size => 50 },
  "phone_type",
  { data_type => "varchar", is_nullable => 0, size => 30 },
  "entity_type",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "entity_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "ismain",
  { data_type => "boolean", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 entity

Type: belongs_to

Related object: L<RODA::RODADB::Result::Person>

=cut

__PACKAGE__->belongs_to(
  "entity",
  "RODA::RODADB::Result::Person",
  { id => "entity_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 entity_type

Type: belongs_to

Related object: L<RODA::RODADB::Result::Org>

=cut

__PACKAGE__->belongs_to(
  "entity_type",
  "RODA::RODADB::Result::Org",
  { id => "entity_type" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:bmlgup77wMy1rFjxVoEXIA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
