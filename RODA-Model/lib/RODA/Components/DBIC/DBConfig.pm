package RODA::Components::DBIC::DBConfig;

use Moose::Role;
use Data::Dumper;

#TODO: cautarea tabelului settings sau chiar setarea lui pe undeva in configul general

our $VERSION = "0.001";
$VERSION = eval $VERSION;


=head1 NUME

RODA::Components::DBIC::DBConfig - extrage automata configuratia din baza de date

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Rol pentru extragerea automata a elementelor de configurare din baza de date

=cut

has 'db_config' => (
    is  => 'rw',
    isa => 'Maybe[HashRef]',
);
requires 'connection';


after 'connection' => sub  {
  my $self = shift;
  my $dbsettings;
  my @configrs = $self->resultset('Setting')->search({})->all;
  foreach my $ss (@configrs) {
     $dbsettings->{$ss->name} = $ss->setting_value->value;
  }
  $self->db_config($dbsettings);
};

1; 