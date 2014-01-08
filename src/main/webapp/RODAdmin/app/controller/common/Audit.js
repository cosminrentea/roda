/**
 * 
 */
Ext.define('RODAdmin.controller.common.Audit', {
	extend : 'Ext.app.Controller',

	stores : ['common.Audit'],

	views : ['RODAdmin.view.common.AuditWindow'],

	refs : [],
/**
 * @method
 */
	init : function(application) {
		this.control({
					"auditwindow grid#auditgrid" : {
			            /**
						 * @listener auditwindow-grid-auditgrid-cellclick triggered-by:
						 *           {@link RODAdmin.view.common.AuditWindow AuditWindow}
						 *           grid#auditgrid
						 *           {@link #cucku}
						 */		        	
						cellclick: this.cucku	
					}					
				});
	},
    /**
	 * @method
	 */	
	cucku: function(component, td, cellIndex, record, tr, rowIndex, e, eOpts) {
//			console.log(record);
			//acu sa punem in celelalte bucati
			var detailsp = component.up('auditwindow').down('dataview#auditdetails');
			
			console.log(record.auditfieldsStore);
			
			detailsp.bindStore(record.auditfieldsStore);
	}
	
});
	