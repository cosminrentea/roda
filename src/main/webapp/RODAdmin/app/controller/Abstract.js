/**
 * Controller de baza pentru diferite ferestre care au butoane. Butonul de OK de la aceste ferestre va face lucruri diferite dar butonul de cancel si cel de close 
 * fac de fiecare data acelasi lucru, inchid fereastra
 */
Ext.define('RODAdmin.controller.Abstract', {
    extend: 'Ext.app.Controller',

    views : [
             'RODAdmin.view.common.WindowForm',
             'RODAdmin.view.common.AuditWindow',
             'RODAdmin.view.common.Confirmation',
             'RODAdmin.view.common.InformationWindow'
             ],
    
    
    init: function(application) {
        this.control({
            "windowform button[itemId=cancel]": {
                /**
    			 * @listener windowform-button[itemId=cancel]-click triggered-by:
    			 *           {@link RODAdmin.view.common.WindowForm WindowForm}
    			 *            button[itemId=cancel]
    			 *           {@link #onButtonClickCancel}
    			 */		        	
                click: this.onButtonClickCancel
            },
            "searchWindow button[itemId=cancel]": {
                /**
    			 * @listener searchWindow-button[itemId=cancel]-click triggered-by:
    			 *           
    			 *            button[itemId=cancel]
    			 *           {@link #onButtonClickCancel}
    			 */		        	
                click: this.onButtonClickCancel
            },
             "auditwindow button[itemId=closewin]": {
                 /**
     			 * @listener auditwindow-button[itemId=cancel]-click triggered-by:
     			 *           {@link RODAdmin.view.common.AuditWindow AuditWindow}
     			 *            button[itemId=closewin]
     			 *           {@link #onButtonClickCancel}
     			 */		        	
                click: this.onButtonClickCancel
            },
            "informationwindow  button[itemId=closewin]": {
                /**
     			 * @listener informationwindow-button[itemId=cancel]-click triggered-by:
     			 *           {@link RODAdmin.view.common.InformationWindow InformationWindow}
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
