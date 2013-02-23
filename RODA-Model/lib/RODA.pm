package RODA;

use Moose;
use RODA::Config;
use RODA::RODADB;
use Data::Dumper;
use namespace::autoclean;

our $VERSION = "0.001";
$VERSION = eval $VERSION;


=head1 NAME

RODA

=head1 DESCRIPTION

Clasa principala a modelului RODA

=cut

=head1 ATTRIBUTES

 B<configfile> - calea completa catre fisierul de configurare. In cazul in care nu exista, se va incerca ghicirea lui, intai in variabila de mediu RODA_CONFIG
      apoi in directorul /etc/roda/roda.ini. 
 B<debugsql> - determina imprimarea tuturor interogarilor SQL trimise de RODA::RODADB catre baza de date
 B<test> - determina RODA::RODADB sa se conecteza la baza de date de test si la sistemul de fisiere de test specificate in fisierul de configurare
 B<rootconfig> - instanta a RODA::Config initializata automat prin citirea fisierului din B<configfile> 
 B<dbschema> - instanta a RODA::DODADB initializata automat dupa conexiunea la baza de date specificata in B<rootconfig>
 
=cut

has 'configfile' => ( is => 'ro', isa => 'Str',          builder => '_guess_config',      lazy => 1 );
has 'rootconfig' => ( is => 'ro', isa => 'RODA::Config', builder => '_build_root_config', lazy => 1 );
has 'dbschema' => ( is => 'ro', isa => 'RODA::RODADB', builder => '_build_db_schema', lazy => 1 );
has 'debugsql' => (is => 'rw', isa=>'Bool', default=>'0');
has 'debugsqlfile' => (is => 'ro', isa=>'Str', default=>'0');
has 'test' => ( is => 'ro', isa => 'Str', default=>'0');
has 'userid'   => ( is => 'rw', isa => 'Maybe[Int]', trigger => \&propagate_user_id, );

=head1 METODE
=cut


=head 2 propagate_user_id

trigger care se executa la setarea userid-ului 

=cut

sub propagate_user_id {
     my $self     = shift;
     my $userid = shift;
     $self->dbschema->userid($userid);
}



=head 2 _build_root_config

construieste instanta RODA::Config si o ataseaza atributului B<configfile>
este apelata automat la constructia obiectului RODA datorita declararii in atribut

=cut

sub _build_root_config {
    my $self = shift; 
    return RODA::Config->new( config_file => $self->configfile );
}

=head 2 _guess_config

cauta fisierul de configurare

=cut



sub _guess_config {
    my $self = shift;
    if ( string_is_empty( $self->configfile ) ) {
        if ( !string_is_empty( $ENV{RODA_CONFIG} ) ) {
            die "Nonexistent config file in RODA_CONFIG env var: $ENV{RODA_CONFIG}"
              unless -f $ENV{RODA_CONFIG};
            return $ENV{RODA_CONFIG};
        }
        if ( -f '/etc/roda/roda.ini' ) {
            return '/etc/roda/roda.ini';
        }
    } else {
        return $self->configfile;
    }
}


=head 2 _build_db_schema

initializeaza ORM-ul si il conecteaza la baza de date aleasa (fie cea de test, fie cea principala)
Ataseaza informatiile suplimentare despre configurare instantei ORM-ului

=cut


sub _build_db_schema {
    my $self = shift;
    my $sc;

    print Dumper(\$self);

    if ( $self->test ) {
        $sc = RODA::RODADB->connect( $self->rootconfig->test_database_dsn, $self->rootconfig->test_database_username, $self->rootconfig->test_database_password, { pg_enable_utf8 => 1} );
		$sc->test($self->test);
    } else {
		$sc = RODA::RODADB->connect( $self->rootconfig->database_dsn, $self->rootconfig->database_username, $self->rootconfig->database_password, { pg_enable_utf8 => 1 } );
    }

    $sc->configfile($self->configfile);
    $sc->rootconfig($self->rootconfig);
    $sc->storage->debug($self->debugsql);
    if ( $self->debugsql ) {
		$sc->storage->debugfh(IO::File->new($self->debugsqlfile, 'w'));
    }

    return $sc;
}

__PACKAGE__->meta()->make_immutable();

1;