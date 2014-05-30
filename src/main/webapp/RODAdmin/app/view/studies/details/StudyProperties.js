/**
 * 
 */
Ext.define('RODAdmin.view.studies.details.StudyProperties', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.studyproperties',
    itemId : 'studyproperties',
    title : 'Study Properties',
    width : "100%",
    height : "100%",
    layout : 'border',
    collapsible : true,
    dockedItems : [
	    {
	        xtype : 'toolbar',
	        itemid : 'stproptoolbar',
	        id : 'stproptoolbar',
	        dock : 'top',
	        items : [
	                {
		                xtype : 'tbfill'
	                }, {
	                    xtype : 'button',
	                    itemId : 'editstudy',
	                    text : 'Edit',
	                    tooltip : 'Edit this study'
	                }, {
	                    xtype : 'button',
	                    itemId : 'deletestudy',
	                    text : 'Delete',
	                    tooltip : 'Deletes the study'
	                }, {
	                    xtype : 'button',
	                    itemId : 'getstudyaudit',
	                    text : 'Study History',
	                    tooltip : 'Get Study History'
	                }
	        ]
	    }
    ],
    items : [
            {
                region : 'center',
                collapsible : false,
                width : '30%',
                split : true,
                xtype : 'panel',
                itemId : 'stdata',
                flex : 1,
                id : 'stdata',
                tpl : [
                        '<tpl if="data.filetype == \'folder\'">', '<h1> {data.name}', '</tpl>',
                        '<tpl if="data.filetype != \'folder\'">', '<H1>{data.name}</H1>', '<H2>Folder: {data.directory}</H2>',
                        '<div style="padding:10px;">', '{data.description}', '</div>', '</tpl>'
                ]
            }
//            , {
//                region : 'east',
//                collapsible : true,
//                resizable : true,
//                width : '70%',
//                split : true,
//                id : 'stenvelope',
////                title : 'smth',
//                flex : 3,
//                layout : 'fit',
//                xtype : 'panel',
//                items : [
//	                {
//	                    xtype : 'codemirror',
////	                    id : 'lycontent',
//	                    itemId : 'lycontent',	                    
//	                    mode : 'htmlmixed',
//	                    readOnly : true,
//	                    enableFixedGutter : true,
//	                    listModes : '',
//	                    showAutoIndent : false,
//	                    showLineNumbers : false,
//	                    enableGutter : true,
//	                    showModes : false,
//	                    pathModes : 'CodeMirror-2.02/mode',
//	                    pathExtensions : 'CodeMirror-2.02/lib/util',
//	                    flex : 1,
//	                    value : ''
//	                }
//                ]
//            }
    ]
});
