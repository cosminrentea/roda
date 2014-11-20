/**
 * 
 */
Ext.define('databrowser.view.SearchResults', {
			extend : 'Ext.window.Window',
			alias : 'widget.searchres',
			height : 250,
			y: 160,
			width : 550,
			bodyPadding : 10,
			layout : {
				type : 'fit'
			},
			title : 'Search',
						items : [{
							xtype : 'container',
							itemId : 'srcres',
							autoScroll: true,
							tpl :  new Ext.XTemplate(
							                         '{numFound} {results}',
							                         '<tpl for="docs">',
							                         '<h3><a href="{url}">{name}</a></H3>',
							                         '<p><div class="srcreshigh">{highlight}</div>',
							                         '</p>',
							                         '</tpl>'
							                         ),
					}]
		});
