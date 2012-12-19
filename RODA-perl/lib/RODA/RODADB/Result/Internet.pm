use utf8;
package RODA::RODADB::Result::Internet;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Internet

=head1 DESCRIPTION

Tabel ce contine toate conturile de pe retelele sociale de pe internet (facebook, twitter, im  si altele)

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

=head1 TABLE: C<internet>

=cut

__PACKAGE__->table("internet");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul contactului respectiv la internet

=head2 entity_type

  data_type: 'integer'
  is_nullable: 0

Tipul entitatii careia ii apartine acest contact

=head2 entity_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul entitatii careia ii apartine acest contact

=head2 internet_type_id

  data_type: 'integer'
  is_nullable: 0

Tipul contului 

=head2 content

  data_type: 'varchar'
  is_nullable: 0
  size: 250

Sir de caractere reprezentand id-ul contului respectiv pe retea

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "entity_type",
  { data_type => "integer", is_nullable => 0 },
  "entity_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "internet_type_id",
  { data_type => "integer", is_nullable => 0 },
  "content",
  { data_type => "varchar", is_nullable => 0, size => 250 },
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

=head2 entity_2

Type: belongs_to

Related object: L<RODA::RODADB::Result::Org>

=cut

__PACKAGE__->belongs_to(
  "entity_2",
  "RODA::RODADB::Result::Org",
  { id => "entity_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:46+wEYbQrr1i7rK/jjwdLA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
