use utf8;
package RODA::RODADB::Result::InstanceTopic;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::InstanceTopic

=head1 DESCRIPTION

Tabel ce stocheaza asocierile dintre instante si topic-uri (implementeaza relatia many-to-many intre aceste tabele)

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

=head1 TABLE: C<instance_topic>

=cut

__PACKAGE__->table("instance_topic");

=head1 ACCESSORS

=head2 instance_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul unei instante careia i se asociaza topic-ul specificat prin atributul topic_id (refera atributul id din tabelul instance)

=head2 topic_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul topic-ului care este asociat instantei specificate prin atributul instance_id (refera atributul id din tabelul topic)

=cut

__PACKAGE__->add_columns(
  "instance_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "topic_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</instance_id>

=item * L</topic_id>

=back

=cut

__PACKAGE__->set_primary_key("instance_id", "topic_id");

=head1 RELATIONS

=head2 instance

Type: belongs_to

Related object: L<RODA::RODADB::Result::Instance>

=cut

__PACKAGE__->belongs_to(
  "instance",
  "RODA::RODADB::Result::Instance",
  { id => "instance_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 topic

Type: belongs_to

Related object: L<RODA::RODADB::Result::Topic>

=cut

__PACKAGE__->belongs_to(
  "topic",
  "RODA::RODADB::Result::Topic",
  { id => "topic_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:BY6TiDCivadgr7XvbdqHRA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
