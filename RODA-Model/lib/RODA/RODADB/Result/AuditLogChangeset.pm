use utf8;
package RODA::RODADB::Result::AuditLogChangeset;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::AuditLogChangeset

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

=head1 TABLE: C<audit_log_changeset>

=cut

__PACKAGE__->table("audit_log_changeset");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'audit_log_changeset_id_seq'

=head2 description

  data_type: 'varchar'
  is_nullable: 1
  size: 255

=head2 timestamp

  data_type: 'timestamp'
  is_nullable: 0

=head2 rodauser

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "audit_log_changeset_id_seq",
  },
  "description",
  { data_type => "varchar", is_nullable => 1, size => 255 },
  "timestamp",
  {
    data_type     => "timestamp",
    default_value => \"current_timestamp",
    is_nullable   => 0,
    original      => { default_value => \"now()" },
  },
  "rodauser",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 audit_log_actions

Type: has_many

Related object: L<RODA::RODADB::Result::AuditLogAction>

=cut

__PACKAGE__->has_many(
  "audit_log_actions",
  "RODA::RODADB::Result::AuditLogAction",
  { "foreign.changeset" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 roda

Type: belongs_to

Related object: L<RODA::RODADB::Result::Sguser>

=cut

__PACKAGE__->belongs_to(
  "rodauser",
  "RODA::RODADB::Result::RodaUser",
  { id => "rodauser" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-14 22:39:37
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:dpxOUFxqS9p7cgNUu6IOjQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
