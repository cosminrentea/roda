Ext.define('RODAdmin.store.dashboard.Trafic', {
    extend: 'RODAdmin.store.Base',	

    requires: [
        'RODAdmin.model.dashboard.Trafic',
    ],

    model: 'RODAdmin.model.dashboard.Trafic',
   
    autoLoad: true,
    proxy: {type: 'main', url: 'http://roda.apiary.io/dashboard/trafic'},

});