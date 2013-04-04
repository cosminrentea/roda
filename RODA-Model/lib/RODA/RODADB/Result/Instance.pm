use utf8;
package RODA::RODADB::Result::Instance;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Instance - Tabel ce contine informatiile principale ale instantelor

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

=head1 TABLE: C<instance>

=cut

__PACKAGE__->table("instance");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'instance_id_seq'

Codul instantei

=head2 study_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul studiului caruia ii apartine instanta (refera atributul id al tabelului study)

=head2 datestart

  data_type: 'timestamp'
  is_nullable: 1

Data de inceput a instantei

=head2 dateend

  data_type: 'timestamp'
  is_nullable: 1

Data de incheiere a instantei

=head2 unit_analysis_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

Codul unitatii de analiza specifice instantei (refera atributul id al tabelului unit_analysis)

=head2 version

  data_type: 'integer'
  is_nullable: 0

Versiunea instantei

=head2 insertion_status

  data_type: 'integer'
  is_nullable: 0

Pasul din wizard-ul de introducere a metadatelor - din moment ce introducerea se face prin wizard, fiecare pas trebuie comis in baza de dat; pana la finalizarea introducerii intregii instante e nevoie sa stim ca ele au fost partial introduse.

=head2 raw_data

  data_type: 'boolean'
  is_nullable: 0

daca datele sunt in forma digitizata (YES) sau in forma de fisiere procesabile/editabile (NO)

=head2 raw_metadata

  data_type: 'boolean'
  is_nullable: 0

daca metadatele sunt in forma digitizata (YES) sau in forma de fisiere procesabile/editabile (NO)

=head2 added_by

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 added

  data_type: 'timestamp'
  is_nullable: 0

=head2 time_meth_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "instance_id_seq",
  },
  "study_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "datestart",
  { data_type => "timestamp", is_nullable => 1 },
  "dateend",
  { data_type => "timestamp", is_nullable => 1 },
  "unit_analysis_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "version",
  { data_type => "integer", is_nullable => 0 },
  "insertion_status",
  { data_type => "integer", is_nullable => 0 },
  "raw_data",
  { data_type => "boolean", is_nullable => 0 },
  "raw_metadata",
  { data_type => "boolean", is_nullable => 0 },
  "added_by",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "added",
  { data_type => "timestamp", is_nullable => 0 },
  "time_meth_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 added_by

Type: belongs_to

Related object: L<RODA::RODADB::Result::User>

=cut

__PACKAGE__->belongs_to(
  "added_by",
  "RODA::RODADB::Result::User",
  { id => "added_by" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 forms

Type: has_many

Related object: L<RODA::RODADB::Result::Form>

=cut

__PACKAGE__->has_many(
  "forms",
  "RODA::RODADB::Result::Form",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instance_acls

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceAcl>

=cut

__PACKAGE__->has_many(
  "instance_acls",
  "RODA::RODADB::Result::InstanceAcl",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instance_descrs

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceDescr>

=cut

__PACKAGE__->has_many(
  "instance_descrs",
  "RODA::RODADB::Result::InstanceDescr",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instance_documents

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceDocument>

=cut

__PACKAGE__->has_many(
  "instance_documents",
  "RODA::RODADB::Result::InstanceDocument",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instance_keywords

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceKeyword>

=cut

__PACKAGE__->has_many(
  "instance_keywords",
  "RODA::RODADB::Result::InstanceKeyword",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instance_orgs

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceOrg>

=cut

__PACKAGE__->has_many(
  "instance_orgs",
  "RODA::RODADB::Result::InstanceOrg",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instance_people

Type: has_many

Related object: L<RODA::RODADB::Result::InstancePerson>

=cut

__PACKAGE__->has_many(
  "instance_people",
  "RODA::RODADB::Result::InstancePerson",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instance_sampling_procedures

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceSamplingProcedure>

=cut

__PACKAGE__->has_many(
  "instance_sampling_procedures",
  "RODA::RODADB::Result::InstanceSamplingProcedure",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instance_topics

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceTopic>

=cut

__PACKAGE__->has_many(
  "instance_topics",
  "RODA::RODADB::Result::InstanceTopic",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 meth_coll_types

Type: has_many

Related object: L<RODA::RODADB::Result::MethCollType>

=cut

__PACKAGE__->has_many(
  "meth_coll_types",
  "RODA::RODADB::Result::MethCollType",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study

Type: belongs_to

Related object: L<RODA::RODADB::Result::Study>

=cut

__PACKAGE__->belongs_to(
  "study",
  "RODA::RODADB::Result::Study",
  { id => "study_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 time_meth

Type: belongs_to

Related object: L<RODA::RODADB::Result::TimeMethType>

=cut

__PACKAGE__->belongs_to(
  "time_meth",
  "RODA::RODADB::Result::TimeMethType",
  { id => "time_meth_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 unit_analysis

Type: belongs_to

Related object: L<RODA::RODADB::Result::UnitAnalysis>

=cut

__PACKAGE__->belongs_to(
  "unit_analysis",
  "RODA::RODADB::Result::UnitAnalysis",
  { id => "unit_analysis_id" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 variables

Type: has_many

Related object: L<RODA::RODADB::Result::Variable>

=cut

__PACKAGE__->has_many(
  "variables",
  "RODA::RODADB::Result::Variable",
  { "foreign.instance_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 collection_models

Type: many_to_many

Composing rels: L</meth_coll_types> -> collection_model

=cut

__PACKAGE__->many_to_many("collection_models", "meth_coll_types", "collection_model");

=head2 documents

Type: many_to_many

Composing rels: L</instance_documents> -> document

=cut

__PACKAGE__->many_to_many("documents", "instance_documents", "document");

=head2 sampling_procedures

Type: many_to_many

Composing rels: L</instance_sampling_procedures> -> sampling_procedure

=cut

__PACKAGE__->many_to_many(
  "sampling_procedures",
  "instance_sampling_procedures",
  "sampling_procedure",
);

=head2 topics

Type: many_to_many

Composing rels: L</instance_topics> -> topic

=cut

__PACKAGE__->many_to_many("topics", "instance_topics", "topic");


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:k1xWRudW/DcgDnQTImaocw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;

sub attach_organizations {

     my ( $self, %params ) = @_;
     foreach my $org (@{$params{orgs}}) { 
     	
        my $assoctypeId;
        #Verificarea tipului asocierii intre instanta si organizatie (assoctype); daca nu exista, este inserat mai intai in tabelul instance_org_assoc
    	if ( $org -> {assoc_name} && $org -> {assoc_name} ne '' ) {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
        	my $assoctypers = $self->result_source->schema()->resultset('InstanceOrgAssoc')->checkassoctype(%$org);    	
        
        	$self->result_source->schema()->resultset('InstanceOrg')->find_or_create({
          																			org_id => $org->{id},
          																			instance_id => $self->id,
          																			assoc_type_id => $assoctypers->id,
         																		  },
         																		  {
         		 																	key => 'primary',
         																		  });
      		$guard->commit;
    	} 	
     }
}

sub attach_persons {

     my ( $self, %params ) = @_;
     foreach my $person (@{$params{persons}}) { 
     	
        my $assoctypeId;
        #Verificarea tipului asocierii intre instanta si persoana (assoctype); daca nu exista, este inserat mai intai in tabelul instance_person_assoc
    	if ( $person -> {assoc_name} && $person -> {assoc_name} ne '' ) {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
        	my $assoctypers = $self->result_source->schema()->resultset('InstancePersonAssoc')->checkassoctype(%$person);    	
        
        	$self->result_source->schema()->resultset('InstancePerson')->find_or_create({
          																			person_id => $person->{id},
          																			instance_id => $self->id,
          																			assoc_type_id => $assoctypers->id,
         																		  },
         																		  {
         		 																	key => 'primary',
         																		  });
      		$guard->commit;
    	} 	
     }
}

sub attach_topics {

     my ( $self, %params ) = @_;
     foreach my $topic (@{$params{topics}}) {      	
     	if ( $topic -> {name} && $topic -> {name} ne '' ) {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
        	my $topicrs = $self->result_source->schema()->resultset('Topic')->checktopic(%$topic);    	
        
        	$self->result_source->schema()->resultset('InstanceTopic')->find_or_create({
          																			 topic_id => $topicrs->id,
          																			 study_id => $self->id,
         																		    },
         																		    {
         		 																	 key => 'primary',
         																		    });
      		$guard->commit;
    	} 	
     }
}

sub attach_keywords {

     my ( $self, %params ) = @_;
     foreach my $keyword (@{$params{keywords}}) {      	
     	if ( $keyword -> {name} && $keyword -> {name} ne '' ) {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
        	my $keywordrs = $self->result_source->schema()->resultset('Keyword')->checkkeyword(%$keyword);    	
        
        	$self->result_source->schema()->resultset('InstanceKeyword')->find_or_create({
          																			   keyword_id => $keywordrs->id,
          																			   study_id => $self->id,
          																			   added => $keyword->{added},
          																			   added_by => $keyword->{added_by},
         																		      },
         																		      {
         		 																	   key => 'primary',
         																		      });
      		$guard->commit;
    	} 	
     }
}

sub attach_variables {
     my ( $self, %params ) = @_;
     foreach my $variable (@{$params{variables}}) { 
     	my $guard = $self->result_source->schema()->txn_scope_guard;
        my $variable_exists = $self->result_source->schema()->resultset('Variable')->checkvariableexists(%$variable);
        if (!$variable_exists) {
        	my $editedTextType;
        	my $editedNumberType;
        	my $selectionType;
        	if ($variable->{type_edited_text} && $variable->{type_edited_text}) {
				$editedTextType = 1;
			} else {
				$editedTextType = 0;
			}
             
        	if ($variable->{type_edited_number} && $variable->{type_edited_number}) {
				$editedNumberType = 1;
			} else {
				$editedNumberType = 0;
			}     
             
        	if ($variable->{type_selection} && $variable->{type_selection}) {
				$selectionType = 1;
			} else {
				$selectionType = 0;
			}
		
            #Deocamdata nu exista identificare pe fisiere
             
        	my $variablers = $self->result_source->schema()->resultset('Variable')
        														 ->find_or_create({          																		   
          																		   instance_id => $self->id,
          																		   label => $variable -> {label},
          																		   type => $variable -> {type},
          																		   order_in_instance => $variable -> {order_in_instance},
          																		   operator_instructions => $variable -> {operator_instructions},
          																		   #file_id => $filers -> id,
          																		   type_edited_text => $editedTextType,
          																		   type_edited_number => $editedNumberType,
          																		   type_selection => $selectionType, 
         																		  },         													
         																		 );
         	if ($variable -> {skips} && @{$variable -> {skips}} > 0){
         		$variablers -> attach_skips(skips => $variable -> {skips});
         	}																		 
         	
         	if ($variable -> {other_statistics} && @{$variable -> {other_statistics}} > 0){
         		$variablers -> attach_other_statistics(other_statistics => $variable -> {other_statistics});
         	}	
         																		 
      	}
      	$guard->commit; 	           
     }
}

sub attach_forms {
     my ( $self, %params ) = @_;
     foreach my $form (@{$params{forms}}) { 
     	my $guard = $self->result_source->schema()->txn_scope_guard;
     	$form -> {instance_id} = $self -> id;
        my $formrs = $self->result_source->schema()->resultset('Form')->checkform(%$form);       
      	$guard->commit; 	           
     }
}

1;
