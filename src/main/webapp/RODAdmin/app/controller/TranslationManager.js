/**
 * 
 */
Ext.define('RODAdmin.controller.TranslationManager', {
    extend: 'Ext.app.Controller',

    views: [
        'RODAdmin.view.Translation'
    ],

    refs: [
        {
            ref: 'translation',
            selector: 'translation'
        }
    ],
   	/**
   	 * @event
   	 */
    onMenuitemClick: function(item, e, options) {
        var menu = this.getTranslation();
        menu.setIconCls(item.iconCls);
        menu.setText(item.text);
        localStorage.setItem("user-lang", item.iconCls);
        window.location.reload();
    },
   	/**
   	 * @event
   	 */
    onSplitbuttonBeforeRender: function(abstractcomponent, options) {
        var lang = localStorage ? (localStorage.getItem('user-lang') || 'en') : 'en';
        abstractcomponent.iconCls = lang;

        if (lang == 'en'){
            abstractcomponent.text = 'English';
        } else if (lang == 'es'){
            abstractcomponent.text = 'Español';
        } else if (lang == 'ro'){
            abstractcomponent.text = 'Romana';
        } else {
            abstractcomponent.text = 'Português';
        }
    },
/**
 * @method
 */
    init: function(application) {
        this.control({
            "translation menuitem": {
                click: this.onMenuitemClick
            },
            "translation": {
                beforerender: this.onSplitbuttonBeforeRender
            }
        });
    }

});
