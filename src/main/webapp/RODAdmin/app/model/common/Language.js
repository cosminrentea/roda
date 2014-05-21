/**
 * 
 */
Ext.define('RODAdmin.model.common.Language', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'indice',
						type : 'integer'
					}, {
						name : 'iso639',
						type : 'string'
					}, {
						name : 'nameEn',
						type : 'string'
					}, {
						name : 'nameRo',
						type : 'string'
					}, {
						name : 'nameSelf',
						type : 'string'
					}]
		});
