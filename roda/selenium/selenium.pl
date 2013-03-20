use IO::Socket;
use strict;
use warnings;
use Time::HiRes qw(sleep);
use Test::WWW::Selenium;
use Test::More "no_plan";
use Test::Exception;

my $testdir='.';
my $socket = 0;
while (! $socket) {
	# check if Selenium server is running
	$socket = IO::Socket::INET->new(Proto=>"tcp", PeerPort=>"4444", PeerAddr=>"localhost");
	if (! $socket) {
    	# Selenium server is not running now and will be created and executed as a child background process
    	my $pid = fork();
    	if (defined($pid) && $pid==0) {
        	exec("java", "-jar", "selenium-server-standalone-2.28.0.jar");
    	}
	}
	# here, we are in the main process and we wait until next server-check
    sleep 1;
}

#my @files = glob( $testdir . '/*.t' );
#foreach (@files) {
#    print $_ . "\n";
#    system("perl", $_);
#}

# create Selenium browser
my $sel = Test::WWW::Selenium->new( host => "localhost", 
                                    port => 4444, 
                                    browser => "*firefox", 
                                    browser_url => "http://localhost:8080/" );

# authentication: user login + logout
$sel->open_ok("/roda/");
$sel->wait_for_page_to_load_ok("30000");

$sel->click_ok("link=Login");
$sel->wait_for_page_to_load_ok("30000");
$sel->type_ok("id=j_username", "admin");
$sel->type_ok("id=j_password", "admin");
$sel->click_ok("id=proceed");
$sel->wait_for_page_to_load_ok("30000");
$sel->click_ok("link=Logout");
$sel->wait_for_page_to_load_ok("30000");


$sel->click_ok("link=Login");
$sel->wait_for_page_to_load_ok("30000");
$sel->type_ok("id=j_username", "user");
$sel->type_ok("id=j_password", "user");
$sel->click_ok("id=proceed");
$sel->wait_for_page_to_load_ok("30000");
$sel->click_ok("link=Logout");
$sel->wait_for_page_to_load_ok("30000");


$sel->click_ok("link=Login");
$sel->wait_for_page_to_load_ok("30000");
$sel->type_ok("id=j_username", "visitor");
$sel->type_ok("id=j_password", "visitor");
$sel->click_ok("id=proceed");
$sel->wait_for_page_to_load_ok("30000");
$sel->click_ok("link=Logout");
$sel->wait_for_page_to_load_ok("30000");


# org prefix
$sel->open_ok("/roda/");
$sel->wait_for_page_to_load_ok("30000");

$sel->click_ok("link=List all Org Prefixes");
$sel->wait_for_page_to_load_ok("30000");
my $nInitialRows=$sel->get_xpath_count("//tr");

$sel->click_ok("link=Create new Org Prefix");
$sel->wait_for_page_to_load_ok("30000");
$sel->type_ok("id=_name_id", "S.C.");
$sel->type_ok("id=_description_id", "societate comerciala");
$sel->click_ok("id=proceed");

$sel->click_ok("link=List all Org Prefixes");
$sel->wait_for_page_to_load_ok("30000");
my $nRows=$sel->get_xpath_count("//tr");
ok($nRows == $nInitialRows + 1, "Number of rows in table");

#done_testing();
