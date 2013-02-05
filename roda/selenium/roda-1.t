use strict;
use warnings;
use Time::HiRes qw(sleep);
use Test::WWW::Selenium;
use Test::More "no_plan";
use Test::Exception;

my $sel = Test::WWW::Selenium->new( host => "localhost", 
                                    port => 4444, 
                                    browser => "*chrome", 
                                    browser_url => "http://localhost:8080/" );

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

$sel->click_ok("link=Find by Name Like");
$sel->wait_for_page_to_load_ok("30000");
$sel->type_ok("id=j_username", "admin");
$sel->type_ok("id=j_password", "RodaAdor");
$sel->click_ok("id=proceed");
$sel->wait_for_page_to_load_ok("30000");
$sel->click_ok("id=_name_id");
$sel->type_ok("id=_name_id", "e");
$sel->click_ok("id=proceed");
$sel->wait_for_page_to_load_ok("30000");
my $nSearch=$sel->get_xpath_count("//tr");
ok($nSearch == 20+2, "Number of rows in table");

$sel->click_ok("link=Logout");
$sel->wait_for_page_to_load_ok("30000");

#done_testing();
