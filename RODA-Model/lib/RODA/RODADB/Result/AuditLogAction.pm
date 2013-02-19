use utf8;
package RODA::RODADB::Result::AuditLogAction;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::AuditLogAction

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

=head1 TABLE: C<audit_log_action>

=cut

__PACKAGE__->table("audit_log_action");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'audit_log_action_id_seq'

=head2 changeset

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 audited_table

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 audited_row

  data_type: 'integer'
  is_nullable: 0

=head2 type

  data_type: 'varchar'
  is_nullable: 0
  size: 10

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "audit_log_action_id_seq",
  },
  "changeset",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "audited_table",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "audited_row",
  { data_type => "varchar", is_nullable => 0, size => 50 },
  "type",
  { data_type => "varchar", is_nullable => 0, size => 10 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 audit_log_changes

Type: has_many

Related object: L<RODA::RODADB::Result::AuditLogChange>

=cut

__PACKAGE__->has_many(
  "audit_log_changes",
  "RODA::RODADB::Result::AuditLogChange",
  { "foreign.action" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 audited_table

Type: belongs_to

Related object: L<RODA::RODADB::Result::AuditLogTable>

=cut

__PACKAGE__->belongs_to(
  "audited_table",
  "RODA::RODADB::Result::AuditLogTable",
  { id => "audited_table" },
  { is_deferrable => 0, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 changeset

Type: belongs_to

Related object: L<RODA::RODADB::Result::AuditLogChangeset>

=cut

__PACKAGE__->belongs_to(
  "changeset",
  "RODA::RODADB::Result::AuditLogChangeset",
  { id => "changeset" },
  { is_deferrable => 0, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-14 22:39:37
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:aSLn0SDbfWOxwRhiiefGbQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
