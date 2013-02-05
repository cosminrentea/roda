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
import ro.roda.OrgEmail;
import ro.roda.OrgEmailDataOnDemand;
import ro.roda.OrgEmailIntegrationTest;
import ro.roda.OrgEmailPK;

privileged aspect OrgEmailIntegrationTest_Roo_IntegrationTest {
    
    declare @type: OrgEmailIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: OrgEmailIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: OrgEmailIntegrationTest: @Transactional;
    
    @Autowired
    private OrgEmailDataOnDemand OrgEmailIntegrationTest.dod;
    
    @Test
    public void OrgEmailIntegrationTest.testCountOrgEmails() {
        Assert.assertNotNull("Data on demand for 'OrgEmail' failed to initialize correctly", dod.getRandomOrgEmail());
        long count = OrgEmail.countOrgEmails();
        Assert.assertTrue("Counter for 'OrgEmail' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void OrgEmailIntegrationTest.testFindOrgEmail() {
        OrgEmail obj = dod.getRandomOrgEmail();
        Assert.assertNotNull("Data on demand for 'OrgEmail' failed to initialize correctly", obj);
        OrgEmailPK id = obj.getId();
        Assert.assertNotNull("Data on demand for 'OrgEmail' failed to provide an identifier", id);
        obj = OrgEmail.findOrgEmail(id);
        Assert.assertNotNull("Find method for 'OrgEmail' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'OrgEmail' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void OrgEmailIntegrationTest.testFindAllOrgEmails() {
        Assert.assertNotNull("Data on demand for 'OrgEmail' failed to initialize correctly", dod.getRandomOrgEmail());
        long count = OrgEmail.countOrgEmails();
        Assert.assertTrue("Too expensive to perform a find all test for 'OrgEmail', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<OrgEmail> result = OrgEmail.findAllOrgEmails();
        Assert.assertNotNull("Find all method for 'OrgEmail' illegally returned null", result);
        Assert.assertTrue("Find all method for 'OrgEmail' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void OrgEmailIntegrationTest.testFindOrgEmailEntries() {
        Assert.assertNotNull("Data on demand for 'OrgEmail' failed to initialize correctly", dod.getRandomOrgEmail());
        long count = OrgEmail.countOrgEmails();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<OrgEmail> result = OrgEmail.findOrgEmailEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'OrgEmail' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'OrgEmail' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void OrgEmailIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'OrgEmail' failed to initialize correctly", dod.getRandomOrgEmail());
        OrgEmail obj = dod.getNewTransientOrgEmail(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'OrgEmail' failed to provide a new transient entity", obj);
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'OrgEmail' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void OrgEmailIntegrationTest.testRemove() {
        OrgEmail obj = dod.getRandomOrgEmail();
        Assert.assertNotNull("Data on demand for 'OrgEmail' failed to initialize correctly", obj);
        OrgEmailPK id = obj.getId();
        Assert.assertNotNull("Data on demand for 'OrgEmail' failed to provide an identifier", id);
        obj = OrgEmail.findOrgEmail(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'OrgEmail' with identifier '" + id + "'", OrgEmail.findOrgEmail(id));
    }
    
}