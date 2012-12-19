use utf8;
package RODA::RODADB::Result::TranslatedTopic;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::TranslatedTopic

=head1 DESCRIPTION

Tabel ce contine traducerile anumitor topic-uri din baza de date (implementeaza relatia many-to-many intre tabelele topic si language)

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

=head1 TABLE: C<translated_topic>

=cut

__PACKAGE__->table("translated_topic");

=head1 ACCESSORS

=head2 language_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul limbii in care este tradus topic-ul referit prin atributul topic_id (refera atributul id din tabelul language)

=head2 topic_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul topic-ului pentru care exista o traducere in limba identificata prin atributul language_id (refera atributul id din tabelul topic)

=head2 translation

  data_type: 'bigint'
  is_nullable: 0

Traducerea topic-ului referit prin atributul topic_id in limba identificata prin atributul language_id

=cut

__PACKAGE__->add_columns(
  "language_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "topic_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "translation",
  { data_type => "bigint", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</language_id>

=item * L</topic_id>

=back

=cut

__PACKAGE__->set_primary_key("language_id", "topic_id");

=head1 RELATIONS

=head2 language

Type: belongs_to

Related object: L<RODA::RODADB::Result::Language>

=cut

__PACKAGE__->belongs_to(
  "language",
  "RODA::RODADB::Result::Language",
  { id => "language_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);

=head2 topic

Type: belongs_to

Related object: L<RODA::RODADB::Result::Topic>

=cut

__PACKAGE__->belongs_to(
  "topic",
  "RODA::RODADB::Result::Topic",
  { id => "topic_id" },
  { is_deferrable => 1, on_delete => "CASCADE", on_update => "CASCADE" },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2012-12-19 19:21:27
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:tLPHhXa7Ln6DID8arMp+Bg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
