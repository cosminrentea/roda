use utf8;
package RODA::RODADB::Result::InstanceKeyword;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::InstanceKeyword

=head1 DESCRIPTION

Tabel ce stocheaza asocierile dintre cuvinte cheie si instante (implementeaza relatia many-to-many intre tabelele instance si keyword)

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

=head1 TABLE: C<instance_keyword>

=cut

__PACKAGE__->table("instance_keyword");

=head1 ACCESSORS

=head2 instance_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul instantei careia ii este asociat cuvantul cheie referit prin atributul keyword_id

=head2 keyword_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul unui cuvant cheie asociat instantei identificate prin atributul instance_id

=head2 added

  data_type: 'timestamp'
  is_nullable: 0

Momentul de timp la care a fost adaugata o asociere intre o instanta si un cuvant cheie

=head2 added_by

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Utilizatorul care a adaugat asocierea dintre o instanta si un cuvant cheie

=cut

__PACKAGE__->add_columns(
  "instance_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "keyword_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "added",
  { data_type => "timestamp", is_nullable => 0 },
  "added_by",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</instance_id>

=item * L</keyword_id>

=item * L</added_by>

=back

=cut

__PACKAGE__->set_primary_key("instance_id", "keyword_id", "added_by");

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

=head2 instance

Type: belongs_to

Related object: L<RODA::RODADB::Result::Instance>

=cut

__PACKAGE__->belongs_to(
  "instance",
  "RODA::RODADB::Result::Instance",
  { id => "instance_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

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


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:mFgCa6XFsJWt1Jv1mKy/vQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
