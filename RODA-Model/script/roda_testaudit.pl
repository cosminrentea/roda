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

my $changes = $roda->dbschema->get_changes({table=>'person', id=>'8'});

my @ch = $changes->all;

foreach my $cc (@ch) {
   print $cc->action->changeset->timestamp->datetime." - ".$cc->action->type." - ".$cc->field->name ." - ".$cc->new_value ."\n";
 
}

my $person = $roda->dbschema->resultset('Person')->find('8');

my $chg = $person->audit_log;

my @ch = $chg->all;

foreach my $cc (@ch) {
   print $cc->action->changeset->timestamp->datetime." - ".$cc->action->type." - ".$cc->field->name ." - ".$cc->new_value ."\n";
 
}

