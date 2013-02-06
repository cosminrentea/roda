use utf8;
package RODA::RODADB::Result::PropertyValue;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::PropertyValue

=head1 DESCRIPTION

Tabel folosit pentru Proprietati (stocheaza valorile acestora)

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

=head1 TABLE: C<property_value>

=cut

__PACKAGE__->table("property_value");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'property_value_id_seq'

=head2 value

  data_type: 'text'
  is_nullable: 1

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "property_value_id_seq",
  },
  "value",
  { data_type => "text", is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 cms_file_property_name_values

Type: has_many

Related object: L<RODA::RODADB::Result::CmsFilePropertyNameValue>

=cut

__PACKAGE__->has_many(
  "cms_file_property_name_values",
  "RODA::RODADB::Result::CmsFilePropertyNameValue",
  { "foreign.property_value_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 file_property_name_values

Type: has_many

Related object: L<RODA::RODADB::Result::FilePropertyNameValue>

=cut

__PACKAGE__->has_many(
  "file_property_name_values",
  "RODA::RODADB::Result::FilePropertyNameValue",
  { "foreign.property_value_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:HBeGLVgWolCmidhzWwp3lg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
