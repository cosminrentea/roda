/**
 * 
 */
Ext.define('databrowser.view.Alert', {
    
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

            if(!databrowser.view.Alert.msgCt) {
                databrowser.view.Alert.msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
            }

            var s = Ext.String.format.apply(String, Array.prototype.slice.call(arguments, 1));
            var m = Ext.DomHelper.append(databrowser.view.Alert.msgCt, createBox(title, s, err), true);
            m.hide();
            m.slideIn('t').ghost("t", { delay: 3000, remove: true});
        }
    }
});