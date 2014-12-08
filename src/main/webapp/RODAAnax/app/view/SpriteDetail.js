Ext.define('anax.view.SpriteDetail', {
			extend : 'Ext.window.Window',
			alias : 'widget.spritedetail',
			height : 450,
			id : 'spritedetail',
			y: 160,
			width : 350,
			bodyPadding : 10,
			layout : {
				type : 'vbox',
				align: 'stretch',
				pack: 'start'
			},
			items: [
			        {
				       	xtype: 'panel',
				       	flex: 1,
				       	itemId: 'spdraw',
				       	layout: 'fit',
				       	items: [
				       	        {
				       	        	xtype: 'anaxdrawing',
				       	        	itemId : 'detaildraw'
				       	        }
				       	        
				       	        ]
			      
			        },
			        {
			        	xtype: 'panel',
			        	itemId: 'spdetails'	,
			        	flex: 2,
			        	tpl: '<div class="deti">jesus man...{name} - {details} </div>'
			        }
			        
			        ]
			
});
