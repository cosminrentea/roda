Ext.define('RODAdmin.model.studies.CBEditor.PersOrg', {
    extend: 'Ext.data.Model',
    fields: [
    {	name: 'id',
   		type : 'int'
	}, {
        name: 'status',
        type: 'string' //ex sau new
    }, {
        name: 'type',
        type: 'string' //pers sau org
    }, {
    	name: 'selectedname',
    	type: 'string'
    },
   //persoana 	
   {
        name: 'persprefix',
        type: 'int'
    }, {
        name: 'persfname',
        type: 'string'
    }, {
        name: 'perslname',
        type: 'string'
    }, {
        name: 'perssuffix',
        type: 'int'
    }, {
        name: 'persemail',
        type: 'string'
    },
    {
        name: 'persphone',
        type: 'string'
    },
    {
        name: 'persaddr1',
        type: 'string'
    },
    {
        name: 'persaddr2',
        type: 'string'
    },
    {
        name: 'perszip',
        type: 'string'
    },
    {
        name: 'perscity',
        type: 'integer'
    },
// organizatie
   {
        name: 'orgprefix',
        type: 'int'
    }, {
        name: 'orgsname',
        type: 'string'
    }, {
        name: 'orglname',
        type: 'string'
    }, {
        name: 'orgsuffix',
        type: 'int'
    }, {
        name: 'orgemail',
        type: 'string'
    },
    {
        name: 'orgphone',
        type: 'string'
    },
    {
        name: 'orgaddr1',
        type: 'string'
    },
    {
        name: 'orgaddr2',
        type: 'string'
    },
    {
        name: 'orgzip',
        type: 'string'
    },
    {
        name: 'orgcity',
        type: 'integer'
    }
    

    ]
});





