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
import ro.roda.Internet;
import ro.roda.InternetDataOnDemand;
import ro.roda.InternetIntegrationTest;

privileged aspect InternetIntegrationTest_Roo_IntegrationTest {
    
    declare @type: InternetIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: InternetIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: InternetIntegrationTest: @Transactional;
    
    @Autowired
    private InternetDataOnDemand InternetIntegrationTest.dod;
    
    @Test
    public void InternetIntegrationTest.testCountInternets() {
        Assert.assertNotNull("Data on demand for 'Internet' failed to initialize correctly", dod.getRandomInternet());
        long count = Internet.countInternets();
        Assert.assertTrue("Counter for 'Internet' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void InternetIntegrationTest.testFindInternet() {
        Internet obj = dod.getRandomInternet();
        Assert.assertNotNull("Data on demand for 'Internet' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Internet' failed to provide an identifier", id);
        obj = Internet.findInternet(id);
        Assert.assertNotNull("Find method for 'Internet' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Internet' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void InternetIntegrationTest.testFindAllInternets() {
        Assert.assertNotNull("Data on demand for 'Internet' failed to initialize correctly", dod.getRandomInternet());
        long count = Internet.countInternets();
        Assert.assertTrue("Too expensive to perform a find all test for 'Internet', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Internet> result = Internet.findAllInternets();
        Assert.assertNotNull("Find all method for 'Internet' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Internet' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void InternetIntegrationTest.testFindInternetEntries() {
        Assert.assertNotNull("Data on demand for 'Internet' failed to initialize correctly", dod.getRandomInternet());
        long count = Internet.countInternets();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Internet> result = Internet.findInternetEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Internet' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Internet' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void InternetIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Internet' failed to initialize correctly", dod.getRandomInternet());
        Internet obj = dod.getNewTransientInternet(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Internet' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Internet' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Internet' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void InternetIntegrationTest.testRemove() {
        Internet obj = dod.getRandomInternet();
        Assert.assertNotNull("Data on demand for 'Internet' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Internet' failed to provide an identifier", id);
        obj = Internet.findInternet(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Internet' with identifier '" + id + "'", Internet.findInternet(id));
    }
    
}