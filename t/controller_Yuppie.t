use strict;
use warnings;
use Test::More;


use Catalyst::Test 'RODA';
use RODA::Controller::Yuppie;

ok( request('/yuppie')->is_success, 'Request should succeed' );
done_testing();
