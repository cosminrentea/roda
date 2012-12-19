use strict;
use warnings;

use RODA;

my $app = RODA->apply_default_middlewares(RODA->psgi_app);
$app;

