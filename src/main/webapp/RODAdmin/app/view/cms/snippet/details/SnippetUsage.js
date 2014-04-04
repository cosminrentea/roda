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
            text: 'Id',
            width: 100,
            dataIndex: 'id'
        },
        {
            text: 'Title',
            flex: 1,
            dataIndex: 'name'
        },
        {
            text: 'Type',
            flex: 1,
            dataIndex: 'type'
        }
        ]	
});
