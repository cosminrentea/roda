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
//                        afterLabelTextTpl: RODAdmin.util.Util.required,
                        anchor: '100%',
                        xtype: 'textfield',
                       // allowBlank: false,
                        labelWidth: 60
                    },
                    items: [
                        {
                            xtype: 'hiddenfield',
                            fieldLabel: 'Label',
                            name: 'id',
                            itemId : 'idfield',
                            allowBlank : false,
                            afterLabelTextTpl: RODAdmin.util.Util.required,
                        },
                        {
                            fieldLabel: 'Firstname',
                            name: 'firstname',
                            allowBlank : false,
                            afterLabelTextTpl: RODAdmin.util.Util.required,
                        },
                        {
                            fieldLabel: 'Lastname',
                            name: 'lastname',
                           	allowBlank : false,
                           	afterLabelTextTpl: RODAdmin.util.Util.required,
                        },
                        {
                            fieldLabel: 'Adresa1',
                            maxLength: 100,
                            name: 'address1',
                            allowBlank : false,                            
                            afterLabelTextTpl: RODAdmin.util.Util.required,
                        },
                        {
                            fieldLabel: 'Adresa2',
                            maxLength: 100,
                            name: 'address2',
                           	allowBlank : true,
                        },
                        {
                            fieldLabel: 'Tara',
                            name: 'country',
                           	allowBlank : true,                            	
                        },
                        {
                            fieldLabel: 'Oras',
                            name: 'city',
                            allowBlank : true,
                        },


                        
                    ]
                },
            ]
        }
    ],

});