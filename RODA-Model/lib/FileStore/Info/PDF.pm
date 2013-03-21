package FileStore::Info::PDF;
use Moose;
use Carp;
use PDF::API2;

our $VERSION = "0.001";
$VERSION = eval $VERSION;

=head1 NUME

FileStore::Info::PDF - Plugin pentru informatii despre fisierele de tip imagine

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Nu se apeleaza direct, se apeleaza automat de catre L<FileStore::File> cand se citeste fisierul
utilizeaza modului L<PDF::API2>

Returneaza urmatoarele proprietati:

=over

=item pages 
- numarul de pagini al pdf-ului

=item isEncrypted
- determina daca fisierul este criptat sau nu

=item title
- titlul pdf-ului (cel din metadate)

=item subject
- rezumatul pdf-ului (cel din metadate)

=item author
- autorul pdf-ului (cel din metadate)

=item creationdate
- data la care a fost creat fisierul

=item creator
- aplicatia folosita pentru constructia fisierul

=item producer
- motorul software utilizat de aplicatia care a creat fisierul

=item lastmod
- data ultimei modificari a fisierul

=item keywords
- cuvintele cheie ale fisierului

=back

=head1 ATRIBUTE

=over

=item  
C<wfile> - obiect de tip FileStore::File 

=back

=cut
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
