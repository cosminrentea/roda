use IO::Socket;
use strict;
use warnings;
use Time::HiRes qw(sleep);
use Test::WWW::Selenium;
use Test::More "no_plan";
use Test::Exception;

# ! Web Application must be already started at: http://localhost:8080/roda

# ! Selenium Server must be already started on local port 4444


# create browser through Selenium Server

# MacOSX setup:
my $sel = Test::WWW::Selenium->new( host => "localhost", 
                                    port => 4444, 
                                    browser => "*firefox /opt/local/lib/firefox-x11/firefox-bin", 
                                    browser_url => "http://localhost:8080/roda/" );

# Linux setup:
#my $sel = Test::WWW::Selenium->new( host => "localhost", 
#                                    port => 4444, 
#                                    browser => "*firefox /usr/bin/firefox", 
#                                    browser_url => "http://localhost:8080/roda/" );
                                  
my $timeout = "10000";

# authentication: user login + logout
$sel->open_ok("roda/");
$sel->wait_for_page_to_load_ok($timeout);

# admin
$sel->click_ok("link=Login");
$sel->wait_for_page_to_load_ok($timeout);
$sel->type_ok("id=j_username", "admin");
$sel->type_ok("id=j_password", "admin");
$sel->click_ok("id=proceed");
$sel->wait_for_page_to_load_ok($timeout);
$sel->click_ok("link=Logout");
$sel->wait_for_page_to_load_ok($timeout);

# user
$sel->click_ok("link=Login");
$sel->wait_for_page_to_load_ok($timeout);
$sel->type_ok("id=j_username", "user");
$sel->type_ok("id=j_password", "user");
$sel->click_ok("id=proceed");
$sel->wait_for_page_to_load_ok($timeout);
$sel->click_ok("link=Logout");
$sel->wait_for_page_to_load_ok($timeout);

# visitor
$sel->click_ok("link=Login");
$sel->wait_for_page_to_load_ok($timeout);
$sel->type_ok("id=j_username", "visitor");
$sel->type_ok("id=j_password", "visitor");
$sel->click_ok("id=proceed");
$sel->wait_for_page_to_load_ok($timeout);
$sel->click_ok("link=Logout");
$sel->wait_for_page_to_load_ok($timeout);


#done_testing();
