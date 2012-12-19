use utf8;
package RODA::RODADB::Result::Title;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Title

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

=head1 TABLE: C<title>

=cut

__PACKAGE__->table("title");

=head1 ACCESSORS

=head2 id

  data_type: 'bigint'
  is_nullable: 0

Codul titlului

=head2 content

  data_type: 'varchar'
  is_nullable: 0
  size: 250

Textul titlului

=head2 limba

  data_type: 'varchar'
  is_nullable: 0
  size: 30

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

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "bigint", is_nullable => 0 },
  "content",
  { data_type => "varchar", is_nullable => 0, size => 250 },
  "limba",
  { data_type => "varchar", is_nullable => 0, size => 30 },
  "title_type_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "study_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 study

Type: belongs_to

Related object: L<RODA::RODADB::Result::Study>

=cut

__PACKAGE__->belongs_to(
  "study",
  "RODA::RODADB::Result::Study",
  { id => "study_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 title_type

Type: belongs_to

Related object: L<RODA::RODADB::Result::TitleType>

=cut

__PACKAGE__->belongs_to(
  "title_type",
  "RODA::RODADB::Result::TitleType",
  { id => "title_type_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:KN6brboO/aL41vvyTUpvBg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
