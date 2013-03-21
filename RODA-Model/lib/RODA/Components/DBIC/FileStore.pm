package RODA::Components::DBIC::FileStore;


use strict;
use warnings;
use File::Spec  ();
use File::Path  ();
use File::Copy  ();
use Path::Class ();
use File::Basename(qw/fileparse/);
use Data::Dumper::Concise;

our $VERSION = "0.001";
$VERSION = eval $VERSION;


=head1 NUME

RODA::Components::DBIC::FileStore;

=cut

=head1 VERSIUNE

version 0.04

=cut

=head1 FUNCTIONARE

La incarcarea schemei (instantierea Result-ului care foloseste componentul) se ruleaza metoda register_column pentru fiecare coloana. 
Register_column este un soi de rol avant la lettre (L<DBIx::Class> nu suporta L<Moose> la nivelul asta inca) care injecteaza (sub forma de referinte catre subrutine anonime)
doua sectiuni care se vor rula la citirea/respectiv scrierea informatiei cu pricina in baza de date:

=over
 
=item inflate 
- se ocupa de convertirea valorii citite din baza de date (in cazul de fata un string) intr-un obiect (in cazul de fata un obiect de tipul FileStore::File)
 
=item deflate 
- se presupune ca primeste un obiect si il transforma intr-un string sau numar intreg sau altceva care poate fi stocat in baza de date

=back

Combinatia inflate/deflate pleaca oarecum de la premisa ca atat operatia de umflare cat si cea de dezumflare se fac de la acelasi tip de obiect. Aici nu e cazul. Mai mult, 
aici e nevoie de operatii suplimentare, de aceea deflate/inflate nu pot fi simetrice. De aceea deflate nu are functionalitati, nu face decat sa returneze valoarea

Ramane inflate, care are rolul de a cauta fisierul corespunzator inregistrarii din tabel si sa-l "umfle" transformandu-l intr-un obiect de tipul L<FileStore::File>

Operatiile propriu-zise de copiere, stergere, etc se fac prin  supraincarcarea metodelor existente deja in L<DBIx::Class::Row> insert, update si delete.  

De mentionat ca nu seteaza singur coloanele de tip mime type si md5. Pentru aceasta va trebui sa facem triggere in clasele result corespunzatoare tabelelor, pentru a nu stabili dinainte numele acelor coloane in clasa aceasta. 

Stocarea fisierelor corespunzatoare coloanelor de tip fs_column se face in urmatoarea cale (relativa la filestore) I<nume_tabel/coloane_pk/nume_coloana/nume_fisier.extensie>

=over

=item nume_tabel - 
numele tabelului din baza de date caruia ii apartine coloana

=item coloane_pk
- director alcatuit din concatenarea valorilor curente ale coloanelor care alcatuiesc cheia primara a tabelului. Daca exista o singura coloana, atunci aceea va constitui directorul

=item nume_coloana
- numele coloanei in care se stocheaza fisierul. Este prezent pentru a permite existenta mai multor coloane de tip fs_column in acelasi tabel

=item nume.fisier.extensie
- numele fisierului trimis catre baza de date, fara cale, cu extensia lui. 

=back

Exemplu: daca tabelul se numeste persoane, are o cheie primara simpla numita id iar coloana in care se stocheaza o imagine numita "photo.jpg" se numeste "profile_photo", calea va fi: I<persoane/13/profile_photo/photo.jpg>, in care 13 este id-ul randului in tabel. 

=cut


=head1 TODO

copy, Testare cu mai multe coloane de tip fisier, Testare cu pk multiplu

=cut

=head1 METHODS

=cut

=head2 update

Incarca metoda update. Algoritmul este urmatorul: stocheaza valoarea veche a coloanei modificate, declanseaza scrierea in baza de date prin apelul metodei urmatoare in lant apoi inlocuieste fisierul atasat randului cu cel nou. 

=cut

sub update {
    my $self = shift;
    my %uplfiles;
    foreach my $col ( keys %{ $self->result_source->columns_info } ) {
        if ( $self->result_source->columns_info->{$col}->{is_fs_column} ) {
            if ( $self->is_column_changed($col) ) {
                if ( length( $self->get_column($col) ) > 0 ) {
                    $uplfiles{$col} = $self->get_column($col);
                    $self->set_column( $col => $self->get_column($col)->basename );
                }
            }
        }
    }
    $self->next::method;
    my $col_data = $self->{_column_data};
    my @prim     = $self->primary_columns;
    my @dirkey;
    foreach my $pc (@prim) {
        push( @dirkey, $self->get_column($pc) );
    }
    foreach my $col ( keys %{ $self->result_source->columns_info } ) {
        if ( $self->result_source->columns_info->{$col}->{is_fs_column} ) {
            my $pathc = Path::Class::dir( $self->table, join( '-', @dirkey ), $col );
            if ( length( $self->get_column($col) ) > 0 ) {
                if ( $uplfiles{$col} ne '' ) {
                    my $filestr = $self->result_source->schema->filestore->volume->writepathfile( $pathc, $uplfiles{$col});
                    my $eraseold = $self->result_source->schema->filestore->volume->dropallbut( $pathc, $uplfiles{$col}->basename );
                }
            } else {
                my $eraseold = $self->result_source->schema->filestore->volume->drop_non_empty_dir( $pathc);
            }
        }
    }
    delete $self->{_inflated_column}{$_} for grep { $self->result_source->column_info($_)->{is_fs_column} }
      keys %$col_data;
}


=head2 register_column

se ruleaza la incarcarea clasei superioare (a celei corespunzatoare tabelului).  injecteaza (sub forma de referinte catre subrutine anonime)
doua sectiuni (inflate si deflate) care se vor rula la citirea/respectiv scrierea informatiei cu pricina in baza de date

=cut

sub register_column {
    my ( $self, $column, $info, @rest ) = @_;
    $self->next::method( $column, $info, @rest );
    return unless defined( $info->{is_fs_column} );
    $self->inflate_column(
        $column => {
            inflate => sub {
                my ( $value, $obj ) = @_;
                my @prim = $obj->primary_columns;
                my @dirkey;
                foreach my $pc (@prim) {
                    push( @dirkey, $obj->get_column($pc) );
                }
                my $pathc = Path::Class::dir($obj->table, join( '-', @dirkey ), $column );
                if ( $value ne '' ) {
                    my $filestr = $obj->result_source->schema->filestore->volume->readfile( $pathc, $value );
                    if ($filestr) {
                        return $filestr;
                    } else {
                        return undef;
                    }
                } else {  
                }
            },
            deflate => sub {
                my ( $value, $obj ) = @_;
                #we don't need no stinky deflator
                return $value;
            },
        }
    );
}

=head2 delete

Incarca metoda delete. Algoritmul este urmatorul, stocheaza valoarea veche coloanei sterse, sterge fisierul corespunzator, apoi declanseaza stergerea acesteia din baza de date prin apelul metodei urmatoare in lant. 

=cut

sub delete {
    my ( $self, @rest ) = @_;

    my @prim = $self->primary_columns;
    my @dirkey;
    foreach my $pc (@prim) {
        push( @dirkey, $self->get_column($pc) );
    }
    for my $column ( $self->columns ) {
        my $column_info = $self->result_source->column_info($column);
        if ( $column_info->{is_fs_column} ) {
            my $pathc = Path::Class::dir($self->table, join( '-', @dirkey ), $column );
            my $eraseold = $self->result_source->schema->filestore->volume->dropfile_and_dir_empty( $pathc, $self->get_column($column) );
        }
    }
    return $self->next::method(@rest);
}

=head2 insert

Stocheaza valoarea coloanei (coloanelor) de tip fs_column (care la insert contin intreaga cale catre fisierul care se doreste atasat), inlocuieste informatia din coloane cu numele fisierului fara cale, declanseaza inserarea in baza de date a informatiei, preia valoarea cheie primare a randului nou creat (pentru eventualitatea in care exista auto-increment), apoi copiaza fisierul in sistem

=cut

sub insert {
    my $self = shift;
    my %uplfiles;
    foreach my $col ( keys %{ $self->result_source->columns_info } ) {
        if ( $self->result_source->columns_info->{$col}->{is_fs_column} ) {
            if ( length( $self->get_column($col) ) ne "" ) {
                $uplfiles{$col} = $self->get_column($col);
                $self->set_column( $col => $self->get_column($col)->basename );
            }
        }
    }
    $self->next::method;
    my @prim = $self->primary_columns;
    my @dirkey;
    foreach my $pc (@prim) {
        push( @dirkey, $self->get_column($pc) );
    }
    foreach my $col ( keys %{ $self->result_source->columns_info } ) {
        if ( $self->result_source->columns_info->{$col}->{is_fs_column} ) {
            if ( length( $self->get_column($col) ) ne "" ) {
                my $filename = $self->get_column($col);
                my $pathc = Path::Class::dir($self->table, join( '-', @dirkey ), $col );
                my $filestr = $self->result_source->schema->filestore->volume->writepathfile( $pathc, $uplfiles{$col} );
                if ($filestr) {
                    delete $self->{_inflated_column}{$col};
                }
            }
        }
    }
    return $self;
}

=head2 DESTROY

Se executa in situatia in care exista coloane care au fost modificate dar nu au fost inregistrate in baza de date, dintr-un motiv sau altul. Verifica daca acestea au atasate fisiere in sistem si daca da, le sterge.

=cut

sub DESTROY {
    my $self = shift;
    return if $self->in_storage;

    # If fs columns were deflated, but the row was never stored, we need to delete the
    # backing files.
    while ( my ( $col, $data ) = each %{ $self->{_column_data} } ) {
        my $column_info = $self->result_source->column_info($col);
        if ( $column_info->{is_fs_column} && defined $data ) {
            my $accessor = $column_info->{accessor} || $col;
        }
    }
}


1;
