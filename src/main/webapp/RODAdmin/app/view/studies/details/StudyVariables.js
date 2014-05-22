/**
 * 
 */
Ext.define('RODAdmin.view.studies.details.StudyVariables', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.studyvariables',
    itemId: 'studyvariables',
    title:'Study Variables',
//    id: 'layoutusage',
	collapsible: true,
	   columns: [
        {
            text: 'Variable Id',
            width: 100,
            dataIndex: 'id'
        },
        {
            text: 'Name',
            flex: 1,
            dataIndex: 'name'
        },
        {
            text: 'Label',
            flex: 1,
            dataIndex: 'label'
        }
        
        ]	
});
