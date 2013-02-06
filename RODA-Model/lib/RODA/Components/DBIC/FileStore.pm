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


=head1 NAME

RODA::Components::DBIC::FileStore;

=cut

=head1 

version 0.01

=cut

=head1 FUNCTIONARE

La incarcarea schemei (instantierea Result-ului care foloseste componentul) se ruleaza register_column pentru fiecare coloana. 
Register_column este un soi de rol avant la lettre (DBIx::Class nu suporta Moose la nivelul asta inca) care injecteaza (sub forma de referinte catre subrutine anonime)
doua sectiuni care se vor rula la citirea/respectiv scrierea informatiei cu pricina in baza de date: 
    - inflate - se ocupa de convertirea valorii citite din baza de date (in cazul de fata un string) intr-un obiect (in cazul de fata un obiect de tipul FileStore::File)
    - deflate - se presupune ca primeste un obiect si il transforma intr-un string sau numar intreg sau altceva care poate fi stocat in baza de date

Combinatia inflate/deflate pleaca oarecum de la premisa ca atat operatia de umflare cat si cea de dezumflare se fac de la acelasi tip de obiect. Aici nu e cazul. Mai mult, 
aici e nevoie de operatii suplimentare, de aceea deflate/inflate nu pot fi simetrice. De aceea renuntam la deflate (trebuie sa fie acolo altfel face scandal) dar nu face decat sa returneze valoarea

Ramane inflate, care are rolul de a cauta fisierul corespunzator inregistrarii din tabel si sa-l "umfle" transformandu-l intr-un obiect de tipul FileStore::File

Operatiile propriu-zise de copiere, stergere, etc se fac prin  supraincarcarea metodelor existente deja in DBIx::Class::ROW: insert, update si delete.  

De mentionat ca nu seteaza singur coloanele de tip mime type si md5. Pentru aceasta va trebui sa facem triggere in clasele result corespunzatoare tabelelor, nu vreau sa
incui numele acelor coloane in clasa aceasta 



=cut


=head1 TODO

Ceva refactoring
copy
Testare cu mai multe coloane de tip fisier
Testare cu pk multiplu

=cut

=head1 METHODS

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
