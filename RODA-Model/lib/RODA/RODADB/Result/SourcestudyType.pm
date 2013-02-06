use utf8;
package RODA::RODADB::Result::SourcestudyType;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::SourcestudyType

=head1 DESCRIPTION

Tabel ce stocheaza tipul (starea) studiilor pe care le poate furniza o organizatie

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

=head1 TABLE: C<sourcestudy_type>

=cut

__PACKAGE__->table("sourcestudy_type");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'sourcestudy_type_id_seq'

Codului tipului (starii) unui studiu care poate fi furnizat de catre o sursa

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 150

Denumirea tipului (starii) in care se poate afla un studiu furnizat de catre o sursa

=head2 description

  data_type: 'text'
  is_nullable: 1

Descrierea tipului (starii) unui studiu care poate fi furnizat de catre o sursa

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "sourcestudy_type_id_seq",
  },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 150 },
  "description",
  { data_type => "text", is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 sourcestudies

Type: has_many

Related object: L<RODA::RODADB::Result::Sourcestudy>

=cut

__PACKAGE__->has_many(
  "sourcestudies",
  "RODA::RODADB::Result::Sourcestudy",
  { "foreign.type" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 sourcestudy_type_histories

Type: has_many

Related object: L<RODA::RODADB::Result::SourcestudyTypeHistory>

=cut

__PACKAGE__->has_many(
  "sourcestudy_type_histories",
  "RODA::RODADB::Result::SourcestudyTypeHistory",
  { "foreign.sourcestudy_type_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 16:35:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:Yb5+5XImJvOB1R9zaOI4qg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
