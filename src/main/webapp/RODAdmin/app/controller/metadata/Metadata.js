Ext.define('RODAdmin.controller.metadata.Metadata', {
    extend: 'Ext.app.Controller',

    views: [
        'RODAdmin.view.metadata.Metadata',
    ],

    init : function(application) {
    	this.listen({
            controller: {
                '*': {
                    controllerMetadataInitView: this.initView
                }
            }
    	 });
    },

    initView : function() {
    	Ext.History.add('menu-metadata');
    },



});