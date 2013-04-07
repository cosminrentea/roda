use utf8;
package RODA::RODADB::Result::City;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NUME

RODA::RODADB::Result::City - Tabel care stocheaza toate referintele la orase. 

=cut

use strict;
use warnings;

use Moose;
use MooseX::NonMoose;
use MooseX::MarkAsMethods autoclean => 1;
extends 'DBIx::Class::Core';

=head1 COMPONENTE UTILIZATE

=over 4

=item * L<DBIx::Class::InflateColumn::DateTime>

=item * L<+RODA::Components::DBIC::DBAudit>

=back

=cut

__PACKAGE__->load_components("InflateColumn::DateTime",  "+RODA::Components::DBIC::DBAudit");

=head1 TABLE: C<city>

=cut

__PACKAGE__->table("city");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'city_id_seq'

Codul orasului

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Numele orasului

=head2 country_id

  data_type: 'char'
  is_foreign_key: 1
  is_nullable: 0
  size: 2

Codul tarii in care se afla orasul (refera atributul id al tabelului country)

=head2 city_code

  data_type: 'varchar'
  is_nullable: 1
  size: 50

=head2 city_code_name

  data_type: 'varchar'
  is_nullable: 1
  size: 100

=head2 city_code_sup

  data_type: 'varchar'
  is_nullable: 1
  size: 100

=head2 prefix

  data_type: 'varchar'
  is_nullable: 1
  size: 50

=head2 city_type

  data_type: 'varchar'
  is_nullable: 1
  size: 50

=head2 city_type_system

  data_type: 'varchar'
  is_nullable: 1
  size: 50

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "city_id_seq",
  },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 100 },
  "country_id",
  { data_type => "char", is_foreign_key => 1, is_nullable => 0, size => 2 },
  "city_code",
  { data_type => "varchar", is_nullable => 1, size => 50 },
  "city_code_name",
  { data_type => "varchar", is_nullable => 1, size => 100 },
  "city_code_sup",
  { data_type => "varchar", is_nullable => 1, size => 100 },
  "prefix",
  { data_type => "varchar", is_nullable => 1, size => 50 },
  "city_type",
  { data_type => "varchar", is_nullable => 1, size => 50 },
  "city_type_system",
  { data_type => "varchar", is_nullable => 1, size => 50 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATII

=head2 addresses

Type: has_many

Related object: L<RODA::RODADB::Result::Address>

=cut

__PACKAGE__->has_many(
  "addresses",
  "RODA::RODADB::Result::Address",
  { "foreign.city_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 country

Type: belongs_to

Related object: L<RODA::RODADB::Result::Country>

=cut

__PACKAGE__->belongs_to(
  "country",
  "RODA::RODADB::Result::Country",
  { id => "country_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 region_cities

Type: has_many

Related object: L<RODA::RODADB::Result::RegionCity>

=cut

__PACKAGE__->has_many(
  "region_cities",
  "RODA::RODADB::Result::RegionCity",
  { "foreign.city_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 regions

Type: many_to_many

Composing rels: L</region_cities> -> region

=cut

__PACKAGE__->many_to_many("regions", "region_cities", "region");


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-01-27 16:35:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:26TTyydW92crtBnCJirBOw


# You can replace this text with custom code or comments, and it will be preserved on regeneration

=head1 METODE SUPLIMENTARE

=cut


=head2 attach_region

ataseaza o regiune (existenta) orasului curent

=cut

sub attach_region {
     my ( $self, %params ) = @_;
     #nu acceptam decat regiuni existente aici, vedem mai incolo ce facem cu inserarea chestiilor noi
     #ne trebuie asa: name, country_id sau country_name, region_type_id sau region_type_name
     #daca nu avem astea, nu inseram

     my $guard = $self->result_source->schema()->txn_scope_guard;

     my $rtrs = $self->result_source->schema()->resultset('Region')->checkregion(
                                                                  name             => $params{name},
                                                                  region_type_name => $params{region_type_name},
                                                                  country_id     =>  $params{country_id},
      );

      if ($rtrs) { 
        #acu trebuie sa inseram asocierea


         $self->result_source->schema()->resultset('RegionCity')->find_or_create({
          region_id => $rtrs->id,
          city_id => $self->id,
         },
         {
          key => 'primary',
         }
         );
      }
        $guard->commit;
}





__PACKAGE__->meta->make_immutable;
1;
