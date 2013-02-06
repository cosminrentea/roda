use utf8;
package RODA::RODADB::Result::Sourcestudy;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Sourcestudy

=head1 DESCRIPTION

Tabel ce stocheaza informatii despre studiile pe care le poate furniza o organizatie 

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

=head1 TABLE: C<sourcestudy>

=cut

__PACKAGE__->table("sourcestudy");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'sourcestudy_id_seq'

Codul studiului pe care il poate furniza o organizatie

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 150

Denumirea studiului

=head2 details

  data_type: 'text'
  is_nullable: 1

Detalii asupra studiului care poate fi furnizat

=head2 org_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul organizatiei care poate pune la dispozitie studiul respectiv (refera atributul org_id din tabelul sources)

=head2 type

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Tipul sursei unui studiu

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "sourcestudy_id_seq",
  },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 150 },
  "details",
  { data_type => "text", is_nullable => 1 },
  "org_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "type",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 org

Type: belongs_to

Related object: L<RODA::RODADB::Result::Source>

=cut

__PACKAGE__->belongs_to(
  "org",
  "RODA::RODADB::Result::Source",
  { org_id => "org_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 sourcestudy_type_histories

Type: has_many

Related object: L<RODA::RODADB::Result::SourcestudyTypeHistory>

=cut

__PACKAGE__->has_many(
  "sourcestudy_type_histories",
  "RODA::RODADB::Result::SourcestudyTypeHistory",
  { "foreign.sourcesstudy_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 type

Type: belongs_to

Related object: L<RODA::RODADB::Result::SourcestudyType>

=cut

__PACKAGE__->belongs_to(
  "type",
  "RODA::RODADB::Result::SourcestudyType",
  { id => "type" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:lRlNLzOCCJLGyUX9Km6oLQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
