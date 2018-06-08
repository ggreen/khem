package io.pivotal.pde.khem;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.Collections;

import org.junit.Test;
import org.mockito.Mockito;

import gedi.solutions.geode.client.GeodeClient;
import io.pivotal.pde.khem.KhemRestService;
import io.pivotal.pde.khem.MoleculeService;
import io.pivotal.pde.khem.data.KHEMCriteria;
import io.pivotal.pde.khem.data.MaterialCriteria;
import io.pivotal.pde.khem.data.Molecule;
import nyla.solutions.core.patterns.iteration.PageCriteria;



public class KhemRestServiceTest
{

	@Test
	public void testSaveAndFindMolecules()
	throws IOException
	{
		GeodeClient.connect();
		
		KhemRestService service = new KhemRestService();
		
		MoleculeService moleService = Mockito.mock(MoleculeService.class);
		service.moleculeService = moleService;
		
		Mockito.when(moleService.findMolecules(null)).thenReturn(null);
		
		Molecule molecule = null;
		
		molecule  = new Molecule();
		molecule.setCanonicalSMILES("C");
		
		service.saveMolecule(molecule);
		KHEMCriteria criteria = new KHEMCriteria();
		
		assertNull(service.findMolecules(null));
		
		Mockito.when(moleService.findMolecules(criteria)).thenReturn(Collections.singleton(molecule));
		
		service.saveMolecule(molecule);
		
		criteria.setStructureCriteria(new MaterialCriteria());
		criteria.getStructureCriteria().setSmiles(molecule.getCanonicalSMILES());
		criteria.setPageCriteria(new PageCriteria());
		
		assertNotNull(service.findMolecules(criteria));
		
	}

}
