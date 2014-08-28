Ext.define('RODAdmin.controller.cms.Cmsnews', {
    extend : 'Ext.app.Controller',

 //   stores : ['cms.news.News'],
    
    views : [
             'RODAdmin.view.cms.news.News',
             'RODAdmin.view.cms.news.NewsItemsview',
             'RODAdmin.view.cms.news.NewsDetails'             
     ],
     refs : [
             {	
             	ref : 'newslist',
             	selector: 'newsitemsview grid#newsiconview'
             }
      
     ],

     init : function(application) {
 	    this.control({

 	    });
     	this.listen({
             controller: {
                 '*': {
                     controllerCmsnewsInitView: this.initView
                 }
             }
     	 });
     },
     initView : function() {
     	console.log('InitView, baby');	
     	Ext.History.add('menu-cmsnews');
     	this.getNewslist().store.load();
     }
});
     
     