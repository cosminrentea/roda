use utf8;
package RODA::RODADB::Result::OrgEmail;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::OrgEmail

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

=head1 TABLE: C<org_email>

=cut

__PACKAGE__->table("org_email");

=head1 ACCESSORS

=head2 org_id

  data_type: 'integer'
  is_auto_increment: 1
  is_foreign_key: 1
  is_nullable: 0
  sequence: 'org_email_org_id_seq'

=head2 email_id

  data_type: 'integer'
  is_auto_increment: 1
  is_foreign_key: 1
  is_nullable: 0
  sequence: 'org_email_email_id_seq'

=head2 is_main

  data_type: 'boolean'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "org_id",
  {
    data_type         => "integer",
    is_foreign_key    => 1,
    is_nullable       => 0,
  },
  "email_id",
  {
    data_type         => "integer",
    is_foreign_key    => 1,
    is_nullable       => 0,
  },
  "is_main",
  { data_type => "boolean", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</org_id>

=item * L</email_id>

=back

=cut

__PACKAGE__->set_primary_key("org_id", "email_id");

=head1 RELATIONS

=head2 email

Type: belongs_to

Related object: L<RODA::RODADB::Result::Email>

=cut

__PACKAGE__->belongs_to(
  "email",
  "RODA::RODADB::Result::Email",
  { id => "email_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 org

Type: belongs_to

Related object: L<RODA::RODADB::Result::Org>

=cut

__PACKAGE__->belongs_to(
  "org",
  "RODA::RODADB::Result::Org",
  { id => "org_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 16:35:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:ybKWyHk/7/GNFMUiZSCFAw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
