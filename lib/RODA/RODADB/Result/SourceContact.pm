use utf8;
package RODA::RODADB::Result::SourceContact;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::SourceContact - Datele de contact ale unei surse 

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

=head1 TABLE: C<source_contacts>

=cut

__PACKAGE__->table("source_contacts");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_nullable: 0

Codul contactului

=head2 person_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul persoanei (refera atributul id din tabelul person)

=head2 contact_date

  data_type: 'timestamp'
  is_nullable: 0

Data la care persoana identificata prin atributul person_id a fost contactata

=head2 contact_synopsis

  data_type: 'text'
  is_nullable: 0

=head2 followup

  data_type: 'integer'
  is_nullable: 0

=head2 contact_method

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Metoda prin care persoana a fost contactata (refera atributul id din tabelul source_contact_method)

=head2 org_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul organizatiei careia ii aunt asociate informatiile de contact (refera atributul org_id din tabelul sources)

=cut

__PACKAGE__->add_columns(
  "id",
  { data_type => "integer", is_nullable => 0 },
  "person_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "contact_date",
  { data_type => "timestamp", is_nullable => 0 },
  "contact_synopsis",
  { data_type => "text", is_nullable => 0 },
  "followup",
  { data_type => "integer", is_nullable => 0 },
  "contact_method",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "org_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 contact_method

Type: belongs_to

Related object: L<RODA::RODADB::Result::SourceContactMethod>

=cut

__PACKAGE__->belongs_to(
  "contact_method",
  "RODA::RODADB::Result::SourceContactMethod",
  { id => "contact_method" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 org

Type: belongs_to

Related object: L<RODA::RODADB::Result::Source>

=cut

__PACKAGE__->belongs_to(
  "org",
  "RODA::RODADB::Result::Source",
  { org_id => "org_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 person

Type: belongs_to

Related object: L<RODA::RODADB::Result::Person>

=cut

__PACKAGE__->belongs_to(
  "person",
  "RODA::RODADB::Result::Person",
  { id => "person_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:IWqVkuE7CvzLlPSaHXfFcw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
