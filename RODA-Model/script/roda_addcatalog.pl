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

my %moi = (name => 'Catalog1',
           owner => 1,
           added => $dtf -> format_datetime(DateTime -> now),                   
           studies => [
           				{
           					id => 1,
                     	},
                     	{
                        	id => 5,
                     	}
                      ],                       
);

my $catalog = $roda->dbschema->resultset('Catalog')->checkcatalog( %moi );
return $catalog;
};

my $catalog;
  try {
    $catalog = $roda->dbschema->txn_do($transaction, {description => 'Inserare catalog in baza de date'});
  } catch {
    my $error = shift;
    die "Eroare la inserarea unui catalog: ".$error;
  };

       
 print "DB: ID: " . $catalog->id . " -> Nume:" . $catalog->name . " -> Owner:" . $catalog->owner . "\n";
