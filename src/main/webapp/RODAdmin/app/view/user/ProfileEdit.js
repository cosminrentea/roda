Ext.define('RODAdmin.view.user.ProfileEdit', {
    extend: 'RODAdmin.view.common.WindowForm',
    alias: 'widget.profileedit',

    height: 290,
    width: 550,

    requires : ['RODAdmin.util.Util'],

    layout: {
        align: 'stretch',
        type: 'vbox'
    },
    title: 'User',

    items: [
        {
            xtype: 'form',
            bodyPadding: 5,
            layout: {
                type: 'hbox',
                align: 'stretch'
            },
            items: [
                {
                    xtype: 'fieldset',
                    flex: 2,
                    title: 'User Information',
                    defaults: {
                        afterLabelTextTpl: RODAdmin.util.Util.required,
                        anchor: '100%',
                        xtype: 'textfield',
                        allowBlank: false,
                        labelWidth: 60
                    },
                    items: [
                        {
                            xtype: 'hiddenfield',
                            fieldLabel: 'Label',
                            name: 'id'
                        },
                        {
                            fieldLabel: 'Firstname',
                            name: 'firstname'
                        },
                        {
                            fieldLabel: 'Lastname',
                            name: 'lastname'
                        },
                        {
                            fieldLabel: 'Adresa1',
                            maxLength: 100,
                            name: 'address1'
                        },
                        {
                            fieldLabel: 'Adresa2',
                            maxLength: 100,
                            name: 'address2'
                        },
                        {
                            fieldLabel: 'Tara',
                            name: 'country'
                        },
                        {
                            fieldLabel: 'Oras',
                            name: 'city'
                        },
                        {
                            fieldLabel: 'Data Nasterii',
                            name: 'birthdate',
                           // type : 'date',
                        },

                        
                    ]
                },
            ]
        }
    ],

});