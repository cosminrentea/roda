package FileStore::Info::Image;


use Moose;
use Carp;
use Image::Info qw(image_info dim);

our $VERSION = "0.001";
$VERSION = eval $VERSION;


has 'wfile'=> (is => 'ro', isa => 'FileStore::File', required=>1 );

  sub BUILD {
      my $self = shift;
      if (!-f $self->wfile->fullfilename) {
          confess "File ".$self->wfile->fullfilename." does not exist";
      }
      if (! $self->wfile->mimetype =~ m/image/i) {
          confess "File ".$self->wfile->fullfilename." is not an image: ".$self->wfile->mimetype ;
      }

      
  }

sub get_properties {
 my $self = shift;
 my $info =  image_info($self->wfile->fullfilename->stringify);
 return ({width => $info->{width},
              height => $info->{height},
              color_type => $info->{color_type}
 });
 
}



1;