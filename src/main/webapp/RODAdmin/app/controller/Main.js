/**
 * Main controller
 */
Ext.define('RODAdmin.controller.Main', {
    extend: 'Ext.app.Controller',

    stores : [
      	    'Menu'
          ],

    refs: [
              {
                  ref: 'mainPanel',
                  selector: 'mainpanel'
              }
          ],
    
    init: function() {
        this.listen({
            // We are using Controller event domain here
            controller: {
                // This selector matches any originating Controller
                '*': {     
                    tokenchange: 'tokenFired'
                }
            }
        });
    },

    //todo: deduplicate all the iconview toolbars
    
    tokenFired: function(token) {
    	console.log('tokenfired');
    	console.log(token);
    	if (token) {
    		var tokenparts = token.split("-"); 
    		if (tokenparts[0] == 'menu') {
    			this.firemenuItem3(tokenparts[1]);
    		}
    	}
    },
    
    firemenuItem3 : function(menu) {
    	var currentMenu = this.getCurentMenuItem(menu);
    },
    
    
    firemenuItem2 : function(menu) {
    	console.log('firing menu item '+ menu);
    	var currentMenu = this.getCurentMenuItem(menu);
    	if (currentMenu) {
    	var mainPanel = this.getMainPanel();
    	//we have the menu, let's see if we can fire it
    	 var ntext = translations[currentMenu.get('text')]; 
		 var newTab = mainPanel.items.findBy(
		        function (tab){ 
		        	return tab.title === ntext; 
		        });
		 if (!newTab){
			  console.log('adding new tab');
			  newTab = mainPanel.add({
	                xtype: currentMenu.raw.className,
	                closable: true,
	                iconCls: currentMenu.get('iconCls'),
	                title: ntext
	            });
	      }
	        var controller = RODAdmin.util.Util.capitalize(currentMenu.raw.className);
	        this.fireEvent('controller'+controller+'InitView');
	        mainPanel.setActiveTab(newTab); 
    	}
    	
    	
    	
    },
    
    getCurentMenuItem : function(menu) {
    	console.log('searching for menu item: ' + menu);
//    	var menustore = this.getMenuStore();
    	var menustore = Ext.StoreManager.get('Menu');
//    	console.log(menustore);
    	var menurec;
    	var me = this;
    	var mainPanel = this.getMainPanel();
    	menustore.load({
    	  callback: function(records, operation, success) {
    		if (success == true) {       
    	    	console.log(menustore);
    	    	var menuclass = menu;
    	    	var menuitem;
    	    	menustore.each(function(smth) {
    	    		console.log('first');
    	    		smth.itemsStore.each(function(subm) {
    	    			 if (subm.data.className == menuclass) {
    	    				 console.log('foundit');
    	    			    //we have the menu, let's see if we can fire it
    	    			     var ntext = translations[subm.get('text')]; 
    	    				 var newTab = mainPanel.items.findBy(
    	    				    function (tab){ 
    	    				       	return tab.title === ntext; 
    	    				     });
    	    				 if (!newTab){
    	    					  console.log('adding new tab');
    	    					  newTab = mainPanel.add({
    	    				          xtype: subm.raw.className,
    	    				          closable: true,
    	    				          iconCls: subm.get('iconCls'),
    	    				          title: ntext
    	    				     });
    	    				 }
    	    				 var controller = RODAdmin.util.Util.capitalize(subm.raw.className);
    	    				 me.fireEvent('controller'+controller+'InitView');
    	    				 mainPanel.setActiveTab(newTab); 
    	    			 }
    	    		});
//    	    		if (mi) {
//    	    			console.log('mi');
//    	    			console.log(mi);
//    	    			menuitem = mi;
//    	    		}
    	    	});
//    	    	if (menuitem) {
//    				console.log('mienuitem');
//    				console.log(menuitem);
//    				menurec = menuitem;
//    	    	}
    		}
    	  }
    	});
    },
    	
    	
    firemenuItem : function(menu) {
    	//let's get the menu store
    	console.log('firing menu item '+ menu);
    	var menustore = this.getMenuStore();
    	var mainPanel = this.getMainPanel();
    	console.log(menustore);
    	console.log(mainPanel);	
    	var menuclass = menu;
    	var menuitem = menustore.each(function(smth) {
    			console.log('main iterator here');
    			var mi = smth.itemsStore.each(
    			         function(subm) {
    			        	 //sa vedem daca putem sa returnam de aici	
    			        	 console.log('searching---');
    			        	 if (subm.data.className == menuclass) {
    			        		 console.log('found it');
    			        		 //firing menu item
    			        		 console.log(subm.get('text'));
    			        		 var ntext = translations[subm.get('text')]; 
    			        		  var newTab = mainPanel.items.findBy(
                                      function (tab){ 
                                          return tab.title === ntext; 
                                      });
    			        		  if (!newTab){
    			        			  console.log('adding new tab');
    			        			  newTab = mainPanel.add({
    			        	                xtype: subm.raw.className,
    			        	                closable: true,
    			        	                iconCls: subm.get('iconCls'),
    			        	                title: ntext
    			        	            });
    			        	      }
   			        	        var controller = RODAdmin.util.Util.capitalize(subm.raw.className);
   			        	        this.fireEvent('controller'+controller+'InitView');
   			        	        mainPanel.setActiveTab(newTab); 
   			        	        return;
    			        	 }
    			         }
    			);
    		}
    	);
    	if (menuitem) {
    		console.log('found menu item');
    		console.log(menuitem);
    	}
    	
    	
    }




});
