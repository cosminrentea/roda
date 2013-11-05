Ext.define('RODAdmin.model.cms.layout.LayoutUsage', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'name',
        type: 'string'
    },{
    	name: 'url',
    	type: 'string'
    },{
    	name: 'id',
    	type: 'integer'
    },{
    	name: 'lang',
    	type: 'string'
    },{
    	name: 'visible',
    	type: 'boolean'
    },{
    	name: 'pagetypeid',
    	type: 'integer'
    },{
    	name: 'pagetypename',
    	type: 'string'
    }]
});



