package com.springsource.roo.pizzashop.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@Transactional
@Configurable
@RooIntegrationTest(entity = PizzaOrder.class)
public class PizzaOrderIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private PizzaOrderDataOnDemand dod;

	@Test
    public void testCountPizzaOrders() {
        org.junit.Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to initialize correctly", dod.getRandomPizzaOrder());
        long count = com.springsource.roo.pizzashop.domain.PizzaOrder.countPizzaOrders();
        org.junit.Assert.assertTrue("Counter for 'PizzaOrder' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindPizzaOrder() {
        com.springsource.roo.pizzashop.domain.PizzaOrder obj = dod.getRandomPizzaOrder();
        org.junit.Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to provide an identifier", id);
        obj = com.springsource.roo.pizzashop.domain.PizzaOrder.findPizzaOrder(id);
        org.junit.Assert.assertNotNull("Find method for 'PizzaOrder' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'PizzaOrder' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllPizzaOrders() {
        org.junit.Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to initialize correctly", dod.getRandomPizzaOrder());
        long count = com.springsource.roo.pizzashop.domain.PizzaOrder.countPizzaOrders();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'PizzaOrder', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<com.springsource.roo.pizzashop.domain.PizzaOrder> result = com.springsource.roo.pizzashop.domain.PizzaOrder.findAllPizzaOrders();
        org.junit.Assert.assertNotNull("Find all method for 'PizzaOrder' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'PizzaOrder' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindPizzaOrderEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to initialize correctly", dod.getRandomPizzaOrder());
        long count = com.springsource.roo.pizzashop.domain.PizzaOrder.countPizzaOrders();
        if (count > 20) count = 20;
        java.util.List<com.springsource.roo.pizzashop.domain.PizzaOrder> result = com.springsource.roo.pizzashop.domain.PizzaOrder.findPizzaOrderEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'PizzaOrder' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'PizzaOrder' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        com.springsource.roo.pizzashop.domain.PizzaOrder obj = dod.getRandomPizzaOrder();
        org.junit.Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to provide an identifier", id);
        obj = com.springsource.roo.pizzashop.domain.PizzaOrder.findPizzaOrder(id);
        org.junit.Assert.assertNotNull("Find method for 'PizzaOrder' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyPizzaOrder(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'PizzaOrder' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMerge() {
        com.springsource.roo.pizzashop.domain.PizzaOrder obj = dod.getRandomPizzaOrder();
        org.junit.Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to provide an identifier", id);
        obj = com.springsource.roo.pizzashop.domain.PizzaOrder.findPizzaOrder(id);
        boolean modified =  dod.modifyPizzaOrder(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        com.springsource.roo.pizzashop.domain.PizzaOrder merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'PizzaOrder' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to initialize correctly", dod.getRandomPizzaOrder());
        com.springsource.roo.pizzashop.domain.PizzaOrder obj = dod.getNewTransientPizzaOrder(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'PizzaOrder' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'PizzaOrder' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        com.springsource.roo.pizzashop.domain.PizzaOrder obj = dod.getRandomPizzaOrder();
        org.junit.Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to provide an identifier", id);
        obj = com.springsource.roo.pizzashop.domain.PizzaOrder.findPizzaOrder(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'PizzaOrder' with identifier '" + id + "'", com.springsource.roo.pizzashop.domain.PizzaOrder.findPizzaOrder(id));
    }
}
