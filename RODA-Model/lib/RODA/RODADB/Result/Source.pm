use utf8;
package RODA::RODADB::Result::Source;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NUME

RODA::RODADB::Result::Source - Tabel ce stocheaza sursele (organizatiile) care pot oferi studii pentru partea de colectare

=cut

use strict;
use warnings;

use Moose;
use MooseX::NonMoose;
use MooseX::MarkAsMethods autoclean => 1;
extends 'DBIx::Class::Core';

=head1 COMPONENTE UTILIZATE

=over 4

=item * L<DBIx::Class::InflateColumn::DateTime>

=back

=cut

__PACKAGE__->load_components("InflateColumn::DateTime");

=head1 TABLE: C<source>

=cut

__PACKAGE__->table("source");

=head1 ACCESSORS

=head2 org_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul organizatiei care reprezinta o sursa a studiilor colectate

=head2 sourcetype_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul tipului sursei (refera atributul id din tabelul sourcetype)

=cut

__PACKAGE__->add_columns(
  "org_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "sourcetype_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</org_id>

=back

=cut

__PACKAGE__->set_primary_key("org_id");

=head1 RELATII

=head2 org

Type: belongs_to

Related object: L<RODA::RODADB::Result::Org>

=cut

__PACKAGE__->belongs_to(
  "org",
  "RODA::RODADB::Result::Org",
  { id => "org_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 source_contacts

Type: has_many

Related object: L<RODA::RODADB::Result::SourceContact>

=cut

__PACKAGE__->has_many(
  "source_contacts",
  "RODA::RODADB::Result::SourceContact",
  { "foreign.source_id" => "self.org_id" },
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

=head2 sourcetype_histories

Type: has_many

Related object: L<RODA::RODADB::Result::SourcetypeHistory>

=cut

__PACKAGE__->has_many(
  "sourcetype_histories",
  "RODA::RODADB::Result::SourcetypeHistory",
  { "foreign.org_id" => "self.org_id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:KziyA/eLpP4OnQ53VlGtBw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;

=head1 METODE SUPLIMENTARE

=cut

=head2 attach_source_contacts

Ataseaza persoanele de contact ale unei surse ce poate furniza studii. De asemenea, functia ataseaza si metoda de contact corespunzatoare fiecarei persoane. 

=cut


sub attach_source_contacts {
    # TODO
}

=head2 attach_source_studies

Ataseaza studiile furnizate de sursa (organizatia) curenta.

=cut

sub attach_source_studies {
     # TODO
}

=head2 attach_source_type_history

Ataseaza istoricul tipului sursei curente. Metoda este utila initial; ulterior, liniile acestui tabel vor fi adaugate automat, prin intermediul unui declansator.

=cut

sub attach_source_type_history {
     # TODO
}

1;
