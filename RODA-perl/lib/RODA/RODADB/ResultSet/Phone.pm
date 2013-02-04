use utf8;

package RODA::RODADB::ResultSet::Phone;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

sub checkphone {
    my ( $self, %params ) = @_;

   print Dumper(%params);

   my $phoners = $self->search({phone => $params{phone}, phone_type => $params{phone_type}});
   if ($phoners->count == 1) {
   		return $phoners->single;
   } 
   
   if ($params{phone} && $params{phone} ne '' ) {
   		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        my $newphoners = $self->create(
                                      {
                                        phone => $params{phone},
                                        phone_type => $params{phone_type},
                                      }
        );
        $guard->commit;
        return $newphoners;
    }
}
1;
