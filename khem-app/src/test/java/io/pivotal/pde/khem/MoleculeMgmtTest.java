package io.pivotal.pde.khem;

import static org.junit.Assert.*;

import org.apache.geode.cache.Region;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.*;

import io.pivotal.pde.khem.data.KHEMCriteria;
import io.pivotal.pde.khem.data.Molecule;

public class MoleculeMgmtTest
{

	@BeforeClass
	public static void setUp()
	{
		Region<String,Molecule> region = mock(Region.class);
		mgmt = new MoleculeMgmt();
		mgmt.moleculesRegion = region;
	}
	@Test
	public void testSaveMolecule()
	{
		assertNull(mgmt.saveMolecule(null));
		
		Molecule molecule = new Molecule();
		try {mgmt.saveMolecule(molecule); fail(); } catch(Exception e) {}
		
		
		molecule.setName("test");
		molecule.setSourceCode("source");
		
		try {mgmt.saveMolecule(molecule); fail(); } catch(Exception e) {}
		
		molecule.setCanonicalSMILES("C");
		try {mgmt.saveMolecule(molecule); fail(); } catch(Exception e) {}
		
		molecule.setMolString("MOLE");
		
		assertEquals(mgmt.saveMolecule(molecule),molecule);
	}

	@Test
	public void testFindMolecules()
	{
		
		assertNull(mgmt.findMolecules(null));
		
		KHEMCriteria criteria = new KHEMCriteria();
		try{ mgmt.findMolecules(criteria);
		fail();
		}
		catch(Exception e) {}
	}

	private static MoleculeMgmt mgmt = new MoleculeMgmt();
}
