Ext.define('RODAdmin.view.security.GroupPermissions', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.grouppermissions',

    requires: ['RODAdmin.store.security.Permissions'],

    title: 'Group Permissions',
    rootVisible: false,
    useArrows: true,
    frame: false,
    viewConfig: {
	    markDirty: false
	},

    store: null//'security.Permissions'//Ext.create('Packt.store.security.Permissions')

});