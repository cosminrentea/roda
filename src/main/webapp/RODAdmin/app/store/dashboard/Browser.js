Ext.define('RODAdmin.store.dashboard.Browser', {
    extend: 'RODAdmin.store.Base',	

    requires: [
        'RODAdmin.model.dashboard.Browser',
    ],

    model: 'RODAdmin.model.dashboard.Browser',
   
    autoLoad: true,
    proxy: {type: 'main', url: '/adminjson/dashboard/browser'},
//    proxy: {type: 'main', url: 'http://roda.apiary.io/dashboard/browser'},
    //proxy: {type: 'main', url: 'cmspageinfo/'},

});