Ext.define('RODAdmin.view.user.UserMessage', {
    extend : 'RODAdmin.view.common.WindowForm',
    alias : 'widget.usermessage',

    height : 260,
    width : 550,

    requires : [
	    'RODAdmin.util.Util'
    ],
    title : 'User',

    items : [
	    {
	        xtype : 'form',
	        bodyPadding : 5,
	        layout : {
	            type : 'vbox',
	            align : 'stretch'
	        },
	        items : [
	                {
	                    xtype : 'hiddenfield',
	                    fieldLabel : 'userid',
	                    name : 'userid'
	                }, {
	                    fieldLabel : 'Sent Message to: ',
	                    xtype : 'displayfield',
	                    name : 'fullname'
	                }, {
	                    fieldLabel : 'Subject',
	                    maxLength : 300,
	                    name : 'subject',
	                    xtype: 'textfield'
	                }, {
	                    xtype : 'textarea',
	                    fieldLabel : 'Message',
	                    name : 'message',
	                    flex : 3,
	                    allowBlank : false,
	                },
	        ]
	    }
    ]
});