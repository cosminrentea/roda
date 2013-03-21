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


=head1 NUME

FileStore::File - Obiect care reprezinta un fisier in L<FileStore>

=cut

=head1 VERSION

version 0.01

=cut

=head1 DESCRIERE

FileStore::File este un obiect care contine toate proprietatile utile ale unui fisier aflat in filestore. Majoritatea acestor proprietati se construiesc automat odata cu instantierea obiectului. 


=head1 Utilizare

      use FileStore::File;
      use FileStore::Volume;

       
      my $fs = FileStore::Volume->new(rootpath => '/my/root/path'); 
      my $file = FileStore::File->new(
                            volume   => $fs,
                            filename => 'image.jpg',
                            filepath => '/cale/relativa',
                            silent   => 1,
      );


      print $file->fullfilepath;
      #afiseaza /my/root/path/cale/relativa/image.jpg pe linux/unix 
   
      print $file->fullpath;      
      #afiseaza /my/root/path/cale/relativa pe linux/unix
      
      print $file->mimetype;
      #afiseaza image/jpeg
      
      #in cazul imaginii
      print $file->properties->{width};
      #afiseaza latimea in pixeli a imaginii

vezi L<FileStore::Volume>

=cut

=head1 ATRIBUTE

=cut


=over

=item  
C<filename> - numele fisierului, fara cale

=item
C<filepath> - calea catre fisier, este un obiect de tip L<Path::Class::Dir>

=item
C<silent>

=item
C<volume> - referinta catre volumul din care face parte fisierul curent, obiect de tip L<FileStore::Volume>

=item
C<fullfilename> - numele intreg al fisierului, incluzand calea catre acesta si calea catre volumul curent. Se construieste automat la initializare

=item
C<fullpath> - numele intreg al caii catre fisier, lipsit de numele fisierului. Se construieste automat la initializare

=item
C<stat> - referinta catre un obiect de tipul L<File::Stat> care contine informatii despre starea fisierului in sistemul de fisiere al computerului (timpul ultimului acces, drepturi). Se construieste automat la initializare

=item
C<mimetype> - identificatorul mime type al fisierului respectiv. Se construieste automat la initializare

=item
C<md5> - amprenta md5 a fisierului (a intregului fisier, nu doar a numelui). Se construieste automat la initializare

=item
C<properties> - referinta catre lista de proprietati obtinuta din plug-inul corespunzator tipului de fisier (L<FileStore::Info::PDF>, L<FileStore::Info::Image>). Se construieste automat la initializare 

=back

=head1 Utilizare



=cut


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


=head1 METODE

=cut

sub BUILD {
    my $self = shift;
    if ( !-f $self->fullfilename ) {
        unless ( $self->silent ) {
            confess "File " . $self->fullfilename . " does not exist";
        }
    }
}

=head3 _build_fullpath

Construieste continutul argumentului c<fullpath>. Se executa automat la initializarea obiectului

=cut

sub _build_fullpath {
    my $self     = shift;
    my $fullpath = $self->volume->rootpath;
    my @subdirs  = $self->filepath->dir_list;
    foreach (@subdirs) {
        $fullpath = $fullpath->subdir($_);
    }
    return $fullpath;
}

=head3 _build_fullfilename

Construieste continutul argumentului c<fullfilename>. Se executa automat la initializarea obiectului

=cut


sub _build_fullfilename {
    my $self = shift;
    return file( $self->fullpath, $self->filename );
}

=head3 _build_fstat

Construieste continutul argumentului c<fstat>. Se executa automat la initializarea obiectului

=cut


sub _build_fstat {
    my $self = shift;
    return new File::Stat( $self->fullfilename );
}

=head3 _build_filetype

Construieste continutul argumentului c<mimetype>. Se executa automat la initializarea obiectului

=cut


sub _build_filetype {
    my $self = shift;
    my $ft   = File::Type->new();
    return $ft->checktype_filename( $self->fullfilename );
}

=head3 _build_md5

Construieste continutul argumentului c<md5>. Se executa automat la initializarea obiectului

=cut


sub _build_md5 {
    my $self = shift;
    return file_md5_hex( $self->fullfilename ) or confess "$!";
}

=head3 _build_properties

Construieste continutul argumentului c<properties>. In functie de tipul fisierului, apeleaza plugin-ul corespunzator care returneaza un hash. 

Se executa automat la initializarea obiectului

=cut


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
