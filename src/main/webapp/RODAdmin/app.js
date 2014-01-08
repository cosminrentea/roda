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
            'Ext.util.History','RODAdmin.proxy.Main','RODAdmin.proxy.MainAjax', 'RODAdmin.util.Globals',
            'RODAdmin.util.TreeGridFilter', 'Ext.menu.Menu', 'Ext.window.Window', 'Ext.form.Panel',
            'Ext.layout.container.Accordion', 'RODAdmin.util.Util', 'Ext.form.FieldSet', 'Ext.form.field.Hidden',
            'Ext.form.field.ComboBox', 'Ext.form.Label', 'Ext.form.field.File', 'Ext.grid.Panel',
    ],

    views : [
            'Main', 'Viewport'
    ],

    stores : [
	    'security.Permissions', 'common.Globals'
    ],

    controllers : [
            'Main', 'Login', 'TranslationManager', 'Menu', 'cms.Dashboard', 'cms.Layout', 'cms.layout.LayoutTree',
            'cms.layout.LayoutList', 'cms.layout.LayoutEdit', 'cms.Snippet', 'cms.snippet.SnippetTree',
            'cms.snippet.SnippetList', 'cms.snippet.SnippetEdit', 'cms.file.FileEdit', 'cms.file.FileTree',
            'cms.file.FileList', 'cms.Files', 'cms.Pages', 'cms.page.PageTree', 'cron.Dashboard', 'cron.Actions',
            'common.Audit', 'Abstract', 'cron.Actions', 'cron.ActionList', 'cron.ActionEdit', 'audit.Audit',
            'user.User', 'user.UserList', 'user.GroupList'
    ],

    splashscreen : {},

    init : function() {

	    // Start the mask on the body and get a reference to the mask
	    splashscreen = Ext.getBody().mask('Loading RODA Admin', 'splashscreen');

	    // Add a new class to this mask as we want it to look different from the
	    // default.
	    splashscreen.addCls('splashscreen');

	    // Insert a new div before the loading icon where we can place our logo.
	    Ext.DomHelper.insertFirst(Ext.query('.x-mask-msg')[0], {
		    cls : 'x-splash-icon'
	    });
    },

    launch : function() {
	    Ext.tip.QuickTipManager.init();
	    
        var me = this;
        // init Ext.util.History on app launch; if there is a hash in the url,
        // our controller will load the appropriate content
        Ext.util.History.init(function(){
            var hash = document.location.hash;
            me.getMainController().fireEvent( 'tokenchange', hash.replace( '#', '' ) );
        })
        // add change handler for Ext.util.History; when a change in the token
        // occurs, this will fire our controller's event to load the appropriate content
        Ext.util.History.on( 'change', function( token ){
            me.getMainController().fireEvent( 'tokenchange', token );
        });
	    
	    
	    var task = new Ext.util.DelayedTask(function() {

		    // Fade out the body mask
		    splashscreen.fadeOut({
		        duration : 1000,
		        remove : true
		    });

		    /**
		     * @todo Reference
		     * Nu merge referinta this.getCommonGlobalsStore()
		     */
		    var cgstore = Ext.StoreManager.get('common.Globals');
		    cgstore.load(function(records, op, success){
		    	cgstore.each(function(setting){
		    		RODAdmin.util.Globals[setting.get("name")] = setting.get("value");
		    	});
		    });		    

		    // Fade out the icon and message
		    splashscreen.next().fadeOut({
		        duration : 1000,
		        remove : true,
		        listeners : {
			        afteranimate : function(el, startTime, eOpts) {
				        Ext.widget('login');
			        }
		        }
		    });
	    });
	    task.delay(2000);
    }
});
