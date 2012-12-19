package RODA::Controller::Yuppie;
use Moose;
use namespace::autoclean;

BEGIN {extends 'Catalyst::Controller'; }

=head1 NAME

RODA::Controller::Yuppie - Catalyst Controller

=head1 DESCRIPTION

Catalyst Controller.

=head1 METHODS

=cut


=head2 index

=cut

sub index :Path :Args(0) {
    my ( $self, $c ) = @_;
    $c->stash->{message} = 'Hei, eu sunt RODA si ma simt bine';
    $c->stash->{template} = 'yuppie/index.tt2';
}


=head1 AUTHOR

Sorin Milutinovici

=head1 LICENSE

This library is free software. You can redistribute it and/or modify
it under the same terms as Perl itself.

=cut

__PACKAGE__->meta->make_immutable;

1;
