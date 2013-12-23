/**
 * 
 */
Ext.define('RODAdmin.store.common.Audit', {
    extend: 'RODAdmin.store.Base',	

    requires: [
        'RODAdmin.model.common.Audit',
        'RODAdmin.model.common.AuditField'
    ],

    model: 'RODAdmin.model.common.Audit',
    proxy: {type: 'main', url: 'http://roda.apiary.io/admin/audit/object/'},      
    autoload: true,
});