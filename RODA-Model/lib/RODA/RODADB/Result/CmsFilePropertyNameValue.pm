use utf8;
package RODA::RODADB::Result::CmsFilePropertyNameValue;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::CmsFilePropertyNameValue

=head1 DESCRIPTION

Tabel ce pastreaza asocierile intre CMS_File si proprietatile fisierului respectiv (name+value)

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

=head1 TABLE: C<cms_file_property_name_value>

=cut

__PACKAGE__->table("cms_file_property_name_value");

=head1 ACCESSORS

=head2 cms_file_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 property_name_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 property_value_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "cms_file_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "property_name_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "property_value_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</cms_file_id>

=item * L</property_name_id>

=item * L</property_value_id>

=back

=cut

__PACKAGE__->set_primary_key("cms_file_id", "property_name_id", "property_value_id");

=head1 RELATIONS

=head2 cms_file

Type: belongs_to

Related object: L<RODA::RODADB::Result::CmsFile>

=cut

__PACKAGE__->belongs_to(
  "cms_file",
  "RODA::RODADB::Result::CmsFile",
  { id => "cms_file_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 property_name

Type: belongs_to

Related object: L<RODA::RODADB::Result::PropertyName>

=cut

__PACKAGE__->belongs_to(
  "property_name",
  "RODA::RODADB::Result::PropertyName",
  { id => "property_name_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 property_value

Type: belongs_to

Related object: L<RODA::RODADB::Result::PropertyValue>

=cut

__PACKAGE__->belongs_to(
  "property_value",
  "RODA::RODADB::Result::PropertyValue",
  { id => "property_value_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:w6/dB51TQS6VFHzk0v4I4Q


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
