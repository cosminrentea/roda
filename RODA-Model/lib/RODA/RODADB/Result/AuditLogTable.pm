use utf8;
package RODA::RODADB::Result::AuditLogTable;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::AuditLogTable

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

=head1 TABLE: C<audit_log_table>

=cut

__PACKAGE__->table("audit_log_table");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'audit_log_table_id_seq'

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 40

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "audit_log_table_id_seq",
  },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 40 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 UNIQUE CONSTRAINTS

=head2 C<audit_log_table_name>

=over 4

=item * L</name>

=back

=cut

__PACKAGE__->add_unique_constraint("audit_log_table_name", ["name"]);

=head1 RELATIONS

=head2 audit_log_actions

Type: has_many

Related object: L<RODA::RODADB::Result::AuditLogAction>

=cut

__PACKAGE__->has_many(
  "audit_log_actions",
  "RODA::RODADB::Result::AuditLogAction",
  { "foreign.audited_table" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 audit_log_fields

Type: has_many

Related object: L<RODA::RODADB::Result::AuditLogField>

=cut

__PACKAGE__->has_many(
  "audit_log_fields",
  "RODA::RODADB::Result::AuditLogField",
  { "foreign.audited_table" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-14 22:39:37
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:6Oy9KaRO++4PJQ8iuij/KA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
