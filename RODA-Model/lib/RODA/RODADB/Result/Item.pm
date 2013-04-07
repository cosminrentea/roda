use utf8;
package RODA::RODADB::Result::Item;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NUME

RODA::RODADB::Result::Item - Tabel ce stocheaza elementele (item-urile) variabilelor de selectie din baza de date

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

=back

=cut

__PACKAGE__->load_components("InflateColumn::DateTime");

=head1 TABLE: C<item>

=cut

__PACKAGE__->table("item");

=head1 ACCESSORS

=head2 id

  data_type: 'bigint'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'item_id_seq'

Identificatorul item-ului

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Numele item-ului

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "bigint",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "item_id_seq",
  },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 100 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATII

=head2 scale

Type: might_have

Related object: L<RODA::RODADB::Result::Scale>

=cut

__PACKAGE__->might_have(
  "scale",
  "RODA::RODADB::Result::Scale",
  { "foreign.item_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 selection_variable_items

Type: has_many

Related object: L<RODA::RODADB::Result::SelectionVariableItem>

=cut

__PACKAGE__->has_many(
  "selection_variable_items",
  "RODA::RODADB::Result::SelectionVariableItem",
  { "foreign.item_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 value

Type: might_have

Related object: L<RODA::RODADB::Result::Value>

=cut

__PACKAGE__->might_have(
  "value",
  "RODA::RODADB::Result::Value",
  { "foreign.item_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:7EOfAEv4Lt6mlleyYCEHgg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;

=head1 METODE SUPLIMENTARE

=cut

=head2 attach_value

Ataseaza o valoare elementului de selectie curent (in cazul unei selectii simple).

=cut

sub attach_value {
	
	my ( $self, %params ) = @_;
	
    if ($params {value}) {
    	my $guard = $self->result_source->schema()->txn_scope_guard;
    	my %value = (value => $params{value}, item_id => $self -> id);					   	
    	$self->result_source->schema()->resultset('Value')->checkvalue(%value);
    	$guard -> commit;
    }       	 	
}

=head2 attach_scale

Ataseaza o scala elementului de selectie curent (in cazul unei selectii de tip scala).

=cut

sub attach_scale {
	
	my ( $self, %params ) = @_;
    if ($params{min_value} && $params{min_item} && $params{max_value} 
    		&& $params{max_item} && $params{units}) {
#    	my %scale -> (item_id => $self -> id,
#    				  min_value => $params{min_value},
#    				  min_item => $params{min_item},
#    				  max_value => $params{max_value},
#    				  max_item => $params{max_item},
#    				  units => $params{units} 
#    				);		
		my $guard = $self->result_source->schema()->txn_scope_guard;	   	
    	$self->result_source->schema()->resultset('Scale')->checkscale(item_id => $self -> id,
    				  min_value => $params{min_value},
    				  min_item => $params{min_item},
    				  max_value => $params{max_value},
    				  max_item => $params{max_item},
    				  units => $params{units} );	
    	$guard -> commit;			  	  
    }       	 	
}

1;
