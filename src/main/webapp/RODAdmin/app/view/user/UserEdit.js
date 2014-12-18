Ext.define('RODAdmin.view.user.UserEdit', {
    extend: 'RODAdmin.view.common.WindowForm',
    alias: 'widget.useredit',

    height: 460,
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
         //               afterLabelTextTpl: RODAdmin.util.Util.required,
                        anchor: '100%',
                        xtype: 'textfield',
              //          allowBlank: false,
                        labelWidth: 60
                    },
                    items: [
                        {
                            xtype: 'hiddenfield',
                            fieldLabel: 'Label',
                            name: 'id'
                        },
                        {
                            fieldLabel: 'Username',
                            name: 'username'
                        },

                        {
                            xtype: 'textfield',
                            fieldLabel: 'Password',
                            name: 'password',
                            inputType:'password', 
                            id : 'passf',
                            allowBlank:false,
                            afterLabelTextTpl: RODAdmin.util.Util.required,
                        },
                        {
                            xtype: 'textfield',
                            fieldLabel: 'Password',
                            name: 'passwordcheck',
                            id : 'passs',
                            inputType:'password', 
                            allowBlank:false,
                            afterLabelTextTpl: RODAdmin.util.Util.required,
                            validator: function() {
                                var pass1 = Ext.getCmp('passf').getValue();
                                var pass2 = Ext.getCmp('passs').getValue();
                                console.log("pass 1 = " + pass1 + "--pass 2 = " + pass2);
                                 if (pass1 == pass2)
                                     return true;
                                 else 
                                     return "Passwords do not match!";
                            }
                        },
                        
                        {
                            fieldLabel: 'Email',
                            maxLength: 100,
                            name: 'email'
                        },
                        {
                            xtype: 'combo',
                            fieldLabel: 'Group',
                            name: 'authority',
                            displayField: 'name',
                            valueField: 'id',
//                            queryMode: 'local',
                            typeAhead: true,
                            tpl: '<tpl for="."><div class="x-boundlist-item"><strong>{name}</strong><br><i>{description}</i><hr></div></tpl>',                            
                            store: 'user.Group'
                        },
                        {
                            fieldLabel: 'Firstname',
                            name: 'firstname',
                            allowBlank : false,
//                            afterLabelTextTpl: RODAdmin.util.Util.required,
                        },
                        {
                            fieldLabel: 'Lastname',
                            name: 'lastname',
                           	allowBlank : false,
//                           	afterLabelTextTpl: RODAdmin.util.Util.required,
                        },
                        {
                            fieldLabel: 'Adresa1',
                            maxLength: 100,
                            name: 'address1',
//                            allowBlank : false,                            
//                            afterLabelTextTpl: RODAdmin.util.Util.required,
                        },
                        {
                            fieldLabel: 'Adresa2',
                            maxLength: 100,
                            name: 'address2',
//                           	allowBlank : true,
                        },
                        {
                            fieldLabel: 'Tara',
                            name: 'country',
//                           	allowBlank : true,                            	
                        },
                        {
                            fieldLabel: 'Oras',
                            name: 'city',
//                            allowBlank : true,
                        },

                    ]
                },

            ]
        }
    ],

});