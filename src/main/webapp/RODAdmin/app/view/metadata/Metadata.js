/**
 * 
 */
Ext.define('RODAdmin.view.metadata.Metadata', {
	 extend: 'RODAdmin.view.metadata.MetadataPanel',    
    alias: 'widget.metadata',

    
    getTools: function(){
        return [{
            xtype: 'tool',
            type: 'gear',
            handler: function(e, target, panelHeader, tool){
                var portlet = panelHeader.ownerCt;
                portlet.setLoading('Loading...');
                Ext.defer(function() {
                    portlet.setLoading(false);
                }, 2000);
            }
        }];
    },
    initComponent: function() {
        
        Ext.apply(this, {

            items: [{
                id: 'mdcol-1',
                items: [
                 {
                    id: 'mcomponent-1',
                    title: 'Prefixes',
                    tools: this.getTools(),
                    items: Ext.create('RODAdmin.view.metadata.mcomponents.Prefixes')
                },
                {
                    id: 'mconponent-2',
                    title: 'Suffixes',
                    tools: this.getTools(),
                    items: Ext.create('RODAdmin.view.metadata.mcomponents.Sufixes')
                },
                {
                    id: 'mconponent-5',
                    title: 'City',
                    tools: this.getTools(),
                    items: Ext.create('RODAdmin.view.metadata.mcomponents.City')
                },
                {
                    id: 'mconponent-6',
                    title: 'Country',
                    tools: this.getTools(),
                    items: Ext.create('RODAdmin.view.metadata.mcomponents.Country')
                },
                
                
                ]
            },{
                id: 'mdcol-2',
                items: [
                {
                    id: 'mcomponent-3',
                    title: 'Persons',
                    tools: this.getTools(),
                    items: Ext.create('RODAdmin.view.metadata.mcomponents.Persons')
                },
                {
                    id: 'mcomponent-4',
                    title: 'Organisations',
                    tools: this.getTools(),
                    items: Ext.create('RODAdmin.view.metadata.mcomponents.Orgs')
                }
                
                ]
            }]
            
        });
                
        this.callParent(arguments);
    }

});
