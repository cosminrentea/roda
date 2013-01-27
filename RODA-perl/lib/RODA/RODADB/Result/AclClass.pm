use utf8;
package RODA::RODADB::Result::AclClass;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::AclClass

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

=head1 TABLE: C<acl_class>

=cut

__PACKAGE__->table("acl_class");

=head1 ACCESSORS

=head2 id

  data_type: 'bigint'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'acl_class_id_seq'

=head2 class

  data_type: 'text'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "bigint",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "acl_class_id_seq",
  },
  "class",
  { data_type => "text", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 UNIQUE CONSTRAINTS

=head2 C<acl_class_class_Idx>

=over 4

=item * L</class>

=back

=cut

__PACKAGE__->add_unique_constraint("acl_class_class_Idx", ["class"]);

=head1 RELATIONS

=head2 acl_object_identities

Type: has_many

Related object: L<RODA::RODADB::Result::AclObjectIdentity>

=cut

__PACKAGE__->has_many(
  "acl_object_identities",
  "RODA::RODADB::Result::AclObjectIdentity",
  { "foreign.object_id_class" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 16:35:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:+gsp2FSj0UkT2MjktRN4lQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
