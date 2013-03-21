package RODA::Util;

use strict;
use warnings;

our $VERSION = "0.001";
$VERSION = eval $VERSION;


use Exporter qw( import );

our @EXPORT_OK
    = qw( string_is_empty trim );
    
    
=head1 NUME

RODA::Util - Functii utile care nu fac parte din reteaua de metode RODA

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Aici se vor acumula diferite functii considerate utile, cu utilizare repetata. 

=cut    

=head1 FUNCTII

=cut

=head2 string_is_empty

determina daca un string este gol, nedefinit, sau oricum altfel incat sa nu putem face nimic cu continutul sau. 
paramentru de intrare: un string

  if (string_is_empty($string)) {}

=cut


sub string_is_empty {
    return 1 if !defined $_[0] || !length $_[0];
    return 0;
}

=head2 trim

inlatura (daca este cazul) spatiile care preced sau urmeaza caracterele utile dintr-un string.
parametru de intrare este un string

  print trim($string);

=cut


sub trim($) {
    my $string = shift;
    $string =~ s/^\s+//;
    $string =~ s/\s+$//;
    return $string;
}
