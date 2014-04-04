/**
 * 
 */
Ext.define('RODAdmin.store.local.PagePublished', {
	extend: 'Ext.data.Store',
	fields: ['name', 'published'],	
	data : [
	        {"name":'Yes',"published":'true'},
	        {"name":'No',"published":'false'}
	        ]	
});