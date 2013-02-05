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
import ro.roda.SelectionVariableItem;
import ro.roda.SelectionVariableItemDataOnDemand;
import ro.roda.SelectionVariableItemIntegrationTest;
import ro.roda.SelectionVariableItemPK;

privileged aspect SelectionVariableItemIntegrationTest_Roo_IntegrationTest {
    
    declare @type: SelectionVariableItemIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: SelectionVariableItemIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: SelectionVariableItemIntegrationTest: @Transactional;
    
    @Autowired
    private SelectionVariableItemDataOnDemand SelectionVariableItemIntegrationTest.dod;
    
    @Test
    public void SelectionVariableItemIntegrationTest.testCountSelectionVariableItems() {
        Assert.assertNotNull("Data on demand for 'SelectionVariableItem' failed to initialize correctly", dod.getRandomSelectionVariableItem());
        long count = SelectionVariableItem.countSelectionVariableItems();
        Assert.assertTrue("Counter for 'SelectionVariableItem' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void SelectionVariableItemIntegrationTest.testFindSelectionVariableItem() {
        SelectionVariableItem obj = dod.getRandomSelectionVariableItem();
        Assert.assertNotNull("Data on demand for 'SelectionVariableItem' failed to initialize correctly", obj);
        SelectionVariableItemPK id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SelectionVariableItem' failed to provide an identifier", id);
        obj = SelectionVariableItem.findSelectionVariableItem(id);
        Assert.assertNotNull("Find method for 'SelectionVariableItem' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'SelectionVariableItem' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void SelectionVariableItemIntegrationTest.testFindAllSelectionVariableItems() {
        Assert.assertNotNull("Data on demand for 'SelectionVariableItem' failed to initialize correctly", dod.getRandomSelectionVariableItem());
        long count = SelectionVariableItem.countSelectionVariableItems();
        Assert.assertTrue("Too expensive to perform a find all test for 'SelectionVariableItem', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<SelectionVariableItem> result = SelectionVariableItem.findAllSelectionVariableItems();
        Assert.assertNotNull("Find all method for 'SelectionVariableItem' illegally returned null", result);
        Assert.assertTrue("Find all method for 'SelectionVariableItem' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void SelectionVariableItemIntegrationTest.testFindSelectionVariableItemEntries() {
        Assert.assertNotNull("Data on demand for 'SelectionVariableItem' failed to initialize correctly", dod.getRandomSelectionVariableItem());
        long count = SelectionVariableItem.countSelectionVariableItems();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<SelectionVariableItem> result = SelectionVariableItem.findSelectionVariableItemEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'SelectionVariableItem' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'SelectionVariableItem' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void SelectionVariableItemIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'SelectionVariableItem' failed to initialize correctly", dod.getRandomSelectionVariableItem());
        SelectionVariableItem obj = dod.getNewTransientSelectionVariableItem(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'SelectionVariableItem' failed to provide a new transient entity", obj);
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'SelectionVariableItem' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void SelectionVariableItemIntegrationTest.testRemove() {
        SelectionVariableItem obj = dod.getRandomSelectionVariableItem();
        Assert.assertNotNull("Data on demand for 'SelectionVariableItem' failed to initialize correctly", obj);
        SelectionVariableItemPK id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SelectionVariableItem' failed to provide an identifier", id);
        obj = SelectionVariableItem.findSelectionVariableItem(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'SelectionVariableItem' with identifier '" + id + "'", SelectionVariableItem.findSelectionVariableItem(id));
    }
    
}