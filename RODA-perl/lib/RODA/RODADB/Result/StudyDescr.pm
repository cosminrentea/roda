use utf8;
package RODA::RODADB::Result::StudyDescr;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::StudyDescr

=head1 DESCRIPTION

Tabel ce contine elementele descriptive ale studiilor din baza de date

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

=head1 TABLE: C<study_descr>

=cut

__PACKAGE__->table("study_descr");

=head1 ACCESSORS

=head2 study_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul studiului (refera atributul id din tabelul study)

=head2 abstract

  data_type: 'text'
  is_nullable: 0

Rezumatul studiului

=cut

__PACKAGE__->add_columns(
  "study_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "abstract",
  { data_type => "text", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</study_id>

=back

=cut

__PACKAGE__->set_primary_key("study_id");

=head1 RELATIONS

=head2 study

Type: belongs_to

Related object: L<RODA::RODADB::Result::Study>

=cut

__PACKAGE__->belongs_to(
  "study",
  "RODA::RODADB::Result::Study",
  { id => "study_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:6j28+zmYDLleICU9bqlBdg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
