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

my %moi = (fname => 'Sorin',
                     mname => 'Petru',
                     lname => 'Milutinovici',
                     prefix => 'domnul',
                     addresses => [{country_name => 'România',
                                           city_name => 'Bucureşti',
                                           address1 => 'Sos. Pantelimon nr. 144',
                                           address2 => 'Bloc 102Am sc. C,. etaj 1, apt. 97',
                                           subdiv_name => 'sector',
                                           subdiv_code => '2',
                                           postal_code => '021644',
                     },{
                                           country_name => 'România',
                                           city_name => 'Bucureşti',
                                           address1 => 'Str. Preot Vasile Lucaciu nr. 117',
                                           address2 => '',
                                           subdiv_name => 'sector',
                                           subdiv_code => '3',
                                           postal_code => '030693',
                     }],
                     emails => [{email=>'sorin@contentlogic.ro', ismain => '1'},{email => 'sorin@greencore.ro'},{email => 'sorin@mediaimage.ro'}],
                     phones => [{phone => '0740236005', phone_type => 'mobile', ismain => '1'},{phone => '0216535817', phone_type => 'home'}],
                     internets => [{internet_type => 'blog', internet=>'http://sorin.greencore.ro'},{internet_type => '500px', internet => 'http://500px.com/sorinmilu'},{internet_type => 'facebook', internet => 'http://www.facebook.com/sorin.milutinovici'}],                       
);

        try {
            my $person = $schema->resultset('Person')->checkperson( %moi );
#            print "DB: ID: " . $person->id . " -> Prenume:" . $person->fname . " -> Nume:" . $person->lname . "\n";
        }
        catch {
            print "Eroare: la adaugarea persoanei $_\n";
        };


sub trim($) {
    my $string = shift;
    $string =~ s/^\s+//;
    $string =~ s/\s+$//;
    return $string;
}
