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

#$schema->storage->debug(1);

my $transaction = sub {
	
my %moi = (name => 'RODA',
           fullname => 'Romanian Data Archive',      
           addresses => [{country_name => 'România',
                          city_name => 'Bucureşti',
                          address1 => 'Bd. Schitu Magureanu nr. 5',
                          address2 => '',
                          subdiv_name => 'sector',
                          subdiv_code => '5',
                          postal_code => '050001',
                          },
                          {country_name => 'România',
                           city_name => 'Bucureşti',
                           address1 => 'Sos. Panduri nr. 345',
                           address2 => '',
                           subdiv_name => 'sector',
                           subdiv_code => '6',
                           postal_code => '060677',
                     }],
                     emails => [{email=>'roda@unibuc.ro', ismain => '1'},{email => 'roda@gmail.com'}],
                     phones => [{phone => '0777900800', phone_type => 'mobile'},{phone => '0213146789'}],
                     internets => [{internet_type => 'blog', internet=>'http://roda.greencore.ro'},{internet_type => 'google', internet => 'http://google.docs'}],                       
          );


	my $org = $roda->dbschema->resultset('Org')->checkorg( %moi );
	return $org;
};

my $org;
  try {
    $org = $roda->dbschema->txn_do($transaction, {description => 'Inserare organizatie in baza de date'});
  } catch {
    my $error = shift;
    die "Eroare la inserarea organizatiei: ".$error;
  };


print "DB: ID: " . $org->id . " -> Fullname:" . $org->fullname . "\n"; 


my $transaction2 = sub {
	
my %moi = (name => 'UNIBUC',
           fullname => 'University of Bucharest',      
           addresses => [{country_name => 'România',
                          city_name => 'Bucureşti',
                          address1 => 'Bd. Mihail Kogalniceanu nr. 52',
                          address2 => '',
                          subdiv_name => 'sector',
                          subdiv_code => '5',
                          postal_code => '050002',
                          },
                          ],
                     emails => [{email=>'contact@unibuc.ro', ismain => '1'},{email => 'unibuc@gmail.com'}],
                     phones => [{phone => '0213145555'}],
                     internets => [{internet_type => 'website', internet => 'http://www.unibuc.docs'}],                       
          );


	my $org = $roda->dbschema->resultset('Org')->checkorg( %moi );
	return $org;
};

my $org2;
  try {
    $org2 = $roda->dbschema->txn_do($transaction2, {description => 'Inserare organizatie in baza de date'});
  } catch {
    my $error = shift;
    die "Eroare la inserarea organizatiei: ".$error;
  };


print "DB: ID: " . $org2->id . " -> Fullname:" . $org2->fullname . "\n"; 
