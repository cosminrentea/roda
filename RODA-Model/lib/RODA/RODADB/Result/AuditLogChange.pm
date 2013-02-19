use utf8;
package RODA::RODADB::Result::AuditLogChange;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::AuditLogChange

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

=head1 TABLE: C<audit_log_change>

=cut

__PACKAGE__->table("audit_log_change");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'audit_log_change_id_seq'

=head2 action

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 field

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 old_value

  data_type: 'varchar'
  is_nullable: 1
  size: 255

=head2 new_value

  data_type: 'varchar'
  is_nullable: 1
  size: 255

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "audit_log_change_id_seq",
  },
  "action",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "field",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "old_value",
  { data_type => "varchar", is_nullable => 1, size => 255 },
  "new_value",
  { data_type => "varchar", is_nullable => 1, size => 255 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 action

Type: belongs_to

Related object: L<RODA::RODADB::Result::AuditLogAction>

=cut

__PACKAGE__->belongs_to(
  "action",
  "RODA::RODADB::Result::AuditLogAction",
  { id => "action" },
  { is_deferrable => 0, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 field

Type: belongs_to

Related object: L<RODA::RODADB::Result::AuditLogField>

=cut

__PACKAGE__->belongs_to(
  "field",
  "RODA::RODADB::Result::AuditLogField",
  { id => "field" },
  { is_deferrable => 0, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-14 22:39:37
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:SGTXn+0fqG3u8PGuMU9WhQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
