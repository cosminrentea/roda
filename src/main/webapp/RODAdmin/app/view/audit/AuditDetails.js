/**
 * 
 */
Ext.define('RODAdmin.view.audit.AuditDetails', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.auditdetails',
    itemId : 'auditdetails',
    activeItem : 0,
    layout : {
        type : 'card',
        // padding:'5',
        // align:'center',
        align : 'stretch'
    },
    // id: 'auditdetails',
    items : [{
                xtype : 'revisionproperties',
            }, {
	            xtype : 'objectproperties',
            }

    ]

});