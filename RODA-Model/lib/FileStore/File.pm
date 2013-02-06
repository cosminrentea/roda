package FileStore::File;
use Moose;
use Moose::Util::TypeConstraints;
use File::Stat;
use File::Type;
use FileStore::Info::Image;
use FileStore::Info::PDF;
use Digest::MD5::File qw(file_md5 file_md5_hex);
use Path::Class;
use Carp;

our $VERSION = "0.001";
$VERSION = eval $VERSION;



has 'filename'     => ( is => 'ro', isa => 'Str' );
has 'filepath'     => ( is => 'ro', isa => 'Path::Class::Dir' );
has 'silent'       => ( is => 'rw', isa => 'Str' );
has 'volume'       => ( is => 'ro', isa => 'FileStore::Volume' );
has 'fullfilename' => ( is => 'ro', isa => 'Path::Class::File', builder => '_build_fullfilename', lazy => 1 );
has 'fullpath'     => ( is => 'ro', isa => 'Path::Class::Dir', builder => '_build_fullpath', lazy => 1 );
has 'stat'         => ( is => 'ro', isa => 'File::Stat', builder => '_build_fstat', lazy => 1 );
has 'mimetype'     => ( is => 'ro', isa => 'Str', builder => '_build_filetype', lazy => 1 );
has 'md5'          => ( is => 'ro', isa => 'Str', builder => '_build_md5', lazy => 1 );
has 'properties'   => ( is => 'ro', isa => 'HashRef', builder => '_build_properties', lazy => 1 );

sub BUILD {
    my $self = shift;
    if ( !-f $self->fullfilename ) {
        unless ( $self->silent ) {
            confess "File " . $self->fullfilename . " does not exist";
        }
    }
}

sub _build_fullpath {
    my $self     = shift;
    my $fullpath = $self->volume->rootpath;
    my @subdirs  = $self->filepath->dir_list;
    foreach (@subdirs) {
        $fullpath = $fullpath->subdir($_);
    }
    return $fullpath;
}

sub _build_fullfilename {
    my $self = shift;
    return file( $self->fullpath, $self->filename );
}

sub _build_fstat {
    my $self = shift;
    return new File::Stat( $self->fullfilename );
}

sub _build_filetype {
    my $self = shift;
    my $ft   = File::Type->new();
    return $ft->checktype_filename( $self->fullfilename );
}

sub _build_md5 {
    my $self = shift;
    return file_md5_hex( $self->fullfilename ) or confess "$!";
}

sub _build_properties {
    my $self = shift;
    if ( $self->mimetype =~ /image/i ) {
        my $img = FileStore::Info::Image->new( wfile => $self );
        return $img->get_properties;
    }
    if ( $self->mimetype =~ /pdf/i ) {
        my $pdf = FileStore::Info::PDF->new( wfile => $self );
        return $pdf->get_properties;
    }
}
__PACKAGE__->meta()->make_immutable();
1;
