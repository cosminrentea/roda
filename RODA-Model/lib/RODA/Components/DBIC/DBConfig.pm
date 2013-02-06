package RODA::Components::DBIC::DBConfig;

use Moose::Role;
use Data::Dumper;


our $VERSION = "0.001";
$VERSION = eval $VERSION;


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