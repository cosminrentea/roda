use utf8;
package RODA::RODADB;

use Moose;
use RODA::FileStore;
use Data::Dumper;
use MooseX::MarkAsMethods autoclean => 1;


our $VERSION = "0.001";
$VERSION = eval $VERSION;


extends 'DBIx::Class::Schema';

with 'RODA::Components::DBIC::DBConfig';



=head1 NUME

RODA::RODADB

=head1 DESCRIERE

Componentul principal al sistemului de acces la date. Acesta se bazeaza pe o mapare relational-obiectuala extinsa cu componente necesare. RODA::RODADB contine elementele principale necesare accesului la datele relationale, metode care permit introducerea datelor in tabele, citirea lor, modificarea celor existente, stergere, etc.

=cut

=head1 ATRIBUTE

=over

=item  
I<configfile> - calea completa catre fisierul de configurare.   

=item  
I<rootconfig> - instanta a RODA::Config  

=item  
I<test> - determina RODA::RODADB sa se conecteza la baza de date de test si la sistemul de fisiere de test specificate in fisierul de configurare

=item  
I<filestore> - instanta a RODA::FileStore initializata automat dupa conexiunea la baza de date 

=back

=cut

has 'configfile' => ( is => 'rw', isa => 'Str' );
has 'rootconfig' => ( is => 'rw', isa => 'Maybe[RODA::Config]'  );
has 'filestore' => ( is => 'ro', isa => 'Maybe[RODA::FileStore]', builder => '_build_filestore', lazy => 1 );
has 'test' => ( is => 'rw', isa => 'Str', default=>'0');
has 'userid'   => ( is => 'rw', isa => 'Maybe[Int]', trigger => \&set_user_id, );
has 'user'     => ( is => 'rw', isa => 'Maybe[RODA::RODADB::Result::RodaUser]' );

__PACKAGE__->load_components(qw/+RODA::Components::DBIC::DBAuditSchema/);

__PACKAGE__->load_namespaces(                                                                                                                      
    result_namespace => 'Result',                                                                                                                  
    resultset_namespace => 'ResultSet',                                                                                                            
); 

=head1 METODE

=cut

=head2 set_user_id

Trigger care se executa la setarea userid-ului. Incarca clasa RodaUser si o ataseaza accesorului user astfel incat toate
componentele schemei vor avea la dispozitie un user id

=cut

sub set_user_id {
    my $self     = shift;
    my $userid = shift;

    my $userrs   = $self->resultset('RodaUser')->find( $userid  );
    if ( $userrs ) {
        $self->user($userrs);
    } else  {
        confess 'User Id not found: ' . $userid . "\n";
    } 
}

=head2 build_filestore 

initializeaza obiectul RODA::FileStore, il conecteaza la sistemul de fisiere si il ataseaza instantei ORM-ului

=cut

sub _build_filestore {
 my $self = shift;
 return RODA::FileStore->new(rootpath => $self->rootconfig->uploads_dir, name => 'uploads');
}

__PACKAGE__->meta->make_immutable(inline_constructor => 0);



1;
