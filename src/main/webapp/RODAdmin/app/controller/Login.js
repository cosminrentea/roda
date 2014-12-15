/**
 * Controller care se ocupa de caseta de login
 */
Ext.define('RODAdmin.controller.Login', {
    extend: 'Ext.app.Controller',

    requires: [
        'RODAdmin.util.MD5',
        'RODAdmin.util.Alert',
        'RODAdmin.view.MainViewport',
        'RODAdmin.util.Util',
        'RODAdmin.util.SessionMonitor',
        'RODAdmin.view.Login',
        'RODAdmin.view.Header',
        'RODAdmin.view.authentication.CapsLockTooltip'
     ],

    views: [
        'RODAdmin.view.Login',
        'RODAdmin.view.Header',
        'RODAdmin.view.authentication.CapsLockTooltip'
    ],

    refs: [
        {
            ref: 'capslockTooltip',
            selector: 'capslocktooltip'
        }
    ],

    init: function(application) {
        this.control({
            "login form button#submit": {
                /**
     			 * @listener login form button#submit-click triggered-by:
     			 *           {@link RODAdmin.view.Login Login}
     			 *            form button#submit
     			 *           {@link #onButtonClickSubmit}
     			 */		        	
                click: this.onButtonClickSubmit
            },
            "login form button#cancel": {
                /**
     			 * @listener login form button#cancel-click triggered-by:
     			 *           {@link RODAdmin.view.Login Login}
     			 *            form button#cancel
     			 *           {@link #onButtonClickCancel}
     			 */		        	
                click: this.onButtonClickCancel
            },
            "login form textfield": {
                /**
     			 * @listener login form textfield-specialkey triggered-by:
     			 *           {@link RODAdmin.view.Login Login}
     			 *            form textfield
     			 *           {@link #onTextfielSpecialKey}
     			 */		        	
                specialkey: this.onTextfielSpecialKey
            },
            "login form textfield[name=password]": {
                /**
     			 * @listener login form textfield[name=password]-keypress triggered-by:
     			 *           {@link RODAdmin.view.Login Login}
     			 *            form textfield[name=password]
     			 *           {@link #onTextfielKeyPress}
     			 */		        	
                keypress: this.onTextfielKeyPress
            },
            "appheader button#logout": {
                /**
     			 * @listener appheader-button-logout-click triggered-by:
     			 *           {@link RODAdmin.view.Header Header}
     			 *            button#logout
     			 *           {@link #onButtonClickLogout}
     			 */		        	
                click: this.onButtonClickLogout
            }
        });

        Ext.apply(Ext.form.field.VTypes, {
    //  vtype validation function
    customPass: function(val, field) {
        return /^((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})/.test(val);
    },
    // vtype Text property: The error text to display when the validation function returns false
    customPassText: 'Not a valid password.  Length must be at least 6 characters and maximum of 20Password must contain one digit, one letter lowercase, one letter uppercase, onse special symbol @#$% and between 6 and 20 characters.',
});

    },

   	/**
   	 * @method
   	 */
    
    onButtonClickSubmit: function(button, e, options) {
        var formPanel = button.up('form'),
            login = button.up('login'),
            user = formPanel.down('textfield[name=j_username]').getValue(),
            pass = formPanel.down('textfield[name=j_password]').getValue();   

        if (formPanel.getForm().isValid()) {

/*        	
            pass = RODAdmin.util.MD5.encode(pass); 
            
            //Ext.get(login.getEl()).mask("Authenticating... Please wait...", 'loading');
            
            login.close();
            Ext.create('RODAdmin.view.MainViewport');
            RODAdmin.util.SessionMonitor.start();
*/

/*        	
            Ext.Ajax.request({
				url: '/roda/auth/login',
            	params: {
                    username: user,
                    password: pass
                },
                success: function(conn, response, options, eOpts) {
                    
                    Ext.get(login.getEl()).unmask();

                    var result = RODAdmin.util.Util.decodeJSON(conn.responseText);

                    if (result.success) {
                        login.close();
                        Ext.create('RODAdmin.view.MainViewport');
                        RODAdmin.util.SessionMonitor.start();

                    } else {
                        RODAdmin.util.Util.showErrorMsg(conn.responseText);
                    }
                },
                failure: function(conn, response, options, eOpts) {

                    Ext.get(login.getEl()).unmask();
                
                    RODAdmin.util.Util.showErrorMsg(conn.responseText);
                }
            });
*/

//            formPanel.getForm().submit({
            Ext.Ajax.request({
                url: '/roda/resources/j_spring_security_check',
            	method:'POST',
            	params: {
                    j_username: user,
                    j_password: pass
                },
                success: function(conn, response, options, eOpts) {

                	console.log('Authenticated');
                	
//                    Ext.get(login.getEl()).unmask();

                    var result = RODAdmin.util.Util.decodeJSON(conn.responseText);

                    if (result.success) {
                        login.close();
                        Ext.create('RODAdmin.view.MainViewport');
//                        RODAdmin.util.SessionMonitor.start();
                        Ext.Ajax.request({
                            url: '/roda/j/user/session/set-interval?interval=-1',
                        	method:'POST',
                            success: function(conn, response, options, eOpts) {
                            	console.log('session set to infinity');
                            }
                        });
                    } else {
                        RODAdmin.util.Util.showErrorMsg(conn.responseText);
                    }

                },
                failure: function(conn, response, options, eOpts) {
                    RODAdmin.util.Util.showErrorMsg(conn.responseText);
                }
            });

            
        }    
    },    

   	/**
   	 * @method
   	 */
    
    onButtonClickCancel: function(button, e, options) {
        button.up('form').getForm().reset();
    },

   	/**
   	 * @method
   	 */
    
    onTextfielSpecialKey: function(field, e, options) {
        if (e.getKey() == e.ENTER){
            var submitBtn = field.up('form').down('button#submit');
            submitBtn.fireEvent('click', submitBtn, e, options);
        }
    },

   	/**
   	 * @method
   	 */
    
    onTextfielKeyPress: function(field, e, options) {
        var charCode = e.getCharCode(); 
        
        if((e.shiftKey && charCode >= 97 && charCode <= 122) ||
            (!e.shiftKey && charCode >= 65 && charCode <= 90)){

            if(this.getCapslockTooltip() === undefined){
                Ext.widget('capslocktooltip');
            }

            this.getCapslockTooltip().show();

        } else {

            if(this.getCapslockTooltip() !== undefined){
                this.getCapslockTooltip().hide();
            }
        }
    },

   	/**
   	 * @method
   	 */
    
    onButtonClickLogout: function(button, e, options) {

        Ext.Ajax.request({
            url: '/roda/resources/j_spring_security_logout',
            success: function(conn, response, options, eOpts){
                var result = RODAdmin.util.Util.decodeJSON(conn.responseText);
                if (result.success) {

                	if (RODAdmin.util.Globals.isAdmin) {
                		button.up('mainviewport').destroy();
                		window.location.reload();
                	}
                	if (RODAdmin.util.Globals.isUser) {
                		button.up('userviewport').destroy();
                		window.location.reload();
                	}
                
                } else {
                    RODAdmin.util.Util.showErrorMsg(conn.responseText);
                }
            },
            failure: function(conn, response, options, eOpts) {
                RODAdmin.util.Util.showErrorMsg(conn.responseText);
            }
        });
    }
});