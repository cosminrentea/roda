use utf8;
package RODA::RODADB::Result::Variable;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NUME

RODA::RODADB::Result::Variable - Tabel care stocheaza  variabilele din cadrul instantelor 

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

=head1 TABLE: C<variable>

=cut

__PACKAGE__->table("variable");

=head1 ACCESSORS

=head2 instance_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul instantei in care este definita variabila

=head2 id

  data_type: 'bigint'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'variable_id_seq'

Codul variabilei in cadrul instantei

=head2 label

  data_type: 'text'
  is_nullable: 0

Reprezentarea textuala a variabilei (numele)

=head2 type

  data_type: 'smallint'
  is_nullable: 0

Tipul de variabila (constanta a unei enumeratii)

=head2 order_in_instance

  data_type: 'integer'
  is_nullable: 0

Intregul ordinal reprezentand pozitia variabilei in secventa de variabile care definesc instanta

=head2 operator_instructions

  data_type: 'text'
  is_nullable: 1

Text care informeaza operatorul ce chestioneaza asupra unor actiuni pe care trebuie sa le faca atunci cand ajunge la variabila aceasta

=head2 file_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

Fisierul din care provine variabila

=head2 type_edited_text

  data_type: 'boolean'
  is_nullable: 0

=head2 type_edited_number

  data_type: 'boolean'
  is_nullable: 0

=head2 type_selection

  data_type: 'boolean'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "instance_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "id",
  {
    data_type         => "bigint",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "variable_id_seq",
  },
  "label",
  { data_type => "text", is_nullable => 0 },
  "type",
  { data_type => "smallint", is_nullable => 0 },
  "order_in_instance",
  { data_type => "integer", is_nullable => 0 },
  "operator_instructions",
  { data_type => "text", is_nullable => 1 },
  "file_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
  "type_edited_text",
  { data_type => "boolean", is_nullable => 0 },
  "type_edited_number",
  { data_type => "boolean", is_nullable => 0 },
  "type_selection",
  { data_type => "boolean", is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 UNIQUE CONSTRAINTS

=head2 C<Variables_QuestionnaireId_Order_Idx>

=over 4

=item * L</instance_id>

=item * L</order_in_instance>

=back

=cut

__PACKAGE__->add_unique_constraint(
  "Variables_QuestionnaireId_Order_Idx",
  ["instance_id", "order_in_instance"],
);

=head1 RELATII

=head2 concept_variables

Type: has_many

Related object: L<RODA::RODADB::Result::ConceptVariable>

=cut

__PACKAGE__->has_many(
  "concept_variables",
  "RODA::RODADB::Result::ConceptVariable",
  { "foreign.variable_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 file

Type: belongs_to

Related object: L<RODA::RODADB::Result::File>

=cut

__PACKAGE__->belongs_to(
  "file",
  "RODA::RODADB::Result::File",
  { id => "file_id" },
  {
    is_deferrable => 0,
    join_type     => "LEFT",
    on_delete     => "NO ACTION",
    on_update     => "NO ACTION",
  },
);

=head2 form_edited_number_vars

Type: has_many

Related object: L<RODA::RODADB::Result::FormEditedNumberVar>

=cut

__PACKAGE__->has_many(
  "form_edited_number_vars",
  "RODA::RODADB::Result::FormEditedNumberVar",
  { "foreign.variable_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 form_edited_text_vars

Type: has_many

Related object: L<RODA::RODADB::Result::FormEditedTextVar>

=cut

__PACKAGE__->has_many(
  "form_edited_text_vars",
  "RODA::RODADB::Result::FormEditedTextVar",
  { "foreign.variable_id" => "self.id" },
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

=head2 other_statistics

Type: has_many

Related object: L<RODA::RODADB::Result::OtherStatistic>

=cut

__PACKAGE__->has_many(
  "other_statistics",
  "RODA::RODADB::Result::OtherStatistic",
  { "foreign.variable_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 selection_variable

Type: might_have

Related object: L<RODA::RODADB::Result::SelectionVariable>

=cut

__PACKAGE__->might_have(
  "selection_variable",
  "RODA::RODADB::Result::SelectionVariable",
  { "foreign.variable_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 skip_next_variables

Type: has_many

Related object: L<RODA::RODADB::Result::Skip>

=cut

__PACKAGE__->has_many(
  "skip_next_variables",
  "RODA::RODADB::Result::Skip",
  { "foreign.next_variable_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 skip_variables

Type: has_many

Related object: L<RODA::RODADB::Result::Skip>

=cut

__PACKAGE__->has_many(
  "skip_variables",
  "RODA::RODADB::Result::Skip",
  { "foreign.variable_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 variable_vargroups

Type: has_many

Related object: L<RODA::RODADB::Result::VariableVargroup>

=cut

__PACKAGE__->has_many(
  "variable_vargroups",
  "RODA::RODADB::Result::VariableVargroup",
  { "foreign.variable_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 concepts

Type: many_to_many

Composing rels: L</concept_variables> -> concept

=cut

__PACKAGE__->many_to_many("concepts", "concept_variables", "concept");

=head2 vargroups

Type: many_to_many

Composing rels: L</variable_vargroups> -> vargroup

=cut

__PACKAGE__->many_to_many("vargroups", "variable_vargroups", "vargroup");


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:jurS4f187HLxFea9z9x4aA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;

=head1 METODE SUPLIMENTARE

=cut

=head2 attach_skips

Ataseaza salturi de la variabila curenta catre alte variabile.

=cut

sub attach_skips {

     my ( $self, %params ) = @_;
     foreach my $skip (@{$params{skips}}) {      	
    	#my $guard = $self->result_source->schema()->txn_scope_guard;
    	$skip->{variable_id} = $self->id;  
        my $skiprs = $self->result_source->schema()->resultset('Skip')->checkskip(%$skip);    	
        
      	#$guard->commit;
     }
}

=head2 attach_other_statistics

Ataseaza statistici asociate variabilei curente.

=cut

sub attach_other_statistics {

     my ( $self, %params ) = @_;
     foreach my $other_statistic (@{$params{other_statistics}}) {      	
    	#my $guard = $self->result_source->schema()->txn_scope_guard;
    	$other_statistic->{variable_id} = $self->id;  
        my $other_statistic_rs = $self->result_source->schema()->resultset('OtherStatistic')->check_other_statistic(%$other_statistic);    	
        
      	#$guard->commit;
     }
}

=head2 attach_concepts

Ataseaza concepte variabilei curente.

=cut

sub attach_concepts {

     my ( $self, %params ) = @_;
     foreach my $concept (@{$params{concepts}}) {      	
     	if ( $concept -> {name} && $concept -> {name} ne '' ) {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
        	my $conceptrs = $self->result_source->schema()->resultset('Concept')->checkconcept(%$concept);    	
        
        	$self->result_source->schema()->resultset('ConceptVariable')->find_or_create({
          																			 concept_id => $conceptrs->id,
          																			 variable_id => $self->id,
         																		    },
         																		    {
         		 																	 key => 'primary',
         																		    });
      		$guard->commit;
    	} 	
     }
}

=head2 attach_vargroups

Ataseaza grupuri variabilei curente.

=cut

sub attach_vargroups {

     my ( $self, %params ) = @_;
     foreach my $vargroup (@{$params{vargroups}}) {      	
     	if ( $vargroup -> {name} && $vargroup -> {name} ne '' ) {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
        	my $vargrouprs = $self->result_source->schema()->resultset('Vargroup')->checkvargroup(%$vargroup);    	
        
        	$self->result_source->schema()->resultset('VariableVargroup')->find_or_create({
          																			 vargroup_id => $vargrouprs->id,
          																			 variable_id => $self->id,
         																		    },
         																		    {
         		 																	 key => 'primary',
         																		    });
      		$guard->commit;
    	} 	
     }
}

=head2 attach_selection_variable

Ataseaza informatii specifice in cazul in care variabila curenta este de tip selectie.

=cut

sub attach_selection_variable {
	
	my ( $self, %params ) = @_;
    if ($self -> type_selection) {
    	my $selection_variable = @{$params{selection_variable}}[0];
     	if ($selection_variable -> {min_count} && $selection_variable -> {max_count}) {
    		$selection_variable -> {variable_id} = $self -> id;					   	
    		$self->result_source->schema()->resultset('SelectionVariable')->check_selection_variable(%$selection_variable);
     	}
    }       	 	
}

1;
