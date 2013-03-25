#!/usr/bin/perl 

use strict;
use FindBin;
use lib "$FindBin::Bin/../lib";
use Getopt::Long;
use Pod::Usage;
use Path::Class;
use Pod::RodaLaTeX::RodaBook;



my $scriptdir  = dir($FindBin::Bin);
my $projectdir = $scriptdir->parent;
print "Project directory: " . $projectdir . "\n\n";

my $configdir = $projectdir->subdir('config');
my $config     = file($configdir, 'rodapodbook.yml');
my $confFile = $config->stringify;

my $extratexdir = $projectdir->subdir('docs')->subdir('extratex');

pod2usage("$0: configuration file $confFile not found.")
    unless ( -e $confFile );

my $parser = Pod::RodaLaTeX::RodaBook->new (extratex => $extratexdir);

$parser->configure_from_file( $confFile );

$parser->parse( );

my $finaldir = $extratexdir->stringify; 

print "PDF Latex run\n";

system("cd $finaldir; pdflatex roda.tex >de_sters.log");
system("cd $finaldir; pdflatex roda.tex >de_sters.log");