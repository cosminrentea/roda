StartTest(function(t) {
    t.diag("Application Load");

    t.ok(RODAdmin, 'RODAdmin Loaded');

    t.requireOk('RODAdmin.view.cms.files.Files');
    t.requireOk('RODAdmin.view.cms.files.FileDetails');
    t.requireOk('RODAdmin.view.cms.files.FileWindow');
    t.requireOk('RODAdmin.view.cms.files.FolderWindow');
    t.requireOk('RODAdmin.view.cms.files.EditFileWindow');

    t.done();   // Optional, marks the correct exit point from the test
})  