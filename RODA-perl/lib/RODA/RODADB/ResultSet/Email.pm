use utf8;

package RODA::RODADB::ResultSet::Email;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

sub checkemail {
    my ( $self, %params ) = @_;

   print Dumper(%params);

   my $emailrs = $self->search({email => $params{email}});
   if ($emailrs->count == 1) {
   		return $emailrs->single;
   } 
   
   if ($params{email} && $params{email} ne '' ) {
   		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        my $newemailrs = $self->create(
                                      {
                                        email => $params{email},
                                      }
        );
        $guard->commit;
        return $newemailrs;
    }
}
1;
