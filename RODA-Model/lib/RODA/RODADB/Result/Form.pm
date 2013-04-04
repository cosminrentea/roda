use utf8;
package RODA::RODADB::Result::Form;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NUME

RODA::RODADB::Result::Form - Tabel pentru informatiile legate de chestionarele aplicate (un rand reprezinta o anumita fisa completata pe teren cu raspunsuri).

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

=head1 TABLE: C<form>

=cut

__PACKAGE__->table("form");

=head1 ACCESSORS

=head2 id

  data_type: 'bigint'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'form_id_seq'

=head2 instance_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 order_in_instance

  data_type: 'integer'
  is_nullable: 0

=head2 operator_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

=head2 operator_notes

  data_type: 'text'
  is_nullable: 1

=head2 fill_time

  data_type: 'timestamp'
  is_nullable: 1

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "bigint",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "form_id_seq",
  },
  "instance_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "order_in_instance",
  { data_type => "integer", is_nullable => 0 },
  "operator_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
  "operator_notes",
  { data_type => "text", is_nullable => 1 },
  "fill_time",
  { data_type => "timestamp", is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 UNIQUE CONSTRAINTS

=head2 C<form_instance_id_Idx>

=over 4

=item * L</instance_id>

=item * L</order_in_instance>

=back

=cut

__PACKAGE__->add_unique_constraint("form_instance_id_Idx", ["instance_id", "order_in_instance"]);

=head1 RELATII

=head2 form_edited_number_vars

Type: has_many

Related object: L<RODA::RODADB::Result::FormEditedNumberVar>

=cut

__PACKAGE__->has_many(
  "form_edited_number_vars",
  "RODA::RODADB::Result::FormEditedNumberVar",
  { "foreign.form_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 form_edited_text_vars

Type: has_many

Related object: L<RODA::RODADB::Result::FormEditedTextVar>

=cut

__PACKAGE__->has_many(
  "form_edited_text_vars",
  "RODA::RODADB::Result::FormEditedTextVar",
  { "foreign.form_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 form_selection_vars

Type: has_many

Related object: L<RODA::RODADB::Result::FormSelectionVar>

=cut

__PACKAGE__->has_many(
  "form_selection_vars",
  "RODA::RODADB::Result::FormSelectionVar",
  { "foreign.form_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

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

=head2 operator

Type: belongs_to

Related object: L<RODA::RODADB::Result::Person>

=cut

__PACKAGE__->belongs_to(
  "operator",
  "RODA::RODADB::Result::Person",
  { id => "operator_id" },
  {
    is_deferrable => 0,
    join_type     => "LEFT",
    on_delete     => "NO ACTION",
    on_update     => "NO ACTION",
  },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:0JBblajr6LhJASw1J1CVmg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;

=head1 METODE SUPLIMENTARE

=cut

=head2 attach_edited_text_vars

Ataseaza variabile de tip text editat formularului curent.

=cut


sub attach_edited_text_vars {
     my ( $self, %params ) = @_;
     foreach my $edited_text_var (@{$params{edited_text_vars}}) { 
     	my $guard = $self->result_source->schema()->txn_scope_guard;
     	
     	#setam adecvat tipul variabilei pentru situatia in care aceasta va fi creata
     	#presupunem ca functiei ii vor fi transmise doar celelalte atribute ale tabelului "variable"
     	$edited_text_var -> {type_edited_text} = 1;
     	$edited_text_var -> {type_edited_number} = 0;
     	$edited_text_var -> {type_selection} = 0; 
        my $variablers = $self->result_source->schema()->resultset('Variable')->checkvariable(%$edited_text_var);
        
        if ($variablers) {
        	if(!$variablers -> get_column('type_edited_text')) {
        		print("Not a edited text variable: " . $variablers -> get_column('label'),"\n");
        	}
        	else {
        		$self->result_source->schema()->resultset('FormEditedTextVar')->find_or_create({
          																			     variable_id => $variablers->id,
          																			     form_id => $self->id,
          																			     text => $edited_text_var->{text},
         																			    },
         																			    {
         		 																	     key => 'primary',
         																			    });
      		}
      		$guard->commit; 	           
        }        
     }
}

=head2 attach_edited_number_vars

Ataseaza variabile editate de tip numeric formularului curent.

=cut

sub attach_edited_number_vars {
     my ( $self, %params ) = @_;
     foreach my $edited_number_var (@{$params{edited_number_vars}}) { 
     	my $guard = $self->result_source->schema()->txn_scope_guard;
     	
     	#setam adecvat tipul variabilei pentru situatia in care aceasta va fi creata
     	#presupunem ca functiei ii vor fi transmise doar celelalte atribute ale tabelului "variable"
     	$edited_number_var -> {type_edited_text} = 0;
     	$edited_number_var -> {type_edited_number} = 1;
     	$edited_number_var -> {type_selection} = 0; 
        my $variablers = $self->result_source->schema()->resultset('Variable')->checkvariable(%$edited_number_var);
        
        if ($variablers) {
        	if(!$variablers -> get_column('type_edited_number')) {
        		print("Not a edited number variable: " . $variablers -> get_column('label'),"\n");
        	}
        	else {
        		$self->result_source->schema()->resultset('FormEditedNumberVar')->find_or_create({
          																			     variable_id => $variablers->id,
          																			     form_id => $self->id,
          																			     value => $edited_number_var->{value},
         																			    },
         																			    {
         		 																	     key => 'primary',
         																			    });
      		}
      		$guard->commit; 	           
        }        
     }
}

=head2 attach_selection_vars

Ataseaza variabile de selectie formularului curent.

=cut

sub attach_selection_vars {
     my ( $self, %params ) = @_;
     foreach my $selection_var (@{$params{selection_vars}}) { 
     	my $guard = $self->result_source->schema()->txn_scope_guard;
     	
     	#nu este necesara verificarea tipului variabilei deoarece legatura se face direct 
     	#cu tabelul asociativ dintre selection_variable_item
     	
        my $selection_variable_item_rs = $self->result_source->schema()->resultset('SelectionVariableItem')
        										->check_selection_variable_item(%$selection_var);
        
        if ($selection_variable_item_rs) {
        	$self->result_source->schema()->resultset('FormSelectionVar')
        										->find_or_create({
          														  variable_id => $selection_variable_item_rs->variable_id,
          														  form_id => $self->id,
          														  item_id => $selection_variable_item_rs->item_id,
          														  order_of_items_in_response => $selection_var -> {order_of_items_in_response},
         													     },
         														 {
         									   				      key => 'primary',
         														 });
      	}
      		$guard->commit; 	                  
     }
}

1;
