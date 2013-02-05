// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.UserAuthLog;
import ro.roda.UserAuthLogDataOnDemand;
import ro.roda.UserAuthLogIntegrationTest;
import ro.roda.UserAuthLogPK;

privileged aspect UserAuthLogIntegrationTest_Roo_IntegrationTest {
    
    declare @type: UserAuthLogIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: UserAuthLogIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: UserAuthLogIntegrationTest: @Transactional;
    
    @Autowired
    private UserAuthLogDataOnDemand UserAuthLogIntegrationTest.dod;
    
    @Test
    public void UserAuthLogIntegrationTest.testCountUserAuthLogs() {
        Assert.assertNotNull("Data on demand for 'UserAuthLog' failed to initialize correctly", dod.getRandomUserAuthLog());
        long count = UserAuthLog.countUserAuthLogs();
        Assert.assertTrue("Counter for 'UserAuthLog' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void UserAuthLogIntegrationTest.testFindUserAuthLog() {
        UserAuthLog obj = dod.getRandomUserAuthLog();
        Assert.assertNotNull("Data on demand for 'UserAuthLog' failed to initialize correctly", obj);
        UserAuthLogPK id = obj.getId();
        Assert.assertNotNull("Data on demand for 'UserAuthLog' failed to provide an identifier", id);
        obj = UserAuthLog.findUserAuthLog(id);
        Assert.assertNotNull("Find method for 'UserAuthLog' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'UserAuthLog' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void UserAuthLogIntegrationTest.testFindAllUserAuthLogs() {
        Assert.assertNotNull("Data on demand for 'UserAuthLog' failed to initialize correctly", dod.getRandomUserAuthLog());
        long count = UserAuthLog.countUserAuthLogs();
        Assert.assertTrue("Too expensive to perform a find all test for 'UserAuthLog', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<UserAuthLog> result = UserAuthLog.findAllUserAuthLogs();
        Assert.assertNotNull("Find all method for 'UserAuthLog' illegally returned null", result);
        Assert.assertTrue("Find all method for 'UserAuthLog' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void UserAuthLogIntegrationTest.testFindUserAuthLogEntries() {
        Assert.assertNotNull("Data on demand for 'UserAuthLog' failed to initialize correctly", dod.getRandomUserAuthLog());
        long count = UserAuthLog.countUserAuthLogs();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<UserAuthLog> result = UserAuthLog.findUserAuthLogEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'UserAuthLog' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'UserAuthLog' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void UserAuthLogIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'UserAuthLog' failed to initialize correctly", dod.getRandomUserAuthLog());
        UserAuthLog obj = dod.getNewTransientUserAuthLog(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'UserAuthLog' failed to provide a new transient entity", obj);
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'UserAuthLog' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void UserAuthLogIntegrationTest.testRemove() {
        UserAuthLog obj = dod.getRandomUserAuthLog();
        Assert.assertNotNull("Data on demand for 'UserAuthLog' failed to initialize correctly", obj);
        UserAuthLogPK id = obj.getId();
        Assert.assertNotNull("Data on demand for 'UserAuthLog' failed to provide an identifier", id);
        obj = UserAuthLog.findUserAuthLog(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'UserAuthLog' with identifier '" + id + "'", UserAuthLog.findUserAuthLog(id));
    }
    
}