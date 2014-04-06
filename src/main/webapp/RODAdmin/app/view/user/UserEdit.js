Ext.define('RODAdmin.view.user.UserEdit', {
    extend: 'RODAdmin.view.common.WindowForm',
    alias: 'widget.useredit',

    height: 260,
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
                            fieldLabel: 'Username',
                            name: 'username'
                        },
                        {
                            fieldLabel: 'Email',
                            maxLength: 100,
                            name: 'email'
                        },
                        {
                            xtype: 'combobox',
                            fieldLabel: 'Group',
                            name: 'Group_id',
                            displayField: 'name',
                            valueField: 'id',
                            queryMode: 'local',
                            store: 'user.Group'
                        },
                        {
                            xtype: 'filefield',
                            fieldLabel: 'Picture',
                            name: 'picture',
                            allowBlank: true,
                            afterLabelTextTpl: ''
                        }
                    ]
                },
                {
                    xtype: 'fieldset',
                    title: 'Picture',
                    width: 170,
                    items: [
                        {
                            xtype: 'image',
                            height: 150,
                            width: 150,
                            src: '/roda/images/userdefault.png',
                        }
                    ]
                }
            ]
        }
    ],

});