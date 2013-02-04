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

=head1 TABLE: C<phone>

=cut

__PACKAGE__->table("phone");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'phone_id_seq'

Codul numarului de telefon in tabel

=head2 phone

  data_type: 'varchar'
  is_nullable: 0
  size: 30

Sirul de caractere reprezentand numarul de telefon

=head2 phone_type

  data_type: 'varchar'
  is_nullable: 1
  size: 50
  
Tipul numarului de telefon  
  

=head2 ismain

  data_type: 'boolean'
  is_nullable: 1

Atribut boolean ce precizeaza daca numarul de telefon este cel principal al entitatii referite prin atributul entity_id

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "phone_id_seq",
  },
  "phone",
  { data_type => "varchar", is_nullable => 0, size => 30 },
  "phone_type",
  { data_type => "varchar", is_nullable => 1, size => 50 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 org_phones

Type: has_many

Related object: L<RODA::RODADB::Result::OrgPhone>

=cut

__PACKAGE__->has_many(
  "org_phones",
  "RODA::RODADB::Result::OrgPhone",
  { "foreign.phone_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 person_phones

Type: has_many

Related object: L<RODA::RODADB::Result::PersonPhone>

=cut

__PACKAGE__->has_many(
  "person_phones",
  "RODA::RODADB::Result::PersonPhone",
  { "foreign.phone_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 orgs

Type: many_to_many

Composing rels: L</org_phones> -> org

=cut

__PACKAGE__->many_to_many("orgs", "org_phones", "org");

=head2 people

Type: many_to_many

Composing rels: L</person_phones> -> person

=cut

__PACKAGE__->many_to_many("people", "person_phones", "person");


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 19:43:20
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:AmBleXZpOxRfLpEH9CXTNg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
