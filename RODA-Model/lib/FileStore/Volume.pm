package FileStore::Volume;
use Moose;
use Moose::Util::TypeConstraints;
use Path::Class;
use Carp;
use File::Copy;
use FileStore::File;
use File::Basename;

our $VERSION = "0.001";
$VERSION = eval $VERSION;


=head1 NUME

FileStore::Volume - Manipulare a directoarelor si fisierelor intr-un volum de tip sistem de fisiere

=cut

=head1 VERSION

version 0.01

=cut

=head1 DESCRIERE

Manipulare a directoarelor si fisierelor intr-un volum de tip sistem de fisiere. 
Nici una dintre operatiile cu fisiere sau directoare facute prin intermediul acestui modul (cu exceptia initializarii) nu
opereaza cu cai absolute.  Toate operatiile cu directoare si fisiere, cu cateva mici exceptii se fac utilizand obiecte de
tipul L<Path::Class>


=head1 ATRIBUTE

=cut

=head3 rootpath 

calea principala a volumului, radacina tuturor directoarelor. Este transmis ca string simplu si este convertit automat intr-un obiect de tip L<Path::Class::Dir>

=cut


=head1 Utilizare

  use FileStore::Volume;
  use Path::Class;

  my $fs = FileStore::Volume->new(rootpath => '/my/root/path'); 
  #nu folosim Path::Class aici, ci un simplu string care insa 
  #va fi automat convertit in Path::Class

  my $newdir = $fs->create_path(dir('ok','for','me'));
  my $isnfile = $fs->writefile(dir('ok/for/me'), $testfile1); 
  my $file = $fs->writepathfile(dir('my','new','directory'), $testfile1); 
  
  print $file->fullfilename;
  print $file->md5;
  print $file->mimetype;

  #Pentru un fisier de tip imagine
  print $file->properties->{width};
  
  #Pentru un fisier de tip pdf
  print $file->properties->{pages};

vezi si L<FileStore::Info::Image> si L<FileStore::Info::PDF> pentru proprietatile care se pot obtine in cazul anumitor tipuri de fisiere, si L<FileStore::File> pentru proprietati generale ale fisierului

=cut


subtype 'FileStore::Volume::rootpath' => as class_type('Path::Class::Dir');
coerce 'FileStore::Volume::rootpath' => from 'Str' => via { Path::Class::Dir->new($_) };
has 'rootpath' => (
                    is      => 'ro',
                    isa     => 'FileStore::Volume::rootpath',
                    coerce  => 1,
                    default => sub { Path::Class::Dir->new('') }
);

sub BUILD {
    my $self = shift;
    if ( !-d $self->rootpath->stringify ) {
        confess "Root path " . $self->rootpath->stringify . " does not exist";
    }
}

#alte chestii de facut
#find empty dirs
#copy file
#move file
#get directory size
#write text file
#read (slurp) text file (trebuie sa adaugam o proprietate content la File care sa nu fie plina decat la fisiere text)
#extensori pentru text, html, css si alte alea cu proprietati suplimentare
#de gasit o metoda mai putin taraneasca de dispatch la File


=head1 METODE

=cut

=head2 readfile

parametri de intrare:

filepath -  calea catre fisier
filename - numele fisierului

returneaza un obiect de tip L<FileStore::File> daca gaseste fisierul 

=cut

sub readfile {
    my $self     = shift;
    my $filepath = shift;
    my $filename = shift;
    die "not a file name" if ( fileparse($filename) ne $filename );
    return
      FileStore::File->new(
                            volume   => $self,
                            filename => $filename,
                            filepath => $filepath,
                            silent   => 1,
      );
}

=head2 writepathfile

scrie un fisier in volum si creaza calea daca nu exista

parametri de intrare:

filepath -  calea catre fisier
filename - numele fisierului

nu returneaza nimic 

=cut

sub writepathfile {
    my $self     = shift;
    my $filepath = shift;
    my $filename = shift;
    $self->check_path($filepath);
    $self->writefile( $filepath, $filename );
}

=head2 writefile

scrie un fisier in volum, in calea data, verificand daca exista calea inainte. Daca nu exista calea sau daca
exista un fisier cu acelasi nume, nu va scrie nimic

parametri de intrare:

filepath -  calea catre fisier
filename - numele fisierului

nu returneaza nimic 

=cut


#writefile nu scrie daca nu exista calea sau daca un fisier cu acelasi nume exista deja
sub writefile {
    my $self        = shift;
    my $filepath    = shift;
    my $filename    = shift;
    my $newfilepath = $self->_build_dir_path($filepath);
    if ( -d $newfilepath ) {
        my $fname = $filename; 
        confess "Filename empty" if ( $fname eq '' );
        my $newfullfilepath = $self->_build_file_path( $filepath, $fname );
        if ( -f $newfullfilepath ) {
            warn "File exists $newfullfilepath";
            return
              FileStore::File->new(
                                    volume   => $self,
                                    filename => $fname->basename,
                                    filepath => $filepath
              );
        } else {
            copy( $filename, $newfullfilepath ) or confess "Copy from " . $filename . " to " . $newfullfilepath . " failed" . $!;
            if ( -f $newfullfilepath ) {
                return
                  FileStore::File->new(
                                        volume   => $self,
                                        filename => $fname->basename,
                                        filepath => $filepath
                  );
            } else {
                confess "Copy from " . $filename . " to " . $newfullfilepath . "failed" . $!;
            }
        }
    } else {
        confess "Filepath " . $filepath . " does not exist on volume " . $self->rootpath;
    }
}

=head2 create_path

Creaza o noua cale (director) in volum. Daca noua cale exista, returneaza o eroare

parametri de intrare: calea relativa a directorului

returneaza calea relativa sub forma de string 

=cut



sub create_path {
    my $self    = shift;
    my $dirpath = shift;
    my $strpath = $self->_build_dir_path($dirpath);
    confess "Path" . $dirpath->stringify . " already exists " if ( -d $strpath->stringify );

    #daca nu...
    $strpath->mkpath;

    #    make_path( $strpath ) or confess $!;
    return $dirpath;
}

=head2 create_path

Expandeaza o cale relativa la o cale absoluta (adaugand calea principala a volumului)

parametri de intrare: calea relativa a directorului

returneaza calea absoluta sub forma de string 

=cut



sub expand_path {
    my $self    = shift;
    my $dirpath = shift;
    return $self->_build_dir_path($dirpath)->stringify;
}

=head2 expand_file_path

Expandeaza o cale relativa catre un fisier

parametri de intrare: calea relativa a fisierului

returneaza calea relativa sub forma de string 

=cut



sub expand_file_path {
    my $self     = shift;
    my $filepath = shift;
    return $self->_build_file_path( $filepath->parent, $filepath->basename )->stringify;
}


=head2 expand_filedir_path

Creaza o noua cale (director) in volum. Daca noua cale exista, returneaza o eroare

parametri de intrare: calea relativa a directorului, numele fisierului

returneaza calea relativa sub forma de string 

=cut


sub expand_filedir_path {
     my $self     = shift;
    my $filepath = shift;
    my $filename = shift;
    return $self->_build_file_path( $filepath, $filename )->stringify;
}


=head2 check_path

Returneaza o cale catre un director existent. Daca nu exista, il creaza si returneaza calea

parametri de intrare: calea relativa a directorului

=cut

sub check_path {
    my $self    = shift;
    my $dirpath = shift;
    my $strpath = $self->_build_dir_path($dirpath);
    if ( -d $strpath ) {
        return $self->_build_dir_path($dirpath);
    } else {
        $self->_build_dir_path($dirpath)->mkpath or confess $!;
    }
    return $dirpath;
}

=head2 freaddir

Returneaza lista simpla (nu obiecte) tuturor fisierelor, inclusiv subdirectoarele dintr-un director relativ

parametri de intrare: calea relativa a directorului

=cut


sub freaddir {
    my $self    = shift;
    my $dirpath = shift;
    my @children;
    my @fchildren = $self->_build_dir_path($dirpath)->children;
    foreach my $child (@fchildren) {
        push( @children, $child->relative( $self->rootpath ) );
    }
    return @children;
}

=head2 freadfiledir

Returneaza lista simpla (nu obiecte) tuturor fisierelor (fara subdirectoare) dintr-un director relativ

parametri de intrare: calea relativa a directorului

=cut

sub freadfiledir {
    my $self    = shift;
    my $dirpath = shift;
    my @children;
    my @fchildren = $self->_build_dir_path($dirpath)->children;
    foreach my $child (@fchildren) {
        unless ( $child->is_dir ) {
            push( @children, $child->relative( $self->rootpath ) );
        }
    }
    return @children;
}

=head2 readdir

Returneaza lista tuturor fisierelor dintr-un director relativ. Fiecare fisier este reprezentat de un obiect de tip L<FileStore::File>

parametri de intrare: calea relativa a directorului

=cut


sub readdir {
    my $self    = shift;
    my $dirpath = shift;
    my @children;
    my @fchildren = $self->_build_dir_path($dirpath)->children;
    foreach my $child (@fchildren) {
        if ( $child->is_dir ) {
            push( @children, $child->relative( $self->rootpath ) );
        } else {
            my $nf = FileStore::File->new(
                                           volume   => $self,
                                           filename => $child->basename,
                                           filepath => $child->relative( $self->rootpath )->parent,
            );
            push( @children,  $nf);
        }
    }
    return @children;
}

=head2 dropfile

Sterge un fisier din volum. Daca fisierul nu exista sau nu poate fi sters din alte motive, returneaza eroare

parametri de intrare: calea relativa a directorului, numele fisierului

=cut

sub dropfile {
    my $self     = shift;
    my $filepath = shift;
    my $filename = shift;
    if ( -f $self->_build_file_path( $filepath, $filename ) ) {
        unlink $self->_build_file_path( $filepath, $filename );
    } else {
        confess $self->_build_file_path( $filepath, $filename ) . "does not exist";
    }
}

=head2 dropfilesilent

Sterge un fisier din volum. Nu returneaza eroare in cazul in care fisierul nu exista sau nu poate fi sters

parametri de intrare: calea relativa a directorului, numele fisierului

=cut


sub dropfilesilent {
    my $self     = shift;
    my $filepath = shift;
    my $filename = shift;
    if ( -f $self->_build_file_path( $filepath, $filename ) ) {
        unlink $self->_build_file_path( $filepath, $filename );
    }
}

=head2 dropallbut

Sterge toate fisierele dintr-un director, cu exceptia celui furnizat ca parametru

parametri de intrare: calea relativa a directorului, numele fisierului care nu trebuie sters

In cazul in care nu poate sterge unul dintre fisiere, returneaza eroare

=cut

sub dropallbut {
    my $self     = shift;
    my $filepath = shift;
    my $filename = shift;
    my @children;
    my @fchildren = $self->_build_dir_path($filepath)->children;
    foreach my $child (@fchildren) {
        if ( $child->is_dir ) {
            push( @children, $child->relative( $self->rootpath ) );
            $child->rmtree;
        } else {
          unless ($child->basename eq $filename) {
          push( @children, $child->relative( $self->rootpath ) );
          unlink( $child->stringify ) or die "Cannot delete: $!"; 
          }
        }
    }
    return @children;
}

=head2 dropallbutsilent

Sterge toate fisierele dintr-un director, cu exceptia celui furnizat ca parametru

parametri de intrare: calea relativa a directorului, numele fisierului care nu trebuie sters

In cazul in care nu poate sterge unul dintre fisiere, nu returneaza eroare

=cut

sub dropallbutsilent {
    my $self     = shift;
    my $filepath = shift;
    my $filename = shift;
    my @children;
    my @fchildren = $self->_build_dir_path($filepath)->children;
    foreach my $child (@fchildren) {
        if ( $child->is_dir ) {
            push( @children, $child->relative( $self->rootpath ) );
            $child->rmtree;
        } else {
          unless ($child->basename eq $filename) {
          push( @children, $child->relative( $self->rootpath ) );
          unlink( $child->stringify );  
          }
        }
    }
    return @children;
}

=head2 dropfile_and_dir_empty

Sterge un fisier dintr-un director, apoi, daca directorul respectiv a ramas gol, il sterge si pe el 

parametri de intrare: calea relativa a directorului, numele fisierului care trebuie sters

=cut


sub dropfile_and_dir_empty {
    my $self     = shift;
    my $filepath = shift;
    my $filename = shift;
    $self->dropfile( $filepath, $filename );
    $self->drop_empty_dir($filepath);
}

=head2 drop_non_empty_dir

Sterge un director, chiar daca nu este gol. Daca nu poate sterge directorul (nu are permisiuni, de exemplu), returneaza eroare

parametri de intrare: calea relativa a directorului

=cut


sub drop_non_empty_dir {
    my $self    = shift;
    my $dirpath = shift;
    $self->_build_dir_path($dirpath)->rmtree or confess $!;
}

=head2 drop_empty_dir

Sterge un director, doar daca este gol. Daca nu poate sterge directorul (nu are permisiuni de exemplu) , returneaza eroare

parametri de intrare: calea relativa a directorului

=cut


sub drop_empty_dir {
    my $self    = shift;
    my $dirpath = shift;
    $self->_build_dir_path($dirpath)->remove  or confess $!;
}

=head2 isempty

Confirma (sau nu) daca un director este gol

parametri de intrare: calea relativa a directorului

=cut


sub isempty {
    my $self    = shift;
    my $dirpath = shift;
    my @it      = $self->readdir($dirpath);
    my $items   = @it;
    if ( $items == 0 ) {
        return 1;
    }
}

=head3 _build_file_path

Metoda interna, este folosita de celelalte functii

Returneaza calea integrala catre un fisier (concatenand calea relativa catre fisier si calea principala a volumului) 

parametri de intrare: calea relativa a directorului, numele fisierului

=cut


sub _build_file_path {
    my $self        = shift;
    my $filepath    = shift;
    my $filename    = shift;
#    return unless (length ($filename) > 1) ;
    my $fulldirpath = $self->_build_dir_path($filepath);
    my $newfullfilepath;
    if( defined( $filename ) && ref( $filename ) && blessed( $filename )){
   # if ($filename && $filename->can('basename')) {
         $newfullfilepath = $fulldirpath->file($filename->basename);
    } else {
         $newfullfilepath = $fulldirpath->file($filename);
    }
    return $newfullfilepath;
}

=head3 _build_file_path

Metoda interna, este folosita de celelalte functii

Returneaza calea integrala a unui director (concatenand calea relativa a acestuia si calea principala a volumului) 

parametri de intrare: calea relativa a directorului

=cut


sub _build_dir_path {
    my $self     = shift;
    my $dirpath  = shift;
    my $fullpath = $self->rootpath;
    if ( $dirpath && length( $dirpath->stringify ) > 0 ) {
        confess "Absolute directory should not get here"  if $dirpath->is_absolute;
        my @subdirs = $dirpath->dir_list;
        foreach (@subdirs) {
            $fullpath = $fullpath->subdir($_);
        }
    }
    if ($self->rootpath->subsumes($fullpath)) {
    return $fullpath->cleanup;
    } else{
     confess "Partial path given is not contained in full extenden path: ".$fullpath->stringify." does not contain ". $dirpath->stringify;
    }
}
__PACKAGE__->meta->make_immutable();



1;
