use utf8;
package RODA::RODADB::Result::StudyKeyword;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB2::Result::StudyKeyword

=head1 DESCRIPTION

Tabel ce stocheaza asocierile dintre studii si cuvintele cheie (implementeaza relatia many-to-many intre tabelele study si keyword)

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

=head1 TABLE: C<study_keyword>

=cut

__PACKAGE__->table("study_keyword");

=head1 ACCESSORS

=head2 study_id

  data_type: 'integer'
  is_auto_increment: 1
  is_foreign_key: 1
  is_nullable: 0
  sequence: 'study_keyword_study_id_seq'

Codul studiului caruia ii este asociat cuvantul cheie referit prin atributul keyword_id

=head2 keyword_id

  data_type: 'integer'
  is_auto_increment: 1
  is_foreign_key: 1
  is_nullable: 0
  sequence: 'study_keyword_keyword_id_seq'

Cuvantul cheie asociat studiului identificat prin atributul study_id

=head2 added

  data_type: 'timestamp'
  is_nullable: 0

Momentul de timp la care a fost adaugata o asociere intre un cuvant cheie si un studiu

=head2 added_by

  data_type: 'integer'
  is_nullable: 0

Utilizatorul care a adaugat asocierea dintre un studiu si un cuvant cheie

=cut

__PACKAGE__->add_columns(
  "study_id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_foreign_key    => 1,
    is_nullable       => 0,
    sequence          => "study_keyword_study_id_seq",
  },
  "keyword_id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_foreign_key    => 1,
    is_nullable       => 0,
    sequence          => "study_keyword_keyword_id_seq",
  },
  "added",
  { data_type => "timestamp", is_nullable => 0 },
  "added_by",
  { data_type => "integer", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</study_id>

=item * L</keyword_id>

=back

=cut

__PACKAGE__->set_primary_key("study_id", "keyword_id");

=head1 RELATIONS

=head2 keyword

Type: belongs_to

Related object: L<RODA::RODADB::Result::Keyword>

=cut

__PACKAGE__->belongs_to(
  "keyword",
  "RODA::RODADB::Result::Keyword",
  { id => "keyword_id" },
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


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 20:06:24
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:9lalD13aJ/IOirRHXt/Wjg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
