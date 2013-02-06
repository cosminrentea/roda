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


=head1 NAME

FileStore::Volume
=cut

=head1 VERSION

version 0.01
=cut

=head1 DESCRIERE

Sistem de manipulare a directoarelor si fisierelor. Deocamdata stocate intr-un volum de tip filesystem
Ideea este ca nici una dintre operatiile cu fisiere facute prin intermediul acestui modul (cu exceptia initializarii) sa nu
opereze cu cai absolute.  Toate operatiile cu directoare si fisiere, cu cateva mici exceptii se fac utilizand obiecte de
tipul Path::Class

=head1 SYNOPSIS

  use FileStore::Volume;
  use Path::Class;

  my $fs = FileStore::Volume->new(rootpath => '/my/root/path'); #nu folosim Path::Class aici, ci un simplu string care insa va fi automat convertit in Path::Class

  my $newdir = $fs->create_path(dir('ok','for','me'));
  
  my $isnfile = $fs->writefile(dir('ok/for/me'), $testfile1); 
  
  my $file = $fs->writepathfile(dir('my','new','directory'), $testfile1); 


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

sub writepathfile {
    my $self     = shift;
    my $filepath = shift;
    my $filename = shift;
    $self->check_path($filepath);
    $self->writefile( $filepath, $filename );
}

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

sub expand_path {
    my $self    = shift;
    my $dirpath = shift;
    return $self->_build_dir_path($dirpath)->stringify;
}

sub expand_file_path {
    my $self     = shift;
    my $filepath = shift;
    return $self->_build_file_path( $filepath->parent, $filepath->basename )->stringify;
}

sub expand_filedir_path {
     my $self     = shift;
    my $filepath = shift;
    my $filename = shift;
    return $self->_build_file_path( $filepath, $filename )->stringify;
}

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

sub dropfilesilent {
    my $self     = shift;
    my $filepath = shift;
    my $filename = shift;
    if ( -f $self->_build_file_path( $filepath, $filename ) ) {
        unlink $self->_build_file_path( $filepath, $filename );
    }
}

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

sub dropfile_and_dir_empty {
    my $self     = shift;
    my $filepath = shift;
    my $filename = shift;
    $self->dropfile( $filepath, $filename );
    $self->drop_empty_dir($filepath);
}

sub drop_non_empty_dir {
    my $self    = shift;
    my $dirpath = shift;
    $self->_build_dir_path($dirpath)->rmtree or confess $!;
}

sub drop_empty_dir {
    my $self    = shift;
    my $dirpath = shift;
    $self->_build_dir_path($dirpath)->remove  or confess $!;
}

sub isempty {
    my $self    = shift;
    my $dirpath = shift;
    my @it      = $self->readdir($dirpath);
    my $items   = @it;
    if ( $items == 0 ) {
        return 1;
    }
}



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
