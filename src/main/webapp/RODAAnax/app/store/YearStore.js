/**
 * 
 */
Ext.define('anax.store.YearStore', {
    extend: 'Ext.data.TreeStore',

    requires: [
               'anax.model.Years'  
               ],
    model: 'anax.model.Years',

  root : {
  name : 'RODA',
  expandable : true,
  expanded : true,
  text : 'Maps',
  type : 'M',
  id : 0,
  children : [
              {
              	id : 1,
              	name : '2013',
              	text : '2013',
              	type : 'YG',
              	children : [
              	            {
              	            	id: 10,
              	            	name : 'Densitatea populatiei',
              	            	text : 'Densitatea populatiei',
              	            	type : 'II',
              	            	mapid : 121,
               	            	leaf: true,
              	            },
              	            {
              	            	id: 11,
              	            	name : 'Densitatea gainilor',
              	            	text: 'Densitatea gainilor',
              	            	type : 'II',
              	            	mapid : 121,
              	            	leaf: true,
              	            }
              	            ]
              },
                  {
                  	id : 2,
                  	name : '2014',
                  	text : '2014',
                  	type : 'YG',
                  	children: [
                 	            {
              	            	id: 13,
              	            	name : 'Densitatea populatiei',
                 	            text : 'Densitatea populatiei',
                 	            leaf: true,
                   	            mapid : 124,
                 	            type : 'II',
              	            },
              	            {
              	            	id: 14,
              	            	name : 'Densitatea gainilor',
              	            	text: 'Densitatea gainilor',
              	            	leaf: true,
              	            	mapid : 124,
              	            	type : 'II',
              	            }                  	           ]
                  },
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

