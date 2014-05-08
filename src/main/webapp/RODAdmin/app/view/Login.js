/**
 * Afiseaza caseta de login. 
 * @uses RODAdmin.view.Translation
 * @uses RODAdmin.controller.Login
 */
Ext.define('RODAdmin.view.Login', {
    extend: 'Ext.window.Window',
    alias: 'widget.login',

    requires: [
        'RODAdmin.view.Translation'
    ],

    autoShow: true,
    height: 170,
    width: 360,
    layout: {
        type: 'fit'
    },
    iconCls: 'key',
    title: translations.login,
    closeAction: 'hide',
    closable: false,
    
    items: [
        {
            xtype: 'form',
            name: 'loginform',
            url: '/roda/resources/j_spring_security_check',
            frame: false,
            standardSubmit: true,
            bodyPadding: 15,
            defaults: {
                xtype: 'textfield',
                anchor: '100%',
                labelWidth: 60,
                allowBlank: false,
                vtype: 'alphanum',
                minLength: 3,
                msgTarget: 'under'
            },
            items: [
                {
                    name: 'j_username',
                    fieldLabel: translations.user,
                    maxLength: 25,
                    value: 'admin'
                },
                {
                    inputType: 'password',
                    name: 'j_password',
                    fieldLabel: translations.password,
                    enableKeyEvents: true,
                    id: 'j_password',
                    maxLength: 32,
                    value: 'admin',
                    //vtype: 'customPass',
                    msgTarget: 'side'
                }
            ],
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'bottom',
                    items: [
                        {
                            xtype: 'translation'
                        },
                        {
                            xtype: 'tbfill'
                        },
                        {
                            xtype: 'button',
                            itemId: 'cancel',
                            iconCls: 'cancel',
                            text: translations.cancel
                        },
                        {
                            xtype: 'button',
                            itemId: 'submit',
                            formBind: true,
                            iconCls: 'key-go',
                            text: translations.submit,
                            type : 'submit'
                        }
                    ]
                }
            ]
        }
    ]
});