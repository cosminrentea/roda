#!/usr/bin/perl
use utf8;
use FindBin;
use DateTime;
use lib "$FindBin::Bin/../lib";
use RODA;
use Text::CSV::Auto;
use Encode qw(decode);
use Data::Dumper;
use Try::Tiny;
use RODA::Util qw(trim);
use Path::Class;

my $scriptdir  = dir($FindBin::Bin);
my $projectdir = $scriptdir->parent;
print "Project directory: " . $projectdir . "\n\n";

my $configdir = $projectdir->subdir('config');
my $config     = file($configdir, 'rodaconfig.ini');

my $roda = RODA->new( configfile => $config->stringify, test => '1', userid=>'1');
my $dtf = $roda->dbschema->storage->datetime_parser;

my $transaction = sub {

	my %moi = (org_1_id => 1,
               org_2_id => 2,
               relation_type => 'afiliere',
               datestart => $dtf -> format_datetime(DateTime->new(year => 2002, month => 10, day => 1)),
               details => 'Institutie afiliata universitatii'                   
               );

	my $orgrelation = $roda->dbschema->resultset('OrgRelation')->checkorgrelation( %moi );
    return $orgrelation;
};

my $orgrelation;

try {
   $orgrelation = $roda->dbschema->txn_do($transaction, {description => 'Inserare asociere intre doua organizatii in baza de date'});
}
catch {
    my $error = shift;
    die "Eroare la inserarea unei asocieri intre doua organizatii: ".$error;
};

print "DB: Org 1 ID: " . $orgrelation->org_1_id . " -> Org 2 ID:" . $orgrelation->org_2_id . " -> Relation ID:" . $orgrelation->org_relation_type_id . "\n";
