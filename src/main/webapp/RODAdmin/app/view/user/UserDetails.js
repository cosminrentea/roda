/**
 * 
 */
Ext.define('RODAdmin.view.user.UserDetails', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.userdetails',
			itemId : 'userdetails',
			activeItem : 0,
			width : '100%',
			layout : {
				type : 'card',
//				deferredRender : true,
				align : 'stretch'
			},
			items : [
			         {
			        	 xtype:'panel', //change with userview
			        	 title: 'Details',
			        	 html: 'Select an user or group'
			         },
			         {
			        	 xtype:'panel', //change with userview
			        	 title: 'userdetail',
			        	 html: 'yuppie user here'
			         },
			         {
			        	 xtype:'panel', //change with groupview
			        	 title: 'groupdetail',
			        	 html: 'yuppie group here'
			         }
			         ],
});