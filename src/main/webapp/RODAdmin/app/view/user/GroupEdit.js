Ext.define('RODAdmin.view.user.GroupEdit', {
    extend: 'RODAdmin.view.common.WindowForm',
    alias: 'widget.groupedit',

    height: 240,
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
                            fieldLabel: 'Name',
                            name: 'name'
                        },
                        {
                            fieldLabel: 'Descriere',
                            name: 'description',
                           	xtype : 'textarea'
                        },
                    ]
                },
            ]
        }
    ],

});