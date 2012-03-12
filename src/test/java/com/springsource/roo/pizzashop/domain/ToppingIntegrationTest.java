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
@RooIntegrationTest(entity = Topping.class)
public class ToppingIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private ToppingDataOnDemand dod;

	@Test
    public void testCountToppings() {
        org.junit.Assert.assertNotNull("Data on demand for 'Topping' failed to initialize correctly", dod.getRandomTopping());
        long count = com.springsource.roo.pizzashop.domain.Topping.countToppings();
        org.junit.Assert.assertTrue("Counter for 'Topping' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindTopping() {
        com.springsource.roo.pizzashop.domain.Topping obj = dod.getRandomTopping();
        org.junit.Assert.assertNotNull("Data on demand for 'Topping' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Topping' failed to provide an identifier", id);
        obj = com.springsource.roo.pizzashop.domain.Topping.findTopping(id);
        org.junit.Assert.assertNotNull("Find method for 'Topping' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Topping' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllToppings() {
        org.junit.Assert.assertNotNull("Data on demand for 'Topping' failed to initialize correctly", dod.getRandomTopping());
        long count = com.springsource.roo.pizzashop.domain.Topping.countToppings();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Topping', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<com.springsource.roo.pizzashop.domain.Topping> result = com.springsource.roo.pizzashop.domain.Topping.findAllToppings();
        org.junit.Assert.assertNotNull("Find all method for 'Topping' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Topping' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindToppingEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Topping' failed to initialize correctly", dod.getRandomTopping());
        long count = com.springsource.roo.pizzashop.domain.Topping.countToppings();
        if (count > 20) count = 20;
        java.util.List<com.springsource.roo.pizzashop.domain.Topping> result = com.springsource.roo.pizzashop.domain.Topping.findToppingEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Topping' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Topping' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        com.springsource.roo.pizzashop.domain.Topping obj = dod.getRandomTopping();
        org.junit.Assert.assertNotNull("Data on demand for 'Topping' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Topping' failed to provide an identifier", id);
        obj = com.springsource.roo.pizzashop.domain.Topping.findTopping(id);
        org.junit.Assert.assertNotNull("Find method for 'Topping' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyTopping(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Topping' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMerge() {
        com.springsource.roo.pizzashop.domain.Topping obj = dod.getRandomTopping();
        org.junit.Assert.assertNotNull("Data on demand for 'Topping' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Topping' failed to provide an identifier", id);
        obj = com.springsource.roo.pizzashop.domain.Topping.findTopping(id);
        boolean modified =  dod.modifyTopping(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        com.springsource.roo.pizzashop.domain.Topping merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'Topping' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Topping' failed to initialize correctly", dod.getRandomTopping());
        com.springsource.roo.pizzashop.domain.Topping obj = dod.getNewTransientTopping(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'Topping' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Topping' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Topping' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        com.springsource.roo.pizzashop.domain.Topping obj = dod.getRandomTopping();
        org.junit.Assert.assertNotNull("Data on demand for 'Topping' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Topping' failed to provide an identifier", id);
        obj = com.springsource.roo.pizzashop.domain.Topping.findTopping(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Topping' with identifier '" + id + "'", com.springsource.roo.pizzashop.domain.Topping.findTopping(id));
    }
}
