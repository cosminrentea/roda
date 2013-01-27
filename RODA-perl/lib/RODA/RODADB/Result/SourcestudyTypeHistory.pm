use utf8;
package RODA::RODADB::Result::SourcestudyTypeHistory;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::SourcestudyTypeHistory

=head1 DESCRIPTION

Tabel ce stocheaza istoricul tipului (starii) surselor studiilor

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

=head1 TABLE: C<sourcestudy_type_history>

=cut

__PACKAGE__->table("sourcestudy_type_history");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'sourcestudy_type_history_id_seq'

Codul liniei referitoare la istoricul unui studiu care poate fi furnizat de catre o sursa

=head2 datestart

  data_type: 'timestamp'
  is_nullable: 0

Data de inceput

=head2 dateend

  data_type: 'timestamp'
  is_nullable: 0

Data de final

=head2 sourcestudy_type_id

  data_type: 'integer'
  is_auto_increment: 1
  is_foreign_key: 1
  is_nullable: 0
  sequence: 'sourcestudy_type_history_sourcestudy_type_id_seq'

Codul tipului (starii) studiului respectiv intre datele datestart si dateend

=head2 addedby

  data_type: 'integer'
  is_nullable: 0

Codul utilizatorului care a adaugat informatia referitoare la istoricul unui studiu

=head2 sourcesstudy_id

  data_type: 'integer'
  is_auto_increment: 1
  is_foreign_key: 1
  is_nullable: 0
  sequence: 'sourcestudy_type_history_sourcesstudy_id_seq'

Codul studiului care poate fi furnizat de catre o sursa

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "sourcestudy_type_history_id_seq",
  },
  "datestart",
  { data_type => "timestamp", is_nullable => 0 },
  "dateend",
  { data_type => "timestamp", is_nullable => 0 },
  "sourcestudy_type_id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_foreign_key    => 1,
    is_nullable       => 0,
    sequence          => "sourcestudy_type_history_sourcestudy_type_id_seq",
  },
  "addedby",
  { data_type => "integer", is_nullable => 0 },
  "sourcesstudy_id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_foreign_key    => 1,
    is_nullable       => 0,
    sequence          => "sourcestudy_type_history_sourcesstudy_id_seq",
  },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 sourcesstudy

Type: belongs_to

Related object: L<RODA::RODADB::Result::Sourcestudy>

=cut

__PACKAGE__->belongs_to(
  "sourcesstudy",
  "RODA::RODADB::Result::Sourcestudy",
  { id => "sourcesstudy_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 sourcestudy_type

Type: belongs_to

Related object: L<RODA::RODADB::Result::SourcestudyType>

=cut

__PACKAGE__->belongs_to(
  "sourcestudy_type",
  "RODA::RODADB::Result::SourcestudyType",
  { id => "sourcestudy_type_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 16:35:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:nMtCLBY1GQTfbpFEEq0Bpw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
