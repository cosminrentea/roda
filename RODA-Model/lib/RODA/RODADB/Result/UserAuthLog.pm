use utf8;
package RODA::RODADB::Result::UserAuthLog;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::UserAuthLog

=head1 DESCRIPTION

Tabel ce stocheaza log-ul corespunzator procesului de autentificare a utilizatorilor

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

=head1 TABLE: C<user_auth_log>

=cut

__PACKAGE__->table("user_auth_log");

=head1 ACCESSORS

=head2 user_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul utilizatorului care a incercat sa se autentifice

=head2 timestamp

  data_type: 'timestamp'
  is_nullable: 0

Timpul la care incercarea de autentificare a avut loc

=head2 action

  data_type: 'varchar'
  is_nullable: 0
  size: 30

Actiunea login/logout/session expire (in functie de tipul de autentificare)

=head2 credential_provider

  data_type: 'text'
  is_nullable: 0

Tipul de furnizor de informatii de acces pentru autentificarea respectiva

=head2 credential_identifier

  data_type: 'text'
  is_nullable: 0

Detalii despre autentificarea utilizatorului

=head2 error_message

  data_type: 'text'
  is_nullable: 0

Mesajul de eroare aparut 

=cut

__PACKAGE__->add_columns(
  "user_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "timestamp",
  { data_type => "timestamp", is_nullable => 0 },
  "action",
  { data_type => "varchar", is_nullable => 0, size => 30 },
  "credential_provider",
  { data_type => "text", is_nullable => 0 },
  "credential_identifier",
  { data_type => "text", is_nullable => 0 },
  "error_message",
  { data_type => "text", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</user_id>

=item * L</timestamp>

=back

=cut

__PACKAGE__->set_primary_key("user_id", "timestamp");

=head1 RELATIONS

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


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:h46pAZH/eOGbEOXs91Qomg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
