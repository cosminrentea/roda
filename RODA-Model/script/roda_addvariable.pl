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
my $config    = file($configdir, 'rodaconfig.ini');

my $roda = RODA->new( configfile => $config->stringify, test => '1', userid=>'1');
my $dtf = $roda->dbschema->storage->datetime_parser;

my $transaction1 = sub {

	my %moi = (label => 'Categorie profesionala',
		       instance_id => 10, #sau alta instanta existenta
		       type => 1, 
		       order_in_instance => 7, 
		       type_edited_text => 0, 
		       type_edited_number => 0, 
		       type_selection => 1,
               other_statistics =>  [{name => 'medie', value => 100, description => 'valoare medie'},
                                     {name => 'suma', value => 1000, description => 'suma'},
                                    ],  
               selection_variable => [{min_count => 1, max_count => 1,
               						   items => [{item => 'scale1',
               						   	          min_item => 'item 1', min_value => '12',
               						   			  max_item => 'item10', max_value => '120', 
               						   			  units => '1',
               						   			  order_of_item_in_variable => 1,                						   	          
               						             }],
               						  }
               						 ],                                 	                          
              );

	my $variable = $roda->dbschema->resultset('Variable')->checkvariable( %moi );
};

my $transaction2 = sub {
	
	my %moi = (label => 'Preferinte politice',
		       instance_id => 10, #sau alta instanta, dar aceeasi ca in $transaction1
		       type => 1, 
		       order_in_instance => 8, 
		       type_edited_text => 0, 
		       type_edited_number => 0, 
		       type_selection => 1,  
		       selection_variable => [{min_count => 1, max_count => 1,
               						   items => [{item => 'item 1', order_of_item_in_variable => 1, 
               						   	          value => '13'},
               						   	         {item => 'item 2', order_of_item_in_variable => 2, 
               						   	          value => '1',
               						             }],
               						  }
               						 ],                           	                          
           	  );

	my $variable = $roda->dbschema->resultset('Variable')->checkvariable( %moi );
	
    
};

my $variable1;
my $variable2;

try {
   $variable1 = $roda->dbschema->txn_do($transaction1, {description => 'Inserare variabila 1 in baza de date'});
   $variable2 = $roda->dbschema->txn_do($transaction2, {description => 'Inserare variabila 2 in baza de date'});
}
catch {
    my $error = shift;
    die "Eroare la inserarea unei variabile: ".$error;
};

#Apoi asociez skips
if ($variable1 -> id && $variable2 -> id) {
	my $transaction = sub {
	
		my %moi = (variable_id => $variable1 -> id,
               	   condition => 'Salt la preferinte politice',
               	   next_variable_id => $variable2 -> id,                                 
               	   );

		my $skip = $roda->dbschema->resultset('Skip')->checkskip( %moi );
    	return $skip;
	};

	my $skip;

	try {
   		$skip = $roda->dbschema->txn_do($transaction, {description => 'Inserare salt intre 2 variabile'});
	}
	catch {
    	my $error = shift;
    	die "Eroare la inserarea unui salt intre doua variabile: ".$error;
	};

	print "DB: Variable 1 ID: " . $skip->variable_id . " -> Variable 2 ID:" . $skip->next_variable_id . "\n";
}
