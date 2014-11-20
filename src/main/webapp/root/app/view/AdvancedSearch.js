/**
 * 
 */
Ext.define('databrowser.view.AdvancedSearch', {
			extend : 'Ext.window.Window',
			alias : 'widget.advsearch',
			height : 350,
			y: 160,
			width : 550,
			bodyPadding : 10,
			layout : {
				type : 'card'
			},
			title : 'Search',
			autoScroll : true,

			items : [{
				xtype : 'form',
				itemId : 'searchform',
				bodyPadding : 10,				
				layout : {
					type : 'anchor',
					align : 'stretch'
				},
				defaults : {
					anchor : '100%',
					xtype : 'textfield',
					allowBlank : false,
					labelWidth : 100
				},
				dockedItems : [{
					xtype : 'cancelsave'
				}],
				items : [{
										fieldLabel : 'Study title',
										name : 'stitle'
									}, 
									{
										fieldLabel : 'Study abstract',
										name : 'stabstract'
									},									
									{
										fieldLabel : 'Study universe',
										name : 'stuniverse'
									},									
									{
										fieldLabel : 'Study variables',
										name : 'stvariables'
									},									
									{
										fieldLabel : 'Research type',
										name : 'strtype'
									},
									
									]
			}, {
				xtype: 'container',
				itemId : 'srcresults',
				autoScroll: true,
				tpl :  new Ext.XTemplate(
				                         '{numFound} {results}',
				                         '<tpl for="docs">',
				                         '<h3><a href="{url}">{name}</a></H3>',
				                         '<p><div class="srcreshigh">{highlight}</div>',
				                         '</p>',
				                         '</tpl>'
				                         ),
			}
			
			
			]
			
			
		});
