Ext.define('RODAdmin.view.metadata.MetadataPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.metadatapanel',

    requires: ['RODAdmin.view.metadata.MetadataColumn'],

    cls: 'x-metadata',
    bodyCls: 'x-metadata-body',
    defaultType: 'metadatacolumn',
    autoScroll: true,

    initComponent : function() {
        var me = this;

        // Implement a Container beforeLayout call from the layout to this Container
        this.layout = {
            type : 'column'
        };
        this.callParent();

        this.addEvents({
            validatedrop: true,
            beforedragover: true,
            dragover: true,
            beforedrop: true,
            drop: true
        });
        this.on('drop', this.doLayout, this);
    },

    // Set columnWidth, and set first and last column classes to allow exact CSS targeting.
    beforeLayout: function() {
        var items = this.layout.getLayoutItems(),
            len = items.length,
            i = 0,
            item;

        for (; i < len; i++) {
            item = items[i];
            item.columnWidth = 1 / len;
            item.removeCls(['x-metadata-column-first', 'x-metadata-column-last']);
        }
        items[0].addCls('x-metadata-column-first');
        items[len - 1].addCls('x-metadata-column-last');
        return this.callParent(arguments);
    },

    // private
    initEvents : function(){
        this.callParent();
        this.dd = Ext.create('RODAdmin.view.metadata.MetadataDropZone', this, this.dropConfig);
    },

    // private
    beforeDestroy : function() {
        if (this.dd) {
            this.dd.unreg();
        }
        this.callParent();
    }
});
