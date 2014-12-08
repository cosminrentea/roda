Ext.define('anax.view.AnaxDrawing', {
    extend: 'Ext.draw.Component',
    config: {
     mapid : 0,	
     maplevel : 0,
     zoom : 1,
     bbx : 0,
     bby : 0,
     bbwidth : 0,
     bbheight: 0,
    },
    alias: 'widget.anaxdrawing',
    viewBox: true,
//    autoScroll : true,
    autoRender: true,
    shrinkWrap : 3
});