function get_pagination(start, page, total, lang) {
var responses  = [];
	console.log('-------' + lang);
	if (start > 0) {
		var pgg = start/page;
		var prev = pgg - 1;
		if (prev < 1) {
			prev = 0;
		}
		if (lang == 'en') {
			responses.push('<div class="src_nav"><a  class="pagprev" onClick=doSearch('+ prev + ','+ page + ",'" + lang + "')>Previous</a></div>");
		} else if (lang == 'ro') {
			responses.push('<div class="src_nav"><a  class="pagprev" onClick=doSearch('+ prev + ','+ page +",'" + lang + "')>Anterior</a></div>");
		}
	}

	if ((total - start) > page) {
		var nstart = start + page;
		if (lang == 'en') {
			responses.push( '<div class="src_nav"><a class="pagnext" onClick=doSearch('+nstart + ','+ page +",'" + lang + "')>Next</a></div>" );
		} else if (lang == 'ro') {
			responses.push( '<div class="src_nav"><a class="pagnext" onClick=doSearch('+nstart + ','+ page +",'" + lang + "')>Urmator</a></div>" );			
		}
	}
	return responses.join("");
}

 
function on_recdata_en(data) {
	return on_data('en', data);
}


function on_recdata_ro(data) {
	return on_data('ro', data);
}

function on_data(lang, data) {
    $('#resultsconteiner').empty();
    $('#results').css('height','400px');
    
	var pagination = get_pagination(data.response.start, 10, data.response.numFound, lang);
	$('#resultsconteiner').append('<div id="header_res">' + pagination+ '<div class="nr_res"><span style="font-size:17px;"><b>'+data.response.numFound + '</b></span> results'+'</div><div class="cpl">&nbsp;</div></div>');
    var docs = data.response.docs;
    if (data.response.numFound > 0) {
    	var rescont = '<div id="res_table"><table class="restable">';
    	$.each(docs, function(i, item) {
				var descr = String(data.highlighting[item.id].description) + '...';
				var cleanText = descr.replace(/<\/?[^>]+(>|$)/g, "");
				var title = new String;
				var cleanTitle = new String;
				var parentstring = new String;
				
				
				
				if (item.name) {
					title = String(item.name) + ' ';					
					cleanTitle = title.replace(/<\/?[^>]+(>|$)/g, "");
				} else {
					if (lang =='ro') { 
						cleanTitle = 'Fara titlu';
					}
					if (lang =='en') {
						cleanTitle = 'Untitled';
					}
				}
				if (lang =='ro') { 
					parentstring = 'parte din <a href="' +item.parenturl + '">'+item.parentname + '</a>';
				}
				if (lang =='en') {
					parentstring = 'member of <a href="' +item.parenturl + '">'+item.parentname + '</a>';
				}
				var entityclass;
				if (item.entity == 'cmspage') {
					entityclass='spage';	
				} else if (item.entity == 'catalog') {
					entityclass='scatalog';	
				} else if (item.entity == 'series') {
					entityclass='sseries';	
				} else if (item.entity == 'variable') {					
					entityclass='svariable';	
					cleanText = cleanText + '<br>' + parentstring;
				} else if (item.entity == 'study') {				
					entityclass='sstudy';	
				} else {
					entityclass = 'tempty';
				}
				
        		rescont = rescont + '<tr><td valign="top" class="'+entityclass+'"></td><td valign="top">' + '<div class="restitle"><a href="'+item.url +'">'+ cleanTitle +'</a></div>' +'<p>' + cleanText + '</p></td></tr>';
    	});
		rescont = rescont + '</table></div>';
    } else {
    	$('#results').css('height','80px');
    }
    $('#resultsconteiner').append(rescont);
	
 	$("#results").unmask();
    $('#results').show();
}

    function doSearch(start,page, lang, solrurl) {
    	$("#results").mask("Searching...");
    	var query = $('#query').val();
        if (query.length == 0) {
            return;
        }
		if (!start) {
			start=0;
		}		
		if (!page) {
			page=10;
		}
        var langpar = 'on_recdata_' +lang;
        
        var url=solrurl.trim() + '/select/?q=language:'+lang+' AND description:'+query+'&version=2.2&hl=true&hl.fl=description&start='+start+'&rows='+page+'&indent=on&wt=json&callback=?&json.wrf='+ langpar;
      //var url2='http://localhost:8983/solr/collection1/select/?q=language:'+lang+' AND description:'+query+'&version=2.2&hl=true&hl.fl=description&start='+start+'&rows='+page+'&indent=on&wt=json&callback=?&json.wrf='+ langpar;

      //  console.log(url);
      //  console.log(url2);
        
        $.getJSON(url);
      
    }
    function closesearch() {
    	  $('#resultsconteiner').empty();
    	  $('#results').hide();
    }
    
    function on_ready(lang, solrurl) {
    	$('#searchbtn').click(function() {
    		doSearch(0,10, lang, solrurl);
    	});
        /* Hook enter to search */
        $('body').keypress(function(e) {
            if (e.keyCode == '13') {
                doSearch(0, 10, lang, solrurl);
            }
        });
    }