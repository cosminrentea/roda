/**
 * 
 */
Ext.define('RODAdmin.store.studies.CBEditor.ConceptsDisp', {
    extend: 'Ext.data.TreeStore',

    requires: [
        'RODAdmin.model.studies.CBEditor.Concept'
    ],
    model: 'RODAdmin.model.studies.CBEditor.Concept',
	root: {
		expanded:true,
		id: 0,
		text: 'Concepts',
		children:[
		{
		id:1,	
		text: 'Concept 1',
		lang: 'en',
		leaf:true		
		},
		{
		id: 2,	
		text: 'Concept 2',
		lang: 'en',
		leaf:true
		}
		]
	},
   
    proxy: {
        type: 'memory',
//        data: {
//        	success: true,
//        	children: [{
//        		name: 'Concepts',
//        		id: 0,
//        	}]
//        }
    }
});