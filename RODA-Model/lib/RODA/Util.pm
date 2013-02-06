package RODA::Util;

use strict;
use warnings;

our $VERSION = "0.001";
$VERSION = eval $VERSION;


use Exporter qw( import );

our @EXPORT_OK
    = qw( string_is_empty trim );

sub string_is_empty {
    return 1 if !defined $_[0] || !length $_[0];
    return 0;
}

sub trim($) {
    my $string = shift;
    $string =~ s/^\s+//;
    $string =~ s/\s+$//;
    return $string;
}
