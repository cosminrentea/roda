use utf8;

package RODA::RODADB::ResultSet::InstanceDescr;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;
use Try::Tiny;

=head1 NUME

RODA::RODADB::ResultSet::InstanceDescr - metode specifice pentru manipularea descrierilor de instante

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de tip descriere de instanta.

=cut

=head1 METODE

=cut

=head2 checkinstancedescr

checkinstancedescr verifica existenta unei descrieri de instanta (preluata prin valori ale parametrilor de intrare) in baza de date; in caz afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce descrierea in baza de date si apoi returneaza obiectul corespunzator. Verificarea existentei in baza de date se realizeaza fie dupa valoarea cheii primare, fie dupa un criteriu de unicitate.

Parametrii de intrare:

=over 

=item C<instance_id>
- cheia primara a instantei pentru care este furnizata descrierea curenta

=item C<lang>
- limba in care este furnizata descrierea curenta a instantei; daca limba nu exista, ea va fi adaugata in baza de date.

=item C<title>
- titlul instantei, in limba specificata prin parametrul lang

=item C<weighting>
- ponderea

=item C<universe>
- universul instantei

=item C<research_instrument>
- instrumentul de cercetare

=item C<scope>
- domeniul instantei

=item C<abstract>
- rezumatul instantei

=back


Criterii de unicitate:

=over

=item
- instance_id + title

=back

=cut

sub checkinstancedescr {
    my ( $self, %params ) = @_;
    my $langId;

	#Verificare lang_id; daca nu exista, este inserat mai intai in tabelul lang
    if ( $params{lang} && $params{lang} ne '' ) {
        my $langrs = $self->result_source->schema()->resultset('Lang')->checklangname(name => lc($params{lang}));
        if ($langrs) {
        	$langId = $langrs->id;
        }
    }

	if ($params{title} && $params{title} ne '') {
		#Adaugarea unei descrieri a unei instante din baza de date are loc doar daca aceasta nu exista deja.
		#Presupunem ca o descriere a unei instante este unic identificata prin atributul title. 
		
    	my $instancedescrexist = $self->result_source->schema()->resultset('InstanceDescr')
    							->search(
                                         {
                                         	instance_id => $params{instance_id},
                                           'lower(me.title)' => lc($params{title}),
       									 }
                                        );

    	if ( $instancedescrexist->count == 1 ) {
    		return $instancedescrexist -> first;
    	}

		#Daca descrierea instantei nu a fost gasita, o inseram. 
    	my $insertinstancedescr;
   
    	$insertinstancedescr->{lang_id} = $langId;
    	$insertinstancedescr->{instance_id} = $params{instance_id};
  	    $insertinstancedescr->{title} = ucfirst($params{title});
  	    $insertinstancedescr->{weighting} = $params{weighting};
  	    $insertinstancedescr->{research_instrument} = $params{research_instrument};
  	    $insertinstancedescr->{scope} = $params{scope};
  	    $insertinstancedescr->{universe} = $params{universe};
  	    $insertinstancedescr->{abstract} = $params{abstract};
  
    	my $guard = $self->result_source->schema()->txn_scope_guard;
    
    	my $instancedescrrs = $self->create($insertinstancedescr);
    
        $guard->commit;
         
    	return $instancedescrrs;
	}
}
1;
