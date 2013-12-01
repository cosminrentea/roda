/*
 * This file is generated and updated by Sencha Cmd. You can edit this file as
 * needed for your application, but these edits will have to be merged by Sencha
 * Cmd when upgrading.
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
			'RODAdmin.util.TreeGridFilter', 
			'Ext.menu.Menu',
			'Ext.window.Window',
			'Ext.form.Panel',
			'Ext.layout.container.Accordion',
			'RODAdmin.util.Util',
			'Ext.form.FieldSet',
			'Ext.form.field.Hidden',
			'Ext.form.field.ComboBox',
			'Ext.form.Label',
			'Ext.form.field.File',
			'Ext.grid.Panel', 
			],

	views : ['Main', 'Viewport'],

	stores : ['security.Permissions'],

	controllers : ['Main', 'Login', 'TranslationManager', 'Menu',
			'cms.Dashboard',
			'cms.Layout', 'cms.layout.LayoutTree','cms.layout.LayoutList','cms.layout.LayoutEdit',
			'cms.Snippet',
			'cms.snippet.SnippetTree',
			'cms.snippet.SnippetList',
			'cms.snippet.SnippetEdit',
			'cms.file.FileEdit',
			'cms.file.FileTree',
			'cms.file.FileList',
			'cms.Files',
			'cms.Pages','cms.page.PageTree',  'cron.Dashboard', 'cron.Actions', 'common.Audit',
			'Abstract',
			'cron.Actions',
			'cron.ActionList',
			'cron.ActionEdit'
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
		var task = new Ext.util.DelayedTask(function() {

					// Fade out the body mask
					splashscreen.fadeOut({
								duration : 1000,
								remove : true
							});

					// Fade out the icon and message
					splashscreen.next().fadeOut({
								duration : 1000,
								remove : true,
								listeners : {
									afteranimate : function(el, startTime,
											eOpts) {
										Ext.widget('login');
									}
								}
							});
				});

		task.delay(2000);
	}
});
