/**
 * 
 */
Ext.define('RODAdmin.store.audit.RevisionbyObject', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.audit.ObjectRevisions',
        'RODAdmin.model.audit.RevisionRow',
        'RODAdmin.model.audit.RevisionField',        
        'RODAdmin.model.audit.RevisionsforObject'
       ],

    model: 'RODAdmin.model.audit.ObjectRevisions',
    autoload: true,    
    proxy: {type: 'main', url: 'adminjson/revisions-by-object'},  
});