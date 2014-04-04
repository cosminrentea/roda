/**
 * 
 */
Ext.define('RODAdmin.util.Util', {

    statics : {

        required: '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',

        decodeJSON : function (text) {

            var result = Ext.JSON.decode(text, true);

            if (!result){
                result = {};
                result.success = false;
                result.msg = text;
            }

            return result;
        },

        showErrorMsg: function (text) {

            Ext.Msg.show({
                title:'Error!',
                msg: text,
                icon: Ext.Msg.ERROR,
                buttons: Ext.Msg.OK
            });
        },
        
        capitalize : function(text) {
        	return text.charAt(0).toUpperCase() + text.slice(1).toLowerCase();
        }
        
    }
});