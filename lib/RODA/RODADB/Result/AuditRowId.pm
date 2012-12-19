use utf8;
package RODA::RODADB::Result::AuditRowId;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::AuditRowId

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

=head1 TABLE: C<audit_row_id>

=cut

__PACKAGE__->table("audit_row_id");

=head1 ACCESSORS

=head2 audit_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 id

  data_type: 'integer'
  is_nullable: 0

=head2 column_name

  data_type: 'varchar'
  is_nullable: 0
  size: 30

=head2 column_value

  data_type: 'integer'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "audit_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "id",
  { data_type => "integer", is_nullable => 0 },
  "column_name",
  { data_type => "varchar", is_nullable => 0, size => 30 },
  "column_value",
  { data_type => "integer", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=item * L</audit_id>

=back

=cut

__PACKAGE__->set_primary_key("id", "audit_id");

=head1 RELATIONS

=head2 audit

Type: belongs_to

Related object: L<RODA::RODADB::Result::Audit>

=cut

__PACKAGE__->belongs_to(
  "audit",
  "RODA::RODADB::Result::Audit",
  { id => "audit_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:28xev5oEy87a1HUbuScF/Q


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
