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

#Mai intai adaug instanta
my $transaction = sub {

	my %moi = (datestart => $dtf -> format_datetime(DateTime->new(year => 2012, month => 10, day => 1)),
	           study_id => '1',
               insertion_status => 1,
               added_by => 1,
               added => $dtf -> format_datetime(DateTime->now),
               version => '1',
               raw_data => 'false',
               raw_metadata => 'false',
               unit_analysis => 'individ',
               time_meth => 'met temp 1',  
               orgs => [{id =>'1', assoc_name=>'producator'},{id => '2', assoc_name => 'investigator principal'}], 
               persons => [{id =>'1', assoc_name=>'finantator'}],                          
               );

	my $instance = $roda->dbschema->resultset('Instance')->checkinstance( %moi );
    
};

my $instance;

try {
   $instance = $roda->dbschema->txn_do($transaction, {description => 'Inserare instanta in baza de date'});
}
catch {
    my $error = shift;
    die "Eroare la inserarea unei instante: ".$error;
};

#Apoi descrierea instantei
#Daca exista o instanta cu acelasi instance_id si datestart, rezulta ca instanta exista deja 
#si nu se adauga aceasta descriere si uneia noi. In acest caz, este returnat id-ul instantei avand descrierea respectiva.
if ($instance -> id) {
	$transaction = sub {
	
		my %moi = (lang => 'français', 
		#'english',
               	   title => "Instance of the study about living in big cities",
               	   instance_id => $instance -> id,                                 
               	   );

		my $instancedescr = $roda->dbschema->resultset('InstanceDescr')->checkinstancedescr( %moi );
    	return $instancedescr;
	};

	my $instancedescr;

	try {
   		$instancedescr = $roda->dbschema->txn_do($transaction, {description => 'Inserare descriere instanta in baza de date'});
	}
	catch {
    	my $error = shift;
    	die "Eroare la inserarea unei descrieri de instanta: ".$error;
	};

	print "DB: Instance ID: " . $instancedescr->instance_id . " -> Title:" . $instancedescr->title . "\n";
}
