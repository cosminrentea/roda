/**
 * 
 */
Ext.define('RODAdmin.store.audit.RevisionInfo', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.audit.Revisions',
        'RODAdmin.model.audit.RevisionObject',
        'RODAdmin.model.audit.RevisionRow',
        'RODAdmin.model.audit.RevisionField'        
    ],

    model: 'RODAdmin.model.audit.Revisions',
    autoload: true,    
    proxy: {type: 'main', url: 'revisionsinfo'},  
});