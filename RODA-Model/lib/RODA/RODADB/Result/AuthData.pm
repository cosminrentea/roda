use utf8;
package RODA::RODADB::Result::AuthData;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::AuthData

=head1 DESCRIPTION

Tabel ce stocheaza datele de autentificare ale utilizatorilor

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

=head1 TABLE: C<auth_data>

=cut

__PACKAGE__->table("auth_data");

=head1 ACCESSORS

=head2 user_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul utilizatorului (refera atributul id al tabelului users)

=head2 credential_provider

  data_type: 'text'
  is_nullable: 0

Furnizorul de informatii de acces

=head2 field_name

  data_type: 'text'
  is_nullable: 0

Denumirea campului de autentificare (de exemplu: username)

=head2 field_value

  data_type: 'text'
  is_nullable: 0

Valoarea campului specificat prin atributul field_name pentru utilizatorul referit prin atributul user_id

=cut

__PACKAGE__->add_columns(
  "user_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "credential_provider",
  { data_type => "text", is_nullable => 0 },
  "field_name",
  { data_type => "text", is_nullable => 0 },
  "field_value",
  { data_type => "text", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</user_id>

=back

=cut

__PACKAGE__->set_primary_key("user_id");

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


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:KW/It3MD2VKJ9PkXMURVVw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
