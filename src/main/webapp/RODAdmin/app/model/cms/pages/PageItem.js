/**
 * 
 */
Ext.define('RODAdmin.model.cms.pages.PageItem', {
	extend : 'Ext.data.Model',
    fields: [
             {    
             	name: 'indice',
             	type : 'int'
         	}, {
                 name: 'title',
                 type: 'string'
             }, {
                 name: 'lang',
                 type: 'string'
             }, {
                 name: 'menutitle',
                 type: 'string'
             }, {
         		name : 'url',
         		type : 'string'
         		},	{
                 name: 'default',
                 type: 'boolean'
             }, {
                 name: 'externalredirect',
                 type: 'string'
             }, {
                 name: 'internalredirect',
                 type: 'string'
             }, {
                 name: 'layout',
                 type: 'string'
             }, 
             {
                 name: 'layoutid',
                 type: 'integer'
             },             
             {
                 name: 'cacheable',
                 type: 'integer'
             }, {
                 name: 'searchable',
                 type: 'boolean'
             },{
                 name: 'published',
                 type: 'boolean'
             },{
                 name: 'target',
                 type: 'string'
             },{
                 name: 'pagetype',
                 type: 'string'
             },
             {
                 name: 'pagetypeid',
                 type: 'integer'
             },
             {
            	 name: 'content',
            	 type: 'string'
             },{
            	 name: 'synopsis',
            	 type: 'string'
             },{
            	 name: 'parent',
            	 type: 'string'
             },{
            	 name: 'parentid',
            	 type: 'integer'
             }
             ]
});
