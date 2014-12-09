Ext.define('anax.controller.IT', {
    extend : 'Ext.app.Controller',

    stores : [
	    'Sprite'
    ],

    refs : [
            {
                ref : 'AnaxDrawing',
                selector : 'anaxdrawing'
            }, {
                ref : 'spdetailsdraw',
                selector : 'spritedetail panel#spdraw anaxdrawing'
            }, {
                ref : 'spdetailspanel',
                selector : 'spritedetail panel#spdetails'
            }, {
                ref : 'anaxmap',
                selector : 'anaxmap'
            }, {
                ref : 'maptoolbar',
                selector : 'anaxmap maindraw toolbar#anmtoolbar'
            }, {
                ref : 'levelmenu',
                selector : 'anaxmap toolbar#anmtoolbar button#aglevel'
            },

    ],

    init : function(application) {
	    this.control({
	        "anaxcontrol treepanel#ItTreePanel" : {
		        selectionchange : this.onTreeSelectionChange
	        },
	        "anaxcontrol treepanel#YearsTreePanel" : {
		        selectionchange : this.onYTreeSelectionChange
	        },
	        "anaxmap toolbar#anmtoolbar button#dewhite" : {
		        click : this.onDewhiteClick
	        },
	        "anaxmap toolbar#anmtoolbar button#rewhite" : {
		        click : this.onRewhiteClick
	        },
	        "anaxmap toolbar#anmtoolbar button#zin" : {
		        click : this.onZinClick
	        },
	        "anaxmap toolbar#anmtoolbar button#zout" : {
		        click : this.onZoutClick
	        },
	        "anaxmap toolbar#anmtoolbar button#pleft" : {
		        click : this.onPleftClick
	        },
	        "anaxmap toolbar#anmtoolbar button#pright" : {
		        click : this.onPrightClick
	        },
	        "anaxmap toolbar#anmtoolbar button#pup" : {
		        click : this.onPupClick
	        },
	        "anaxmap toolbar#anmtoolbar button#pdown" : {
		        click : this.onPdownClick
	        },
	        "anaxmap toolbar#anmtoolbar button#gsr" : {
		        click : this.onGsr
	        },
	        "anaxmap anaxdrawing#maindraw" : {
		        drop : this.onMouseDown
	        },
	        'anaxmap menu' : {
		        click : this.onAppMenu
	        }

	    });

    },

    onAppMenu : function(menu, item, event) {
	    console.log('menu clicked');
	    console.log(menu);
	    console.log(item);
	    console.log(event);
	    // need the current map id
	    this.getAnaxmap().setLoading('Loading....');

	    var me = this;
	    Ext.Ajax.request({
	        url : '/roda/resources/RODAAnax/data/map.json',
	        method : "GET",
	        params : {
		        id : this.getAnaxDrawing().getMapid()
	        },
	        success : function(response, request) {
		        var responseJson = Ext.decode(response.responseText);
		        if (responseJson.success === true) {
			        var clevel = me.setCurrentLevel(responseJson, item.itemId);
			        me.cleanMap();
			        me.drawMap(responseJson, clevel);
			        me.getAnaxDrawing().setMaplevel(clevel);
			        me.getAnaxmap().setLoading(false);
		        }
		        else {
			        console.log('not');
		        }
	        },
	        failure : function(response, opts) {
		        Ext.Msg.alert('Failure', response);
	        }
	    });

    },

    onMouseDown : function(one, two, three) {
	    console.log('mouse is down');
	    console.log(one);
	    console.log(two);
	    console.log(three);

    },

    onGsr : function() {
	    var uat = this.getUat();
	    var srob = new Object;
	    for (i = 0; i < uat.length; i++) {
		    srob[uat[i][0]] = uat[i][4];
	    }
	    console.log(Ext.encode(srob));
    },

    onPupClick : function(button) {
	    var bboxwidth = this.getAnaxDrawing().getBbwidth() - this.getAnaxDrawing().getBbx();
	    var pansize = bboxwidth / 10;
	    this.getAnaxDrawing().surface.setViewBox(this.getAnaxDrawing().getBbx(), this.getAnaxDrawing().getBby()
	            + pansize, this.getAnaxDrawing().getBbwidth(), this.getAnaxDrawing().getBbheight() + pansize);

	    this.setDrawConfig({
	        zoom : this.getAnaxDrawing().getZoom(),
	        bbx : this.getAnaxDrawing().getBbx(),
	        bby : this.getAnaxDrawing().getBby() + pansize,
	        bbwidth : this.getAnaxDrawing().getBbwidth(),
	        bbheight : this.getAnaxDrawing().getBbheight() + pansize
	    });

    },

    onPdownClick : function(button) {
	    var bboxwidth = this.getAnaxDrawing().getBbwidth() - this.getAnaxDrawing().getBbx();
	    var pansize = bboxwidth / 10;
	    // x = x-pansize, width = width - pansize
	    this.getAnaxDrawing().surface.setViewBox(this.getAnaxDrawing().getBbx(), this.getAnaxDrawing().getBby()
	            - pansize, this.getAnaxDrawing().getBbwidth(), this.getAnaxDrawing().getBbheight() - pansize);

	    this.setDrawConfig({
	        zoom : this.getAnaxDrawing().getZoom(),
	        bbx : this.getAnaxDrawing().getBbx(),
	        bby : this.getAnaxDrawing().getBby() - pansize,
	        bbwidth : this.getAnaxDrawing().getBbwidth(),
	        bbheight : this.getAnaxDrawing().getBbheight() - pansize
	    });

    },

    onPrightClick : function(button) {
	    console.log(this.getAnaxDrawing().getSize());
	    // pleft means translating on x with 10% from x bbox width
	    var bboxwidth = this.getAnaxDrawing().getBbwidth() - this.getAnaxDrawing().getBbx();
	    var pansize = bboxwidth / 10;
	    // x = x-pansize, width = width - pansize
	    this.getAnaxDrawing().surface.setViewBox(this.getAnaxDrawing().getBbx() - pansize, this.getAnaxDrawing()
	            .getBby(), this.getAnaxDrawing().getBbwidth() - pansize, this.getAnaxDrawing().getBbheight());

	    this.setDrawConfig({
	        zoom : this.getAnaxDrawing().getZoom(),
	        bbx : this.getAnaxDrawing().getBbx() - pansize,
	        bby : this.getAnaxDrawing().getBby(),
	        bbwidth : this.getAnaxDrawing().getBbwidth() - pansize,
	        bbheight : this.getAnaxDrawing().getBbheight()
	    });

    },

    onPleftClick : function(button) {
	    console.log(this.getAnaxDrawing().getSize());
	    // pleft means translating on x with 10% from x bbox width
	    var bboxwidth = this.getAnaxDrawing().getBbwidth() - this.getAnaxDrawing().getBbx();
	    var pansize = bboxwidth / 10;
	    // x = x-pansize, width = width - pansize
	    this.getAnaxDrawing().surface.setViewBox(this.getAnaxDrawing().getBbx() + pansize, this.getAnaxDrawing()
	            .getBby(), this.getAnaxDrawing().getBbwidth() + pansize, this.getAnaxDrawing().getBbheight());

	    this.setDrawConfig({
	        zoom : this.getAnaxDrawing().getZoom(),
	        bbx : this.getAnaxDrawing().getBbx() + pansize,
	        bby : this.getAnaxDrawing().getBby(),
	        bbwidth : this.getAnaxDrawing().getBbwidth() + pansize,
	        bbheight : this.getAnaxDrawing().getBbheight()
	    });

    },

    onZinClick : function(button) {
	    var zoomValue = this.getAnaxDrawing().getZoom() + 0.2;
	    var cr = this.getAnaxDrawing().surface;
	    var mygroup = cr.getGroup('mapgroup');
	    var bbox = cr.items.getBBox();
	    cr.setViewBox(bbox.x, bbox.y, this.getAnaxDrawing().getSize().width / zoomValue, this.getAnaxDrawing()
	            .getSize().height
	            / zoomValue);
	    this.getAnaxDrawing().setZoom(zoomValue);

	    this.setDrawConfig({
	        zoom : zoomValue,
	        bbx : bbox.x,
	        bby : bbox.y,
	        bbwidth : this.getAnaxDrawing().getSize().width / zoomValue,
	        bbheight : this.getAnaxDrawing().getSize().height / zoomValue
	    });
    },

    onZoutClick : function(button) {
	    var zoomValue = this.getAnaxDrawing().getZoom() - 0.2;
	    var cr = this.getAnaxDrawing().surface;
	    var bbox = cr.items.getBBox();
	    cr.setViewBox(bbox.x, bbox.y, this.getAnaxDrawing().getSize().width / zoomValue, this.getAnaxDrawing()
	            .getSize().height
	            / zoomValue);
	    this.getAnaxDrawing().setZoom(zoomValue);

	    this.setDrawConfig({
	        zoom : zoomValue,
	        bbx : bbox.x,
	        bby : bbox.y,
	        bbwidth : this.getAnaxDrawing().getSize().width / zoomValue,
	        bbheight : this.getAnaxDrawing().getSize().height / zoomValue
	    });
    },

    setDrawConfig : function(conf) {
	    this.getAnaxDrawing().setZoom(conf.zoom);
	    this.getAnaxDrawing().setBbx(conf.bbx);
	    this.getAnaxDrawing().setBby(conf.bby);
	    this.getAnaxDrawing().setBbwidth(conf.bbwidth);
	    this.getAnaxDrawing().setBbheight(conf.bbheight);
    },

    scalegroup : function(draw, groupId) {
	    var tgroup = draw.surface.getGroup(groupId);
	    var swidth = draw.surface.width;
	    var sheight = draw.surface.height;
	    var bbox = tgroup.getBBox();
	    if (bbox.width > swidth || bbox.height > sheight) {
		    console.log('scale down');
		    // which to scale
		    xlg = bbox.width - swidth;
		    ylg = bbox.height - sheight;
		    if (xlg >= ylg) { // scaling x
			    console.log('scaling x');
			    var xscalefactor = swidth / bbox.width;
			    var yscalefactor = xscalefactor;
			    var newheight = bbox.height * yscalefactor;
			    var newwidth = bbox.width * xscalefactor;
		    }
		    else if (xlg < ylg) { // scaling down y
			    console.log('scaling y');
			    var yscalefactor = sheight / bbox.height;
			    var xscalefactor = yscalefactor;
			    var newheight = bbox.height * yscalefactor;
			    var newwidth = bbox.width * xscalefactor;
			    console.log('yscalefactor' + yscalefactor);
		    }
	    }
	    else {
		    console.log('scale up');
		    // which to scale?
		    var xfactor = swidth - bbox.width;
		    var yfactor = sheight - bbox.height;
		    console.log('xfactor' + xfactor + ' yfactor' + yfactor);
		    if (xfactor >= yfactor) {
			    console.log('scale up y')
			    // assuming we scale up y, how much?
			    var yscalefactor = sheight / bbox.height;
			    var xscalefactor = yscalefactor;
			    console.log('yscalefactor ' + yscalefactor);
			    var newheight = bbox.height * yscalefactor;
			    var newwidth = bbox.width * xscalefactor;
		    }
		    else if (xfactor < yfactor) {
			    console.log('scale up x')
			    var xscalefactor = swidth / bbox.width;
			    var yscalefactor = xscalefactor;
			    console.log('yscalefactor ' + yscalefactor);
			    var newheight = bbox.height * yscalefactor;
			    var newwidth = bbox.width * xscalefactor;
		    }
	    }
	    tgroup.setAttributes({
		    scale : {
		        cx : 0,
		        cy : 0,
		        x : xscalefactor,
		        y : yscalefactor,
		    }
	    }, true);
	    var scbbox = tgroup.getBBox();
	    ftrx = -scbbox.x
	    ftry = -scbbox.y

	    tgroup.setAttributes({
		    translate : {
		        x : ftrx,
		        y : ftry
		    }
	    }, true);
	    var ftbbox = tgroup.getBBox();
	    var newtrx = (swidth - ftbbox.width);
	    var newtry = 170;
	    var stbbox = tgroup.getBBox();
    },

    showYG : function(component, selected, event) {
	    console.log('yg show');
    },

    cleanMap : function() {
	    console.log('cleanmap');
	    this.getAnaxDrawing().surface.removeAll();
    },

    replaceMap : function(component, selected, event) {
	    console.log('replacemap');
	    var colors = this.getcolors(selected[0].data.id);
	    var nm2 = this.getnm2();
	    var uat = this.getUat();
	    var cr = this.getAnaxDrawing().surface;
	    var mygroup = cr.getGroup('mapgroup');
	    console.log(mygroup);

	    for (i = 0; i < mygroup.items.length; i++) {
		    var csprite = mygroup.items[i];
		    csprite.setAttributes({
			    fill : colors[nm2[csprite.id]]
		    }, true);
	    }
    },

    cl : function(temp) {// check length

	    templen = temp.length;
	    if (templen == 4) {
		    temp.length = templen * 2;
		    for (l = 0; l < templen; l++) {
			    temp[templen + l] = temp[l];
		    }
		    templen = templen * 2;
	    }
    },

    drawCountryBorder : function(group) {
	    var realheight = 10000;
	    var realwidth = 14211.376;

	    var demoheight = this.getAnaxDrawing().surface.height;
	    var scala = demoheight / realheight;
	    var grom = this.grom();
	    var uat = this.getUat();
	    var siruteB = this.siruteB();
	    var borders = this.getborders();
	    // var colors = this.getcolors(selected[0].data.id);
	    var temp = borders[grom[0]];
	    // this.cl(temp);
	    templen = temp.length;
	    if (templen == 4) {
		    temp.length = templen * 2;
		    for (l = 0; l < templen; l++) {
			    temp[templen + l] = temp[l];
		    }
		    templen = templen * 2;
	    }
	    path = "M " + temp[0] * scala + "," + temp[1] * scala;

	    contor = 0;
	    for (k = 2; k < templen; k++) {
		    if (contor == 0) {
			    path = path + "C";
			    contor = 3;
		    }
		    else {
			    path = path + ",";
		    }
		    path = path + temp[k] * scala + "," + temp[k + 1] * scala;
		    contor -= 1;
		    k += 1;
	    }
	    first = temp[0] + temp[1];
	    last = temp[temp.length - 2] + temp[templen - 1];

	    for (i = 1; i < grom.length; i++) {
		    temp = borders[grom[i]];
		    // this.cl(temp);
		    templen = temp.length;
		    if (templen == 4) {
			    temp.length = templen * 2;
			    for (l = 0; l < templen; l++) {
				    temp[templen + l] = temp[l];
			    }
			    templen = templen * 2;
		    }

		    if (last == temp[temp.length - 2] + temp[templen - 1]) {
			    last = temp[0] + temp[1];

			    contor = 0;
			    for (k = 2; k < templen; k++) {
				    if (contor == 0) {
					    path = path + "C";
					    contor = 3;
				    }
				    else {
					    path = path + ",";
				    }
				    path = path + temp[templen - k - 2] * scala + "," + temp[templen - k - 1] * scala;
				    contor -= 1;
				    k += 1;
			    }

		    }
		    else {
			    last = temp[temp.length - 2] + temp[templen - 1];
			    contor = 0;
			    for (k = 2; k < templen; k++) {
				    if (contor == 0) {
					    path = path + "C";
					    contor = 3;
				    }
				    else {
					    path = path + ",";
				    }
				    path = path + temp[k] * scala + "," + temp[k + 1] * scala;
				    contor -= 1;
				    k += 1;
			    }

		    }
	    }

	    path = path + "Z";
	    var rbsprite = this.getAnaxDrawing().surface.add({
	        type : 'path',
	        path : path,
	        id : 'romborder',
	        group : 'mapgroup',
	    });

	    console.log(scala);

	    rbsprite.setAttributes({
	        fill : "#Fff",
	        stroke : "#ff0000",
	        "stroke-width" : scala * 12,
	        "fill-opacity" : 0.7
	    });
	    rbsprite.show(true);

    },

    drawUATs : function(mapdata, svggroup, level, geodatatype, startdate, enddate) {
	    var dtstore = Ext.StoreManager.get('Indicator');

	    dtstore.load({
	        geographyid : geographyid,
	        geodatatype : geodatatype,
	        startdate : startdate,
	        enddate : enddate,
	        scope : this,
	        callback : function(records, operation, success) {
		        if (success) {

			        var maxind = 0;
			        var minind = 1000;

			        dtstore.each(function(record) {
				        if (minind > record.data.value) {
					        minind = record.data.value
				        }

				        if (maxind < record.data.value) {
					        maxind = record.data.value;
				        }
			        });

			        console.log('maxind: ' + maxind);
			        console.log('minind: ' + minind);

			        var colors = new Array;
			        var levels = 10;
			        var hue = 100;
			        var huepadding = 5;
			        for (i = 100 - huepadding; i > huepadding; i -= (100 - 2 * huepadding) / levels) {
				        colors.push(this.hsvtohex(hue, 100, i));
			        }
			        for (i = 0; i < mapdata.levels.length; i++) {
				        if (mapdata.levels[i].id == level) {
					        var uat = mapdata.levels[i].data;
				        }
			        }

			        var realheight = mapdata.realheight;
			        var realwidth = mapdata.realwidth;
			        var demoheight = this.getAnaxDrawing().surface.height;
			        var scala = demoheight / realheight;
			        var siruteB = mapdata.siruteB;
			        var borders = mapdata.borders;
			        for (i = 0; i < uat.length; i++) {
				        if (siruteB.indexOf(uat[i][0]) < 0) {
					        temp = borders[uat[i][1][0]];
					        this.cl(temp);
					        path = "M" + temp[0] * scala + "," + temp[1] * scala;
					        contor = 0;
					        for (k = 2; k < templen; k++) {
						        if (contor == 0) {
							        path = path + "C";
							        contor = 3;
						        }
						        else {
							        path = path + ",";
						        }
						        path = path + temp[k] * scala + "," + temp[k + 1] * scala;
						        contor -= 1;
						        k += 1;
					        }
					        first = temp[0] + temp[1];
					        last = temp[temp.length - 2] + temp[temp.length - 1];
					        poligon = false;
					        if (uat[i][1].length > 1) {
						        for (j = 1; j < uat[i][1].length; j++) {
							        temp = borders[uat[i][1][j]];
							        this.cl(temp);
							        if (poligon) {
								        path = path + "ZM" + temp[0] * scala + "," + temp[1] * scala;
								        contor = 0;
								        for (k = 2; k < templen; k++) {
									        if (contor == 0) {
										        path = path + "C";
										        contor = 3;
									        }
									        else {
										        path = path + ",";
									        }
									        path = path + temp[k] * scala + "," + temp[k + 1] * scala;
									        contor -= 1;
									        k += 1;
								        }
								        first = temp[0] + temp[1];
								        last = temp[templen - 2] + temp[templen - 1];
								        poligon = (temp[0] + temp[1]) == last;
							        }
							        else {
								        poligon = first == temp[0] + temp[1]
								                | first == temp[templen - 2] + temp[templen - 1];
								        if (last == temp[temp.length - 2] + temp[templen - 1]) {
									        last = temp[0] + temp[1];
									        contor = 0;
									        for (k = 2; k < templen; k++) {
										        if (contor == 0) {
											        path = path + "C";
											        contor = 3;
										        }
										        else {
											        path = path + ",";
										        }
										        path = path + temp[templen - k - 2] * scala + ","
										                + temp[templen - k - 1] * scala;
										        contor -= 1;
										        k += 1;
									        }
								        }
								        else {
									        last = temp[temp.length - 2] + temp[templen - 1];
									        contor = 0;
									        for (k = 2; k < templen; k++) {
										        if (contor == 0) {
											        path = path + "C";
											        contor = 3;
										        }
										        else {
											        path = path + ",";
										        }
										        path = path + temp[k] * scala + "," + temp[k + 1] * scala;
										        contor -= 1;
										        k += 1;
									        }
								        }
							        }
						        }
					        }
					        path = path + "Z";
					        color = colors[9];
					        var step = (200 - minind) / levels;
					        var cvv = dtstore.getAt(dtstore.findExact('geocode', uat[i][0]));
					        if (cvv) {
						        var position = Math.ceil((cvv.data.value - minind) / step);
						        if (position < 10) {
							        var color = colors[position];
						        }
					        }
					        else {
						        color = colors[9];
					        }

					        var mysprite = this.getAnaxDrawing().surface.add({
					            type : 'path',
					            path : path,
					            id : uat[i][0],
					            group : svggroup,
					        });
					        mysprite.setAttributes({
					            fill : color,
					            stroke : "#000000",
					            "stroke-width" : scala * 2,
					            "fill-opacity" : 0.7
					        });
					        var sw = scala * 2;
					        this.setEvents(mysprite, sw);
					        mysprite.show(true);
				        }
			        }
		        }
	        }
	    });
    },

    setEvents : function(mysprite, sw) {
	    mysprite.addListener('mouseover', function(sprite, event, other) {
		    sprite.setAttributes({
			    "stroke-width" : 2 + sw
		    }, true);
	    });
	    mysprite.addListener('mouseout', function(sprite, event, other) {
		    sprite.setAttributes({
			    "stroke-width" : sw
		    }, true);
	    });
	    var me = this;
	    mysprite.addListener('click', function(sprite, event, other) {
		    var ssstore = me.getSpriteStore();
		    sprite.setAttributes({
			    "stroke-width" : sw
		    }, true);
		    ssstore.load({
		        scope : me,
		        callback : function(rec, operation, success) {
			        var win = Ext.WindowMgr.get('spritedetail');
			        console.log(win);
			        if (!win) {
				        win = Ext.create('anax.view.SpriteDetail');
			        }
			        else {
				        win.down('panel#spdraw anaxdrawing#detaildraw').surface.removeAll();
			        }
			        win.setTitle('Sprite ' + sprite.id + 'detail');
			        win.show();
			        var mydraw = win.down('panel#spdraw anaxdrawing#detaildraw');
			        // add a group

			        var nngroup = Ext.create('Ext.draw.CompositeSprite', {
			            surface : mydraw.surface,
			            id : 'drawdetails'
			        });
			        me.getSpdetailspanel().update(ssstore.first().data);
			        ssstore.first().spritesStore.each(function(record, id) {
				        var windraw = win.down('panel#spdraw anaxdrawing#detaildraw');
				        var mysprite = windraw.surface.add({
				            type : 'path',
				            path : sprite.path,
				            id : 'sgdr' + sprite.id,
				            fill : sprite.attr.fill,
				            "stroke-width" : 0.5,
				            stroke : sprite.attr.stroke,
				            group : 'drawdetails',
				        });
				        mysprite.show(true);
			        });
			        this.scalegroup(mydraw, 'drawdetails');
		        }
		    });
	    });

    },

    drawMap : function(mapdata, level, geodatatype, startdate, enddate) {
	    var mapgroup = 'mapgroup';
	    var mapid = mapdata.id;
	    this.getAnaxDrawing().setMapid(mapid);

	    console.log('mapid set: ' + this.getAnaxDrawing().getMapid());

	    var nngroup = Ext.create('Ext.draw.CompositeSprite', {
	        surface : this.getAnaxDrawing().surface,
	        id : mapgroup
	    });
	    // this.drawCountryBorder(mapdata, mapgroup);
	    this.drawUATs(mapdata, mapgroup, level, geodatatype, startdate, enddate);
	    // aici luam indicatorul si coloram.
    },

    setEvents : function(mysprite, sw) {
	    mysprite.addListener('mouseover', function(sprite, event, other) {
		    sprite.setAttributes({
			    "stroke-width" : 2 + sw
		    }, true);
	    });
	    mysprite.addListener('mouseout', function(sprite, event, other) {
		    sprite.setAttributes({
			    "stroke-width" : sw
		    }, true);
	    });
	    var me = this;
	    mysprite.addListener('click', function(sprite, event, other) {
		    var ssstore = me.getSpriteStore();
		    sprite.setAttributes({
			    "stroke-width" : sw
		    }, true);
		    ssstore.load({
		        scope : me,
		        callback : function(rec, operation, success) {
			        var win = Ext.WindowMgr.get('spritedetail');
			        console.log(win);
			        if (!win) {
				        win = Ext.create('anax.view.SpriteDetail');
			        }
			        else {
				        win.down('panel#spdraw anaxdrawing#detaildraw').surface.removeAll();
			        }
			        win.setTitle('Sprite ' + sprite.id + 'detail');
			        win.show();
			        var mydraw = win.down('panel#spdraw anaxdrawing#detaildraw');
			        // add a group

			        var nngroup = Ext.create('Ext.draw.CompositeSprite', {
			            surface : mydraw.surface,
			            id : 'drawdetails'
			        });
			        me.getSpdetailspanel().update(ssstore.first().data);
			        ssstore.first().spritesStore.each(function(record, id) {
				        var windraw = win.down('panel#spdraw anaxdrawing#detaildraw');
				        var mysprite = windraw.surface.add({
				            type : 'path',
				            path : sprite.path,
				            id : 'sgdr' + sprite.id,
				            fill : sprite.attr.fill,
				            "stroke-width" : 0.5,
				            stroke : sprite.attr.stroke,
				            group : 'drawdetails',
				        });
				        mysprite.show(true);
			        });
			        this.scalegroup(mydraw, 'drawdetails');
		        }
		    });
	    });

    },

    buildLeveler : function(mapdata) {
	    console.log('build leveler');
	    console.log(mapdata.levelsnr);
	    if (mapdata.levelsnr > 1) {
		    // see if the menu is shown
		    if (this.getLevelmenu().hidden) {
			    this.getLevelmenu().show();
		    }
		    else {
			    var smth = this.getLevelmenu().down('lvmenu');
			    console.log(smth);
		    }
		    var me = this;
		    var lmenu = Ext.create('Ext.menu.Menu', {
			    itemId : "lvmenu"
		    });
		    for (i = 0; i < mapdata.levels.length; i++) {

			    lmenu.add({
			        text : mapdata.levels[i].name,
			        itemId : mapdata.levels[i].id,
			    })
		    }
		    this.getLevelmenu().menu = lmenu;
	    }
    },

    setCurrentLevel : function(mapdata, level) {
	    // see if we have a default level
	    if (!level > 0) {
		    level = mapdata.defaultlevel;
	    }
	    for (i = 0; i < mapdata.levels.length; i++) {
		    // sa vedem care e
		    if (mapdata.levels[i].id == level) {
			    console.log('found it');
			    this.getLevelmenu().setText(mapdata.levels[i].name);
			    this.getAnaxDrawing().setMaplevel(mapdata.levels[i].id);
			    return mapdata.levels[i].id;
		    }
	    }
    },

    drawNewMap : function(geographyid, geodatatype, startdate, enddate) {
	    console.log('drawnewmap2');

	    // acu ar trebui sa vedem cate nivele avem
	    var me = this;
	    Ext.Ajax.request({
	        url : '/roda/resources/RODAAnax/data/map.json',
	        method : "GET",
	        params : {
		        id : geographyid,
	        },
	        success : function(response, request) {
		        var responseJson = Ext.decode(response.responseText);
		        if (responseJson.success === true) {
			        me.buildLeveler(responseJson);
			        var clevel = me.setCurrentLevel(responseJson);
			        me.drawMap(responseJson, clevel, geodatatype, startdate, enddate);
			        me.getAnaxmap().setLoading(false);
		        }
		        else {
			        console.log('not');
		        }
	        },
	        failure : function(response, opts) {
		        Ext.Msg.alert('Failure', response);

	        }
	    });

    },

    setData : function(geographyid, geodatatype, startdate, enddate) {
	    console.log('setdata');
	    // setdata ia levelul din harta curent, ea e deja desenata acum
	    var clevel = this.getAnaxDrawing().getMaplevel();
	    // sa vedem data

	    var dtstore = Ext.StoreManager.get('Indicator');

	    dtstore.load({
	        geographyid : geographyid,
	        geodatatype : geodatatype,
	        startdate : startdate,
	        enddate : enddate,
	        scope : this,
	        callback : function(records, operation, success) {
		        if (success) {

			        var maxind = 0;
			        var minind = 1000;

			        dtstore.each(function(record) {
				        if (minind > record.data.value) {
					        minind = record.data.value
				        }

				        if (maxind < record.data.value) {
					        maxind = record.data.value;
				        }
			        });

			        console.log('maxind: ' + maxind);
			        console.log('minind: ' + minind);
			        // how many levels?
			        var colors = new Array;
			        var levels = 10;
			        var hue = 100;
			        var huepadding = 20;
			        for (i = huepadding; i < 100 - huepadding; i += (100 - 2 * huepadding) / levels) {
				        console.log(i);
				        // console.log(this.hsvtohex(hue, 100, i));
				        colors.push(this.hsvtohex(hue, 100, i));
			        }
			        var cvv = dtstore.getAt(dtstore.findExact('geocode', 103513));

			        var step = (maxind - minind) / levels;
			        var cr = this.getAnaxDrawing().surface;
			        console.log(cr);
			        var mygroup = cr.getGroup('mapgroup');
			        console.log(mygroup);
			        for (j = 0; j < mygroup.items.length; j++) {
				        console.log('-----' + j);
				        var csprite = mygroup.items[j];
			        }
		        }
	        }
	    });
    },

    toHex : function(nr) {
	    var hex = nr.toString(16);
	    return hex.length == 1 ? "0" + hex : hex;
    },

    hsvtohex : function(h, s, v) {
	    console.log('hsvtohex');

	    var r, g, b;
	    var i;
	    var f, p, q, t;

	    // Make sure our arguments stay in-range
	    h = Math.max(0, Math.min(360, h));
	    s = Math.max(0, Math.min(100, s));
	    v = Math.max(0, Math.min(100, v));

	    s /= 100;
	    v /= 100;

	    h /= 60; // sector 0 to 5
	    i = Math.floor(h);
	    f = h - i; // factorial part of h
	    p = v * (1 - s);
	    q = v * (1 - s * f);
	    t = v * (1 - s * (1 - f));

	    switch (i) {
	    case 0:
		    r = v;
		    g = t;
		    b = p;
		    break;

	    case 1:
		    r = q;
		    g = v;
		    b = p;
		    break;

	    case 2:
		    r = p;
		    g = v;
		    b = t;
		    break;

	    case 3:
		    r = p;
		    g = q;
		    b = v;
		    break;

	    case 4:
		    r = t;
		    g = p;
		    b = v;
		    break;

	    default: // case 5:
		    r = v;
		    g = p;
		    b = q;
	    }
	    return "#" + this.toHex(Math.round(r * 255)) + this.toHex(Math.round(g * 255))
	            + this.toHex(Math.round(b * 255));
    },

    showII : function(component, selected, event) {
	    geographyid = selected[0].data.geographyid;
	    startdate = selected[0].data.startdate;
	    enddate = selected[0].data.enddate;
	    geodatatype = selected[0].data.geodatatype;
	    var currentmapid = this.getAnaxDrawing().getMapid();
	    console.log(currentmapid);
	    if (currentmapid) {
		    if (currentmapid === geographyid) {
			    console.log('replacing map');
			    // this.replaceMap(component, selected, event);
		    }
		    else {
			    console.log('clean old, new map');
			    this.cleanMap();
			    this.drawNewMap(geographyid, geodatatype, startdate, enddate);
		    }
	    }
	    else {
		    console.log('drawing new map');
		    this.drawNewMap(geographyid, geodatatype, startdate, enddate);
	    }
    },

    onYTreeSelectionChange : function(component, selected, event) {
	    var type = selected[0].data.type;
	    if (type == 'YG') {
		    this.showYG(component, selected, event);
	    }
	    else if (type == 'II') {
		    this.showII(component, selected, event);
	    }
    },

    onTreeSelectionChange : function(component, selected, event) {
	    var type = selected[0].data.type;
	    if (type == 'IG') {
		    this.showYG(component, selected, event);
	    }
	    else if (type == 'II') {
		    this.showII(component, selected, event);
	    }
    },

});
