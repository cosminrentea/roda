/**
 * Studylist - controller care se ocupa de lista de studii din
 * {@link RODAdmin.view.cms.layout.LayoutItemsview LayoutItemsview}
 */
Ext.define('RODAdmin.controller.studies.StudyList', {
    extend : 'Ext.app.Controller',

  
    stores : [
              'studies.Study', 
              'common.Audit'],

    
    views : [
            'RODAdmin.view.studies.StudyItemsview',
            'RODAdmin.view.studies.StudyDetails',
            'RODAdmin.view.studies.details.StudyProperties',
            'RODAdmin.view.studies.details.StudyVariables'
    ],
    /**
	 * @cfg
	 */
    refs : [
            {
                ref : 'iconview',
                selector : 'studyitemsview grid#sticonview'
            }, {
                ref : 'studyproperties',
                selector : 'studyproperties panel#stdata'
            }, {
                ref : 'stdetailspanel',
                selector : 'studies panel#stdetailscontainer '
            }, 
            {
                ref : 'stenvelope',
                 selector : 'studyproperties panel#stenvelope'
            },
            {
                ref : 'stcontent',
                selector : 'studyproperties panel#stenvelope codemirror#stcontent'
            }            

    ],
    /**
	 * @method
	 */
    init : function(application) {
	    this.control({
	        "studyitemsview grid#sticonview" : {
	            /**
				 * @listener studyitemsview-selectionchange triggered-by:
				 *           {@link RODAdmin.view.studies.StudyItemsview StudyItemsview}
				 *           grid#sticonview executes
				 *           {@link #onDeleteStudyClick}
				 */
	            selectionchange : this.onIconViewSelectionChange,
	            /**
				 * @listener icdeletestudy-itemcontextmenu triggered-by:
				 *           {@link RODAdmin.view.studies.StudyItemsview StudyItemsview}
				 *           grid#sticonview executes {@link #onItemContextMenu}
				 */
	            itemcontextmenu : this.onItemContextMenu
	        },
	        "studyitemviewcontextmenu menuitem#icdeletestudy" : {
		        /**
				 * @listener icdeletestudy-click triggered-by:
				 *           {@link RODAdmin.view.studies.StudyItemviewContextMenu StudyItemsviewContextMenu}
				 *           menuitem#icdeletestudy executes
				 *           {@link #onDeleteStudyClick}
				 */
		        click : this.onDeleteStudyClick
	        },
	        "studyitemviewcontextmenu menuitem#iceditstudy" : {
		        /**
				 * @listener iceditstudy-click triggered-by:
				 *           {@link RODAdmin.view.studies.StudyItemviewContextMenu StudyItemsviewContextMenu}
				 *           menuitem#iceditstudy executes
				 *           {@link #onEditStudyClick}
				 * 
				 */
		        click : this.onEditStudyClick
	        },
	        "studyitemsview grid#sticonview toolbar button#reloadgrid" : {
	            /**
				 * @listener studyitemsview-treepanel-stfolderview-toolbar-button-reloadtree triggered-by:
				 *           {@link RODAdmin.view.studies.StudyItemsview StudyItemsview}
				 *           treepanel#stfolderview toolbar button#reloadtree
				 *           {@link #onReloadTreeClick}
				 */
		        click : this.onReloadGridClick
	        }, 
	    });
    },
    /**
	 * @method
	 */
    onEditStudyClick : function(component, record, item, index, e) {
	    console.log('onEditStudyClick');
	    var currentNode = this.getIconview().getSelectionModel().getLastSelected();
	    win = Ext.WindowMgr.get('studyedit');
	    console.log(win);
	    if (!win) {
	    	win = Ext.create('RODAdmin.view.studies.EditStudyWindow');
	    }	    
	    win.setTitle('Edit Study');
	    var wtree = win.down('treepanel');
	    var studyitemstore = Ext.create('RODAdmin.store.studies.StudyItem');
	    studyitemstore.load({
	        id : currentNode.data.indice, // set the id here
	        scope : this,
	        callback : function(records, operation, success) {
		        if (success) {
			        var studyitem = studyitemstore.first();
			        win.down('form').getForm().loadRecord(studyitem);
		        }
	        }
	    });
	    win.show();
    },
    /**
	 * @method
	 */
    onDeleteStudyClick : function(component, event) {
	    var currentNode = this.getIconview().getSelectionModel().getLastSelected();
	    var store = Ext.StoreManager.get('studies.Study');
	    Ext.Msg.confirm('Delete Requirement', 'Are you sure you want to delete the study ' + currentNode.data.name
	            + '?', function(id, value) {
		    if (id === 'yes') {
			    console.log('we will delete');
			    Ext.Ajax.request({
			        url : '/roda/j/admin/studydrop',
			        method : "POST",
			        params : {
				        studyid : currentNode.data.indice
			        },
			        success : function() {
				        RODAdmin.util.Alert.msg('Success!', 'Study deleted.');
				        store.load;
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
    onIconViewSelectionChange : function(component, selected, event) {
	    console.log('folderviewselectionchange');
	    var record = selected[0];
	    if (record != null) {
	    console.log(record);
	    var stprop = this.getStudyproperties();
	    var stdetails = this.getStdetailspanel();
	    //variabilele!
	    //var stcontent = this.getStcontent();
	    var stenvelope = this.getStenvelope();	    
	    stdetails.setTitle(record.data.name);

	    
	    stenvelope.expand();  
	    var stitemstore = Ext.StoreManager.get('studies.StudyItem');
	    stitemstore.load({
	        id : record.data.indice, // set the id here
	        scope : this,
	        callback : function(records, operation, success) {
		        if (success) {
			        var stitem = stitemstore.first();
			    //    stcontent.setValue(stitem.data.content);
			        stprop.update(stitem);
//			        if (typeof stitem.usageStore === 'object') {
//						   lyusage.bindStore(lyitem.usage());
//					   }
		        }
	        }
	    });
	    }
    },
    /**
	 * @method
	 */
    onItemContextMenu : function(component, record, item, index, e) {
	    e.stopEvent();
	    if (!this.contextmenu) {
		    this.contextmenu = Ext.create('widget.studyitemviewcontextmenu');
	    }
	    this.contextmenu.showAt(e.getXY());
    },
    /**
	 * @method
	 */
    onReloadGridClick : function(button, e, options) {
	    console.log('reloading grid');
	    var iconview = this.getIconview();
	    var currentNode = iconview.getSelectionModel().getLastSelected();
	    console.log(currentNode);
	    var mmstore = this.getIconview().store;
	    var me = this;
	    mmstore.reload({
	        callback : function(records, operation, success) {
		        var root = me.getFolderview().store.getRootNode();
		        var myid = root.findChild('indice', currentNode.data.indice, true);
			    if (myid != null) {
	        	console.log(currentNode);
	        	var mrr = mmstore.find('indice', currentNode.data.indice);
	        	console.log('selecting current node');
		    	iconview.getSelectionModel().select(mrr);
 }
	        }
	    });
    },
});