use utf8;
package RODA::RODADB::Result::InstanceDescr;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::InstanceDescr - Tabel ce contine elementele descriptive ale instantelor

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

=head2 lang_id

  data_type: 'char'
  is_foreign_key: 1
  is_nullable: 0
  size: 2

=head2 weighting

  data_type: 'text'
  is_nullable: 1

=head2 research_instrument

  data_type: 'text'
  is_nullable: 1

=head2 scope

  data_type: 'text'
  is_nullable: 1

=head2 universe

  data_type: 'text'
  is_nullable: 1

=head2 abstract

  data_type: 'text'
  is_nullable: 1

=head2 title

  data_type: 'text'
  is_nullable: 0

Titlul instantei

=cut

__PACKAGE__->add_columns(
  "instance_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "lang_id",
  { data_type => "char", is_foreign_key => 1, is_nullable => 0, size => 2 },
  "weighting",
  { data_type => "text", is_nullable => 1 },
  "research_instrument",
  { data_type => "text", is_nullable => 1 },
  "scope",
  { data_type => "text", is_nullable => 1 },
  "universe",
  { data_type => "text", is_nullable => 1 },
  "abstract",
  { data_type => "text", is_nullable => 1 },
  "title",
  { data_type => "text", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</instance_id>

=item * L</lang_id>

=back

=cut

__PACKAGE__->set_primary_key("instance_id", "lang_id");

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

=head2 lang

Type: belongs_to

Related object: L<RODA::RODADB::Result::Lang>

=cut

__PACKAGE__->belongs_to(
  "lang",
  "RODA::RODADB::Result::Lang",
  { id => "lang_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:eGbtH3Fmdj9drIhkZFzf9w


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
