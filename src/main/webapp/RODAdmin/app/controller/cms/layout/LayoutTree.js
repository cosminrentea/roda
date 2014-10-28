/**
 * 
 */
Ext.define('RODAdmin.controller.cms.layout.LayoutTree', {
	extend : 'Ext.app.Controller',

	/**
	 * @todo getstores De convertit toate apelurile catre store-uri spre
	 *       getStore.
	 */

	stores : [
	          'cms.layout.LayoutTree',
	          'cms.layout.LayoutItem',
	          'cms.layout.LayoutGroup',
	          'cms.layout.Layout',
	          'common.Audit'
	          ],

	          views : [
	                   'RODAdmin.view.cms.layout.Layouts',
	                   'RODAdmin.view.cms.layout.LayoutItemsview',
	                   'RODAdmin.view.cms.layout.LayoutDetails',
	                   'RODAdmin.view.cms.layout.details.LayoutProperties',
	                   "RODAdmin.view.cms.layout.EditLayoutWindow",
	                   'RODAdmin.view.cms.layout.LayoutContextMenu',
	                   'RODAdmin.view.cms.layout.LayoutGroupContextMenu',
	                   'RODAdmin.view.cms.layout.AddLayoutToGroupWindow',
	                   'RODAdmin.view.cms.layout.LayoutItemviewContextMenu',
	                   'RODAdmin.view.cms.layout.details.LayoutUsage'
	                   ],

	                   refs : [
	                           {
	                        	   ref : 'lydetailspanel',
	                        	   selector : 'cmslayouts panel#lydetailscontainer '
	                           }, {
	                        	   ref : 'lyusagepanel',
	                        	   selector : 'layoutusage'
	                           }, {
	                        	   ref : 'layoutproperties',
	                        	   selector : 'layoutproperties panel#lydata '
	                           }, {
	                        	   ref : 'lycontent',
	                        	   selector : 'layoutproperties panel#lyenvelope codemirror#lycontent'
	                           },
	                           {
	                        	   ref : 'lyenvelope',
	                        	   selector : 'layoutproperties panel#lyenvelope'
	                           },
	                           {
	                        	   ref : 'itemsview',
	                        	   selector : 'layoutitemsview'
	                           }, {
	                        	   ref : "folderview",
	                        	   selector : "layoutitemsview treepanel#lyfolderview"
	                           }, {
	                        	   ref : 'folderselect',
	                        	   selector : 'layoutedit treepanel#groupselect'
	                           }
	                           ],



	                           /**
	                            * @method
	                            */
	                           init : function(application) {
	                        	   this.control({
	                        		   "layoutitemsview treepanel#lyfolderview" : {
	                        			   /**
	                        			    * @listener layoutitemsview-treepanel-folderview-selectionchange
	                        			    *           triggered-by:
	                        			    *           {@link RODAdmin.view.cms.layout.LayoutItemview LayoutItemsview}
	                        			    *           treepanel#lyfolderview executes
	                        			    *           {@link #onFolderviewSelectionChange}
	                        			    */
	                        			   selectionchange : this.onFolderviewSelectionChange,
	                        			   /**
	                        			    * @listener layoutitemsview-treepanel-folderview-itemcontextmenu
	                        			    *           triggered-by:
	                        			    *           {@link RODAdmin.view.cms.layout.LayoutItemview LayoutItemsview}
	                        			    *           treepanel#lyfolderview executes
	                        			    *           {@link #onTreeContextMenu}
	                        			    */
	                        			   itemcontextmenu : this.onTreeContextMenu
	                        		   },
	                        		   "layoutitemsview treepanel#lyfolderview toolbar button#reloadtree" : {
	                        			   /**
	                        			    * @listener layoutitemsview-treepanel-lyfolderview-toolbar-button-reloadtree
	                        			    *           triggered-by:
	                        			    *           {@link RODAdmin.view.cms.layout.LayoutItemview LayoutItemsview}
	                        			    *           treepanel#lyfolderview toolbar button#reloadtree
	                        			    *           {@link #onReloadTreeClick}
	                        			    */
	                        			   click : this.onReloadTreeClick
	                        		   },
	                        		   "layoutitemsview treepanel#lyfolderview toolbar button#addroot" : {
	                        			   /**
	                        			    * @listener layoutitemsview-treepanel-lyfolderview-toolbar-button-reloadtree
	                        			    *           triggered-by:
	                        			    *           {@link RODAdmin.view.cms.layout.LayoutItemview LayoutItemsview}
	                        			    *           treepanel#lyfolderview toolbar button#reloadtree
	                        			    *           {@link #onReloadTreeClick}
	                        			    */
	                        			   click : this.onAddRootClick
	                        		   },	                        		   
	                        		   "layoutitemsview treepanel#lyfolderview toolbar button#collapsetree" : {
	                        			   /**
	                        			    * @listener layoutitemsview-treepanel-lyfolderview-toolbar-button-collapsetree
	                        			    *           triggered-by:
	                        			    *           {@link RODAdmin.view.cms.layout.LayoutItemview LayoutItemsview}
	                        			    *           treepanel#lyfolderview toolbar button#collapsetree
	                        			    *           {@link #onCollapseTreeClick}
	                        			    */
	                        			   click : this.onCollapseTreeClick
	                        		   },
	                        		   "layoutitemsview treepanel#lyfolderview toolbar button#expandtree" : {
	                        			   /**
	                        			    * @listener layoutitemsview-treepanel-lyfolderview-toolbar-button-expandtree
	                        			    *           triggered-by:
	                        			    *           {@link RODAdmin.view.cms.layout.LayoutItemview LayoutItemsview}
	                        			    *           treepanel#lyfolderview toolbar button#expandtree
	                        			    *           {@link #onExpandTreeClick}
	                        			    */	        	
	                        			   click : this.onExpandTreeClick
	                        		   },
	               					"layoutitemsview treepanel#lyfolderview > treeview" : {
	            			            /**
	            						 * @listener itemsview-treepanel-folderview-treeview-drop triggered-by:
	            						 *           {@link RODAdmin.view.cms.files.Itemsview Itemsview}
	            						 *           treepanel#folderview > treeview  
	            						 *           {@link #onTreeDrop}
	            						 */	        	
	            						drop: this.onTreeDrop
	            					},
	                        		   "layoutcontextmenu menuitem#deletelayout" : {
	                        			   /**
	                        			    * @listener layoutcontextmenu-menuitem-deletelayout
	                        			    *           triggered-by:
	                        			    *           {@link RODAdmin.view.cms.layout.LayoutContextMenu LayoutContextMenu}
	                        			    *           menuitem#deletelayout {@link #onDeleteLayoutClick}
	                        			    */	        	
	                        			   click : this.onDeleteLayoutClick
	                        		   },
	                        		   "layoutcontextmenu menuitem#editlayout" : {
	                        			   /**
	                        			    * @listener layoutcontextmenu-menuitem-editlayout triggered-by:
	                        			    *           {@link RODAdmin.view.cms.layout.LayoutContextMenu LayoutContextMenu}
	                        			    *           menuitem#editlayout {@link #onEditLayoutClick}
	                        			    */	        	
	                        			   click : this.onEditLayoutClick
	                        		   },
	                        		   "layoutgroupcontextmenu menuitem#addlayout" : {
	                        			   /**
	                        			    * @listener layoutgroupcontextmenu-menuitem-addlayout
	                        			    *           triggered-by:
	                        			    *           {@link RODAdmin.view.cms.layout.LayoutGroupContextMenu LayoutGroupContextMenu}
	                        			    *           menuitem#addlayout {@link #onAddLayoutClick}
	                        			    */	        	
	                        			   click : this.onAddLayoutClick
	                        		   },
	                        		   "layoutgroupcontextmenu menuitem#newgroup" : {
	                        			   /**
	                        			    * @listener layoutgroupcontextmenu-menuitem-newgriup
	                        			    *           triggered-by:
	                        			    *           {@link RODAdmin.view.cms.layout.LayoutGroupContextMenu LayoutGroupContextMenu}
	                        			    *           menuitem#newgroup {@link #onNewGroupClick}
	                        			    */	        	
	                        			   click : this.onNewGroupClick
	                        		   },
	                        	   });
	                           },
	                           
	                           onTreeDrop : function(node,data,overModel,dropPosition) {
	                           	console.log('moved ' + data.records[0].data.name + ' to ' + overModel.data.name + ' ' + dropPosition );
	                           },
	                           
	                           /**
	                            * @method
	                            */
	                           onGroupselectCellClick : function(component, td, cellIndex, record, tr, rowIndex, e, eOpts) {
	                        	   console.log('were here');
	                        	   component.up('layoutedit').down('form').down('fieldset').query('displayfield')[0].setValue(record.data.name);
	                        	   component.up('layoutedit').down('fieldset').query('hiddenfield')[0].setValue(record.data.id);
	                           },
	                           /**
	                            * @method
	                            */
	                           onNewGroupClick : function(component, event) {
	                        	   var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
	                        	   console.log('indice nod curent'+ currentNode.data.indice);
	                        	   var win = Ext.create('RODAdmin.view.cms.layout.GroupWindow');
	                        	   win.setTitle('Add a new subgroup to "' + currentNode.data.name + '" (id: ' + currentNode.data.indice + ')');
	                        	   win.setIconCls('group_add');
	                        	   
	                        	   win.down('form').down('fieldset').down('hiddenfield').setValue(currentNode.data.indice);
	                        	   console.log(currentNode.data);
	                        	   win.show();
	                           },
	                           /**
	                            * @method
	                            */
	                           onAddLayoutClick : function(component, event) {
	                        	   var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
	                        	   console.log(currentNode);
	                        	   var win = Ext.create('RODAdmin.view.cms.layout.AddLayoutToGroupWindow');
	                        	   win.setTitle('Add a new layout to "' + currentNode.data.name + '" (id: ' + currentNode.data.indice + ')');
	                        	   win.setIconCls('file_add');
	                        	   win.down('form').down('fieldset').down('hiddenfield').setValue(currentNode.data.indice);
	                        	   win.down('form').down('fieldset').down('displayfield').setValue(currentNode.data.directory);
	                        	   win.show();
	                           },
	                           /**
	                            * @method
	                            */
	                           onDeleteLayoutClick : function(component, event) {
	                        	   var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
	                        	   var me = this;
	                        	   Ext.Msg.confirm('Delete Requirement', 'Are you sure you want to delete the layout ' + currentNode.data.name
	                        	                   + '?', function(id, value) {
	                        		   if (id === 'yes') {
	                        			   console.log('we will delete');
	                        			   Ext.Ajax.request({
	                        				   url : RODAdmin.util.Globals.baseurl + '/adminjson/layoutdrop',
	                        				   method : "POST",
	                        				   params : {
	                        					   layoutid : currentNode.data.indice
	                        				   },
	                        				   success : function() {
	                        					   RODAdmin.util.Alert.msg('Success!', 'Layout deleted.');
	                        					   me.getFolderview().store.load();
//	                        					   store.load;
	                        				   },
	                        				   failure : function(response, opts) {
	                        					   Ext.Msg.alert('Failure', response);

	                        				   }
	                        			   });
	                        		   }
	                        	   }, this);
	                        	   event.stopEvent();
	                           },
	                           /**
	                            * @method
	                            */
	                           onAddRootClick: function(button, e, options) {
	                        	   win = Ext.WindowMgr.get('lygroupadd');
	                        	   console.log(win);
	                        	   if (!win) {
	                        		   win = Ext.create('RODAdmin.view.cms.layout.GroupWindow');
	                        	   }
	                        	   win.setTitle('Add Root Group');
	                        	   win.show();
	                           },
	                           
	                           /**
	                            * @method
	                            */
	                           onEditLayoutClick : function(component, record, item, index, e) {
                        		   console.log('edit layout smth');
	                        	   var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
	                        	   if (currentNode.data.itemtype == 'layoutgroup') {	        
	                        		   console.log('edit layout group');
		                        	   win = Ext.WindowMgr.get('layoutgroupedit');
		                        	   console.log(win);
		                        	   if (!win) {
		                        		   win = Ext.create('RODAdmin.view.cms.layout.EditLayoutGroupWindow');
		                        	   }
		                        	   win.setTitle('Edit Layout Group');
//		                        	   var wtree = win.down('treepanel');
//		                        	   var layoutitemstore = Ext.create('RODAdmin.store.cms.layout.LayoutItem');
//		                        	   layoutitemstore.load({
//		                        		   id : currentNode.data.indice, // set the id here
//		                        		   scope : this,
//		                        		   callback : function(records, operation, success) {
//		                        			   if (success) {
//		                        				   var layoutitem = layoutitemstore.first();
//		                        				   win.down('form').getForm().loadRecord(layoutitem);
//		                        				   win.down('form').down('hiddenfield#groupid').setValue(layoutitem.data.groupid);
//		                        			   }
//		                        		   }
//		                        	   });
		                        	   win.show();
	                        	   } else {	   
                        		   console.log('edit layout group');	                        		   
	                        	   win = Ext.WindowMgr.get('layoutedit');
	                        	   console.log(win);
	                        	   if (!win) {
	                        		   win = Ext.create('RODAdmin.view.cms.layout.EditLayoutWindow');
	                        	   }
	                        	   win.setTitle('Edit Layout');
	                        	   var wtree = win.down('treepanel');
	                        	   var layoutitemstore = Ext.create('RODAdmin.store.cms.layout.LayoutItem');
	                        	   layoutitemstore.load({
	                        		   id : currentNode.data.indice, // set the id here
	                        		   scope : this,
	                        		   callback : function(records, operation, success) {
	                        			   if (success) {
	                        				   var layoutitem = layoutitemstore.first();
	                        				   win.down('form').getForm().loadRecord(layoutitem);
	                        				   win.down('form').down('hiddenfield#groupid').setValue(layoutitem.data.groupid);
	                        			   }
	                        		   }
	                        	   });
	                        	   win.show();
	                        	   }
	                           },
	                           /**
	                            * @method
	                            */
	                           onTreeContextMenu : function(component, record, item, index, e) {
	                        	   e.stopEvent();
	                        	   if (record.data.itemtype == 'layoutgroup') {
	                        		   if (!this.foldermenu) {
	                        			   this.groupmenu = Ext.create('widget.layoutgroupcontextmenu');
	                        		   }
	                        		   this.groupmenu.showAt(e.getXY());
	                        	   }
	                        	   else {
	                        		   if (!this.itemmenu) {
	                        			   this.itemmenu = Ext.create('widget.layoutcontextmenu');
	                        		   }
	                        		   this.itemmenu.showAt(e.getXY());
	                        	   }
	                           },
	                           /**
	                            * @method
	                            */
	                           onCollapseTreeClick : function(button, e, options) {
	                        	   console.log('onCollapseTreeClick');
	                        	   this.getFolderview().collapseAll();
	                           },
	                           /**
	                            * @method
	                            */
	                           onExpandTreeClick : function(button, e, options) {
	                        	   console.log('onExpandTreeClick');
	                        	   this.getFolderview().expandAll();
	                           },
	                           /**
	                            * @method
	                            */
	                           onReloadTreeClick : function(button, e, options) {
	                        	   var folderview = this.getFolderview();
	                        	   var currentNode = folderview.getSelectionModel().getLastSelected();
	                        	   console.log(currentNode);
	                        	   var mmstore = this.getFolderview().store;
	                        	   var me = this;
	                        	   mmstore.reload({
	                        		   callback : function(records, operation, success) {
	                        			   var root = me.getFolderview().store.getRootNode();
	                        			   var myid = root.findChild('indice', currentNode.data.indice, true);
	                        			   if (myid != null) {
	                        				   console.log(myid);
	                        				   folderview.getSelectionModel().select(myid);
	                        			   }
	                        		   }
	                        	   });
	                           },
	                           /**
	                            * @method
	                            */
	                           onFolderselectCellClick : function(component, td, cellIndex, record, tr, rowIndex, e, eOpts) {
	                        	   component.up('layoutedit').down('form').down('fieldset').query('displayfield')[0].setValue(record.data.name);
	                        	   component.up('layoutedit').down('fieldset').query('hiddenfield')[0].setValue(record.data.id);
	                           },
	                           
	                           /**
	                       	 * @method 
	                       	 * 
	                       	 * drop event for page tree
	                       	 */
	                           onTreeDrop : function(node,data,overModel,dropPosition) {
	                           	console.log('id ' + data.records[0].data.indice + ' group ' + overModel.data.indice + ' ' + dropPosition );

	                            if (data.records[0].data.itemtype == 'layoutgroup') {	    
	                            	console.log('moving layout group');
	                            	var newparent;
	                            	var tomove = data.records[0].data.indice;
	                            	if (dropPosition == 'append') {
	                            		newparent = overModel.data.indice;
	                            	} else {
	                            		newparent = overModel.parentNode.data.indice;
	                            	}
                            		console.log(newparent);
    	                           	Ext.Ajax.request({
	                       	        url : RODAdmin.util.Globals.baseurl + '/adminjson/layoutgroupmove/',
	                       	        method : "POST",
	                       	        params : {
		                       	            group : tomove,
		                       	            parent: newparent,
		                       	        },
		                       	        success : function(response,request) {
		                       		           var responseJson = Ext.decode(response.responseText);
		                       		            if (responseJson.success === true) {
		                       		                // whatever stuff needs to happen on success
//		                       		            	RODAdmin.util.Alert.msg('Success!', 'Page '+ pgtitle +' moved.');
		                       		            } else {
		                       		            	RODAdmin.util.Alert.msg('Failure!', responseJson.message, true);
		                       		            }
		                       	        },
		                       	        failure : function(response, opts) {
		                       		        Ext.Msg.alert('Failure', response);
		                       	        }
		                       	    });		
	                            } else {
	                            	console.log('moving layout');
	                            }
	                           	
//	                           	var pgtitle = data.records[0].data.title;
//	                           	var pgid = 	data.records[0].data.indice;
//	                           	var groupid = overModel.data.indice;
//	                           	var mode = dropPosition;
//	                           	Ext.Ajax.request({
//	                       	        url : '/roda/j/admin/cmspagemove/',
//	                       	        method : "POST",
//	                       	        params : {
//	                       	            id : pgid,
//	                       	            group: groupid,
//	                       	            mode: mode,
//	                       	        },
//	                       	        success : function(response,request) {
//	                       		           var responseJson = Ext.decode(response.responseText);
//	                       		            if (responseJson.success === true) {
//	                       		                // whatever stuff needs to happen on success
//	                       		            	RODAdmin.util.Alert.msg('Success!', 'Page '+ pgtitle +' moved.');
////	                       						store.load();
//	                       		            } else {
//	                       		            	RODAdmin.util.Alert.msg('Failure!', responseJson.message, true);
//
//	                       		            }
//	                       	        },
//	                       	        failure : function(response, opts) {
//	                       		        Ext.Msg.alert('Failure', response);
//
//	                       	        }
//	                       	    });		
//	                           	this.getFolderview().store.load();
	                           },
	                           
	                           /**
	                            * @method
	                            */
	                           onFolderviewSelectionChange : function(component, selected, event) {
	                        	   console.log('folderviewselectionchange');
	                        	   console.log(RODAdmin.util.Globals.stare);
	                        	   var record = selected[0];
	                        	   if (record) {
	                        	   var lydetails = this.getLydetailspanel();
                        		   var lyusage = this.getLyusagepanel();
                        		   var lyprop = this.getLayoutproperties();
                        		   var lycontent = this.getLycontent(); 
	                        	   if (record != null) {	    
	                        		   var lyenvelope = this.getLyenvelope();
	                        		   lydetails.setTitle(record.data.name);

	                        		   if (record.data.itemtype == 'layoutgroup') {
	                        			   lyusage.collapse(true);
	                        			   var lygroupstore = Ext.StoreManager.get('cms.layout.LayoutGroup');
	                        			   lycontent.setValue('');  
	                        			   lyenvelope.collapse();	
	                        			   lygroupstore.load({
	                        				   scope : this,
	                        				   id : record.data.indice, 
	                        				   callback : function(records, operation, success) {
	                        					   if (success) {
	                        						   var lyitem = lygroupstore.first();
	                        						   lyprop.update(lyitem);
	                        					   }
	                        				   }
	                        			   });
	                        		   }
	                        		   else {
	                        			   lyusage.expand();
	                        			   lyenvelope.expand();  
	                        			   var lyitemstore = Ext.StoreManager.get('cms.layout.LayoutItem');
	                        			   lyitemstore.load({
	                        				   id : record.data.indice, 
	                        				   scope : this,
	                        				   callback : function(records, operation, success) {
	                        					   if (success) {
	                        						   var lyitem = lyitemstore.first();
	                        						   lycontent.setValue(lyitem.data.content);
	                        						   lyprop.update(lyitem);
	                        						   if (typeof lyitem.usageStore === 'object') {
	                        							   lyusage.bindStore(lyitem.usage());
	                        						   }
	                        					   }
	                        				   }
	                        			   });
	                        		   }
	                        	   }
	                            else {
                       		   
                        		   lydetails.setTitle('');
                        		   lyprop.update('');
                        		   lyusage.store.removeAll();
                        		   lycontent.setValue('');
	                           }
	                   }
	                           }
});