/**
 * 
 */
Ext.define('RODAdmin.store.cms.news.NewsItem', {
    extend: 'RODAdmin.store.Base',		

    requires: [
        'RODAdmin.model.cms.news.NewsItem',
    ],

    model: 'RODAdmin.model.cms.news.NewsItem',
    
    autoload: true,
    proxy: {type: 'main', url: 'adminjson/cmsnewslist'},    

});