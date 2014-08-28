/**
 * 
 */
Ext.define('RODAdmin.view.studies.details.StudyVariables', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.studyvariables',
    itemId: 'studyvariables',
    title:'Study Variables',
	collapsible: true,
	   columns: [
        {
            text: 'Id',
            width: 100,
            dataIndex: 'indice'
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
