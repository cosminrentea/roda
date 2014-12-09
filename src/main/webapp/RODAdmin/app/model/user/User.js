/**
 * 
 */
Ext.define('RODAdmin.model.user.User', {
    extend : 'Ext.data.Model',
    fields : [
            {
                name : 'id',
                type : 'int'
            }, {
                name : 'username',
                type : 'string'
            }, {
                name : 'firstname',
                type : 'string'
            }, {
                name : 'lastname',
                type : 'string'
            }, {
                name : 'email',
                type : 'string'
            }, {
                name : 'enabled',
                type : 'boolean'
            }, {
                name : 'fullname',
                type : 'string',
                convert : function(v, r) {
	                return r.get('firstname') + ' ' + r.get('lastname');
                }
            },

            {
                name : 'userid',
                type : 'integer',
                convert : function(v, r) {
	                return r.get('id');
                }
            },

    ]
});
