var Harness = Siesta.Harness.Browser.ExtJS;

Harness.configure({
    title       : 'Mastering Ext JS Test Suite',
    loaderPath  : { 'RODAdmin' : '../app' },

    preload     : [
        '../bootstrap.css',
        '../ext/ext-all-debug-w-comments.js',
        '../bootstrap.js',
        '../app.js',
        '../app.css',
        '../app/application.js',
        '../app/util/Util.js'
        
    ]
});

Harness.start(
    '010_extload.t.js',
    '020_appload.t.js'
);