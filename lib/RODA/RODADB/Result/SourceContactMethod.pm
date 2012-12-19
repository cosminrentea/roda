use utf8;
package RODA::RODADB::Result::SourceContactMethod;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::SourceContactMethod

=head1 DESCRIPTION

Tabel ce contine metodele prin care persoana de contact al unui posibil furnizor de studii poate fi contactata

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

=head1 TABLE: C<source_contact_method>

=cut

__PACKAGE__->table("source_contact_method");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul metodei de contact

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 150

Denumirea metodei de contact (telefon, email etc.)

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 150 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 source_contacts

Type: has_many

Related object: L<RODA::RODADB::Result::SourceContact>

=cut

__PACKAGE__->has_many(
  "source_contacts",
  "RODA::RODADB::Result::SourceContact",
  { "foreign.contact_method" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:pwkfvoKkDIz3OfUIdmoDow


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
