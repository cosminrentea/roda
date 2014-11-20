/**
 * 
 */
Ext.define('RODAdmin.view.cms.layout.details.LayoutUsage', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.layoutusage',
    itemId: 'layoutusage',
    title:'Layout Usage',
//    id: 'layoutusage',
	collapsible: true,
	   columns: [
        {
            text: translations.ly_pageid,
            width: 100,
            dataIndex: 'id'
        },
        {
            text: translations.ly_title,
            flex: 1,
            dataIndex: 'name'
        },
        {
            text: translations.ly_url,
            flex: 1,
            dataIndex: 'url'
        },
        {
            text: translations.ly_lang,
            xtype: 'templatecolumn',            
            flex: 1,
			tpl: '<div class="lang_{lang}">&nbsp;</div>',
            dataIndex: 'lang'
        },
        {
            text: translations.ly_visible,
            flex: 1,
            dataIndex: 'visible'
        },
        {
            text: translations.ly_pagetype,
            flex: 1,
            dataIndex: 'pagetype'
        }
        
        ]	
});
