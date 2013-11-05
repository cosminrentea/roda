Ext.define('RODAdmin.store.cms.layout.Layout', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.cms.layout.Layout'
    ],

    model: 'RODAdmin.model.cms.layout.Layout',
    autoload: true,    
    proxy: {
        type: 'ajax',
//        url: 'data/layout/layoutlist.json',
//		url: 'http://roda.apiary.io/admin/layout/layoutlist',
		url: 'http://localhost:8080/roda/admin/cmslayoutlist',
        reader: {
                type: 'json',
                root: 'data'
        },
        listeners: {
            exception: function(proxy, response, operation){
                Ext.MessageBox.show({
                    title: 'REMOTE EXCEPTION',
                    msg: operation.getError(),
                    icon: Ext.MessageBox.ERROR,
                    buttons: Ext.Msg.OK
                });
            }
        }
    }
});