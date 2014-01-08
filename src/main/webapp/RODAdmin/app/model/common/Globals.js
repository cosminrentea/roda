/**
 *  Model pentru setarile globale ale aplicatiei care se incarca inainte de pornire. 
 */
Ext.define('RODAdmin.model.common.Globals', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'name',
						type : 'string',
					}, {
						name : 'value',
						type : 'string'
					}]
		});
