use utf8;
package RODA::RODADB::Result::Filetype;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Filetype

=head1 DESCRIPTION

Tabel ce contine tipurile de documente ale caror informatii sunt retinute in baza de date

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

=head1 TABLE: C<filetype>

=cut

__PACKAGE__->table("filetype");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'filetype_id_seq'

Codul tipului de document

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Denumirea tipului de document

=head2 mimetype

  data_type: 'varchar'
  is_nullable: 0
  size: 100

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "filetype_id_seq",
  },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 100 },
  "mimetype",
  { data_type => "varchar", is_nullable => 0, size => 100 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 files

Type: has_many

Related object: L<RODA::RODADB::Result::File>

=cut

__PACKAGE__->has_many(
  "files",
  "RODA::RODADB::Result::File",
  { "foreign.filetype_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 16:35:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:gZcmttMBtwFy3zSArehKfA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
