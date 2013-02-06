use utf8;
package RODA::RODADB::Result::InstanceDocument;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::InstanceDocument

=head1 DESCRIPTION

Tabel ce contine asocierile dintre instante si documente (implementeaza relatia many-to-many intre tabelele instance si documents)

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

=head1 TABLE: C<instance_documents>

=cut

__PACKAGE__->table("instance_documents");

=head1 ACCESSORS

=head2 instance_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul instantei care contine documentul specificat prin atributul document_id (refera atributul id al tabelului instance)

=head2 document_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul documentului asociat instantei specificate prin atributul instance_id (refera atributul id al tabelului documents)

=cut

__PACKAGE__->add_columns(
  "instance_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "document_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</instance_id>

=item * L</document_id>

=back

=cut

__PACKAGE__->set_primary_key("instance_id", "document_id");

=head1 RELATIONS

=head2 document

Type: belongs_to

Related object: L<RODA::RODADB::Result::File>

=cut

__PACKAGE__->belongs_to(
  "document",
  "RODA::RODADB::Result::File",
  { id => "document_id" },
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


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:Sc0cEo9pFlGJa78jLJNLRg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
