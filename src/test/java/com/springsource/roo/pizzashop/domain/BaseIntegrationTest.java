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
@RooIntegrationTest(entity = Base.class)
public class BaseIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private BaseDataOnDemand dod;

	@Test
    public void testCountBases() {
        org.junit.Assert.assertNotNull("Data on demand for 'Base' failed to initialize correctly", dod.getRandomBase());
        long count = com.springsource.roo.pizzashop.domain.Base.countBases();
        org.junit.Assert.assertTrue("Counter for 'Base' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindBase() {
        com.springsource.roo.pizzashop.domain.Base obj = dod.getRandomBase();
        org.junit.Assert.assertNotNull("Data on demand for 'Base' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Base' failed to provide an identifier", id);
        obj = com.springsource.roo.pizzashop.domain.Base.findBase(id);
        org.junit.Assert.assertNotNull("Find method for 'Base' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Base' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllBases() {
        org.junit.Assert.assertNotNull("Data on demand for 'Base' failed to initialize correctly", dod.getRandomBase());
        long count = com.springsource.roo.pizzashop.domain.Base.countBases();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Base', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<com.springsource.roo.pizzashop.domain.Base> result = com.springsource.roo.pizzashop.domain.Base.findAllBases();
        org.junit.Assert.assertNotNull("Find all method for 'Base' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Base' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindBaseEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Base' failed to initialize correctly", dod.getRandomBase());
        long count = com.springsource.roo.pizzashop.domain.Base.countBases();
        if (count > 20) count = 20;
        java.util.List<com.springsource.roo.pizzashop.domain.Base> result = com.springsource.roo.pizzashop.domain.Base.findBaseEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Base' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Base' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        com.springsource.roo.pizzashop.domain.Base obj = dod.getRandomBase();
        org.junit.Assert.assertNotNull("Data on demand for 'Base' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Base' failed to provide an identifier", id);
        obj = com.springsource.roo.pizzashop.domain.Base.findBase(id);
        org.junit.Assert.assertNotNull("Find method for 'Base' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyBase(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Base' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMerge() {
        com.springsource.roo.pizzashop.domain.Base obj = dod.getRandomBase();
        org.junit.Assert.assertNotNull("Data on demand for 'Base' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Base' failed to provide an identifier", id);
        obj = com.springsource.roo.pizzashop.domain.Base.findBase(id);
        boolean modified =  dod.modifyBase(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        com.springsource.roo.pizzashop.domain.Base merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'Base' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Base' failed to initialize correctly", dod.getRandomBase());
        com.springsource.roo.pizzashop.domain.Base obj = dod.getNewTransientBase(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'Base' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Base' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Base' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        com.springsource.roo.pizzashop.domain.Base obj = dod.getRandomBase();
        org.junit.Assert.assertNotNull("Data on demand for 'Base' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Base' failed to provide an identifier", id);
        obj = com.springsource.roo.pizzashop.domain.Base.findBase(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Base' with identifier '" + id + "'", com.springsource.roo.pizzashop.domain.Base.findBase(id));
    }
}
