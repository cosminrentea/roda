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

#Mai intai adaug studiul
my $transaction = sub {

	my %moi = (datestart => $dtf -> format_datetime(DateTime->new(year => 2002, month => 10, day => 1)),
               insertion_status => 1,
               added_by => 1,
               added => $dtf -> format_datetime(DateTime->now),
               can_digitize => 'true',
               can_use_anonymous => 'false',  
               orgs => [{id =>'1', assoc_name=>'producator'},{id => '2', assoc_name => 'investigator principal'}], 
               persons => [{id =>'1', assoc_name=>'finantator'}],                          
               );

	my $study = $roda->dbschema->resultset('Study')->checkstudy( %moi );
    
};

my $study;

try {
   $study = $roda->dbschema->txn_do($transaction, {description => 'Inserare studiu in baza de date'});
}
catch {
    my $error = shift;
    die "Eroare la inserarea unui studiu: ".$error;
};

#Apoi descrierea studiului
#Daca exista un studiu cu acelasi title_type, title, lang_id si datestart, rezulta ca studiul exista deja 
#si nu se adauga aceasta descriere si unuia nou. In acest caz, este returnat id-ul studiului avand descrierea respectiva.
if ($study -> id) {
	$transaction = sub {
	
		my %moi = (lang => 'english',
				   title_type => 'main',	
            	   datestart => $dtf -> format_datetime(DateTime->new(year => 2002, month => 10, day => 1)),
               	   title => "Study about living in big cities",
               	   study_id => $study -> id,                                 
               	   );

		my $studydescr = $roda->dbschema->resultset('StudyDescr')->checkstudydescr( %moi );
    	return $studydescr;
	};

	my $studydescr;

	try {
   		$studydescr = $roda->dbschema->txn_do($transaction, {description => 'Inserare descriere studiu in baza de date'});
	}
	catch {
    	my $error = shift;
    	die "Eroare la inserarea unei descrieri de studiu: ".$error;
	};

	print "DB: Study ID: " . $studydescr->study_id . " -> Title type ID:" . $studydescr->title_type_id . " -> Title:" . $studydescr->title . "\n";
}
