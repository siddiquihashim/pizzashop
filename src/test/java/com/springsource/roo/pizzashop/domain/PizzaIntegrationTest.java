package com.springsource.roo.pizzashop.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@Transactional
@RooIntegrationTest(entity = Pizza.class)
public class PizzaIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private PizzaDataOnDemand dod;

	@Test
    public void testCountPizzas() {
        org.junit.Assert.assertNotNull("Data on demand for 'Pizza' failed to initialize correctly", dod.getRandomPizza());
        long count = com.springsource.roo.pizzashop.domain.Pizza.countPizzas();
        org.junit.Assert.assertTrue("Counter for 'Pizza' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindPizza() {
        com.springsource.roo.pizzashop.domain.Pizza obj = dod.getRandomPizza();
        org.junit.Assert.assertNotNull("Data on demand for 'Pizza' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Pizza' failed to provide an identifier", id);
        obj = com.springsource.roo.pizzashop.domain.Pizza.findPizza(id);
        org.junit.Assert.assertNotNull("Find method for 'Pizza' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Pizza' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllPizzas() {
        org.junit.Assert.assertNotNull("Data on demand for 'Pizza' failed to initialize correctly", dod.getRandomPizza());
        long count = com.springsource.roo.pizzashop.domain.Pizza.countPizzas();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Pizza', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<com.springsource.roo.pizzashop.domain.Pizza> result = com.springsource.roo.pizzashop.domain.Pizza.findAllPizzas();
        org.junit.Assert.assertNotNull("Find all method for 'Pizza' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Pizza' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindPizzaEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Pizza' failed to initialize correctly", dod.getRandomPizza());
        long count = com.springsource.roo.pizzashop.domain.Pizza.countPizzas();
        if (count > 20) count = 20;
        java.util.List<com.springsource.roo.pizzashop.domain.Pizza> result = com.springsource.roo.pizzashop.domain.Pizza.findPizzaEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Pizza' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Pizza' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        com.springsource.roo.pizzashop.domain.Pizza obj = dod.getRandomPizza();
        org.junit.Assert.assertNotNull("Data on demand for 'Pizza' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Pizza' failed to provide an identifier", id);
        obj = com.springsource.roo.pizzashop.domain.Pizza.findPizza(id);
        org.junit.Assert.assertNotNull("Find method for 'Pizza' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyPizza(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Pizza' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMerge() {
        com.springsource.roo.pizzashop.domain.Pizza obj = dod.getRandomPizza();
        org.junit.Assert.assertNotNull("Data on demand for 'Pizza' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Pizza' failed to provide an identifier", id);
        obj = com.springsource.roo.pizzashop.domain.Pizza.findPizza(id);
        boolean modified =  dod.modifyPizza(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        com.springsource.roo.pizzashop.domain.Pizza merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'Pizza' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Pizza' failed to initialize correctly", dod.getRandomPizza());
        com.springsource.roo.pizzashop.domain.Pizza obj = dod.getNewTransientPizza(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'Pizza' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Pizza' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Pizza' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        com.springsource.roo.pizzashop.domain.Pizza obj = dod.getRandomPizza();
        org.junit.Assert.assertNotNull("Data on demand for 'Pizza' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Pizza' failed to provide an identifier", id);
        obj = com.springsource.roo.pizzashop.domain.Pizza.findPizza(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Pizza' with identifier '" + id + "'", com.springsource.roo.pizzashop.domain.Pizza.findPizza(id));
    }
}
