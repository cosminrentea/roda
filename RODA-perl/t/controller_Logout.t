use strict;
use warnings;
use Test::More;


use Catalyst::Test 'RODA';
use RODA::Controller::Logout;

ok( request('/logout')->is_success, 'Request should succeed' );
done_testing();
