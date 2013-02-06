use utf8;
package RODA::RODADB::Result::AclSid;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::AclSid

=head1 DESCRIPTION

Tabel folosit de Spring Security pentru ACL. Uniquely identify any principal or authority in the system ("SID" stands for "security identity"). The only columns are the ID, a textual representation of the SID, and a flag to indicate whether the textual representation refers to a principal name or a GrantedAuthority. Thus, there is a single row for each unique principal or GrantedAuthority. When used in the context of receiving a permission, a SID is generally called a "recipient".

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

=head1 TABLE: C<acl_sid>

=cut

__PACKAGE__->table("acl_sid");

=head1 ACCESSORS

=head2 id

  data_type: 'bigint'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'acl_sid_id_seq'

=head2 principal

  data_type: 'boolean'
  is_nullable: 0

=head2 sid

  data_type: 'text'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "bigint",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "acl_sid_id_seq",
  },
  "principal",
  { data_type => "boolean", is_nullable => 0 },
  "sid",
  { data_type => "text", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 UNIQUE CONSTRAINTS

=head2 C<acl_sid_principal_Idx>

=over 4

=item * L</principal>

=item * L</sid>

=back

=cut

__PACKAGE__->add_unique_constraint("acl_sid_principal_Idx", ["principal", "sid"]);

=head1 RELATIONS

=head2 acl_entries

Type: has_many

Related object: L<RODA::RODADB::Result::AclEntry>

=cut

__PACKAGE__->has_many(
  "acl_entries",
  "RODA::RODADB::Result::AclEntry",
  { "foreign.sid" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 acl_object_identities

Type: has_many

Related object: L<RODA::RODADB::Result::AclObjectIdentity>

=cut

__PACKAGE__->has_many(
  "acl_object_identities",
  "RODA::RODADB::Result::AclObjectIdentity",
  { "foreign.owner_sid" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:UAoYBD8/Ed8VqCdOUBS8Hw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
