Ext.define('RODAdmin.view.dashboard.Dashboard', {
    extend: 'RODAdmin.view.dashboard.DashboardPanel',    
    
    alias: 'widget.dashboard',

    getTools: function(){
        return [{
            xtype: 'tool',
            type: 'gear',
            handler: function(e, target, panelHeader, tool){
                var portlet = panelHeader.ownerCt;
                portlet.setLoading(translations.loading);
                Ext.defer(function() {
                    portlet.setLoading(false);
                }, 2000);
            }
        }];
    },
    
    initComponent: function() {
        
        Ext.apply(this, {

            items: [{
                id: 'col-1',
                items: [
                 {
                    id: 'portlet-1',
                    title: translations.dash_lastadded,
                    tools: this.getTools(),
                    items: Ext.create('RODAdmin.view.dashboard.portlets.StudyPortlet')
                },
                {
                    id: 'portlet-2',
                    title: translations.dash_temporary,
                    tools: this.getTools(),
                    items: Ext.create('RODAdmin.view.dashboard.portlets.TempStudyPortlet')
                },
                ]
            },{
                id: 'col-2',
                items: [
                {
                    id: 'portlet-3',
                    title: translations.dash_traffic,
                    tools: this.getTools(),
                    items: Ext.create('RODAdmin.view.dashboard.portlets.TraficPortlet')
                },
                {
                    id: 'browser',
                    title: translations.dash_browser,
                    tools: this.getTools(),
                    items: Ext.create('RODAdmin.view.dashboard.portlets.BrowserPortlet')
                }
                
                ]
            }]
            
        });
                
        this.callParent(arguments);
    }
});
