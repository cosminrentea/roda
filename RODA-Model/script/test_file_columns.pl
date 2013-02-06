#!/usr/bin/perl

use Test::More tests => 27;

use FindBin;
use lib "$FindBin::Bin/../lib";
use RODA;
use Path::Class;
use Data::Dumper;


my $scriptdir  = dir($FindBin::Bin);
my $projectdir = $scriptdir->parent;
print "Project directory: " . $projectdir . "\n";

my $configdir = $projectdir->subdir('config');
my $config     = file($configdir, 'rodaconfig.ini');

ok( -f $config,       'Caut fisierul de configurare - '.$config );

my $sourcedir = $projectdir->subdir( 'uploads', 'test' );
ok( -d $sourcedir,    'Directorul sursa pentru fisierele de test - '.$sourcedir );

my $fakedir = $projectdir->subdir( 'uploads', 'nothere' );
ok( !-d $fakedir,    'Test daca recunosc un director fals - '.$fakedir );

my $initvolume = $projectdir->subdir('uploads');
ok( -d $initvolume,    'Directorul root pentru volum - '.$initvolume );

my $testfile1 = file( $sourcedir, 'sigla.gif' );
my $testfile2 = file( $sourcedir, 'report.pdf' );

ok( -f $testfile1,    'Primul fisier sursa pentru test - '.$testfile1 );
ok( -f $testfile2,    'Al doilea fisier sursa pentru test - '.$testfile2 );


my $roda = RODA->new( configfile => $config->stringify, test => '1');


isa_ok($roda, 'RODA', 'Verificarea incarcarii clasei principale');

isa_ok($roda->dbschema, 'RODA::RODADB', 'Verificarea incarcarii clasei ORM-ului');

isa_ok($roda->rootconfig, 'RODA::Config', 'Verificarea incarcarii configurarii principale');

isa_ok($roda->dbschema->filestore, 'RODA::FileStore', 'Verificarea incarcarii clasei FileStore');

my $root = $roda->dbschema->resultset('CmsFolder')->search({name => 'Root'})->first;

isa_ok($root, 'RODA::RODADB::Result::CmsFolder', 'Am gasit folderul Root');

my $newdoc = $roda->dbschema->resultset('CmsFile')->create(
                                                             {
                                                               label    => 'test file 1',
                                                               cms_folder_id  => $root->id,
                                                               filename => $testfile1,
                                                             }
);

isa_ok($newdoc, 'RODA::RODADB::Result::CmsFile', 'Resultul CMsFile a fost creat corect?');

if ( $newdoc->id > 0 ) {

cmp_ok($newdoc->id, '>', 0, 'Id-ul returnat este mai mare decat 0'.$newdoc->id);

isa_ok($newdoc->filename, 'FileStore::File', 'filename returneaza un filestore... ok');


ok( -f $newdoc->filename->fullfilename->stringify,    'Testez copierea fisierului in filestorage - '.$newdoc->filename->fullfilename->stringify );
    
    my $dbfile =  $newdoc->filename->filename;
##################################################################

    print "----------------------------------------update alta coloana \n";

    $newdoc->label('updated label');
    $newdoc->update();

    isa_ok($newdoc->filename, 'FileStore::File', 'filename returneaza un filestore... ok');

    cmp_ok($dbfile, 'eq', $newdoc->filename->filename, 'Fisierul din baza de date a ramas acelasi?');

    ok( -f $newdoc->filename->fullfilename->stringify,    'Fisierul a ramas corect in filestorage - '.$newdoc->filename->fullfilename->stringify );


####################################################################################

    print "----------------------------------------update coloana de file \n";
    $newdoc->filename($testfile2);
    $newdoc->update();



    isa_ok($newdoc->filename, 'FileStore::File', 'filename returneaza un filestore... ok');
    cmp_ok($newdoc->filename->filename, 'eq', $testfile2->basename, 'Fisierul a fost modificat corect in baza de date?');

    ok( -f $newdoc->filename->fullfilename->stringify,    'Fisierul a fost copiat in storage - '.$newdoc->filename->fullfilename->stringify );

    my $updatedfile = $newdoc->filename->fullfilename->stringify; 

     print "----------------------------------------update coloana de file la zero \n";

    $newdoc->filename("");
    $newdoc->update();

    cmp_ok($newdoc->filename, 'eq', '', 'Fisierul din baza de date a devenit nul?');
    ok( !-f $updatedfile,    'Fisierul a fost sters corect din filestorage - ');



    print "----------------------------------------update coloana de file de la zero\n";
    $newdoc->filename($testfile2);
    $newdoc->update();


    isa_ok($newdoc->filename, 'FileStore::File', 'filename returneaza un filestore... ok');
    cmp_ok($newdoc->filename->filename, 'eq', $testfile2->basename, 'Fisierul a fost modificat corect in baza de date?');

    ok( -f $newdoc->filename->fullfilename->stringify,    'Fisierul a fost copiat in storage - '.$newdoc->filename->fullfilename->stringify );


  print "----------------------------------------delete resultset \n";

  my $existfile = $newdoc->filename->fullfilename->stringify;
  my $existid = $newdoc->id; 


 $newdoc->delete();

   my $delcheck = $roda->dbschema->resultset('CmsFile')->search({id => $existid})->count;

   cmp_ok($delcheck, '==', 0, 'Inregistrarea a fost corect stearsa din baza de date?');
   ok( !-f $existfile,    'Fisierul a fost sters corect din filestorage - ');

}



