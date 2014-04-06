/**
 * 
 */
Ext.define('RODAdmin.model.user.UserProfile', {
    extend : 'Ext.data.Model',
    fields : [
            {
                name : "address1",
                type : 'string'
            }, {
                name : "address2",
                type : 'string'
            }, {
                name : "birthdate",
                type : 'string',
            }, {
                name : "city",
                type : 'string'
            }, {
                name : "country",
                type : 'string',
            }, {
                name : "firstname",
                type : 'string',
            }, {
                name : "image",
                type : 'string',
            }, {
                name : "lastname",
                type : 'string',
            }, {
                name : "middlename",
                type : 'string',
            }, {
                name : "phone",
                type : 'string',
            }, {
                name : "salutation",
                type : 'string',
            }, {
                name : "sex",
                type : 'string',
            }, {
                name : "title",
                type : 'string',
            }
    ]
});
