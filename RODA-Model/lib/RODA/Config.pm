package RODA::Config;

use Moose;
use MooseX::Configuration;
use MooseX::Types::Common::String;

our $VERSION = "0.001";
$VERSION = eval $VERSION;

=head1 NUME

RODA::Config

=head1 DESCRIERE

Clasa care se ocupa de citirea si interpretarea fisierului de configurare a aplicatiei, in care sunt scrise informatiile esentiale, de care sistemul are nevoie pentru initializare inainte de a putea accesa serverul de base de date

=cut

=head1 UTILIZARE


=cut



has software_name => (
    is      => 'ro',
    isa     => 'Str',
    default => 'RODA',
    key     => 'software_name',
    documentation =>
        'The name of the software to be displayed in the application.',
);

has database_dsn => (
    is      => 'ro',
    isa     => 'Str',
    lazy    => 1,
    builder => '_build_database_dsn',
);

has database_name => (
    is      => 'ro',
    isa     => 'Str',
    default => '',
    section => 'database',
    key     => 'name',
    documentation =>
        'The name of the database.',
    writer => '_set_database_name',
);

has database_username => (
    is      => 'ro',
    isa     => 'Str',
    default => q{postgres},
    section => 'database',
    key     => 'username',
    documentation =>
        'The username to use when connecting to the database. By default, this is empty.',
    writer => '_set_database_username',
);

has database_password => (
    is      => 'ro',
    isa     => 'Str',
    default => q{postgres},
    section => 'database',
    key     => 'password',
    documentation =>
        'The password to use when connecting to the database. By default, this is empty.',
    writer => '_set_database_password',
);

has database_host => (
    is      => 'ro',
    isa     => 'Str',
    default => q{91.199.243.222},
    section => 'database',
    key     => 'host',
    documentation =>
        'The host to use when connecting to the database. By default, this is empty.',
    writer => '_set_database_host',
);

has database_port => (
    is      => 'ro',
    isa     => 'Str',
    default => q{},
    section => 'database',
    key     => 'port',
    documentation =>
        'The port to use when connecting to the database. By default, this is empty.',
    writer => '_set_database_port',
);

has database_ssl => (
    is      => 'ro',
    isa     => 'Bool',
    default => 0,
    section => 'database',
    key     => 'ssl',
    documentation =>
        'If this is true, then the database connection with require SSL.',
    writer => '_set_database_ssl',
);

has uploads_dir => (
    is      => 'rw',
    isa     => 'Str',
    key     => 'uploads_dir',
    section => 'filesystem',
    writer => '_set_uploads_dir',
);

has test_database_dsn => (
    is      => 'ro',
    isa     => 'Str',
    lazy    => 1,
    builder => '_build_test_database_dsn',
);

has test_database_name => (
    is      => 'ro',
    isa     => 'Str',
    default => '',
    section => 'test database',
    key     => 'name',
    documentation =>
        'The name of the test database.',
    writer => '_set_test_database_name',
);

has test_database_username => (
    is      => 'ro',
    isa     => 'Str',
    default => q{postgres},
    section => 'test database',
    key     => 'username',
    documentation =>
        'The username to use when connecting to the test database. By default, this is postgres.',
    writer => '_set_test_database_username',
);

has test_database_password => (
    is      => 'ro',
    isa     => 'Str',
    default => q{postgres},
    section => 'test database',
    key     => 'password',
    documentation =>
        'The password to use when connecting to the test database. By default, this is postgres.',
    writer => '_set_test_database_password',
);

has test_database_host => (
    is      => 'ro',
    isa     => 'Str',
    default => q{localhost},
    section => 'test database',
    key     => 'host',
    documentation =>
        'The host to use when connecting to the database. By default, this is 127.0.0.0.',
    writer => '_set_test_database_host',
);

has test_database_port => (
    is      => 'ro',
    isa     => 'Str',
    default => q{},
    section => 'test database',
    key     => 'port',
    documentation =>
        'The port to use when connecting to the database. By default, this is empty.',
    writer => '_set_test_database_port',
);

has test_database_ssl => (
    is      => 'ro',
    isa     => 'Bool',
    default => 0,
    section => 'test database',
    key     => 'ssl',
    documentation =>
        'If this is true, then the database connection with require SSL. Default is 0 (it means no)',
    writer => '_set_test_database_ssl',
);

has test_uploads_dir => (
    is      => 'rw',
    isa     => 'Str',
    key     => 'uploads_dir',
    section => 'test filesystem',
    writer => '_set_test_uploads_dir',
);



sub _build_database_dsn {
    my $self = shift;

    my $dsn = 'dbi:Pg:dbname=' . $self->database_name();

    if ( my $host = $self->database_host() ) {
        $dsn .= ';host=' . $host;
    }

    if ( my $port = $self->database_port() ) {
        $dsn .= ';port=' . $port;
    }

    $dsn .= ';sslmode=require'
        if $self->database_ssl();

    return $dsn
}

sub _build_test_database_dsn {
    my $self = shift;

    my $dsn = 'dbi:Pg:dbname=' . $self->test_database_name();

    if ( my $host = $self->test_database_host() ) {
        $dsn .= ';host=' . $host;
    }

    if ( my $port = $self->test_database_port() ) {
        $dsn .= ';port=' . $port;
    }

    $dsn .= ';sslmode=require'
        if $self->test_database_ssl();

    return $dsn
}


around write_config_file => sub {
    my $orig = shift;
    my $self = shift;

    my $generated = "Config file generated by RODA";

    $self->$orig( generated_by => $generated, @_ );
};

__PACKAGE__->meta()->make_immutable();

1;