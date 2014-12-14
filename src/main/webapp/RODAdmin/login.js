Ext.onReady(function(){
    Ext.QuickTips.init();
 
    var login = new Ext.FormPanel({ 
        labelWidth:80,
        itemId: 'loginpanel',
        id: 'loginpanel',
        url: '/roda/resources/j_spring_security_check',
        frame:true, 
        title:'RODA Login', 
        defaultType:'textfield',
	monitorValid:true,
        items:[{ 
                fieldLabel:'Username', 
                name:'j_username', 
                value: 'admin',
                allowBlank:false 
            },{ 
                fieldLabel:'Password', 
                name:'j_password', 
                value: 'admin',
                inputType:'password', 
                allowBlank:false 
            }],
 
        buttons:[{ 
                text:'Login',
                formBind: true,	 
                handler:function(){ 
            		Ext.ComponentManager.get('lwin').setVisible(false);
                    login.getForm().submit({ 
                        method:'POST', 
                        timeout:200000,
                        waitTitle:'Connecting', 
                        waitMsg:'Sending data...',
 
                        success:function(){ 
                        		login.setVisible = false;
                        		var redirect = 'index.html'; 
		                        window.location = redirect;
                        },
                        failure:function(form, action){ 
                            if(action.failureType == 'server'){ 
                        		Ext.ComponentManager.get('lwin').setVisible(true);
                                obj = Ext.decode(action.response.responseText); 
                                console.log(obj);
                                Ext.Msg.alert('Login Failed!', obj.message); 
                            }else{ 
                                Ext.Msg.alert('Warning!', 'Authentication server is unreachable : ' + action.response.responseText); 
                            } 
                            login.getForm().reset(); 
                        } 
                    }); 
                } 
            }] 
    });
 
    var win = new Ext.Window({
        layout:'fit',
        id:'lwin',
        itemid: 'lwin',
        width:300,
        height:150,
        closable: false,
        resizable: false,
        plain: true,
        border: false,
        items: [login]
	});
	win.show();
});