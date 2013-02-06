use utf8;
package RODA::RODADB::Result::StudyDescr;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::StudyDescr

=head1 DESCRIPTION

Tabel ce stocheaza informatii despre titlurile studiilor din baza de date

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

=head1 TABLE: C<study_descr>

=cut

__PACKAGE__->table("study_descr");

=head1 ACCESSORS

=head2 lang_id

  data_type: 'char'
  is_foreign_key: 1
  is_nullable: 0
  size: 2

Limba in care este furnizat titlul identificat prin atributul id

=head2 title_type_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Tipul titlului (refera atributul id din tabelul table_type)

=head2 study_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul studiului caruia ii corespunde titlul (refera atributul id din tabelul study)

=head2 abstract

  data_type: 'text'
  is_nullable: 1

=head2 grant_details

  data_type: 'text'
  is_nullable: 1

=head2 title

  data_type: 'varchar'
  is_nullable: 0
  size: 300

=cut

__PACKAGE__->add_columns(
  "lang_id",
  { data_type => "char", is_foreign_key => 1, is_nullable => 0, size => 2 },
  "title_type_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "study_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "abstract",
  { data_type => "text", is_nullable => 1 },
  "grant_details",
  { data_type => "text", is_nullable => 1 },
  "title",
  { data_type => "varchar", is_nullable => 0, size => 300 },
);

=head1 PRIMARY KEY

=over 4

=item * L</study_id>

=item * L</lang_id>

=back

=cut

__PACKAGE__->set_primary_key("study_id", "lang_id");

=head1 RELATIONS

=head2 lang

Type: belongs_to

Related object: L<RODA::RODADB::Result::Lang>

=cut

__PACKAGE__->belongs_to(
  "lang",
  "RODA::RODADB::Result::Lang",
  { id => "lang_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 study

Type: belongs_to

Related object: L<RODA::RODADB::Result::Study>

=cut

__PACKAGE__->belongs_to(
  "study",
  "RODA::RODADB::Result::Study",
  { id => "study_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 title_type

Type: belongs_to

Related object: L<RODA::RODADB::Result::TitleType>

=cut

__PACKAGE__->belongs_to(
  "title_type",
  "RODA::RODADB::Result::TitleType",
  { id => "title_type_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:bp2G23eFf/JaNE4hqKttvw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
