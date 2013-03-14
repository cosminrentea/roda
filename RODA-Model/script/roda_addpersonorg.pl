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

my $transaction = sub {

	my %moi = (person_id => 1,
               org_id => 1,
               role => 'project manager'                                 
               );

	my $personorg = $roda->dbschema->resultset('PersonOrg')->checkpersonorg( %moi );
    return $personorg;
};

my $personorg;

try {
   $personorg = $roda->dbschema->txn_do($transaction, {description => 'Inserare asociere intre persoana si organizatie in baza de date'});
}
catch {
    my $error = shift;
    die "Eroare la inserarea unei asocieri intre persoana si organizatie: ".$error;
};

print "DB: Person ID: " . $personorg->person_id . " -> Org ID:" . $personorg->org_id . " -> Role ID:" . $personorg->role_id . "\n";
