package khem.solutions.cheminformatics.dao.gemfire;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.geode.cache.Region;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.gemfire.GemfireTemplate;

import khem.solutions.cheminformatics.data.MaterialCriteria;
import khem.solutions.cheminformatics.data.Molecule;
import nyla.solutions.core.io.IO;

public class StructureGemFireDAOTest
{

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testFindMoleculesByMol()
	throws Exception
	{
		String fourbezenes = IO.readFile("src/test/resources/input/data/four-bezenes.mol");
		
		Molecule expected = new Molecule();
		expected.setMolString(fourbezenes);
		Map<String, Molecule> map = new HashMap<String, Molecule>();
		map.put("001", expected);
		
		Region<String, Molecule> region = (Region<String, Molecule>)mock(Region.class);
		
		GemfireTemplate gemfireTemplate = mock(GemfireTemplate.class);
		when(gemfireTemplate.getRegion()).thenReturn((Region)region);
		when(region.getAll(Mockito.anyCollection())).thenReturn(map);
		when(gemfireTemplate.getAll(Mockito.anyCollection())).thenReturn((Map)map);
		when(region.keySetOnServer()).thenReturn(map.keySet());
		
		StructureGemFireDAO dao = new StructureGemFireDAO();
		dao.setMoleculeTemplate(gemfireTemplate);
		
		assertNull(dao.findMoleculesByMol(null));
		
		MaterialCriteria structureCriteria = new MaterialCriteria();
		
		String molString = IO.readFile("src/test/resources/input/data/one-benzene.mol");
		
		structureCriteria.setMolString(molString);
		
		Collection<Molecule> molecules = dao.findMoleculesByMol(structureCriteria);
		
		assertNotNull(molecules);
		assertTrue(!molecules.isEmpty());
		
		
	}

}
