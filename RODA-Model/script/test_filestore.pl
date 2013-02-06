#!/usr/bin/perl
use Test::More tests => 63;
use Test::Exception;

#TODO: de verificat ca toate alea merg si cu filename simplu si cu filename ca obiect Path::Class::File

use FindBin;
use lib "$FindBin::Bin/../lib";
use FileStore::Volume;
use Path::Class;
use Data::Dumper;


print "\n Testarea configuratiei initiale\n ";

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

my $initvolume = $projectdir->subdir('uploads','filestore_test');
ok( -d $initvolume,    'Directorul root pentru volum - '.$initvolume );

my $testfile1 = file( $sourcedir, 'sigla.gif' );
my $testfile2 = file( $sourcedir, 'report.pdf' );

ok( -f $testfile1,    'Primul fisier sursa pentru test - '.$testfile1 );
ok( -f $testfile2,    'Al doilea fisier sursa pentru test - '.$testfile2 );

print "\n Testarea initializarii filestore\n ";


my $fs = FileStore::Volume->new(rootpath => $initvolume->stringify);

isa_ok($fs, 'FileStore::Volume', 'Verificarea incarcarii clasei principale cu argument de tip string');

isa_ok($fs->rootpath, 'Path::Class::Dir', 'rootpath este corect');

cmp_ok($fs->rootpath->stringify, 'eq', $initvolume->stringify, 'Rootpath setat corect?'.$fs->rootpath->stringify);

####test create_path

print "\n Testarea unui nou path\n ";

my $newdir = $fs->create_path(dir('ok','for','me'));

isa_ok($newdir, 'Path::Class::Dir', 'Returneaza tipul corect de obiect');

my $createdpath = $fs->rootpath;
my @subdirs = $newdir->dir_list;
 
foreach (@subdirs) {
     $createdpath = $createdpath->subdir($_);
}

ok( -d $createdpath->stringify,    'Directorul a fost creat - '.$createdpath->stringify );

#testam expand_path

cmp_ok($fs->expand_path($newdir), 'eq', $createdpath->stringify, 'Expand_path pare sa functioneze');

ok( -d $fs->expand_path($newdir),    'Directorul a fost creat - '.$fs->expand_path($newdir) );


print "\n Testarea scrierii fisierelor in FileStore\n ";


#test writefile with path not existent

dies_ok { $fs->writefile(dir('notok/for/me'), $testfile1)}  'Testez refuzul de a scrie intr-o cale inexistenta' ;

#test writefile with path existent

my $isnfile = $fs->writefile(dir('ok/for/me'), $testfile1);

isa_ok($isnfile, 'FileStore::File', 'obiectul fisier este corect');

ok( -f $isnfile->fullfilename,    'Fisierul a fost copiat - '.$isnfile->fullfilename );

my $isnfile2 = $fs->writefile(dir('ok/for/me'), $testfile2);

isa_ok($isnfile2, 'FileStore::File', 'obiectul fisier este corect');

ok( -f $isnfile2->fullfilename,    'Fisierul a fost copiat - '.$isnfile2->fullfilename );

#test writepathfile

my $file = $fs->writepathfile(dir('my','new','directory'), $testfile1);

isa_ok($file, 'FileStore::File', 'obiectul fisier este corect');

ok( -f $file->fullfilename,    'Fisierul a fost copiat - '.$file->fullfilename );

my $readfile = $fs->readfile($file->filepath, $testfile1->basename);

isa_ok($readfile, 'FileStore::File', 'obiectul fisier citit cu readfile este corect');

#test check_path

my $existingpath = $fs->check_path(dir('my','new','directory'));

isa_ok($existingpath, 'Path::Class::Dir', 'obiectul returnat de check_path este corect');

my $nonexistingpath = $fs->check_path(dir('my','newew','directory'));

isa_ok($nonexistingpath, 'Path::Class::Dir', 'obiectul returnat de check_path este corect');

ok( -d $fs->expand_path($nonexistingpath),    'Path-ul a fost creat - '.$nonexistingpath->stringify );

print "\nTest freaddir cu directoare\n";

my @rootchildren = $fs->freaddir();

isa_ok($rootchildren[0], 'Path::Class::Dir', 'Primul director returnat de freaddir este Path::Class::Dir');

ok( -d $fs->expand_path($rootchildren[0]),    'Path-ul a fost returnat corect - '.$rootchildren[0]->stringify );

isa_ok($rootchildren[1], 'Path::Class::Dir', 'Al doilea director returnat de freaddir este Path::Class::Dir');

ok( -d $fs->expand_path($rootchildren[1]),    'Path-ul a fost returnat corect - '.$rootchildren[1]->stringify );


print "\nTest freaddir cu fisiere\n";
#testam freaddir cu fisiere
# in on/for/me avem doua fisiere
#freaddir returneaza Path::Class::File, nu FileStore::Volume::File

my @files = $fs->freaddir(dir('ok', 'for', 'me'));

isa_ok($files[0], 'Path::Class::File', 'Primul fisier returnat de freaddir este Path::Class::File');

ok( -f $fs->expand_file_path($files[0]),    'Fisierul a fost returnat corect - '.$files[0]->stringify );

isa_ok($files[1], 'Path::Class::File', 'Al doilea fisier returnat de freaddir este Path::Class::File');

ok( -f $fs->expand_file_path($files[1]),    'Fisierull a fost returnat corect - '.$files[1]->stringify );


##facem doua directoare in directorul cu fisiere si vedem daca freaddir returneaza corect

print "\nTest freaddir cu continut amestecat (directoare si fisiere)\n";

my $newdirt1 = $fs->create_path(dir('ok','for','me','dude'));

my $newdirt2 = $fs->create_path(dir('ok','for','me','sir'));

my @mixed = $fs->freaddir(dir('ok', 'for', 'me'));

my $files=0;
my $dirs=0;

foreach my $mx (@mixed) {
	if ($mx->isa('Path::Class::File')) {
			$files++;
			ok (($mx->basename eq $testfile1->basename || $mx->basename eq $testfile2->basename), 'Fisier corect - '. $mx->basename);
	} elsif ($mx->isa('Path::Class::Dir')) {
			$dirs++;
			ok (($mx->stringify eq dir('ok','for','me','dude')->stringify || $mx->stringify eq dir('ok','for','me','sir')->stringify ), 'Director corect: - '.$mx->stringify);
	}
}
ok (($files == 2 && $dirs == 2), 'freaddir returneaza doua fisiere si 2 directoare');


#test freadfiledir - trebuie sa returneze obiecte de tip FileStore::File (doar fisierele)

print "\nTest freadfiledir - returneaza doar fisierele dintr-un director cu continut mixt";

my @fileread = $fs->freadfiledir(dir('ok', 'for', 'me'));

my $nritems = @fileread;

cmp_ok($nritems, '==', 2, 'Freadfiledir returneaza doar 2 intrari');

isa_ok($fileread[0], 'Path::Class::File', 'Primul fisier returnat de freaddir este Path::Class::File');

ok( -f $fs->expand_file_path($fileread[0]),    'Fisierul a fost returnat corect - '.$fileread[0]->stringify );

isa_ok($fileread[1], 'Path::Class::File', 'Al doilea fisier returnat de freaddir este Path::Class::File');

ok( -f $fs->expand_file_path($fileread[1]),    'Fisierull a fost returnat corect - '.$fileread[1]->stringify );

print "\nTest readdir - returneaza tot dar expandeaza fisierele la FileStore::File\n";

my @fullfileread = $fs->readdir(dir('ok', 'for', 'me'));

my $dfiles=0;
my $ddirs=0;

foreach my $mx (@fullfileread) {
	if ($mx->isa('FileStore::File')) {
			$dfiles++;
			ok (($mx->filename eq $testfile1->basename || $mx->filename eq $testfile2->basename), 'Fisier corect - '. $mx->filename);
			if ($mx->filename eq $testfile1->basename) {
				cmp_ok($mx->properties->{width}, '==', 148, 'Latimea corect identificata: 148');
			}
			if ($mx->filename eq $testfile1->basename) {
				cmp_ok($mx->properties->{pages}, '==', 7, 'Numarul de pagini corect identificat: 7');
			}
			
	} elsif ($mx->isa('Path::Class::Dir')) {
			$ddirs++;
			ok (($mx->stringify eq dir('ok','for','me','dude')->stringify || $mx->stringify eq dir('ok','for','me','sir')->stringify ), 'Director corect: - '.$mx->stringify);
	}
}
ok (($dfiles == 2 && $ddirs == 2), 'freaddir returneaza doua fisiere si 2 directoare');



print "\nTestarea proprietatilor fisierelor\n";


print "\nTestam dropfile\n";

dies_ok { $fs->dropfile(dir('my','new','directory'), 'dummyfile')}  'Refuza sa stearga un fisier inexistent' ;

dies_ok { $fs->dropfile(dir('my','new','directorate'), $testfile1->basename)}  'Refuza sa stearga un fisier intr-o cale inexistenta' ;

$fs->dropfile(dir('my','new','directory'), $testfile1->basename);

ok( !-f $fs->expand_filedir_path(dir('my','new','directory'), $testfile1->basename),    'Fisierul a fost sters - ' );


print "\nTestam dropfilesilent\n";


my $repfile = $fs->writepathfile(dir('my','new','directory'), $testfile1);

lives_ok {$fs->dropfilesilent(dir('my','new','directory'), 'dummy file');}  'Refuza sa stearga un fisier inexistent dar fara scandal' ;

lives_ok { $fs->dropfilesilent(dir('my','new','directory'), $testfile1->basename)}  'Refuza sa stearga un fisier intr-o cale inexistenta fara scandal' ;

$fs->dropfilesilent(dir('my','new','directory'), $testfile1->basename);

ok( !-f $fs->expand_filedir_path(dir('my','new','directory'), $testfile1->basename),    'Fisierul a fost sters - ' );


print "\nTestam dropallbut trebuie sa stearga tot ce e acolo mai putin fisierul cu pricina\n";

my @deleted = $fs->dropallbut(dir('ok/for/me'), $testfile1->basename);

#acum in directorul ok for me trebuie sa fie doar testfile1

my @allbtest = $fs->freadfiledir(dir('ok', 'for', 'me'));

my $allbtestnr = @allbtest; 

cmp_ok($allbtestnr, '==', 1, 'Directorul contine un singur fisier');

cmp_ok($allbtest[0]->basename, 'eq', $testfile1->basename, 'Fisierul ramas e cel care trebuie: '.$testfile1->basename);


print "\nTestam dropfile_and_dir_empty trebuie sa stearga fisierul si daca directorul in care se afla ramane gol, sa-l stearga si pe ala\n";

$fs->dropfile_and_dir_empty(dir('ok/for/me'), $testfile1->basename);

ok( !-f $fs->expand_filedir_path(dir('ok','for','me'), $testfile1->basename),    'Fisierul a fost sters - ' );

ok( !-d $fs->expand_path(dir('ok','for','me')),    'Directorul ramas gol a fost sters - ' );

print "\nTestam drop non empty dir\n";

my $rfile1 = $fs->writepathfile(dir('my','new','directory'), $testfile1);
my $rfile2 = $fs->writepathfile(dir('my','new','directory'), $testfile2);

$fs->drop_non_empty_dir(dir('my','new'));

ok( !-d $fs->expand_path(dir('my','new')),    'Directorul a fost sters ' );

print "\nTestam drop_empty_dir\n";

dies_ok { $fs->drop_empty_dir(dir('my','newew'))}  'Refuza sa stearga un fisier director care nu e gol' ;

$fs->drop_empty_dir(dir('my','newew', 'directory'));

ok( !-d $fs->expand_path(dir('my','newew', 'directory')),    'Directorul a fost sters ' );

print "\nTestam isempty\n";

cmp_ok($fs->isempty(dir('my','newew')), '==', 1, 'Directorul este gol');

print "\nFacem curatenie\n";

$fs->drop_empty_dir(dir('my','newew'));
$fs->drop_empty_dir(dir('my'));
$fs->drop_empty_dir(dir('ok','for'));
$fs->drop_empty_dir(dir('ok'));

