package RODA::View::TT;

use strict;
use warnings;

use base 'Catalyst::View::TT';

__PACKAGE__->config(
    TEMPLATE_EXTENSION => '.tt2',
    INCLUDE_PATH => [
        RODA->path_to( 'root' ),
    ],
    ENCODING     => 'utf-8',
    render_die => 1,
    debug => 'all',
);

=head1 NAME

RODA::View::TT - TT View for RODA

=head1 DESCRIPTION

TT View for RODA.

=head1 SEE ALSO

L<RODA>

=head1 AUTHOR

Sorin Milutinovici

=head1 LICENSE

This library is free software. You can redistribute it and/or modify
it under the same terms as Perl itself.

=cut

1;
