use utf8;
package RODA::RODADB::Result::Person;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

RODA::RODADB::Result::Person - Tabel unic pentru toate persoanele, oriunde ar fi ele

=cut

use strict;
use warnings;
use Email::Valid;
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

=head1 TABLE: C<person>

=cut

__PACKAGE__->table("person");

=head1 ACCESSORS

=head2 fname

  data_type: 'varchar'
  is_nullable: 0
  size: 100

=head2 mname

  data_type: 'varchar'
  is_nullable: 1
  size: 100

Prenumele persoanei

=head2 lname

  data_type: 'varchar'
  is_nullable: 0
  size: 100

Numele din mijloc al persoanei

=head2 prefix_id

  data_type: 'integer'
  is_nullable: 1

Numele de familie al persoanei

=head2 suffix_id

  data_type: 'integer'
  is_nullable: 1

Codul prefixului corespunzator persoanei (refera atributul id din tabelul prefix)

=head2 id

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0
  sequence: 'person_id_seq'

Codul sufixului

=cut

__PACKAGE__->add_columns(
  "fname",
  { data_type => "varchar", is_nullable => 0, size => 100 },
  "mname",
  { data_type => "varchar", is_nullable => 1, size => 100 },
  "lname",
  { data_type => "varchar", is_nullable => 0, size => 100 },
  "prefix_id",
  { data_type => "integer", is_nullable => 1 },
  "suffix_id",
  { data_type => "integer", is_nullable => 1 },
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "person_id_seq",
  },
);

=head1 PRIMARY KEY

=over 4

=item * L</id>

=back

=cut

__PACKAGE__->set_primary_key("id");

=head1 RELATIONS

=head2 person_addresses

Type: has_many

Related object: L<RODA::RODADB::Result::PersonAddress>

=cut

__PACKAGE__->has_many(
  "person_addresses",
  "RODA::RODADB::Result::PersonAddress",
  { "foreign.person_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 person_emails

Type: has_many

Related object: L<RODA::RODADB::Result::PersonEmail>

=cut

__PACKAGE__->has_many(
  "person_emails",
  "RODA::RODADB::Result::PersonEmail",
  { "foreign.person_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 person_internets

Type: has_many

Related object: L<RODA::RODADB::Result::PersonInternet>

=cut

__PACKAGE__->has_many(
  "person_internets",
  "RODA::RODADB::Result::PersonInternet",
  { "foreign.person_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 person_links

Type: has_many

Related object: L<RODA::RODADB::Result::PersonLink>

=cut

__PACKAGE__->has_many(
  "person_links",
  "RODA::RODADB::Result::PersonLink",
  { "foreign.person" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 person_orgs

Type: has_many

Related object: L<RODA::RODADB::Result::PersonOrg>

=cut

__PACKAGE__->has_many(
  "person_orgs",
  "RODA::RODADB::Result::PersonOrg",
  { "foreign.person_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 source_contacts

Type: has_many

Related object: L<RODA::RODADB::Result::SourceContact>

=cut

__PACKAGE__->has_many(
  "source_contacts",
  "RODA::RODADB::Result::SourceContact",
  { "foreign.person_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 study_people

Type: has_many

Related object: L<RODA::RODADB::Result::StudyPerson>

=cut

__PACKAGE__->has_many(
  "study_people",
  "RODA::RODADB::Result::StudyPerson",
  { "foreign.person_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07012 @ 2013-01-08 22:32:48
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:5tpPYjePPpyW8AiDIVGRnA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
__PACKAGE__->meta->make_immutable;


sub attach_addresses {
     my ( $self, %params ) = @_;
     foreach my $address (@{$params{addresses}}) {
         my $guard = $self->result_source->schema()->txn_scope_guard;
         my $addressrs = $self->result_source->schema()->resultset('Address')->checkaddress(%$address);
         #acum trebuie sa inseram si in many-to-many
         if ($addressrs) { 
        		#acu trebuie sa inseram asocierea
         		$self->result_source->schema()->resultset('PersonAddress')->find_or_create({
          																					address_id => $addressrs->id,
          																					person_id => $self->id,
         																					},
         																					{
         		 																			key => 'primary',
         																					});
      	}
        $guard->commit; 
     }
}

sub attach_emails {
     my ( $self, %params ) = @_;
     #aici ar trebui sa verificam emailul
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
         		$self->result_source->schema()->resultset('PersonEmail')->find_or_create({
          																				  email_id => $emailrs->id,
          																				  person_id => $self->id,
          																				  is_main => $ismain,
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
        	$self->result_source->schema()->resultset('PersonPhone')->find_or_create({
          																			  phone_id => $phoners->id,
          																			  person_id => $self->id,
          																			  is_main => $ismain,
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
        	$self->result_source->schema()->resultset('PersonInternet')->find_or_create({
          																			     internet_id => $internetrs->id,
          																			     person_id => $self->id,
          																			     is_main => $ismain,
         																			    },
         																			    {
         		 																	     key => 'primary',
         																			    });
      		}
      		$guard->commit; 	           
        }
}

1;
