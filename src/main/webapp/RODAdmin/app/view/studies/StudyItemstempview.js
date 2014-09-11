/**
 * 
 */
Ext.define('RODAdmin.view.studies.StudyItemstempview', {
			extend : 'Ext.panel.Panel',
			/**
			 * @config
			 */
			alias : 'widget.studyitemstempview',
			itemId : 'studyitemstempview',
			activeItem : 1,
			width : '100%',
			layout : {
				type : 'fit',
			},
			items : [{
						xtype : 'grid',
						itemId : 'sticonview',
						itemSelector : 'div.thumb-wrap',
						store : 'studies.TempStudy',
						features : [Ext.create('Ext.ux.grid.FiltersFeature', {
									local : true
								})],
						columns : [
						           	{
						           		text : 'id',
						           		width : 25,
						           		dataIndex : 'indice',
						           		sortable : true,
						           		filter : {
						           			type : 'integer'
						           		}
						           	},
						           {
									itemId : 'colname',
									text : 'Study',
									flex : 2,
									sortable : true,
									dataIndex : 'name',
									filterable : true
						           },
						           {
									itemId : 'colsize',
									text : 'Size',
									flex : 1,
									sortable : true,
									dataIndex : 'filesize',
									filterable : true
						           },
						           ]
					}]
		});
