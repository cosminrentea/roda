\copy cms_folder(name,parent_id,description) FROM stdin DELIMITERS ',' CSV;
Root,,"Root folder"
\.

\copy country(id,alpha3,name) FROM stdin DELIMITERS ',' CSV;
ro,rou,românia
at,aut,austria
be,bel,belgia
bg,bgr,bulgaria
cy,cyp,cyprus
cz,cze,cehia
dk,dnk,danemarca
ee,est,estonia
fi,fin,finlanda
fr,fra,franţa
de,deu,germania
gr,grc,grecia
hu,hun,ungaria
ie,irl,irlanda
it,ita,italia
lv,lva,letonia
lt,ltu,lituania
lu,lux,luxemburg
mt,mlt,malta
nl,nld,olanda
pl,pol,polonia
pt,prt,portugalia
sk,svk,slovacia
si,svn,slovenia
es,esp,spania
se,swe,suedia
gb,gbr,"marea britanie"
\.

\copy lang(id,name) FROM stdin DELIMITERS ',' CSV;
ro,română
en,english
fr,français
fi,suomi
de,deutsch
he,עברית
hu,magyar
it,italiano
lv,latviešu
pl,polszczyzna
pt,português
ru,русский
sk,slovenčina
\.

\copy prefix(id,name) FROM stdin DELIMITERS ',' CSV;
1,domnul
2,doamna
3,domnişoara
\.

\copy regiontype(id,name) FROM stdin DELIMITERS ',' CSV;
1,Judeţ
2,Regiune de dezvoltare
\.

\copy rodauser(id,credential_provider) FROM stdin DELIMITERS ',' CSV;
1,RODA
\.

\copy sampling_procedure(Id,Name) FROM stdin DELIMITERS ',' CSV;
1,no sampling (total universe)
2,quota sample
3,simple random sample
4,one-stage stratified or systematic random sample
5,one-stage cluster sample
6,multi-stage stratified random sample
7,quasi-random (e.g. random walk) sample
8,purposive selection/case studies
9,volunteer sample
10,convenience sample
\.

\copy time_meth_type(Id,Name) FROM stdin DELIMITERS ',' CSV;
1,Longitudinal
2,Longitudinal.CohortEventBased
3,Longitudinal.TrendRepeatedCrossSection
4,Longitudinal.Panel
5,Longitudinal.Panel.Continuous
6,Longitudinal.Panel.Interval
7,TimeSeries
8,TimeSeries.Continuous
9,TimeSeries.Discrete
10,CrossSection
11,CrossSectionAdHocFollowUp
12,Other
\.

\copy unit_analysis(Id,Name) FROM stdin DELIMITERS ',' CSV;
1,Individual
2,Organization
3,Family
4,Family.HouseholdFamily
5,Household
6,HousingUnit
7,EventOrProcess
8,GeographicUnit
9,TimeUnit
10,TextUnit
11,"Group"
12,Object
13,Other
\.

\copy region(region_code,region_code_name,regiontype_id,name,country_id) FROM stdin DELIMITERS ',' CSV;
10,siruta,1,alba,ro
29,siruta,1,arad,ro
38,siruta,1,argeş,ro
47,siruta,1,bacãu,ro
56,siruta,1,bihor,ro
65,siruta,1,bistriţa-nãsãud,ro
74,siruta,1,botoşani,ro
83,siruta,1,braşov,ro
92,siruta,1,brãila,ro
109,siruta,1,buzãu,ro
118,siruta,1,caraş-severin,ro
519,siruta,1,cãlãraşi,ro
127,siruta,1,cluj,ro
136,siruta,1,constanţa,ro
145,siruta,1,covasna,ro
154,siruta,1,dâmboviţa,ro
163,siruta,1,dolj,ro
172,siruta,1,galaţi,ro
528,siruta,1,giurgiu,ro
181,siruta,1,gorj,ro
190,siruta,1,harghita,ro
207,siruta,1,hunedoara,ro
216,siruta,1,ialomiţa,ro
225,siruta,1,iaşi,ro
234,siruta,1,ilfov,ro
243,siruta,1,maramureş,ro
252,siruta,1,mehedinţi,ro
261,siruta,1,mureş,ro
270,siruta,1,neamţ,ro
289,siruta,1,olt,ro
298,siruta,1,prahova,ro
305,siruta,1,satu mare,ro
314,siruta,1,sãlaj,ro
323,siruta,1,sibiu,ro
332,siruta,1,suceava,ro
341,siruta,1,teleorman,ro
350,siruta,1,timiş,ro
369,siruta,1,tulcea,ro
378,siruta,1,vaslui,ro
387,siruta,1,vâlcea,ro
396,siruta,1,vrancea,ro
403,siruta,1,bucureşti,ro
\.

\copy city(city_code,name,city_code_name,city_code_type,city_code_sup,regiune,judet) FROM stdin DELIMITERS ',' CSV;
1017,municipiul alba iulia,siruta,1,10,7,1
403,municipiul bucureşti,siruta,40,1,8,40
1026,alba iulia,siruta,9,1017,7,1
1035,bãrãbanţ,siruta,10,1017,7,1
1044,miceşti,siruta,10,1017,7,1
1053,oarda,siruta,10,1017,7,1
1062,pâclişa,siruta,10,1017,7,1
34752,sãrãţel,siruta,23,34690,6,6
34761,valea mãgheruşului,siruta,23,34690,6,6
34770,şieu-odorhei,siruta,3,65,6,6
34789,şieu-odorhei,siruta,22,34770,6,6
34798,agrişu de jos,siruta,23,34770,6,6
34805,agrişu de sus,siruta,23,34770,6,6
34814,bretea,siruta,23,34770,6,6
34823,coasta,siruta,23,34770,6,6
34832,cristur-şieu,siruta,23,34770,6,6
34841,şirioara,siruta,23,34770,6,6
34850,şieuţ,siruta,3,65,6,6
34869,şieuţ,siruta,22,34850,6,6
34878,lunca,siruta,23,34850,6,6
34887,ruştior,siruta,23,34850,6,6
34896,sebiş,siruta,23,34850,6,6
34903,şintereag,siruta,3,65,6,6
34912,şintereag,siruta,22,34903,6,6
34921,blãjenii de jos,siruta,23,34903,6,6
34930,blãjenii de sus,siruta,23,34903,6,6
34949,caila,siruta,23,34903,6,6
34958,cociu,siruta,23,34903,6,6
34967,şieu-sfântu,siruta,23,34903,6,6
73362,moţãţei garã,siruta,23,73335,4,16
73371,murgaşi,siruta,3,163,4,16
73380,balota de jos,siruta,22,73371,4,16
73399,balota de sus,siruta,23,73371,4,16
73406,buşteni,siruta,23,73371,4,16
73415,gaia,siruta,23,73371,4,16
73424,murgaşi,siruta,23,73371,4,16
123852,pârâul fagului,siruta,23,123790,1,27
123843,petru vodã,siruta,23,123790,1,27
123861,poiana largului,siruta,23,123790,1,27
123870,roşeni,siruta,23,123 790,1,27
\.

