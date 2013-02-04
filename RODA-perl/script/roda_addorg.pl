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

try {
	my $org = $schema->resultset('Org')->checkorg( %moi );
} 
catch {
	print "Eroare: la adaugarea organizatiei $_\n";
};


sub trim($) {
    my $string = shift;
    $string =~ s/^\s+//;
    $string =~ s/\s+$//;
    return $string;
}
