/**
 * 
 */
Ext.define('RODAdmin.store.cms.news.News', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.cms.news.News'
    ],

    model: 'RODAdmin.model.cms.news.News',
    autoload: true,   
    proxy: {type: 'main', url: 'cmsnewslist'},    
 
});