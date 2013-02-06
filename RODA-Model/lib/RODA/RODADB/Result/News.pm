use utf8;
package RODA::RODADB::Result::News;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::News

=head1 DESCRIPTION

Tabel ce stocheaza stirile ce vor aparea in interfata aplicatiei

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

=head1 TABLE: C<news>

=cut

__PACKAGE__->table("news");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'news_id_seq'

Codul stirii

=head2 added_by

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul utilizatorului care a introdus stirea

=head2 added

  data_type: 'timestamp'
  is_nullable: 0

Momentul de timp la care stirea a fost adaugata

=head2 visible

  data_type: 'boolean'
  is_nullable: 0

Atribut boolean, a carui valoare este true daca stirea este vizibila

=head2 title

  data_type: 'text'
  is_nullable: 0

Titlul stirii

=head2 content

  data_type: 'text'
  is_nullable: 1

Continutul stirii

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "news_id_seq",
  },
  "added_by",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "added",
  { data_type => "timestamp", is_nullable => 0 },
  "visible",
  { data_type => "boolean", is_nullable => 0 },
  "title",
  { data_type => "text", is_nullable => 0 },
  "content",
  { data_type => "text", is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 added_by

Type: belongs_to

Related object: L<RODA::RODADB::Result::User>

=cut

__PACKAGE__->belongs_to(
  "added_by",
  "RODA::RODADB::Result::User",
  { id => "added_by" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:5eIl4Q3KPIOXV+K5kvCucA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
