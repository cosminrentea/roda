use utf8;

package RODA::RODADB::ResultSet::Value;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::Value - metode specifice prelucrarii valorilor corespunzatoare elemetelor de selectie

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip valoare a unui element de selectie.

=cut

=head1 METODE

=cut

=head2 checkvalue

checkconcept adauga valoarea corespunzatoare unui element de selectie (preluata prin valori ale parametrilor de intrare) in baza de date.
Unicitatea este verificata la nivelul elementelor de selectie (item).

Parametrii de intrare:

=over 

=item C<item_id>
- cheia primara a elementului din tabelul de elemente de selectie (item); totodata, este cheie primara si in tabelul ce inregistreaza valorile acestor elemente (value).

=item C<value>
- valoarea elementului de selectie

=back


Criterii de unicitate:

=over

=item
- N/A

=back

=cut


sub checkvalue {
   my ( $self, %params ) = @_;
   
   if ($params{item_id} && $params{item_id} ne '' && $params{value} && $params{value} ne '' ) {
   	   
#		my $where = 'upper(me.value) = ?';
#        my @bind  = ($params{value});
#		
#    	my $itemexist = $self->result_source->schema()->resultset('Value')
#    							->search_literal($where, @bind,
#                                                 {
#                                                 	join => 'item',
#       												 '+select' => [ 'item.name' ],
#                                                     '+as'     => [ 'name' ],                                                                           	                                                                          
#                                                 }
#                                                );
#
#    	if ( $itemexist->count >= 1 ) {
#    		if ($itemexist -> first -> get_column('name') eq $params{name}) {
#        		return $itemexist -> first;
#    		}
#    	}   	
 
    	my $insertvalue;
   
    	$insertvalue->{item_id} = $params{item_id};
    	$insertvalue->{value} = $params{value};
   	  
   		my $guard = $self->result_source->schema()->txn_scope_guard;
 
        my $newvaluers = $self->create($insertvalue);
        $guard->commit;
        return $insertvalue;
    }
#    else {   	
#    	if ((!$params{item_id} || $params{item_id} eq '') && $params{item} && $params{item} ne '') {
#			my $newitem = $self->result_source->schema()->resultset('Item')->checkitem(item => $params{item}, value => $params{value});
#			return $newitem;
#			my $insertvalue;
#   
#    		$insertvalue->{item_id} = $newitem -> {item_id};
#    		$insertvalue->{value} = $params{value};
#   	  
#   			my $guard = $self->result_source->schema()->txn_scope_guard;
# 
#        	my $newvaluers = $self->create($insertvalue);
#        	$guard->commit;
#        	return $insertvalue;    	
#    	} 
#    }
}
1;
