use utf8;
package RODA::RODADB::Result::AclEntry;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::AclEntry

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

=head1 TABLE: C<acl_entry>

=cut

__PACKAGE__->table("acl_entry");

=head1 ACCESSORS

=head2 id

  data_type: 'bigint'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'acl_entry_id_seq'

=head2 acl_object_identity

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

=head2 ace_order

  data_type: 'integer'
  is_nullable: 0

=head2 sid

  data_type: 'bigint'
  is_auto_increment: 1
  is_foreign_key: 1
  is_nullable: 0
  sequence: 'acl_entry_sid_seq'

=head2 mask

  data_type: 'integer'
  is_nullable: 0

=head2 granting

  data_type: 'boolean'
  is_nullable: 0

=head2 audit_success

  data_type: 'boolean'
  is_nullable: 0

=head2 audit_failure

  data_type: 'boolean'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "bigint",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "acl_entry_id_seq",
  },
  "acl_object_identity",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
  "ace_order",
  { data_type => "integer", is_nullable => 0 },
  "sid",
  {
    data_type         => "bigint",
    is_auto_increment => 1,
    is_foreign_key    => 1,
    is_nullable       => 0,
    sequence          => "acl_entry_sid_seq",
  },
  "mask",
  { data_type => "integer", is_nullable => 0 },
  "granting",
  { data_type => "boolean", is_nullable => 0 },
  "audit_success",
  { data_type => "boolean", is_nullable => 0 },
  "audit_failure",
  { data_type => "boolean", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 acl_object_identity

Type: belongs_to

Related object: L<RODA::RODADB::Result::AclObjectIdentity>

=cut

__PACKAGE__->belongs_to(
  "acl_object_identity",
  "RODA::RODADB::Result::AclObjectIdentity",
  { id => "acl_object_identity" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 sid

Type: belongs_to

Related object: L<RODA::RODADB::Result::AclSid>

=cut

__PACKAGE__->belongs_to(
  "sid",
  "RODA::RODADB::Result::AclSid",
  { id => "sid" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 16:35:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:gWckoYpJFlZM7/iFFlC6lg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
