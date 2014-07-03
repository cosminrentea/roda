Ext.ux.FitToContainer = function(options) {
    // Takes options hash with following specifications:
    // var options = { 
    //    container: container_el
    //    width: true, // e.g. resize to container width (default true)
    //    height: false // e.g. do not resize to container height (default true)
    //    width_adjust: int // adjust the width up or down by this number in pixels
    //    height_adjust: int // adjust the height up or down by this number in pixels
    //    x: int // set absolute x coordinate
    //    y: int // set absolute y coordinate
    //    x_adjust: int // adjust the (existing) x coordinate right or left
    //    y_adjust: int // adjust the (existing) y coordinate up or down
    //    trigger: a config object specifies the precise event at which the fit is performed; 
    //        trigger takes two properties: 'listenTo' and 'listenFor'
    //        For example, to apply the fit adjustments after an arbitrary store object's load event is fired,
    //        trigger: {
    //            listenTo: someStoreObject,
    //            listenFor: 'load'
    //        }
    if (!options) options = {};
    var container;
    var doResize = function() {
        var size = container.getViewSize();
        var pos = this.getPosition();
        var width = size.width + (options.width_adjust? options.width_adjust : 0);
        var height = size.height + (options.height_adjust? options.height_adjust : 0);
        if ((options.width === undefined) || (options.width === true)) {
            this.setWidth(width);
        }else
            ;
        if ((options.height === undefined) || (options.height === true))
            this.setHeight(height);
        else
            ;
        if (options.x)
            pos[0] = options.x;
        if (options.y)
            pos[1] = options.y;
        if (options.x_adjust)
            pos[0] = pos[0] + options.x_adjust;
        if (options.y_adjust)
            pos[1] = pos[1] + options.y_adjust;
        this.setPagePosition(pos);
    };
    return {
        init: function(component) {
            if (options.hideFirst === true) 
                component.hide();

            if ((options.trigger) && (options.trigger.listenTo) && (options.trigger.listenFor)) {
                var listener_config = {};
                listener_config[options.trigger.listenFor] = {
                    fn: function() {
                        container = Ext.get(options.container || component.container.dom);
                        doResize.createDelegate(component)();
                        if (options.hideFirst === true) 
                            component.show();
                    }
                };
                options.trigger.listenTo.on(listener_config);
            } else {
                component.on('render', function(component) {
                    parent = Ext.get(parent || component.el.dom.parentNode);
                });
                component.monitorResize = true;
                component.doLayout = component.doLayout.createInterceptor(function() {
                    var pos = this.getPosition();
                    var size = parent.getViewSize();
                    this.setSize(size.width - pos[0], size.height - pos[1]);
                    if (options.hideFirst === true) 
                        component.show();
                },component);
            }
        }
    };
};
