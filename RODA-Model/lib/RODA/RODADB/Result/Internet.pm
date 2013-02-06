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
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'internet_id_seq'

Codul contului pe internet al unui contact

=head2 internet_type

  data_type: 'varchar'
  is_nullable: 1
  size: 50

Tipul contului 

=head2 internet

  data_type: 'text'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "internet_id_seq",
  },
  "internet_type",
  { data_type => "varchar", is_nullable => 1, size => 50 },
  "internet",
  { data_type => "text", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 org_internets

Type: has_many

Related object: L<RODA::RODADB::Result::OrgInternet>

=cut

__PACKAGE__->has_many(
  "org_internets",
  "RODA::RODADB::Result::OrgInternet",
  { "foreign.internet_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 person_internets

Type: has_many

Related object: L<RODA::RODADB::Result::PersonInternet>

=cut

__PACKAGE__->has_many(
  "person_internets",
  "RODA::RODADB::Result::PersonInternet",
  { "foreign.internet_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:JtESmwwW8khc5GI9RS8AcQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
