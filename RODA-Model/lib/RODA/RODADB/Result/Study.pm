use utf8;
package RODA::RODADB::Result::Study;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Study

=head1 DESCRIPTION

Tabel care stocheaza studiile desfasurate, ale caror informatii sunt prezente in baza de date 

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

__PACKAGE__->load_components("InflateColumn::DateTime", "+RODA::Components::DBIC::DBAudit");

=head1 TABLE: C<study>

=cut

__PACKAGE__->table("study");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'study_id_seq'

Codul studiului

=head2 datestart

  data_type: 'timestamp'
  is_nullable: 1

Data de inceput a studiului

=head2 dateend

  data_type: 'timestamp'
  is_nullable: 1

Data de final a studiului

=head2 insertion_status

  data_type: 'integer'
  is_nullable: 0

Pasul din wizard-ul de introducere a metadatelor - din moment ce introducerea se face prin wizard, fiecare pas trebuie comis in baza de date; pana la finalizarea introducerii intregului studiu e nevoie sa stim ca ele au fost partial introduse.

=head2 added_by

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 0

=head2 added

  data_type: 'timestamp'
  is_nullable: 0

=head2 can_digitize

  data_type: 'boolean'
  is_nullable: 0

=head2 can_use_anonymous

  data_type: 'boolean'
  is_nullable: 0

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "study_id_seq",
  },
  "datestart",
  { data_type => "timestamp", is_nullable => 1 },
  "dateend",
  { data_type => "timestamp", is_nullable => 1 },
  "insertion_status",
  { data_type => "integer", is_nullable => 0 },
  "added_by",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 0 },
  "added",
  { data_type => "timestamp", is_nullable => 0 },
  "can_digitize",
  { data_type => "boolean", is_nullable => 0 },
  "can_use_anonymous",
  { data_type => "boolean", is_nullable => 0 },
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

Related object: L<RODA::RODADB::Result::RodaUser>

=cut

__PACKAGE__->belongs_to(
  "added_by",
  "RODA::RODADB::Result::RodaUser",
  { id => "added_by" },
  { is_deferrable => 0, on_delete => "NO ACTION", on_update => "NO ACTION" },
);

=head2 catalog_studies

Type: has_many

Related object: L<RODA::RODADB::Result::CatalogStudy>

=cut

__PACKAGE__->has_many(
  "catalog_studies",
  "RODA::RODADB::Result::CatalogStudy",
  { "foreign.study_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 instances

Type: has_many

Related object: L<RODA::RODADB::Result::Instance>

=cut

__PACKAGE__->has_many(
  "instances",
  "RODA::RODADB::Result::Instance",
  { "foreign.study_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_acls

Type: has_many

Related object: L<RODA::RODADB::Result::StudyAcl>

=cut

__PACKAGE__->has_many(
  "study_acls",
  "RODA::RODADB::Result::StudyAcl",
  { "foreign.study_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_descrs

Type: has_many

Related object: L<RODA::RODADB::Result::StudyDescr>

=cut

__PACKAGE__->has_many(
  "study_descrs",
  "RODA::RODADB::Result::StudyDescr",
  { "foreign.study_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_files

Type: has_many

Related object: L<RODA::RODADB::Result::StudyFile>

=cut

__PACKAGE__->has_many(
  "study_files",
  "RODA::RODADB::Result::StudyFile",
  { "foreign.study_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_keywords

Type: has_many

Related object: L<RODA::RODADB::Result::StudyKeyword>

=cut

__PACKAGE__->has_many(
  "study_keywords",
  "RODA::RODADB::Result::StudyKeyword",
  { "foreign.study_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_orgs

Type: has_many

Related object: L<RODA::RODADB::Result::StudyOrg>

=cut

__PACKAGE__->has_many(
  "study_orgs",
  "RODA::RODADB::Result::StudyOrg",
  { "foreign.study_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_people

Type: has_many

Related object: L<RODA::RODADB::Result::StudyPerson>

=cut

__PACKAGE__->has_many(
  "study_people",
  "RODA::RODADB::Result::StudyPerson",
  { "foreign.study_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_topics

Type: has_many

Related object: L<RODA::RODADB::Result::StudyTopic>

=cut

__PACKAGE__->has_many(
  "study_topics",
  "RODA::RODADB::Result::StudyTopic",
  { "foreign.study_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 files

Type: many_to_many

Composing rels: L</study_files> -> file

=cut

__PACKAGE__->many_to_many("files", "study_files", "file");

=head2 topics

Type: many_to_many

Composing rels: L</study_topics> -> topic

=cut

__PACKAGE__->many_to_many("topics", "study_topics", "topic");


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:04
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:GNMD2SKONKSNyYAOCvjO7w


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;

sub attach_organizations {

     my ( $self, %params ) = @_;
     foreach my $org (@{$params{orgs}}) { 
     	
        my $assoctypeId;
        #Verificarea tipului asocierii intre studiu si organizatie (assoctype); daca nu exista, este inserat mai intai in tabelul study_org_assoc
    	if ( $org -> {assoc_name} && $org -> {assoc_name} ne '' ) {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
        	my $assoctypers = $self->result_source->schema()->resultset('StudyOrgAssoc')->checkassoctype(%$org);    	
        
        	$self->result_source->schema()->resultset('StudyOrg')->find_or_create({
          																			org_id => $org->{id},
          																			study_id => $self->id,
          																			assoctype_id => $assoctypers->id,
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
        #Verificarea tipului asocierii intre studiu si persoana (assoctype); daca nu exista, este inserat mai intai in tabelul study_person_assoc
    	if ( $person -> {assoc_name} && $person -> {assoc_name} ne '' ) {
    		my $guard = $self->result_source->schema()->txn_scope_guard;
        	my $assoctypers = $self->result_source->schema()->resultset('StudyPersonAssoc')->checkassoctype(%$person);    	
        
        	$self->result_source->schema()->resultset('StudyPerson')->find_or_create({
          																			person_id => $person->{id},
          																			study_id => $self->id,
          																			assoctype_id => $assoctypers->id,
         																		  },
         																		  {
         		 																	key => 'primary',
         																		  });
      		$guard->commit;
    	} 	
     }
}

1;
