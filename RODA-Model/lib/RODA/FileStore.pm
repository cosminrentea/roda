package RODA::FileStore;

use FileStore::Volume;
use Moose;

our $VERSION = "0.001";
$VERSION = eval $VERSION;


=head1 NUME

RODA::FileStore

=head1 DESCRIERE

Initializeaza volumul pe care se vor scrie fisierele statice

=cut

=head1 ATRIBUTE

 B<rootpath> - calea de baza a volumului de stocare a fisierelor statice
 B<volume> - obiect FileStore::Volume conectat la calea din rootpath
 B<name> - denumirea volumului
 
=cut


has 'volume' => ( is => 'ro', isa => 'FileStore::Volume', builder => '_build_volume', lazy => 1 );
has 'rootpath' => ( is => 'ro', isa => 'Str', required => 1 );
has 'name' => ( is => 'ro', isa => 'Str', required => 1 );


=head1 METODE

=cut

=head2 build_volume

initializeaza volumul de stocare si il ataseaza atributului volume

=cut

sub _build_volume {
my $self = shift;
return  FileStore::Volume->new(rootpath => $self->rootpath);
}

1;