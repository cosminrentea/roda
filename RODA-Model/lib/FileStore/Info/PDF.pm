package FileStore::Info::PDF;
use Moose;
use Carp;
use PDF::API2;

our $VERSION = "0.001";
$VERSION = eval $VERSION;


has 'wfile' => ( is => 'ro', isa => 'FileStore::File', required => 1 );

sub BUILD {
    my $self = shift;
    if ( !-f $self->wfile->fullfilename ) {
        confess "File " . $self->wfile->fullfilename . " does not exist";
    }
    if ( !$self->wfile->mimetype =~ m/pdf/i ) {
        confess "File " . $self->wfile->fullfilename . " is not a pdf file: " . $self->wfile->mimetype;
    }
}

sub get_properties {
    my $self = shift;
    my $pdf = PDF::API2->open( $self->wfile->fullfilename->stringify );

    my %info = $pdf->info;
    return (
             {
               pages        => $pdf->pages,
               isEncrypted  => $pdf->isEncrypted,
               title        => $info{"Title"},
               subject      => $info{"Subject"},
               author       => $info{"Author"},
               creationdate => $info{'CreationDate'},
               creator      => $info{"Creator"},
               producer     => $info{"Producer"},
               lastmod      => $info{"ModDate"},
               keywords     => $info{"Keywords"},
             }
    );

}
1;
