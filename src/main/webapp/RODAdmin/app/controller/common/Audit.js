Ext.define('RODAdmin.controller.common.Audit', {
	extend : 'Ext.app.Controller',

	stores : ['common.Audit'],

	views : ['common.AuditWindow'],

	refs : [],

	init : function(application) {
		this.control({
					"auditwindow grid#auditgrid" : {
						cellclick: this.cucku	
					}					
				});
	},
	
	cucku: function(component, td, cellIndex, record, tr, rowIndex, e, eOpts) {
//			console.log(record);
			//acu sa punem in celelalte bucati
			var detailsp = component.up('auditwindow').down('dataview#auditdetails');
			
			console.log(record.auditfieldsStore);
			
			detailsp.bindStore(record.auditfieldsStore);
	}
	
});
	