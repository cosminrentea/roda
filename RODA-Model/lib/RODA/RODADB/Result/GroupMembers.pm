use utf8;
package RODA::RODADB::Result::GroupMembers;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::GroupMembers

=head1 DESCRIPTION

Tabel care stocheaza membrii grupurilor de utilizatori.

=cut

use strict;
use warnings;

use Moose;
use MooseX::NonMoose;
use MooseX::MarkAsMethods autoclean => 1;
extends 'DBIx::Class::Core';

=head1 COMPONENTE UTILIZATE

=over 4

=item * L<DBIx::Class::InflateColumn::DateTime>

=back

=cut

__PACKAGE__->load_components("InflateColumn::DateTime");

=head1 TABLE: C<group_members>

=cut

__PACKAGE__->table("group_members");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'group_members_id_seq'

Codul unui grup de utilizatori 

=head2 username

  data_type: 'varchar'
  is_nullable: 0
  size: 64

Numele utilizatorului

=head2 group_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

Codul grupului din care face parte utilizatorul

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "cms_layout_id_seq",
  },
  "username",
  { data_type => "varchar", is_nullable => 0, size => 64 },
  "group_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },  
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATII

=head2 groups

Type: belongs_to

Related object: L<RODA::RODADB::Result::Groups>

=cut

__PACKAGE__->belongs_to(
  "groups",
  "RODA::RODADB::Result::Groups",
  { id => "groups_id" },
  {
    is_deferrable => 0,
    join_type     => "LEFT",
    on_delete     => "NO ACTION",
    on_update     => "NO ACTION",
  },
);

# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:FsHxj/EHZJvmGVoBYPV2nQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
