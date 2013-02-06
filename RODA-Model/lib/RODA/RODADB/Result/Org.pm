use utf8;
package RODA::RODADB::Result::Org;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Org - Tabel ce contine toate organizatiile din baza de date

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

=head1 TABLE: C<org>

=cut

__PACKAGE__->table("org");

=head1 ACCESSORS

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'org_id_seq'

Codul organizatiei

=head2 name

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Denumirea prescurtata a organizatiei (posibil un acronim al acesteia)

=head2 org_prefix_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

Codul prefixului organizatiei (refera atributul id din tabelul org_prefix)

=head2 fullname

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Denumirea completa a organizatiei 

=head2 org_sufix_id

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

Codul sufixului organizatiei (refera atributul id din tabelul org_sufix)

=cut

__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "org_id_seq",
  },
  "name",
  { data_type => "varchar", is_nullable => 0, size => 100 },
  "org_prefix_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
  "fullname",
  { data_type => "varchar", is_nullable => 0, size => 100 },
  "org_sufix_id",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 instance_orgs

Type: has_many

Related object: L<RODA::RODADB::Result::InstanceOrg>

=cut

__PACKAGE__->has_many(
  "instance_orgs",
  "RODA::RODADB::Result::InstanceOrg",
  { "foreign.org_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 org_addresses

Type: has_many

Related object: L<RODA::RODADB::Result::OrgAddress>

=cut

__PACKAGE__->has_many(
  "org_addresses",
  "RODA::RODADB::Result::OrgAddress",
  { "foreign.org_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 org_emails

Type: has_many

Related object: L<RODA::RODADB::Result::OrgEmail>

=cut

__PACKAGE__->has_many(
  "org_emails",
  "RODA::RODADB::Result::OrgEmail",
  { "foreign.org_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 org_internets

Type: has_many

Related object: L<RODA::RODADB::Result::OrgInternet>

=cut

__PACKAGE__->has_many(
  "org_internets",
  "RODA::RODADB::Result::OrgInternet",
  { "foreign.org_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 org_phones

Type: has_many

Related object: L<RODA::RODADB::Result::OrgPhone>

=cut

__PACKAGE__->has_many(
  "org_phones",
  "RODA::RODADB::Result::OrgPhone",
  { "foreign.org_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 org_prefix

Type: belongs_to

Related object: L<RODA::RODADB::Result::OrgPrefix>

=cut

__PACKAGE__->belongs_to(
  "org_prefix",
  "RODA::RODADB::Result::OrgPrefix",
  { id => "org_prefix_id" },
  {
    is_deferrable => 0,
    join_type     => "LEFT",
    on_delete     => "NO ACTION",
    on_update     => "NO ACTION",
  },
);

=head2 org_relations_org_1s

Type: has_many

Related object: L<RODA::RODADB::Result::OrgRelation>

=cut

__PACKAGE__->has_many(
  "org_relations_org_1s",
  "RODA::RODADB::Result::OrgRelation",
  { "foreign.org_1_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 org_relations_org_2s

Type: has_many

Related object: L<RODA::RODADB::Result::OrgRelation>

=cut

__PACKAGE__->has_many(
  "org_relations_org_2s",
  "RODA::RODADB::Result::OrgRelation",
  { "foreign.org_2_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 org_sufix

Type: belongs_to

Related object: L<RODA::RODADB::Result::OrgSufix>

=cut

__PACKAGE__->belongs_to(
  "org_sufix",
  "RODA::RODADB::Result::OrgSufix",
  { id => "org_sufix_id" },
  {
    is_deferrable => 0,
    join_type     => "LEFT",
    on_delete     => "NO ACTION",
    on_update     => "NO ACTION",
  },
);

=head2 person_orgs

Type: has_many

Related object: L<RODA::RODADB::Result::PersonOrg>

=cut

__PACKAGE__->has_many(
  "person_orgs",
  "RODA::RODADB::Result::PersonOrg",
  { "foreign.org_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 source

Type: might_have

Related object: L<RODA::RODADB::Result::Source>

=cut

__PACKAGE__->might_have(
  "source",
  "RODA::RODADB::Result::Source",
  { "foreign.org_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_orgs

Type: has_many

Related object: L<RODA::RODADB::Result::StudyOrg>

=cut

__PACKAGE__->has_many(
  "study_orgs",
  "RODA::RODADB::Result::StudyOrg",
  { "foreign.org_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07033 @ 2013-02-05 11:04:03
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:9B0rorAzWGUlvEtSxPbvrw

sub attach_addresses {
     my ( $self, %params ) = @_;
     foreach my $address (@{$params{addresses}}) {
         my $guard = $self->result_source->schema()->txn_scope_guard;
         my $addressrs = $self->result_source->schema()->resultset('Address')->checkaddress(%$address);
         #acum trebuie sa inseram si in many-to-many
         if ($addressrs) { 
        	#acu trebuie sa inseram asocierea
         	$self->result_source->schema()->resultset('OrgAddress')->find_or_create({
          	address_id => $addressrs->id,
          	org_id => $self->id,
         },
         {
          key => 'primary',
         }
         );
      }
        $guard->commit; 
     }
}

sub attach_emails {
     my ( $self, %params ) = @_;
     foreach my $email (@{$params{emails}}) { 
        if (Email::Valid->address($email->{email})) {
			my $guard = $self->result_source->schema()->txn_scope_guard;
        	my $emailrs = $self->result_source->schema()->resultset('Email')->checkemail(%$email);   
			my $ismain;
			if (!$email->{ismain}) {
				$ismain = '0';
			} else {
				$ismain = $email->{ismain};
			}
			
            if ($emailrs) { 
         		$self->result_source->schema()->resultset('OrgEmail')->find_or_create({
          																				  email_id => $emailrs->id,
          																				  org_id => $self->id,
          																				  main => $ismain,
         																				 },
         																				 {
         		 																		  key => 'primary',
         																				 });
      		}
      		$guard->commit; 	
        } else {
         	die 'Invalid Email';
        }
     }
}

sub attach_phones {
     my ( $self, %params ) = @_;
     foreach my $phone (@{$params{phones}}) { 
     	my $guard = $self->result_source->schema()->txn_scope_guard;
        my $phoners = $self->result_source->schema()->resultset('Phone')->checkphone(%$phone);  
        my $ismain;
		if (!$phone->{ismain}) {
			$ismain = '0';
		} else {
			$ismain = $phone->{ismain};
		}
			   
        if ($phoners) { 
        	$self->result_source->schema()->resultset('OrgPhone')->find_or_create({
          																			  phone_id => $phoners->id,
          																			  org_id => $self->id,
          																			  main => $ismain,
         																			 },
         																			 {
         		 																	  key => 'primary',
         																			 });
      		}
      		$guard->commit; 	
        }
}

sub attach_internets {
     my ( $self, %params ) = @_;
     foreach my $internet (@{$params{internets}}) { 
     	my $guard = $self->result_source->schema()->txn_scope_guard;
        my $internetrs = $self->result_source->schema()->resultset('Internet')->checkinternet(%$internet);
        my $ismain;
        if (!$internet->{ismain}) {
			$ismain = '0';
		} else {
			$ismain = $internet->{ismain};
		}
             
        if ($internetrs) { 
        	$self->result_source->schema()->resultset('OrgInternet')->find_or_create({
          																			     internet_id => $internetrs->id,
          																			     org_id => $self->id,
          																			     main => $ismain,
         																			    },
         																			    {
         		 																	     key => 'primary',
         																			    });
      		}
      		$guard->commit; 	           
        }
}

# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;
1;
