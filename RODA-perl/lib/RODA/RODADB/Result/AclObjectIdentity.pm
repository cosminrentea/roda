use utf8;
package RODA::RODADB::Result::AclObjectIdentity;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::AclObjectIdentity

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

=head1 TABLE: C<acl_object_identity>

=cut

__PACKAGE__->table("acl_object_identity");

=head1 ACCESSORS

=head2 id

  data_type: 'bigint'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'acl_object_identity_id_seq'

=head2 object_id_class

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

=head2 object_id_identity

  data_type: 'bigint'
  is_nullable: 0

=head2 parent_object

  data_type: 'bigint'
  is_foreign_key: 1
  is_nullable: 0

=head2 owner_sid

  data_type: 'bigint'
  is_auto_increment: 1
  is_foreign_key: 1
  is_nullable: 0
  sequence: 'acl_object_identity_owner_sid_seq'

=head2 entries_inheriting

  data_type: 'boolean'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "bigint",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "acl_object_identity_id_seq",
  },
  "object_id_class",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
  "object_id_identity",
  { data_type => "bigint", is_nullable => 0 },
  "parent_object",
  { data_type => "bigint", is_foreign_key => 1, is_nullable => 0 },
  "owner_sid",
  {
    data_type         => "bigint",
    is_auto_increment => 1,
    is_foreign_key    => 1,
    is_nullable       => 0,
    sequence          => "acl_object_identity_owner_sid_seq",
  },
  "entries_inheriting",
  { data_type => "boolean", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 UNIQUE CONSTRAINTS

=head2 C<acl_object_identity_object_id_class_Idx>

=over 4

=item * L</object_id_class>

=item * L</object_id_identity>

=back

=cut

__PACKAGE__->add_unique_constraint(
  "acl_object_identity_object_id_class_Idx",
  ["object_id_class", "object_id_identity"],
);

=head1 RELATIONS

=head2 acl_entries

Type: has_many

Related object: L<RODA::RODADB::Result::AclEntry>

=cut

__PACKAGE__->has_many(
  "acl_entries",
  "RODA::RODADB::Result::AclEntry",
  { "foreign.acl_object_identity" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 acl_object_identities

Type: has_many

Related object: L<RODA::RODADB::Result::AclObjectIdentity>

=cut

__PACKAGE__->has_many(
  "acl_object_identities",
  "RODA::RODADB::Result::AclObjectIdentity",
  { "foreign.parent_object" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 object_id_class

Type: belongs_to

Related object: L<RODA::RODADB::Result::AclClass>

=cut

__PACKAGE__->belongs_to(
  "object_id_class",
  "RODA::RODADB::Result::AclClass",
  { id => "object_id_class" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 owner_sid

Type: belongs_to

Related object: L<RODA::RODADB::Result::AclSid>

=cut

__PACKAGE__->belongs_to(
  "owner_sid",
  "RODA::RODADB::Result::AclSid",
  { id => "owner_sid" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 parent_object

Type: belongs_to

Related object: L<RODA::RODADB::Result::AclObjectIdentity>

=cut

__PACKAGE__->belongs_to(
  "parent_object",
  "RODA::RODADB::Result::AclObjectIdentity",
  { id => "parent_object" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 16:35:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:j/mIqDMrPwgYlEiMtH5DsQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
