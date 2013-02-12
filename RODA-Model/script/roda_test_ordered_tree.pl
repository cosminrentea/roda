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
use Test::More tests => 27;

my $scriptdir  = dir($FindBin::Bin);
my $projectdir = $scriptdir->parent;
print "Project directory: " . $projectdir . "\n\n";
my $configdir = $projectdir->subdir('config');
my $config    = file( $configdir, 'rodaconfig.ini' );
my $roda      = RODA->new( configfile => $config->stringify, test => '1');

isa_ok($roda, 'RODA', 'Verificarea incarcarii clasei principale');

#adaugam un nou root

my $newroot = $roda->dbschema->resultset('Catalog')->create({name => 'TestRoot', description => 'Acesta nu trebuie sa existe in momentul rularii textului, asa ca, if you see me, you fucked up smth...', owner => 1});

isa_ok($newroot, 'RODA::RODADB::Result::Catalog', 'Verificarea obiectului returnat de create');

my $children  = $newroot->children();


print "Children: ". $children."\n";
 
cmp_ok($children, '==', 0, 'Numarul de copii ai noului root este 0');

print "Pentru a construi un copil, trebuie creat in gol\n";

my $new = $roda->dbschema->resultset('Catalog')->create({ name=>'TestChild', description => 'Test child lets see' , owner => 1});

isa_ok($new, 'RODA::RODADB::Result::Catalog', 'Verificarea obiectului returnat de create');

$newroot->append_child($new);

my $childr2  = $newroot->children();

cmp_ok($childr2, '==', 1, 'Numarul de copii ai noului root este acum 1');

my @children = $newroot->children();

isa_ok($children[0], 'RODA::RODADB::Result::Catalog', 'Aschia n-a sarit prea departe de trunchi');


cmp_ok($children[0]->name, 'eq', 'TestChild', 'Copilul are numele corect');

isa_ok($children[0]->parent, 'RODA::RODADB::Result::Catalog', 'Copilul este recunoscut de parinte');

cmp_ok($children[0]->parent->name, 'eq', 'TestRoot', 'Copilul este recunoscut de parintele corect');


my $juju = $roda->dbschema->resultset('Catalog')->create({ name=>'juju', description => 'juju lets see' , owner => 1});

isa_ok($juju, 'RODA::RODADB::Result::Catalog', 'Verificarea obiectului returnat de create');

$new->attach_child($juju);

my $childr3  = $new->children();

cmp_ok($childr3, '==', 1, 'Copilul are copil');

isa_ok($juju->parent, 'RODA::RODADB::Result::Catalog', 'Parintele seamana la specie cu copilul');

cmp_ok($juju->parent->name, 'eq', 'TestChild' , 'Copilul stie care e parintele');

#mai bagam doi frati pentru $new


my $abel = $roda->dbschema->resultset('Catalog')->create({ name=>'Abel', description => 'abel description' ,  owner => 1});
my $cain = $roda->dbschema->resultset('Catalog')->create({ name=>'Cain', description => 'cain description'  , owner => 1});

$newroot->attach_child($abel);
$abel->attach_sibling($cain);

my $allchildren = $newroot->children;

isa_ok($allchildren, 'DBIx::Class::ResultSet', 'In context scalar, children returneaza un resultset');

cmp_ok($allchildren->count, '==', 3 , 'Bunicul are 3 copii');

#sa vedem daca obtinem fratii lui abel

my $abelsibl = $abel->siblings;

isa_ok($abelsibl, 'DBIx::Class::ResultSet', 'In context scalar, siblings returneaza un resultset');

cmp_ok($abelsibl->count, '==', 2 , 'Abel trebuie sa aiba doi frati acum');

#acum, abel si cain sunt frunze, TestChild e ramura, juju e frunza, NewRoot e parinte

cmp_ok($abel->is_leaf, '==', 1 , 'Abel e frunza');

cmp_ok($cain->is_leaf, '==', 1 , 'Cain e frunza');

cmp_ok($juju->is_leaf, '==', 1 , 'JUJU e frunza - n-are copii');

cmp_ok($new->is_branch, '==', 1 , 'NEW e ramura - are si copii si parinte');

cmp_ok($newroot->is_root, '==', 1 , 'NewRoot e root - n-are parinti');

#acu sa vedem stramosii lui juju 

my @ancestors = $juju->ancestors;

cmp_ok(@ancestors, '==', 2 , 'juju are doi stramosi');

cmp_ok($ancestors[0]->name, 'eq', 'TestChild' , 'Primul stramos este TestChild');
cmp_ok($ancestors[1]->name, 'eq', 'TestRoot' , 'Al doilea stramos este TestRoot (tatal lui TestChild)');

cmp_ok($newroot->has_descendant( $juju->id ), '==', 1 , $newroot->name." stie ca pe undeva printre descendentii lui se gaseste ".$juju->name);

$newroot->delete;

my $count = $roda->dbschema->resultset('Catalog')->search({})->count;

cmp_ok($count, '==', 0 , 'In tabel nu a mai ramas nimic');








