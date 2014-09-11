Ext.define('RODAdmin.view.metadata.MetadataColumn', {
    extend: 'Ext.container.Container',
    alias: 'widget.metadatacolumn',

    requires: ['RODAdmin.view.metadata.MComponent'],

    layout: 'anchor',
    defaultType: 'mcomponent',
    cls: 'x-metadata-column'

    // This is a class so that it could be easily extended
    // if necessary to provide additional behavior.
});
