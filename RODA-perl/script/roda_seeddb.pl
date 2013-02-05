#!/usr/bin/perl
use utf8;
use FindBin;
use DateTime;
use lib "$FindBin::Bin/../lib";
use RODA::RODADB;
use Text::CSV::Auto;
use Encode qw(decode);
use Data::Dumper;
use Try::Tiny;
use Config::Properties;

open my $fh, '<', 'database.properties'
    or die "Unable to open database.properties file";

my $prop = Config::Properties->new();
$prop->load($fh);

my $schema = RODA::RODADB->connect($prop->getProperty('roda_dbname_host'),$prop->getProperty('roda_user'),$prop->getProperty('roda_password'), { pg_enable_utf8 => 1 } );
$schema->storage->debug(1);

print "Language\n";

#language
if ( -f "$FindBin::Bin/../csv/language.csv" ) {
    my $langcsv = Text::CSV::Auto->new("$FindBin::Bin/../csv/language.csv");
    my $rows    = $langcsv->slurp();
    foreach my $row (@$rows) {
        try {
            my $lng = $schema->resultset('Lang')->checklanguage( id => $row->{id}, nume => $row->{nume} );
            print "DB: ID: " . $lng->id . " -> Nume:" . $lng->name . "\n";
        }
        catch {
            print "Eroare: la import language $_\n";
        };
    }
} else {
    print "Language file not found .... $FindBin::Bin/../csv/language.csv\n";
}

print "Prefixe";

if ( -f "$FindBin::Bin/../csv/prefixe.csv" ) {
    my $prefcsv = Text::CSV::Auto->new("$FindBin::Bin/../csv/prefixe.csv");
    my $rows    = $prefcsv->slurp();
    foreach my $row (@$rows) {
        try {
            my $pref = $schema->resultset('Prefix')->checkprefix( id => $row->{id}, name => $row->{nume} );
            print "DB: ID: " . $pref->id . " -> Nume:" . $pref->name . "\n";
        }
        catch {
            print "Eroare: la import prefix $_\n";
        };
    }
} else {
    print "Prefix file not found .... $FindBin::Bin/../csv/prefixe.csv\n";
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
} else {
    print "Nu gasesc fisierul $FindBin::Bin/../csv/country.csv";
}

print "Region types\n";

#fisierul region_types are id in el degeaba pentru ca altfel idiotul de Text::CSV nu isi da seama ca e csv
if ( -f "$FindBin::Bin/../csv/region_type.csv" ) {
    my $rtcsv = Text::CSV::Auto->new("$FindBin::Bin/../csv/region_type.csv");
    my $rows  = $rtcsv->slurp();
    foreach my $row (@$rows) {
        try {
            my $rtrs = $schema->resultset('Regiontype')->find_or_create( name => lc( $row->{name} ) );
            if ($rtrs) {
                print "DB: ID: " . $rtrs->id . " -> Nume:" . $rtrs->name . "\n";
            }
        }
        catch {
            print "Eroare: la import country $_\n";
        };
    }
} else {
    print "Nu gasesc fisierul $FindBin::Bin/../csv/region_type.csv";
}

print "Regions\n";

#fisierul region_types are id in el degeaba pentru ca altfel idiotul de Text::CSV nu isi da seama ca e csv
if ( -f "$FindBin::Bin/../csv/region.csv" ) {
    my $rgcsv = Text::CSV::Auto->new("$FindBin::Bin/../csv/region.csv");
    my $rows  = $rgcsv->slurp();
    foreach my $row (@$rows) {
        try {
            my $rtrs = $schema->resultset('Region')->checkregion(
                                                                  name             => trim( lc( $row->{name} ) ),
                                                                  region_code      => trim( lc( $row->{region_code} ) ),
                                                                  rcode_name       => trim( lc( $row->{rcode_name} ) ),
                                                                  region_type_name => trim( lc( $row->{region_type} ) ),
                                                                  country_name     => trim( lc( $row->{country_name} ) ),
            );
            if ($rtrs) {
                print "DB: ID: " . $rtrs->id . " -> Nume:" . $rtrs->name . "\n";
            }
        }
        catch {
            print "Eroare: la import regiune $_\n";
        };
    }
} else {
    print "Nu gasesc fisierul $FindBin::Bin/../csv/region.csv";
}

#city
print "City\n";

if ( -f "$FindBin::Bin/../csv/orase_ro_rr.csv" ) {
    my $rgcsv = Text::CSV::Auto->new("$FindBin::Bin/../csv/orase_ro_rr.csv");
    my $rows  = $rgcsv->slurp();
    foreach my $row (@$rows) {

        #        #aici e putin mai complicat
        #        #intai, un pic de curatenie
        if ( $row->{nume} =~ m/municipiul/i ) {
            $row->{prefix} = 'municipiul';
            $row->{nume} =~ s/municipiul//i;
        }
        if ( $row->{nume} =~ m/oraş/i ) {
            $row->{prefix} = 'oraş';
            $row->{nume} =~ s/oraş//i;
        }

        #        #regiunile si judetele le explicitam aici sa nu incurcam lucrurile
        $row->{regiune} = 'nord-est'           if $row->{regiune} eq '1';
        $row->{regiune} = 'sud-est'            if $row->{regiune} eq '2';
        $row->{regiune} = 'sud'                if $row->{regiune} eq '3';
        $row->{regiune} = 'sud-vest'           if $row->{regiune} eq '4';
        $row->{regiune} = 'vest'               if $row->{regiune} eq '5';
        $row->{regiune} = 'nord-vest'          if $row->{regiune} eq '6';
        $row->{regiune} = 'centru'             if $row->{regiune} eq '7';
        $row->{regiune} = 'bucureşti-ilfov'   if $row->{regiune} eq '8';
        $row->{judet}   = 'alba'               if $row->{judet}   eq '1';
        $row->{judet}   = 'arad'               if $row->{judet}   eq '2';
        $row->{judet}   = 'argeş'             if $row->{judet}   eq '3';
        $row->{judet}   = 'bacău'             if $row->{judet}   eq '4';
        $row->{judet}   = 'bihor'              if $row->{judet}   eq '5';
        $row->{judet}   = 'bistriţa-nãsãud' if $row->{judet}   eq '6';
        $row->{judet}   = 'botoşani'          if $row->{judet}   eq '7';
        $row->{judet}   = 'braşov'            if $row->{judet}   eq '8';
        $row->{judet}   = 'brăila'            if $row->{judet}   eq '9';
        $row->{judet}   = 'buzău'             if $row->{judet}   eq '10';
        $row->{judet}   = 'caraş-severin'     if $row->{judet}   eq '11';
        $row->{judet}   = 'călăraşi'        if $row->{judet}   eq '51';
        $row->{judet}   = 'cluj'               if $row->{judet}   eq '12';
        $row->{judet}   = 'constanţa'         if $row->{judet}   eq '13';
        $row->{judet}   = 'covasna'            if $row->{judet}   eq '14';
        $row->{judet}   = 'dâmboviţa'        if $row->{judet}   eq '15';
        $row->{judet}   = 'dolj'               if $row->{judet}   eq '16';
        $row->{judet}   = 'galaţi'            if $row->{judet}   eq '17';
        $row->{judet}   = 'giurgiu'            if $row->{judet}   eq '52';
        $row->{judet}   = 'gorj'               if $row->{judet}   eq '18';
        $row->{judet}   = 'harghita'           if $row->{judet}   eq '19';
        $row->{judet}   = 'hunedoara'          if $row->{judet}   eq '20';
        $row->{judet}   = 'ialomiţa'          if $row->{judet}   eq '21';
        $row->{judet}   = 'iaşi'              if $row->{judet}   eq '22';
        $row->{judet}   = 'ilfov'              if $row->{judet}   eq '23';
        $row->{judet}   = 'maramureş'         if $row->{judet}   eq '24';
        $row->{judet}   = 'mehedinţi'         if $row->{judet}   eq '25';
        $row->{judet}   = 'mureş'             if $row->{judet}   eq '26';
        $row->{judet}   = 'neamţ'             if $row->{judet}   eq '27';
        $row->{judet}   = 'olt'                if $row->{judet}   eq '28';
        $row->{judet}   = 'prahova'            if $row->{judet}   eq '29';
        $row->{judet}   = 'satu mare'          if $row->{judet}   eq '30';
        $row->{judet}   = 'sălaj'             if $row->{judet}   eq '31';
        $row->{judet}   = 'sibiu'              if $row->{judet}   eq '32';
        $row->{judet}   = 'suceava'            if $row->{judet}   eq '33';
        $row->{judet}   = 'teleorman'          if $row->{judet}   eq '34';
        $row->{judet}   = 'timiş'             if $row->{judet}   eq '35';
        $row->{judet}   = 'tulcea'             if $row->{judet}   eq '36';
        $row->{judet}   = 'vaslui'             if $row->{judet}   eq '37';
        $row->{judet}   = 'vâlcea'            if $row->{judet}   eq '38';
        $row->{judet}   = 'vrancea'            if $row->{judet}   eq '39';
        $row->{judet}   = 'bucureşti'         if $row->{judet}   eq '40';
        try {
            my $cityrs = $schema->resultset('City')->checkcity(
                                                                name          => trim( lc( $row->{nume} ) ),
                                                                country_id    => 'ro',
                                                                city_code     => $row->{cod},
                                                                city_code_name    => $row->{cod_name},
                                                                prefix        => $row->{prefix},
                                                                city_type     => trim( $row->{tip} ),
                                                                city_type_system  => $row->{cod_name},
                                                                city_code_sup => $row->{sirsup},
            );
            if ($cityrs) {
                print "DB: ID: " . $cityrs->id . " -> Nume:" . $cityrs->name . "\n";

                #atasam regiunile
                $cityrs->attach_region(
                                        name             => $row->{regiune},
                                        country_id       => 'ro',
                                        region_type_name => 'regiune de dezvoltare'
                );
                $cityrs->attach_region(
                                        name             => $row->{judet},
                                        country_id       => 'ro',
                                        region_type_name => 'județ'
                );
            }
        }
        catch {
            print "Eroare: la import oras $_\n";
        };
    }
}

sub trim($) {
    my $string = shift;
    $string =~ s/^\s+//;
    $string =~ s/\s+$//;
    return $string;
}
