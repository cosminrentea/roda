use utf8;

package RODA::RODADB::ResultSet::Variable;
use strict;
use warnings;
use Data::Dumper;
use parent qw/DBIx::Class::ResultSet/;

=head1 NUME

RODA::RODADB::ResultSet::Variable - metode specifice prelucrarii variabilelor

=cut

=head1 VERSIUNE

version 0.01

=cut

=head1 DESCRIERE

Metode suplimentare care se aplica asupra seturilor de rezultate de variabila.

=cut

=head1 METODE

=cut

=head2 checkvariableexists

checkvariableexists verifica existenta unei variabile (preluata prin valori ale parametrilor de intrare) in baza de date. 
In caz ca exista, metoda returneaza valoarea 1; altfel, valoarea returnata este 0. 
Existenta variabilei este determinata fie prin valoarea cheii sale primare, fie prin intermediul criteriului de unicitate.


Parametrii de intrare:

=over 

=item C<variable_id>
- cheia primara a variabilei din tabelul de variabile (variable)

=item C<instance_id>
- cheia primara a instantei din care face parte variabila

=item C<label>
- eticheta variabilei

=back


Criterii de unicitate:

=over

=item
- instance_id + label (presupunem ca etichetele variabilelor sunt unice in cadrul unei instante)

=back

=cut


sub checkvariableexists {
    my ( $self, %params ) = @_;

    #daca avem parametri corecti
    if ( $params{instance_id} && $params{label} ) {
        my $variablers = $self->search(
                                          {
                                             instance_id => $params{instance_id},
                                             label => lc($params{label}),
                                          },
                                          { key => 'primary' }
                                         );
        if ($variablers->count >= 1) {                                 
        	return 1;
        }
    }
    return 0;
}

=head2 checkvariable

checkvariable verifica existenta unei variabile (preluata prin valori ale parametrilor de intrare) in baza de date. 
In caz ca exista, metoda returneaza obiectul corespunzator; altfel, variabila este inserata si obiectul corespunzator este returnat. 
Existenta variabilei este determinata fie prin valoarea cheii sale primare, fie prin intermediul criteriului de unicitate.


Parametrii de intrare:

=over 

=item C<variable_id>
- cheia primara a variabilei din tabelul de variabile (variable)

=item C<instance_id>
- cheia primara a instantei din care face parte variabila

=item C<label>
- eticheta variabilei

=item C<type>
- tipul variabilei

=item C<order_in_instance>
- numarul de ordine al variabilei in cadrul instantei

=item C<operator_instructions>
- instructiuni destinate operatorului, referitor la variabila curenta

=item C<type_edited_text>
- parametru boolean, ce indica daca variabila este de tip text editat

=item C<type_edited_number>
- parametru boolean, ce indica daca variabila este de tip numeric editat

=item C<type_selection>
- parametru boolean, ce indica daca variabila este de tip selectie

=item C<skips>
- lista salturilor care pornesc de la variabila respectiva

=item C<other_statistics>
- lista statisticilor care pornesc de la variabila curenta

=item C<selection_variable>
- informatiile asociate variabilei, in cazul in care aceasta este de tip selectie

=back


Criterii de unicitate:

=over

=item
- instance_id + label (presupunem ca etichetele variabilelor sunt unice in cadrul unei instante)

=back

=cut

sub checkvariable {
    my ( $self, %params ) = @_;

    #cautare dupa cod; daca este dar codul, atunci se incearca regasirea variabilei avand codul specificat
    if ( $params{variable_id} && $params{variable_id} ne '') {
        my $variablers = $self->search(
                                          {
                                             id => $params{variable_id},
                                          },                                         
                                         );
        if ($variablers->count == 1) {                                 
        	return $variablers -> single;
        }
    }

	#daca nu este gasit codul, se incearca regasirea dupa {instance_id, label}
	#daca nici in urma acestei cautari variabila nu este regasita, va fi inserata, cu un cod nou generat 
	#(chiar daca variable_id a fost specificat)
	 
    #daca avem parametri corecti:
    if ( $params{instance_id} && $params{label} ) {
        my $variablers = $self->search(
                                          {
                                             instance_id => $params{instance_id},
                                             label => lc($params{label}),
                                          },
                                          { key => 'primary' }
                                         );
        if ($variablers->count >= 1) {                                 
        	return $variablers -> first;
        }
        
        my $insertvariable;
        
        $insertvariable->{instance_id} = $params{instance_id};
        $insertvariable->{label} = $params{label};
        $insertvariable->{type} =  $params{type};
        $insertvariable->{order_in_instance} = $params{order_in_instance};
        $insertvariable->{operator_instructions} = $params{operator_instructions};
        
        #TODO: file
    	#$insertvariable->{file_id} = $params{file_id};
    	
    	$insertvariable->{type_edited_text} = $params{type_edited_text};
    	$insertvariable->{type_edited_number} = $params{type_edited_number};
    	$insertvariable->{type_selection} = $params{type_selection};

    	my $guard   = $self->result_source->schema()->txn_scope_guard;
    	$variablers = $self->create($insertvariable);
        
        
 		if ($params{skips} && @{$params{skips}} > 0) {
        	$variablers->attach_skips( skips=>$params{skips} );
        }
        
        if ($params{other_statistics} && @{$params{other_statistics}} > 0) {
        	$variablers->attach_other_statistics( other_statistics=>$params{other_statistics} );
        }
 		 
 		if ($params{selection_variable} && @{$params{selection_variable}} > 0) {
        	$variablers->attach_selection_variable( selection_variable=>$params{selection_variable} );
        } 
 		 
 		$guard->commit;
    	return $variablers;      
    }

}
 
1;
