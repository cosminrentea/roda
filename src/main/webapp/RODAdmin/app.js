/*
 * This file is generated and updated by Sencha Cmd. You can edit this file as
 * needed for your application, but these edits will have to be merged by Sencha
 * Cmd when upgrading.
 */
/**
 * Definitia principala a aplicatiei
 * 
 */
// DO NOT DELETE - this directive is required for Sencha Cmd packages to work.
// @require @packageOverrides
Ext.Loader.setConfig({
    enabled : true,
    paths : {
        // Ext: '.',
        'Ext.ux' : 'ux',
        'RODAdmin' : 'app',
        'RODAdmin.util' : 'app/util'
    }
});

Ext.application({
    name : 'RODAdmin',
    extend : 'RODAdmin.Application',

    requires : [
            'Ext.util.History','RODAdmin.proxy.Main','RODAdmin.proxy.MainAjax', 'RODAdmin.proxy.MainNoRest','RODAdmin.util.Globals',
            'RODAdmin.util.TreeGridFilter', 'Ext.menu.Menu', 'Ext.window.Window', 'Ext.form.Panel',
            'Ext.layout.container.Accordion', 'RODAdmin.util.Util', 'Ext.form.FieldSet', 'Ext.form.field.Hidden',
            'Ext.form.field.ComboBox', 'Ext.form.Label', 'Ext.form.field.File', 'Ext.grid.Panel','Ext.ux.grid.FiltersFeature'    
            
            
            // ,  'Ext.ux.form.MultiSelect'
    ],

    views : [
            'Main', 'Viewport', 'MainUserPanel'
    ],

    stores : [
	    'security.Permissions', 'common.Globals','common.Language'
    ],

    controllers : [
            'Main', 'Login', 'TranslationManager', 'Menu', 'cms.Dashboard', 'cms.Cmslayouts', 'cms.layout.LayoutTree',
            'cms.layout.LayoutList', 'cms.layout.LayoutEdit', 'cms.Cmssnippet', 'cms.snippet.SnippetTree',
            'cms.snippet.SnippetList', 'cms.snippet.SnippetEdit', 'cms.file.FileEdit', 'cms.file.FileTree',
            'cms.file.FileList', 'cms.Cmsfiles', 'cms.Cmspages', 'cms.page.PageTree', 'cron.Dashboard', 'cron.Actions',
            'common.Audit', 'Abstract', 'cron.Actions', 'cron.ActionList', 'cron.ActionEdit', 'audit.AuditMain', 'studies.Studies','studies.StudyList', 'studies.StudyEdit', 'studies.StudyTree',
            'user.User', 'user.UserList', 'user.GroupList','cms.page.PageEdit', 'cms.Cmsnews', 'cms.news.NewsList', 'cms.news.NewsEdit', 'studies.CBEditor.StudyAdd', 'studies.StudyVariables', 'metadata.Metadata',
            'metadata.mcomponents.Prefix','metadata.mcomponents.Suffix','metadata.mcomponents.Org','metadata.mcomponents.Person','studies.StudyTemp'  
    ],

    splashscreen : {},

    init : function() {

    	  /**
	     * @todo Reference
	     * Nu merge referinta this.getCommonGlobalsStore()
	     */
    	
    	console.log('init application');
	    var cgstore = Ext.StoreManager.get('common.Globals');
	    cgstore.load(function(records, op, success){
	    	cgstore.each(function(setting){
	    		RODAdmin.util.Globals[setting.get("name")] = setting.get("value");
	    		console.log('load settings: ' + setting.get("name") + '' + setting.get("value"));
	    	});
	    });	
    },

    launch : function() {
    	console.log('launch application');    	
	    Ext.tip.QuickTipManager.init();
        var me = this;
        // init Ext.util.History on app launch; if there is a hash in the url,
        // our controller will load the appropriate content
        Ext.Ajax.timeout = 300000; 
	    Ext.override(Ext.form.Basic, {     timeout: Ext.Ajax.timeout / 1000 });
	    Ext.override(Ext.data.proxy.Server, {     timeout: Ext.Ajax.timeout });
	    Ext.override(Ext.data.Connection, {     timeout: Ext.Ajax.timeout });
        //see if we can get the user session
	    Ext.Ajax.request({
			url: '/roda/userjson/session/list',
			waitTitle: 'Connecting',
			waitMsg: 'Sending data...',                                     
			scope:this,
			success : function(response, opts) {
				var resp = Ext.JSON.decode(response.responseText);
				console.log (resp);
				if (resp.success) {
					//sa vedem cine e si ce roluri are
					var session_context = resp.data[0].value;
					var splits = session_context.split(';');
					//plecam de la ideea ca ramane asa cum e
					//user name
					var sprvalues = splits[0].split(':');
					var username = sprvalues[sprvalues.length - 1].trim();
					RODAdmin.util.Globals.username = username;
					for (i = 0; i < splits.length; i++) {
						if (splits[i].match(/Authorities/i)) {
							var authorities = splits[i].split(':');
							if (authorities[1].match(/ADMIN/)) {
								RODAdmin.util.Globals.isAdmin = true;
							}
							if (authorities[1].match(/USER/)) {
								RODAdmin.util.Globals.isUser = true;
							}
						}
					}
					if (RODAdmin.util.Globals.isAdmin ) {
						Ext.create('RODAdmin.view.MainViewport');
						RODAdmin.util.SessionMonitor.start();
						Ext.util.History.init(function(){
							console.log('history init firing event');
							var hash = document.location.hash;
							me.getMainController().fireEvent( 'tokenchange', hash.replace( '#', '' ) );
						})
						Ext.util.History.on( 'change', function( token ){
							console.log('history change firing event');
							me.getMainController().fireEvent( 'tokenchange', token );
						});
						} else if (RODAdmin.util.Globals.isUser) {
							Ext.create('RODAdmin.view.UserViewport');
							RODAdmin.util.SessionMonitor.start();
						}			        
					
				} else {
					console.log ('error');
					RODAdmin.util.Util.showErrorMsg(response.message);
				}
			},
			failure : function(response, opts) {
				console.log ('failure');
				switch (action.failureType) {
				case Ext.form.action.Action.CLIENT_INVALID:
                    	Ext.Msg.alert('Failure', 'Form fields may must be submitted with invalid values');
                    	break;
                    	
				case Ext.form.action.Action.CONNECT_FAILURE:
                    	Ext.Msg.alert('Failure', 'doesn\'t work');
                    	break;
				case Ext.form.action.Action.SERVER_INVALID:
                    	Ext.Msg.alert('Failure', action.result.msg);
                    	break;
				}
			}
		});
    }
});
