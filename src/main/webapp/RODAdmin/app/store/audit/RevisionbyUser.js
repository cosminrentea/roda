/**
 * 
 */
Ext.define('RODAdmin.store.audit.RevisionbyUser', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.audit.UserRevisions',
        'RODAdmin.model.audit.RevisionRow',
        'RODAdmin.model.audit.RevisionField',
        'RODAdmin.model.audit.RevisionObject',
        'RODAdmin.model.audit.RevisionsforUser'
       ],

    model: 'RODAdmin.model.audit.UserRevisions',
    autoload: true,    
    proxy: {type: 'main', url: 'revisions-by-user'},  
});