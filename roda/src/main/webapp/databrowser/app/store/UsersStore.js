/*
 * File: app/store/UsersStore.js
 *
 * This file was generated by Sencha Architect version 2.2.1.
 * http://www.sencha.com/products/architect/
 *
 * This file requires use of the Ext JS 4.2.x library, under independent license.
 * License of Sencha Architect does not include license for Ext JS 4.2.x. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * This file will be auto-generated each and everytime you save your project.
 *
 * Do NOT hand edit this file.
 */

Ext.define('databrowser.store.UsersStore', {
    extend: 'Ext.data.Store',

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            storeId: 'UsersStore',
            proxy: {
                type: 'rest',
                url: '../../userses',
                reader: {
                    type: 'json'
                }
            },
            fields: [
                {
                    name: 'password'
                },
                {
                    name: 'username'
                }
            ]
        }, cfg)]);
    }
});