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
import ro.roda.SourcestudyTypeHistory;
import ro.roda.SourcestudyTypeHistoryDataOnDemand;
import ro.roda.SourcestudyTypeHistoryIntegrationTest;

privileged aspect SourcestudyTypeHistoryIntegrationTest_Roo_IntegrationTest {
    
    declare @type: SourcestudyTypeHistoryIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: SourcestudyTypeHistoryIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: SourcestudyTypeHistoryIntegrationTest: @Transactional;
    
    @Autowired
    private SourcestudyTypeHistoryDataOnDemand SourcestudyTypeHistoryIntegrationTest.dod;
    
    @Test
    public void SourcestudyTypeHistoryIntegrationTest.testCountSourcestudyTypeHistorys() {
        Assert.assertNotNull("Data on demand for 'SourcestudyTypeHistory' failed to initialize correctly", dod.getRandomSourcestudyTypeHistory());
        long count = SourcestudyTypeHistory.countSourcestudyTypeHistorys();
        Assert.assertTrue("Counter for 'SourcestudyTypeHistory' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void SourcestudyTypeHistoryIntegrationTest.testFindSourcestudyTypeHistory() {
        SourcestudyTypeHistory obj = dod.getRandomSourcestudyTypeHistory();
        Assert.assertNotNull("Data on demand for 'SourcestudyTypeHistory' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SourcestudyTypeHistory' failed to provide an identifier", id);
        obj = SourcestudyTypeHistory.findSourcestudyTypeHistory(id);
        Assert.assertNotNull("Find method for 'SourcestudyTypeHistory' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'SourcestudyTypeHistory' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void SourcestudyTypeHistoryIntegrationTest.testFindAllSourcestudyTypeHistorys() {
        Assert.assertNotNull("Data on demand for 'SourcestudyTypeHistory' failed to initialize correctly", dod.getRandomSourcestudyTypeHistory());
        long count = SourcestudyTypeHistory.countSourcestudyTypeHistorys();
        Assert.assertTrue("Too expensive to perform a find all test for 'SourcestudyTypeHistory', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<SourcestudyTypeHistory> result = SourcestudyTypeHistory.findAllSourcestudyTypeHistorys();
        Assert.assertNotNull("Find all method for 'SourcestudyTypeHistory' illegally returned null", result);
        Assert.assertTrue("Find all method for 'SourcestudyTypeHistory' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void SourcestudyTypeHistoryIntegrationTest.testFindSourcestudyTypeHistoryEntries() {
        Assert.assertNotNull("Data on demand for 'SourcestudyTypeHistory' failed to initialize correctly", dod.getRandomSourcestudyTypeHistory());
        long count = SourcestudyTypeHistory.countSourcestudyTypeHistorys();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<SourcestudyTypeHistory> result = SourcestudyTypeHistory.findSourcestudyTypeHistoryEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'SourcestudyTypeHistory' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'SourcestudyTypeHistory' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void SourcestudyTypeHistoryIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'SourcestudyTypeHistory' failed to initialize correctly", dod.getRandomSourcestudyTypeHistory());
        SourcestudyTypeHistory obj = dod.getNewTransientSourcestudyTypeHistory(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'SourcestudyTypeHistory' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'SourcestudyTypeHistory' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'SourcestudyTypeHistory' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void SourcestudyTypeHistoryIntegrationTest.testRemove() {
        SourcestudyTypeHistory obj = dod.getRandomSourcestudyTypeHistory();
        Assert.assertNotNull("Data on demand for 'SourcestudyTypeHistory' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SourcestudyTypeHistory' failed to provide an identifier", id);
        obj = SourcestudyTypeHistory.findSourcestudyTypeHistory(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'SourcestudyTypeHistory' with identifier '" + id + "'", SourcestudyTypeHistory.findSourcestudyTypeHistory(id));
    }
    
}