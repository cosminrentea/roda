/**
 * Menu controller
 */
Ext.define('RODAdmin.controller.Menu', {
    extend: 'Ext.app.Controller',

    requires: [
        'RODAdmin.view.security.Profile',
        'RODAdmin.view.security.GroupPermissions'
    ], 

    models: [
        'RODAdmin.model.menu.Root',
        'RODAdmin.model.menu.Item'
    ],
    stores: [
        'Menu'
    ],
    views: [
        'RODAdmin.view.menu.Accordion',
        'RODAdmin.view.menu.Item'
    ],

    refs: [
        {
            ref: 'mainPanel',
            selector: 'mainpanel'
        }
    ],
   	/**
   	 * @event
   	 */
    onPanelRender: function(abstractcomponent, options) {
    	console.log(this);
//    	this.getMenuStore().getProxy().url = RODAdmin.Globals.baseurl;
        this.getMenuStore().load(function(records, op, success){

            var menuPanel = Ext.ComponentQuery.query('mainmenu')[0];

            Ext.each(records, function(root){

                var menu = Ext.create('RODAdmin.view.menu.Item',{
                    title: translations[root.get('text')],
                    iconCls: root.get('iconCls')
                });

                Ext.each(root.items(), function(itens){

                    Ext.each(itens.data.items, function(item){

                        menu.getRootNode().appendChild({
                            text: translations[item.get('text')], 
                            leaf: true, 
                            iconCls: item.get('iconCls'),
                            id: item.get('id'),
                            className: item.get('className') 
                        });
                    });  
                });

                menuPanel.add(menu);
            }); 
        });
    },
   	/**
   	 * @method
   	 */
    onTreepanelSelect: function(selModel, record, index, options) {
        //console.log(record.raw.className);

        var mainPanel = this.getMainPanel();

        var newTab = mainPanel.items.findBy(
        function (tab){ 
            return tab.title === record.get('text'); 
        });

        console.log(record.raw.className);

        if (!newTab){
            newTab = mainPanel.add({
                xtype: record.raw.className,
                closable: true,
                iconCls: record.get('iconCls'),
                title: record.get('text')
            });
        }

        mainPanel.setActiveTab(newTab);
    },
   	/**
   	 * @method
   	 */
    onTreepanelItemClick: function(view, record, item, index, event, options){
        this.onTreepanelSelect(view, record, index, options);
    },
/**
 * @method
 */
    init: function(application) {
        this.control({
            "mainmenu": {
                render: this.onPanelRender
            },
            "mainmenuitem": {
                //select: this.onTreepanelSelect,
                itemclick: this.onTreepanelItemClick
            }
        });
    }
});
