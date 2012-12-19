use utf8;
package RODA::RODADB::Result::Source;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Source

=head1 DESCRIPTION

Tabel ce stocheaza sursele de studii pentru partea de colectare

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

=head1 TABLE: C<sources>

=cut

__PACKAGE__->table("sources");

=head1 ACCESSORS

=head2 org_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul organizatiei care reprezinta o sursa a studiilor colectate

=head2 stype

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul tipului sursei (refera atributul id din tabelul sourcetype)

=cut

__PACKAGE__->add_columns(
  "org_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "stype",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</org_id>

=back

=cut

__PACKAGE__->set_primary_key("org_id");

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

=head2 source_contacts

Type: has_many

Related object: L<RODA::RODADB::Result::SourceContact>

=cut

__PACKAGE__->has_many(
  "source_contacts",
  "RODA::RODADB::Result::SourceContact",
  { "foreign.org_id" => "self.org_id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 source_type_histories

Type: has_many

Related object: L<RODA::RODADB::Result::SourceTypeHistory>

=cut

__PACKAGE__->has_many(
  "source_type_histories",
  "RODA::RODADB::Result::SourceTypeHistory",
  { "foreign.org_id" => "self.org_id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 sourcestudies

Type: has_many

Related object: L<RODA::RODADB::Result::Sourcestudy>

=cut

__PACKAGE__->has_many(
  "sourcestudies",
  "RODA::RODADB::Result::Sourcestudy",
  { "foreign.org_id" => "self.org_id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 stype

Type: belongs_to

Related object: L<RODA::RODADB::Result::Sourcetype>

=cut

__PACKAGE__->belongs_to(
  "stype",
  "RODA::RODADB::Result::Sourcetype",
  { id => "stype" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:ksfzcEoPFQMLC8k2Vn4WLA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
