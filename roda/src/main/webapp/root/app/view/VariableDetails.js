Ext.define('databrowser.view.VariableDetails', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.variabledetails',
    autoRender: true,
    id: 'VariableDetails',
    itemId: 'VariableDetails',
    width: '100%',
    height:600,
    header: false,
    layout: 'fit',
    border: 1,
    items : [
	         {
        xtype: 'freqchart',
        id: 'freqchart'
	
	         }],
	
	
});