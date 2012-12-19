use utf8;
package RODA::RODADB::Result::InstanceDescr;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::InstanceDescr - Tabel ce contine elementele descriptive ale instantei

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

=head1 TABLE: C<instance_descr>

=cut

__PACKAGE__->table("instance_descr");

=head1 ACCESSORS

=head2 instance_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul instantei pentru care sunt furnizate elemente descriptive

=head2 title

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Titlul instantei

=head2 abstract

  data_type: 'text'
  is_nullable: 1

Rezumatul instantei

=cut

__PACKAGE__->add_columns(
  "instance_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "title",
  { data_type => "varchar", is_nullable => 0, size => 100 },
  "abstract",
  { data_type => "text", is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</instance_id>

=back

=cut

__PACKAGE__->set_primary_key("instance_id");

=head1 RELATIONS

=head2 instance

Type: belongs_to

Related object: L<RODA::RODADB::Result::Instance>

=cut

__PACKAGE__->belongs_to(
  "instance",
  "RODA::RODADB::Result::Instance",
  { id => "instance_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:26
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:OjAFPOplQ1SwcMmbEy/xtA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
