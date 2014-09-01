/**
 * 
 */
Ext.define('RODAdmin.view.cms.page.details.PageCode', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.pagecode',
    itemId : 'pagecode',
    title : 'Page Code',
    layout: 'fit',
    // id: 'fileproperties',
    collapsible : true,
    items : [
             {
                 xtype : 'codemirror',
                 mode : 'htmlmixed',
                 itemId: 'pagecontent',
                 readOnly : true,
                 enableFixedGutter : true,
                 listModes : '',
                 showAutoIndent : false,
                 showLineNumbers : false,
                 enableGutter : true,
                 showModes : false,
                 pathModes : 'CodeMirror-2.02/mode',
                 pathExtensions : 'CodeMirror-2.02/lib/util',
                 flex : 1,
                 value : ''
             }
         ]
});
