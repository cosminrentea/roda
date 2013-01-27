use utf8;
package RODA::RODADB::Result::SourcetypeHistory;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::SourcetypeHistory - Tabel ce stocheaza istoricul tipului surselor

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

=head1 TABLE: C<sourcetype_history>

=cut

__PACKAGE__->table("sourcetype_history");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'sourcetype_history_id_seq'

Codul liniei referitoare la istoricul surselor

=head2 datestart

  data_type: 'timestamp'
  is_nullable: 0

Data de inceput a starii unei surse

=head2 dateend

  data_type: 'timestamp'
  is_nullable: 1

Data de final a starii unei surse

=head2 org_id

  data_type: 'integer'
  is_auto_increment: 1
  is_foreign_key: 1
  is_nullable: 0
  sequence: 'sourcetype_history_org_id_seq'

Codul organizatiei (sursei)careia ii corespunde o stare intre cele doua date calendaristice

=head2 sourcetype_id

  data_type: 'integer'
  is_auto_increment: 1
  is_foreign_key: 1
  is_nullable: 0
  sequence: 'sourcetype_history_sourcetype_id_seq'

Codul tipului sursei dintre cele doua date

=head2 added_by

  data_type: 'integer'
  is_nullable: 0

Codul utilizatorului care a adaugat informatia referitoare la istoricul unei surse

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "sourcetype_history_id_seq",
  },
  "datestart",
  { data_type => "timestamp", is_nullable => 0 },
  "dateend",
  { data_type => "timestamp", is_nullable => 1 },
  "org_id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_foreign_key    => 1,
    is_nullable       => 0,
    sequence          => "sourcetype_history_org_id_seq",
  },
  "sourcetype_id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_foreign_key    => 1,
    is_nullable       => 0,
    sequence          => "sourcetype_history_sourcetype_id_seq",
  },
  "added_by",
  { data_type => "integer", is_nullable => 0 },
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

=head2 sourcetype

Type: belongs_to

Related object: L<RODA::RODADB::Result::Sourcetype>

=cut

__PACKAGE__->belongs_to(
  "sourcetype",
  "RODA::RODADB::Result::Sourcetype",
  { id => "sourcetype_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 16:35:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:fvIbUsZzRtXESZnKvxDR3Q


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
