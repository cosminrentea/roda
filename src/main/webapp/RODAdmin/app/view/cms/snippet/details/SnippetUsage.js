/**
 * 
 */
Ext.define('RODAdmin.view.cms.snippet.details.SnippetUsage', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.snippetusage',
    itemId: 'snippetusage',
    title:'Snippet Usage',
//    id: 'layoutusage',
	collapsible: true,
	   columns: [
        {
            text: 'Page Id',
            width: 100,
            dataIndex: 'id'
        },
        {
            text: 'Title',
            flex: 1,
            dataIndex: 'name'
        },
        {
            text: 'Url',
            flex: 1,
            dataIndex: 'url'
        },
        {
            text: 'Language',
            flex: 1,
            dataIndex: 'lang'
        },
        {
            text: 'Visible',
            flex: 1,
            dataIndex: 'visible'
        },
        {
            text: 'Page Type',
            flex: 1,
            dataIndex: 'pagetypename'
        }
        
        ]	
});
