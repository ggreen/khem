package io.pivotal.pde.khem;

import static org.junit.Assert.*;

import org.apache.geode.cache.Region;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.Collection;
import io.pivotal.pde.khem.data.KHEMCriteria;
import io.pivotal.pde.khem.data.MaterialCriteria;
import io.pivotal.pde.khem.data.Molecule;
import nyla.solutions.core.io.IO;
import io.pivotal.pde.khem.data.MaterialCriteria.MaterialCriteriaType;

public class MoleculeMgmtTest
{	
	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void setUp()
	{
		Region<String,Molecule> region = mock(Region.class);
		QuerierService querierService = mock(QuerierService.class);
		mgmt = new MoleculeMgmt();
		mgmt.moleculesRegion = region;
		when(region.get("valid|valid")).thenReturn(molecule);
		mgmt.querierService = querierService;
	}//------------------------------------------------
	@Test
	public void testSaveMolecule()
	throws Exception
	{
		assertNull(mgmt.saveMolecule(null));
		
		Molecule molecule = new Molecule();
		try {mgmt.saveMolecule(molecule); fail(); } catch(Exception e) {}
		
		
		molecule.setName("test");
		molecule.setSourceCode("source");
		
		try {mgmt.saveMolecule(molecule); fail(); } catch(Exception e) {}
		
		molecule.setCanonicalSMILES("C");
		try {mgmt.saveMolecule(molecule); fail(); } catch(Exception e) {}
		
		String molfile = IO.readFile("src/test/resources/sdf/acenaphthylene.mol");
		molecule.setMolString(molfile);
		
		assertEquals(mgmt.saveMolecule(molecule),molecule);
	}//------------------------------------------------

	@Test
	public void testFindMolecules()
	{
		
		assertNull(mgmt.findMolecules(null));
		
		KHEMCriteria criteria = new KHEMCriteria();
		try{ mgmt.findMolecules(criteria);
		fail();
		}
		catch(Exception e) {}
	}//------------------------------------------------
	@Test
	public void testFindBySourceAndName() throws Exception
	{
		String source = "invalid";
		String name = "valid";
		
		KHEMCriteria criteria = new KHEMCriteria();
		criteria.setStructureCriteria(new MaterialCriteria());
		criteria.getStructureCriteria().setSearchType(MaterialCriteriaType.BySourceAndName);
		
		try{mgmt.findMolecules(criteria); fail();} catch(Exception e) {}
		criteria.getStructureCriteria().setSource(source);
		
		try{mgmt.findMolecules(criteria); fail();} catch(Exception e) {}
		criteria.getStructureCriteria().setName(name);
		
		Collection<Molecule> molecules = mgmt.findMolecules(criteria);
		assertNull(molecules);
		 source = "valid";
		 criteria.getStructureCriteria().setSource(source);
		 
		 molecules = mgmt.findMolecules(criteria);
		 assertNotNull(molecules);
		
		assertTrue(!molecules.isEmpty());
	}//------------------------------------------------
	@Test
	public void testFindByWeight() throws Exception
	{
		
		KHEMCriteria criteria = new KHEMCriteria();
		criteria.setStructureCriteria(new MaterialCriteria());
		criteria.getStructureCriteria().setSearchType(MaterialCriteriaType.ByWEIGHT);
		criteria.getStructureCriteria().setWeight(10.01);
		Collection<Molecule> molecules = mgmt.findMolecules(criteria);
		assertNotNull(molecules);
		
	}
	@Test
	public void testFindByFormula() throws Exception
	{
		
		KHEMCriteria criteria = new KHEMCriteria();
		criteria.setStructureCriteria(new MaterialCriteria());
		criteria.getStructureCriteria().setSearchType(MaterialCriteriaType.ByFORMULA);
		criteria.getStructureCriteria().setFormula("C");
		Collection<Molecule> molecules = mgmt.findMolecules(criteria);
		assertNotNull(molecules);
		
	}
	
	@Test
	public void testDelete()
	throws Exception
	{
		Molecule mole = null;
		mgmt.deleteMolecule(null);
		
		mole = new Molecule();
		mgmt.deleteMolecule(mole);
		
		Molecule out = mgmt.findBySourceCodeAndName(mole.getSourceCode(),mole.getName());
		
		assertNull(out);
		mole.setName("valid");
		mole.setSourceCode("valid");
		mole.setCanonicalSMILES("C");
		mole.setMolString(IO.readFile("src/test/resources/sdf/acenaphthylene.mol"));
		mgmt.saveMolecule(mole);
		
		out = mgmt.findBySourceCodeAndName(mole.getSourceCode(),mole.getName());
		assertNotNull(out);
		
		mgmt.deleteMolecule(mole);
		
		out = mgmt.findBySourceCodeAndName(mole.getSourceCode(),mole.getName());
		
	}
	private static Molecule molecule = new Molecule();
	private static MoleculeMgmt mgmt = new MoleculeMgmt();
}
