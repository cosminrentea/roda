#!/usr/bin/perl
use FindBin;
use DateTime;
use lib "$FindBin::Bin/../lib";
use RODA::RODADB;
use Text::CSV::Auto;
use Encode qw(decode);
use Data::Dumper;
use Try::Tiny;
my $schema = RODA::RODADB->connect( 'dbi:Pg:dbname=roda;host=193.228.153.170', 'roda2012', '2012roda', { pg_enable_utf8 => 1 } );
print "Language\n";

#language
if ( -f "$FindBin::Bin/../csv/language.csv" ) {
    my $langcsv = Text::CSV::Auto->new("$FindBin::Bin/../csv/language.csv");
    my $rows    = $langcsv->slurp();
    foreach my $row (@$rows) {
        try {
            my $lng = $schema->resultset('Language')->checklanguage( id => $row->{id}, nume => $row->{nume} );
            print "DB: ID: " . $lng->id . " -> Nume:" . $lng->nume . "\n";
        }
        catch {
            print "Eroare: la import language $_\n";
        };
    }
} else {
    print "Language file not found .... $FindBin::Bin/../csv/language.csv\n";
}
print "Country\n";

#country
if ( -f "$FindBin::Bin/../csv/country.csv" ) {
    my $countrycsv = Text::CSV::Auto->new("$FindBin::Bin/../csv/country.csv");
    my $rows       = $countrycsv->slurp();
    foreach my $row (@$rows) {
        try {
            my $countryrs = $schema->resultset('Country')->checkcountry( id => $row->{id}, name => $row->{name}, alpha3 => $row->{alpha3} );
            if ($countryrs) {
                print "DB: ID: " . $countryrs->id . " -> Nume:" . $countryrs->name . "\n";
            }
        }
        catch {
            print "Eroare: la import country $_\n";
        };
    }
}
print "Region types\n";

#fisierul region_types are id in el degeaba pentru ca altfel idiotul de Text::CSV nu isi da seama ca e csv

if ( -f "$FindBin::Bin/../csv/region_type.csv" ) {
    my $rtcsv = Text::CSV::Auto->new("$FindBin::Bin/../csv/region_type.csv");
    my $rows       = $rtcsv->slurp();
    foreach my $row (@$rows) {
        try {
            my $rtrs = $schema->resultset('RegionType')->find_or_create( name => lc($row->{name}));
            if ($rtrs) {
                print "DB: ID: " . $rtrs->id . " -> Nume:" . $rtrs->name . "\n";
            }
        }
        catch {
            print "Eroare: la import country $_\n";
        };
    }
}







