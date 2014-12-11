/**
 * 
 */
Ext.define('RODAdmin.view.studies.CBEditor.studyadd.sDataProd', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.sdataprod',
	autoScroll : true,
	title : 'Data Production',
	items : [{
		xtype : 'form',
		bodyPadding : 5,
		itemId : 'dataprodform',
		items : [{
					xtype : 'fieldset',
					itemId : 'dpdate',
					title : 'Date',
					layout : 'hbox',
					items : [{
								xtype : 'datefield',
								fieldLabel : 'Start Data Production',
								name : 'stdataprod',
								itemId : 'stdataprod',
								padding : 5,
								flex : 1,
								value : ''
							}, {
								xtype : 'datefield',
								fieldLabel : 'End Data Production',
								name : 'enddataprod',
								itemId : 'enddataprod',
								padding : 5,
								flex : 1,
								value : ''
							}]
				}, {
					xtype : 'fieldset',
					itemId : 'dpresponsibility',
					title : 'Responsibility',
					collapsible : true,
					items : [{
						xtype : 'grid',
						itemId : 'dprespdisplay',
						store : 'studies.CBEditor.DPResp',
						columns : [{
									itemId : 'status',
									text : 'Status',
									sortable : true,
									flex : 1,
									dataIndex : 'status'
								}, {
									itemId : 'type',
									text : 'Type',
									sortable : true,
									flex : 1,
									dataIndex : 'type'
								}, {
									itemId : 'name',
									text : 'Name',
									sortable : true,
									flex : 2,
									dataIndex : 'selectedname'
								}, {
									xtype : 'actioncolumn',
									width : 30,
									sortable : false,
									menuDisabled : true,
									itemId : 'actionqcoderesp',
									items : [{
										icon : 'images/delete.gif',
										tooltip : 'Delete Plant',
										scope : this,
										handler : function(view, rowIndex,
												colIndex, item, e, record, row) {
											var mygrid = view.up('grid');
											mygrid.fireEvent('deleteRecord',
													mygrid, record, rowIndex,
													row);
										}
									}]
								}]
					}, {
						xtype : 'button',
						itemId : 'dpresp',
						text : 'Add Data Production Responsibility'
					}]
				}, {
					xtype : 'fieldset',
					itemId : 'dpvariables',
					collapsible: true,
					title : 'Variables',
					items : [{
						xtype : 'grid',
						itemId : 'variabledisplay',
						store : 'studies.CBEditor.Variables',
						plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
									clicksToEdit : 2
								})],
						selModel : {
							selType : 'cellmodel'
						},
						tbar : [{
									text : 'Add Variable',
									itemId : 'addvariable'
								}],
						columns : [{
									itemId : 'id',
									text : 'ID',
									sortable : true,
									hidden: true,
									dataIndex : 'id'
								}, {
									itemId : 'question',
									text : 'Question',
									sortable : true,
									flex : 1,
									dataIndex : 'oqtext'
								}, {
									itemId : 'vname',
									text : 'Variable name',
									sortable : true,
									flex : 1,
									dataIndex : 'name',
									editor : {
										xtype : 'textfield',
										allowBlank : false
									}
								}, {
									itemId : 'vlabel',
									text : 'Label',
									flex: 2,
									sortable : true,
									dataIndex : 'label'
								}, {
									xtype : 'actioncolumn',
									width : 60,
									sortable : false,
									menuDisabled : true,
									itemId : 'actionvariabledisplay',
									items : [{
										icon : 'images/cog_edit.png',
										tooltip : 'Edit Variable',
										scope : this,
										handler : function(view, rowIndex,
												colIndex, item, e, record, row) {
											console.log('firing event edit');
											var mygrid = view.up('grid');
											mygrid.fireEvent('editRecord',
													mygrid, record, rowIndex,
													row);
										}
									}, {
										icon : 'images/delete.gif',
										tooltip : 'Delete Variable',
										scope : this,
										handler : function(view, rowIndex,
												colIndex, item, e, record, row) {
											console.log('firing event delete');
											var mygrid = view.up('grid');
											mygrid.fireEvent('deleteRecord',
													mygrid, record, rowIndex,
													row);
										}
									}]
								}]
					}]

				}, {
					xtype : 'fieldset',
					itemId : 'dpdatafiles',
					title : 'Data files',
					collapsible: true,
					items : [{
						xtype : 'grid',
						itemId : 'filesdisplay',
						store : 'studies.CBEditor.Files',
						columns : [{
									itemId : 'id',
									text : 'ID',
									sortable : true,
									hidden: true,
									dataIndex : 'id'
								}, {
									itemId : 'name',
									text : 'File name',
									sortable : true,
									flex : 1,
									dataIndex : 'fname'
								}, {
									itemId : 'ftype',
									text : 'File type',
									sortable : true,
									flex : 1,
									dataIndex : 'ftype',
								}, 
								{
									itemId : 'uptype',
									text : 'Upload type',
									sortable : true,
									flex : 1,
									dataIndex : 'uptype',
								}, 
								{
									itemId : 'uploadid',
									text : 'Upload id',
									sortable : true,
									flex : 1,
									dataIndex : 'uploadid',
								}, 
								{
									itemId : 'furi',
									text : 'URI',
									flex: 2,
									sortable : true,
									dataIndex : 'uri'
								}, {
									xtype : 'actioncolumn',
									width : 60,
									sortable : false,
									menuDisabled : true,
									itemId : 'actionvariabledisplay',
									items : [{
										icon : 'images/delete.gif',
										tooltip : 'Delete file',
										scope : this,
										handler : function(view, rowIndex,
												colIndex, item, e, record, row) {
											console.log('firing event delete');
											var mygrid = view.up('grid');
											mygrid.fireEvent('deleteRecord',
													mygrid, record, rowIndex,
													row);
										}
									}]
								}]
					},
					{
						xtype : 'button',
						itemId : 'addfile',
						text : 'Add File'
					}]
				}
		]
	}]
});
