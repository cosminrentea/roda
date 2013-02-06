use utf8;
package RODA::RODADB::Result::PersonLink;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::PersonLink

=head1 DESCRIPTION

Tabel ce stocheaza relatiile dintre persoane si utilizatorii aplicatiei 

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

=head1 TABLE: C<person_links>

=cut

__PACKAGE__->table("person_links");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'person_links_id_seq'

Codul relatiei intre persoana referita prin atributul person_id si utilizatorul identificat prin user_id

=head2 person_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul persoanei (refera atributul id din tabelul person)

=head2 user_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul utilizatorului (refera atributul id din tabelul user)

=head2 simscore

  data_type: 'numeric'
  is_nullable: 0
  size: [10,2]

Scorul general de similaritate intre persoana si utilizator (determinat pentru a stabili automat relatia intre persoana si utilizator)

=head2 namescore

  data_type: 'numeric'
  is_nullable: 0
  size: [10,2]

Scorul de similaritate bazat pe numele persoanei si al utilizatorului (determinat pentru a stabili automat relatia intre persoana si utilizator)

=head2 emailscore

  data_type: 'numeric'
  is_nullable: 0
  size: [10,2]

Scorul de similaritate bazat pe adresa de email (determinat pentru a stabili automat relatia intre persoana si utilizator)

=head2 status

  data_type: 'integer'
  is_nullable: 0

Starea deciziei existentei unei legaturi intre intre persoana si utilizator (confirmat, infirmat, in asteptare)

=head2 status_by

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul utilizatorului care a actualizat campul status (refera atributul id din tabelul user)

=head2 status_time

  data_type: 'timestamp'
  is_nullable: 0

Momentul de timp al ultimei actualizari a atributului status

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "person_links_id_seq",
  },
  "person_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "user_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "simscore",
  { data_type => "numeric", is_nullable => 0, size => [10, 2] },
  "namescore",
  { data_type => "numeric", is_nullable => 0, size => [10, 2] },
  "emailscore",
  { data_type => "numeric", is_nullable => 0, size => [10, 2] },
  "status",
  { data_type => "integer", is_nullable => 0 },
  "status_by",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "status_time",
  { data_type => "timestamp", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 person

Type: belongs_to

Related object: L<RODA::RODADB::Result::Person>

=cut

__PACKAGE__->belongs_to(
  "person",
  "RODA::RODADB::Result::Person",
  { id => "person_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 status_by

Type: belongs_to

Related object: L<RODA::RODADB::Result::User>

=cut

__PACKAGE__->belongs_to(
  "status_by",
  "RODA::RODADB::Result::User",
  { id => "status_by" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 user

Type: belongs_to

Related object: L<RODA::RODADB::Result::User>

=cut

__PACKAGE__->belongs_to(
  "user",
  "RODA::RODADB::Result::User",
  { id => "user_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:3ebsBXC3GS1PMW8xzBKBLQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
