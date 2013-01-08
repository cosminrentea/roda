use utf8;
package RODA::RODADB::Result::Email;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Email - Tabel unic pentru toate adresele de e-mail din baza de date

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

=head1 TABLE: C<emails>

=cut

__PACKAGE__->table("emails");

=head1 ACCESSORS

=head2 email

  data_type: 'varchar'
  is_nullable: 0
  size: 200

=head2 entity_type

  data_type: 'integer'
  is_nullable: 0

Sirul de caractere reprezentand adresa de email

=head2 entity_id

  data_type: 'integer'
  is_nullable: 0

Tipul entitatii careia ii apartine adresa de email

=head2 ismain

  data_type: 'boolean'
  default_value: false
  is_nullable: 0

Codul entitatii careia ii apartine adresa de email

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'emails_id_seq'

Atribut boolean care specifica daca adresa de email este cea principala a entitatii respective

=cut

__PACKAGE__->add_columns(
  "email",
  { data_type => "varchar", is_nullable => 0, size => 200 },
  "entity_type",
  { data_type => "integer", is_nullable => 0 },
  "entity_id",
  { data_type => "integer", is_nullable => 0 },
  "ismain",
  { data_type => "boolean", is_nullable => 1 },
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "emails_id_seq",
  },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2013-01-09 00:12:10
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:n3OjCY/h5KE8sFYxofEB2g


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
