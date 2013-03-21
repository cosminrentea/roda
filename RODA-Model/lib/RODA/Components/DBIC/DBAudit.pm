package RODA::Components::DBIC::DBAudit;

use strict;
use warnings;


our $VERSION = "0.001";
$VERSION = eval $VERSION;

=head1 NUME

RODA::Components::DBIC::DBAudit;

=cut

=head1 VERSIUNE

version 0.02

=cut

=head1 INCARCARE

In clasa result a tabelului cu pricina: 

  __PACKAGE__->load_components(qw/+RODA::Components::DBIC::DBAudit/);

Nu functioneaza pe tabelele cu chei multiple. Inca. 


=head1 FUNCTIONARE

Pachetul injecteaza propriile metode in clasa asociata tabelelor. 
In timpul rularii metodelor de modificare a datelor, capteaza solicitarile de insert, update
delete si propaga inserarea datelor in tabelele de audit, folosind metodele adaugate la schema. 

=cut


=head1 METODE

=cut


=head2 insert

Incarca metoda insert, prin apelarea metodei private _action_setup si apoi _store_changes

=cut

sub insert {
    my $self = shift;
    return $self->next::method(@_) if $self->in_storage;
    my $result = $self->next::method(@_);
    my ( $action, $table ) = $self->_action_setup( $result, 'insert' );
    if ($action) {
        my %column_data = $result->get_columns;
        $self->_store_changes( $action, $table, {}, \%column_data );
    }
    return $result;
}

=head2 update

Incarca metoda update

=cut

sub update {
    my $self = shift;
    my $stored_row = $self->get_from_storage;
    my %old_data   = $stored_row->get_columns;
    my %new_data   = $self->get_columns;
    my @changed_columns = keys %{$_[0]||{}};

    my $result = $self->next::method(@_);

    if (@changed_columns) {
     
        @new_data{@changed_columns} = map $self->get_column($_),
            @changed_columns;
    }

    foreach my $col ($self->columns){
	    if($self->_force_audit($col)){
		    $old_data{$col} = $stored_row->get_column($col) 
		    	unless defined $old_data{$col};
		    $new_data{$col} = $self->get_column($col) 
		    	unless defined $new_data{$col};
	    }
    }

    foreach my $key ( keys %new_data ) {
        if (   defined $old_data{$key}
            && defined $new_data{$key}
	    && (! $self->_force_audit($key))
            && $old_data{$key} eq $new_data{$key} )
        {
            delete $new_data{$key};
        }
    }

    if ( keys %new_data ) {
        my ( $action, $table )
            = $self->_action_setup( $stored_row, 'update' );

        if ($action) {
            $self->_store_changes( $action, $table, \%old_data, \%new_data );
        }
    }

    return $result;
}

=head2 audit_log

Adauga metoda audit_log unui rand de tabel. Metoda este una de convenienta pentru get_changes, paseaza mai departe orice parametru, mai putin table si id pe care le seteaza automat la cele curente

   my $person = $roda->dbschema->resultset('Person')->find('8');
 
   my $chg = $person->audit_log;

   my @ch = $chg->all;

   foreach my $cc (@ch) {
      print $cc->action->changeset->timestamp->datetime." - ".$cc->action->type." - ".$cc->field->name ." - ".$cc->new_value ."\n";
   }



=cut

sub audit_log {
     my $self = shift;
     my $options = shift;
     my $table = $self->table;
     return "need a row" unless $self->in_storage;
     my @pkk = $self->primary_columns;
     my $pk = $pkk[0];
     $options->{table} = $table;
     $options->{id} = $self->$pk;
     return $self->result_source->schema->get_changes($options);
}

=head2 delete

Incarca metoda delete, similara cu insert, apeleaza _action_setup si apoi _store_changes

=cut

sub delete {
    my $self = shift;
    my $stored_row = $self->get_from_storage;

    my $result = $self->next::method(@_);

    my ( $action, $table ) = $self->_action_setup( $stored_row, 'delete' );

    if ($action) {
        my %old_data = $stored_row->get_columns;
        $self->_store_changes( $action, $table, \%old_data, {} );
    }

    return $result;
}

=head2 setup

apeleaza metoda audit_log_create_action injectata in schema de RODA::Components::DBIC::DBAuditSchema si creeaza actiunea
audit_log_create_action va creea changesetul daca e nevoie sau il va refolosi pe cel existent

=cut

sub _action_setup {
    my $self = shift;
    my $row  = shift;
    my $type = shift;
    return $self->result_source->schema()->audit_log_create_action(
        {   row   => $row->id,
            table => $row->result_source_instance->name,
            type  => $type,
        }
    );

}

=head2 store changes

construieste intrarile in tabel care corespund tuturor modificarilor coloanelor. Acestea sunt stocate in tabelul audit_log_change.
Se apeleaza dupa setarea unei actiuni si verifica prezenta campurilor in tabelul audit_log_fields  

=cut

sub _store_changes {
    my $self       = shift;
    my $action     = shift;
    my $table      = shift;
    my $old_values = shift;
    my $new_values = shift;
    foreach my $column (
        keys %{$new_values} ? keys %{$new_values} : keys %{$old_values} )
    {
        if ( $self->_do_audit($column) ) {
            my $field
                = $table->find_or_create_related( 'audit_log_fields', { name => $column } );

        my $create_params = {
            field => $field->id,
        };

        if($self->_do_modify_audit_value($column)){
                    $create_params->{new_value} = 
                $self->_modify_audit_value($column,$new_values->{$column});
                    $create_params->{old_value} = 
                $self->_modify_audit_value($column,$old_values->{$column});
        }else{
                    $create_params->{new_value} = $new_values->{$column};
                    $create_params->{old_value} = $old_values->{$column};
        }

            $action->create_related(
                'audit_log_changes',
		$create_params,
            );
        }
    }
}

=head2 _force_audit

Returneaza 0 sau 1 daca in coloana curenta a fost setat parametrul force_audit_log_column care determina inserarea coloanei in tabelele de audit chiar
in cazul unui update care nu i-a atins valoarea.

=cut

sub _force_audit{
    my ($self, $column) = @_;
    my $info = $self->column_info($column);
    return
        defined $info->{force_audit_log_column};

}

=head2 _do_audit

Determina daca coloana curenta trebuie introdusa in tabelele de audit. Ia in calcul atat force_audit cat si eventualitatea in care
a fost setat parametrul audit_log_column =0 la definirea coloanei

=cut


sub _do_audit {
    my $self   = shift;
    my $column = shift;
    return 1 if $self->_force_audit($column);

    my $info = $self->column_info($column);
    return
        defined $info->{audit_log_column}
        && $info->{audit_log_column} == 0 ? 0 : 1;
}

=head2 _do_modify_audit_value

Determina daca e nevoie sa ruleze un cod pentru modificarea valorii coloanei inainte de auditare

=cut

sub _do_modify_audit_value{
    my $self   = shift;
    my $column = shift;
    my $info = $self->column_info($column);

    return
        $info->{modify_audit_value} ? 1 : 0; 
}

=head2 _modify_audit_value

Incearca modificarea valorii coloanei, prin rularea codului specificat in aces scop la definirea coloanei

=cut

sub _modify_audit_value{
    my $self   = shift;
    my $column = shift;
    my $value = shift;
    my $info = $self->column_info($column);
    my $meth = $info->{modify_audit_value};
    return $value unless 
        defined $meth;

    return &$meth($self, $value)
        if ref($meth) eq 'CODE';

    $meth = "modify_audit_$column"
        unless $self->can($meth);

    return $self->$meth($value)
        if $self->can($meth);

    die "unable to find modify_audit_method ($meth) for $column in $self";

}

1;


