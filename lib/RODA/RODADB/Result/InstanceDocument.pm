use utf8;
package RODA::RODADB::Result::InstanceDocument;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::InstanceDocument - Tabel ce contine documentele instantei

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

Related object: L<RODA::RODADB::Result::Document>

=cut

__PACKAGE__->belongs_to(
  "document",
  "RODA::RODADB::Result::Document",
  { id => "document_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 instance

Type: belongs_to

Related object: L<RODA::RODADB::Result::Instance>

=cut

__PACKAGE__->belongs_to(
  "instance",
  "RODA::RODADB::Result::Instance",
  { id => "instance_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 selection_variable_cards

Type: has_many

Related object: L<RODA::RODADB::Result::SelectionVariableCard>

=cut

__PACKAGE__->has_many(
  "selection_variable_cards",
  "RODA::RODADB::Result::SelectionVariableCard",
  {
    "foreign.instance_id"   => "self.instance_id",
    "foreign.response_card" => "self.document_id",
  },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 variables

Type: has_many

Related object: L<RODA::RODADB::Result::Variable>

=cut

__PACKAGE__->has_many(
  "variables",
  "RODA::RODADB::Result::Variable",
  {
    "foreign.file_id"     => "self.document_id",
    "foreign.instance_id" => "self.instance_id",
  },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:h4s1OHct5j9vMUuCsqNRcw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
