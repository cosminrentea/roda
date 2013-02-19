package RODA::Components::DBIC::DBAuditSchema;
use base qw/DBIx::Class::Schema/;
use strict;
use warnings;
use Data::Dumper;

our $VERSION = "0.001";
$VERSION = eval $VERSION;


__PACKAGE__->mk_group_accessors( simple => '_current_changeset_container' );



=head1 NAME

RODA::Components::DBIC::DBAuditSchema;

=cut

=head1 

version 0.01

=cut


=head1 INCARCARE

In clasa schemei: 

__PACKAGE__->load_components(qw/+RODA::Components::DBIC::DBAuditSchema/);


=head1 FUNCTIONARE

Permite auditarea tabelelor prin inregistrarea modificarilor intr-o serie de tabele speciale

Definirea coloanelor in clasele Result permite urmatoarele:

  -evitarea auditarii unei coloane

    __PACKAGE__->add_columns(
           "age",
          { data_type => "integer", audit_log_column => 0 },
    );


  -fortarea auditarii unei coloane, chiar daca modificarile nu au atins-o

   __PACKAGE__->add_columns(
          "age",
          { data_type => "integer", audit_log_column => 0 },
    );

Pachetul permite de asemenea interceptarea valorii unor coloane inainte de audit si modificarea acestora, prin utilizarea atributului
modify_audit_value

Variante de specificare a modificatorilor sunt urmatoarele:

Trimiterea unei referinte la o subrutina anonima: 

        __PACKAGE__->add_columns(
            "+name",
            { modify_audit_value => sub{
                my ($self, $value) = @_;
                $value =~ tr/A-Z/a-z/;
                return $value;
            }, }
        );

trimiterea unui nume de subrutina si scrierea acesteia mai jos

        __PACKAGE__->add_columns(
            "+name",
            { modify_audit_value => 'to_lowercase'}, 
        );
        
        sub to_lowercase{
            my ($self, $value) = @_;
            $value =~ tr/A-Z/a-z/;
            return $value;
        }


Pachetul injecteaza propriile metode in schema generala. Astfel, la executia unei tranzactii prin txn_do
metoda locala capteaza blocul de cod si seteaza o serie de variabile pentru a permite urmarirea tranzactiei. 

In timpul rularii codului propriu-zis, metodele incarcate din RODA::Components::DBIC::DBAudit capteaza solicitarile de insert, update
delete si propaga inserarea datelor in tabelele de audit, folosind metodele adaugate la schema. 

Apelarea txn_do permite adaugarea unei descrieri a tranzactiei

        $roda->dbschema->txn_do(
            sub {
                $row->update({ ... });
            },
            {
                description => 'description of transaction' # optional
            }
        );

Apelarea txn_do pentru tabelele auditate TREBUIE sa fie autentificata, fie prin specificarea parametrului userid la conexiune:

my $roda = RODA->new( configfile => $config, test => '1', userid=>'1');

fie prin setarea userid-ului ulterior conexiunii

$roda->userid('3');


=cut


=head1 METHODS

=cut


=head2 txn_do

txn_do se va executa inainte de txn_do din DBIx::Class. Verifica daca exista deja un changeset activ (pentru eventualitatea in care
s-a solicitat o tranzactie in interiorul altei tranzactii. Daca exista, trece mai departe, daca nu, va seta atributul _current_changeset_container
apoi trece mai departe la urmatorul txn_do din lantul de executie

=cut

sub txn_do {
    my ( $self, $user_code, @args ) = @_;
    my $code              = $user_code;
    my $changeset_data    = $args[0];
    my $current_changeset = $self->_current_changeset;
    if ( !$current_changeset ) {
        my $current_changeset_ref = $self->_current_changeset_container;
        unless ($current_changeset_ref) {
            $current_changeset_ref = {};
            $self->_current_changeset_container($current_changeset_ref);
        }
        $code = sub {

            # creates local variables in the transaction scope to store
            # the changset args, and the changeset id
            local $current_changeset_ref->{args}      = $args[0];
            local $current_changeset_ref->{changeset} = '';
            $user_code->(@_);
        };
    } else {
    }
    return $self->next::method( $code, @args );
}

=head2 audit_init

Pentru eventualitatea in care nu se doreste utilizarea tranzactiilor prin txn_do. In acest caz, toate executiile de pe durata de viata a schemei curente
se vor asocia unui singur changeset. Aceasta ase poate evita prin apelarea metodei audit_init care va initializa un changeset particular.

Permite argumentul descriere, un string. 

=cut



sub audit_init {
    my ( $self, $description ) = @_;
        #This is a non transactional changeset, works great
        print "Audit_init: \n";
        my $current_changeset_ref = $self->_current_changeset_container;
        unless ($current_changeset_ref) {
            $current_changeset_ref = {};
            $self->_current_changeset_container($current_changeset_ref);
        }
        $self->_current_changeset_container($current_changeset_ref);
        my $changeset = $self->audit_log_create_changeset( { description => $description } );
        $self->_current_changeset_container->{changeset} = $changeset->id;
        return $changeset->id;
}

=head2 audit_commit

Inchide changesetul curent, permitand operatia non-tranzactionala cu mai multe changeseturi. Trebuie apelata doar daca a fost apelata audit_init

=cut


sub audit_commit {
    my ( $self ) = @_;
    print "txn_commit in our schmea\n";
    my $current_changeset_ref = {};
    $self->_current_changeset_container($current_changeset_ref);
}

=head2 get_changes

Metoda generala de citire a modificarilor din tabelele de audit. Se apeleaza la nivel de schema

    my $changes = $roda->dbschema->get_changes({table=>'person', id=>'8'});

Are nevoie de numele in clar al tabelului si de id-ul randului care trebuie urmarit. 

Lista de argumente:

 id - id-ul randului din tabel pentru care se doreste auditul. Obligatoriu 
 table - tabelul de interes. Obligatoriu
 change_order - default desc, ordinea in care sunt returnate modificarile
 field - in lipsa specificarii unui camp, vor fi returnate toate campurile tabelului curent.
 timestamp - filtreaza dupa timestampul changeseturilor
 action_types - default este [ 'insert', 'update', 'delete' ], se poate inlocui cu o lista redusa
  


=cut

sub get_changes {
    my $self    = shift;

    my $options = shift;
    my $audited_row  = $options->{id};
    my $change_order = $options->{change_order} || 'desc';
    my $field_name   = $options->{field};
    my $table_name   = $options->{table};
    my $timestamp    = $options->{timestamp};
    my $action_types = $options->{action_types}
      || [ 'insert', 'update', 'delete' ];

    # row and table are required for all changes
    return if !$audited_row || !$table_name;
    my $table = $self->resultset('AuditLogTable')->find( { name => $table_name, } );
    # cannot get changes if the specified table hasn't been logged
    return unless defined $table;
    my $changeset_criteria = {};
    $changeset_criteria->{timestamp} = $timestamp if $timestamp;
    my $changesets = $self->resultset('AuditLogChangeset')->search_rs($changeset_criteria);
    my $actions = $changesets->search_related(
                                               'audit_log_actions',
                                               {
                                                  audited_table => $table->id,
                                                  audited_row   => $audited_row,
                                                  type          => $action_types,
                                               }
    );
    if ( $actions && $actions->count ) {
        my $field = $table->find_related( 'Field', { name => $field_name } ) if $field_name;

        # if field is passed and the passed field wasn't found in the Field
        # table set field id to -1 to ensure a $changes object with ->count =
        # 0 is returned
        my $criteria = {};
        if ($field_name) {
            $criteria->{field} = $field ? $field->id : -1;
        }
        my $changes = $actions->search_related( 'audit_log_changes', $criteria, { order_by => 'me.id ' . $change_order, } );
        return $changes;
    }
    return;
}

=head2 audit_log_create_changeset

Metoda care se ocupa de constructia unui changeset. Preia automat userid-ul din schema

=cut



sub audit_log_create_changeset {
    my $self           = shift;
    my $changeset_data = shift;
    my ( $changeset, $user );
    if ( $self->user && $self->user->id > 0 ) {
        $changeset = $self->resultset('AuditLogChangeset')->create( { description => $changeset_data->{description}, rodauser => $self->user->id } );
    }
    return $changeset;
}

=head2 audit_log_create_action

Metoda care se ocupa de verificarea existentei tabelului curent in tabelul audit_log_table si de crearea actiunii. 
O actiune este o modificare a unui rand de tabel. Apeleaza metoda current_changeset care
declanzeaza crearea changesetului (e un soi de checker). 

=cut


sub audit_log_create_action {
    my $self        = shift;
    my $action_data = shift;
        print Dumper($action_data);
            
    my $changeset   = $self->current_changeset;
    
    
    if ($changeset) {
        my $table = $self->resultset('AuditLogTable')->find_or_create( { name => $action_data->{table} } );
        return (
                 $self->resultset('AuditLogChangeset')->find($changeset)->create_related(
                                                                                          'audit_log_actions',
                                                                                          {
                                                                                            audited_row   => $action_data->{row},
                                                                                            audited_table => $table->id,
                                                                                            type          => $action_data->{type},
                                                                                          }
                 ),
                 $table
        );
    }
    return;
}

sub _current_changeset {
    my $self = shift;
    my $ref = $self->_current_changeset_container;
    return $ref && $ref->{changeset};
}

=head2 current_changeset

Declanzeaza crearea changesetului (e un soi de checker). Daca nu gaseste nimic in _current_changeset_container va introduce un 
changeset netranzactional, setand descrierea sa fie: Non transactional changeset

=cut


sub current_changeset {
    my ( $self, @args ) = @_;
    $self->throw_exception('Cannot set changeset manually. Use txn_do.')
      if @args;
    if (    defined $self->_current_changeset_container
         && defined $self->_current_changeset_container->{changeset} )
    {
        my $id = $self->_current_changeset;
        unless ($id) {
            my $changeset = $self->audit_log_create_changeset(
                                                               $self->_current_changeset_container->{args}
            );
            $self->_current_changeset_container->{changeset} = $changeset->id;
            $id = $changeset->id;
        }
        return $id;
    } else {
        #This is a non transactional changeset, works great
        print "Non transactional: \n";
        my $current_changeset_ref = $self->_current_changeset_container;
        unless ($current_changeset_ref) {
            $current_changeset_ref = {};
            $self->_current_changeset_container($current_changeset_ref);
        }
        $self->_current_changeset_container($current_changeset_ref);
        my $changeset = $self->audit_log_create_changeset( { description => 'Non transactional changeset' } );
        $self->_current_changeset_container->{changeset} = $changeset->id;
        return $changeset->id;
    }
    return;
}
1;


