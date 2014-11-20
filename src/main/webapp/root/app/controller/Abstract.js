/**
 * Controller de baza pentru diferite ferestre care au butoane. Butonul de OK de la aceste ferestre va face lucruri diferite dar butonul de cancel si cel de close 
 * fac de fiecare data acelasi lucru, inchid fereastra
 */
Ext.define('databrowser.controller.Abstract', {
    extend: 'Ext.app.Controller',
   
    init: function(application) {
        this.control({
            "advsearch button[itemId=cancel]": {
                /**
    			 * @listener windowform-button[itemId=cancel]-click triggered-by:
    			 *           {@link RODAdmin.view.common.WindowForm WindowForm}
    			 *            button[itemId=cancel]
    			 *           {@link #onButtonClickCancel}
    			 */		        	
                click: this.onButtonClickCancel
            },
             "advsearch button[itemId=closewin]": {
                 /**
     			 * @listener auditwindow-button[itemId=cancel]-click triggered-by:
     			 *           {@link RODAdmin.view.common.AuditWindow AuditWindow}
     			 *            button[itemId=closewin]
     			 *           {@link #onButtonClickCancel}
     			 */		        	
                click: this.onButtonClickCancel
            },
        });
    },
   	/**
   	 * @method
   	 */
    onButtonClickCancel: function(button, e, options) {
       button.up('window').close();
    }

});
