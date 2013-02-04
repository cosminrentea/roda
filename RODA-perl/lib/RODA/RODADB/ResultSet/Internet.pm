use utf8;

package RODA::RODADB::ResultSet::Internet;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

sub checkinternet {
    my ( $self, %params ) = @_;

   print Dumper(%params);

   my $internetrs = $self->search({internet => $params{internet}, internet_type => $params{internet_type}});
   if ($internetrs->count == 1) {
   		return $internetrs->single;
   } 
   
   if ($params{internet} && $params{internet} ne '' ) {
   		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        my $newinternetrs = $self->create(
                                      {
                                        internet => $params{internet},
                                        internet_type => $params{internet_type},
                                      }
        );
        $guard->commit;
        return $newinternetrs;
    }
}
1;
