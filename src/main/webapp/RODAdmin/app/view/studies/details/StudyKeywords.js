/**
 * 
 */
Ext.define('RODAdmin.view.studies.details.StudyKeywords', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.studykeywords',
    itemId: 'studykeywords',
    title:'Study Keywords',
//    id: 'layoutusage',
	collapsible: true,
	   columns: [
        {
            text: 'Id',
            width: 100,
            dataIndex: 'id'
        },
        {
            text: 'Name',
            flex: 1,
            dataIndex: 'name'
        },
        ]	
});
