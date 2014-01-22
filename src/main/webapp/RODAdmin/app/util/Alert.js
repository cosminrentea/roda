/**
 * 
 */
Ext.define('RODAdmin.util.Alert', {
    
    statics : {
        msgCt : null,

        msg : function (title, format, err) {

            function createBox (t, s) {
            	if (err == true) {
                return '<div class="msgerr"><h3>' + t + '</h3><p>' + s + '</p></div>';
            	} else {
           		return '<div class="msg"><h3>' + t + '</h3><p>' + s + '</p></div>';            		
            	}
            }

            if(!RODAdmin.util.Alert.msgCt) {
                RODAdmin.util.Alert.msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
            }

            var s = Ext.String.format.apply(String, Array.prototype.slice.call(arguments, 1));
            var m = Ext.DomHelper.append(RODAdmin.util.Alert.msgCt, createBox(title, s, err), true);
            m.hide();
            m.slideIn('t').ghost("t", { delay: 30000, remove: true});
        }
    }
});