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
                tpl : [
                       	'<div style="padding:10px;">',
                        '<h1> {name}</h1>',
                        '<div class="year">Year: {an}</div>',
                        '<div class="geocov">Geographical coverage: {geo_coverage}</div>',
                        '<div class="geounit">Geographical unit: {geo_unit}</div>',
                        '<div class="weighting">Weighting: {weighting}</div>',
                        '<div class="unit_analysis">Unit analysts: {unit_analysis}</div>',                        
                        '{description}',
                        '</div>',
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
