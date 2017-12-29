package khem.solutions.cheminformatics;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.geode.cache.Region;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import gedi.solutions.geode.client.GeodeClient;
import khem.solutions.cheminformatics.data.KHEMCriteria;
import khem.solutions.cheminformatics.data.MaterialCriteria;
import khem.solutions.cheminformatics.data.Molecule;
import khem.solutions.cheminformatics.data.StructureKey;
import khem.solutions.cheminformatics.io.SDFEntry;
import khem.solutions.cheminformatics.io.SDFileReader;
import khem.solutions.cheminformatics.joelib.JOELib;
import nyla.solutions.core.patterns.iteration.PageCriteria;
import nyla.solutions.core.patterns.iteration.Paging;
import nyla.solutions.global.io.IO;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:/META-INF/client/khem-spring-client-cache.xml"})
//@Ignore
public class CommasCompoundMgmtTest
{
	
	private static String  two_bezenes = null;
	private static String one_benzene = null;
	private static CompoundService compondService = new CommasCompoundMgmt();

	
	
	//@Autowired
	//Cache cache;
	
	public CommasCompoundMgmtTest()
	throws Exception
	{
		if(two_bezenes == null)
		{
			two_bezenes = IO.readFile("src/test/resources/input/data/two-bezenes.mol");
			one_benzene = IO.readFile("src/test/resources//input/data/one-benzene.mol");
		}
	}
	
	//private static GUnit gunit = null;

	@BeforeClass
	public static void setUp() throws Exception
	{
		//gunit = new GUnit();
		//gunit.startCluster();
		
		//gunit.createRegion("molecules", RegionShortcut.PARTITION);
	}
	
	@AfterClass
	public static void shutdown()
	{
		//gunit.shutdown();
	}

//	@Test
//	public void testAInit()
//	{
//		CommasServiceFactory factory = CommasServiceFactory.getCommasServiceFactory();
//		
//		assertNotNull(factory);
//		
//		Command<Paging<Molecule>,KHEMCriteria> command = factory.createCommand("CompoundMgmt.findMolecules");
//		assertNotNull(command);
//		
//		command= null;
//		
//	}// --------------------------------------------------------

	
	@Test
	public void testFindMoleculeByKey2()
	{
		StructureKey structureKey = new StructureKey();
		String sourceCode = "eMolecules";
		String id = "001";
		structureKey.setId(id);
		structureKey.setSourceCode(sourceCode);
		
		Molecule molecule = new Molecule();
		
		molecule.setStructureKey(id);
		molecule.setFormula("H2 x O -> H20");
		
		
		Region<String, Molecule> molecules = GeodeClient.connect().getRegion("molecules");
				
		molecules.put(molecule.getId(),molecule);
		
		Molecule resultMolecule = compondService.findMoleculeByKey(structureKey);
		
		assertNotNull(resultMolecule);
		assertEquals(molecule.getFormula(), resultMolecule.getFormula());
		
		structureKey.setId("Q#$@#$@$");
		assertNull(compondService.findMoleculeByKey(structureKey));
	}
	
	@Test
	public void testFindMolecules()
	{
		String id = "IDKEHM01";
		Molecule molecule = new Molecule();
		
		assertTrue(two_bezenes != null && two_bezenes.length() > 0);
		
		molecule.setStructureKey(id);
		molecule.setMolString(two_bezenes);
		molecule.setFormula("H20");
		molecule.setName("2+Benzene");
		molecule.setSourceCode("eMolecules");
		molecule.setWeight(18.01528);
		Region<String,Molecule> region = GeodeClient.connect().getRegion("molecules");
		
		//moleculeFinderByFunction
		//MoleculeRepository moleculeRepository = new GemFireMolRepository();
		region.put(molecule.getStructureKey(),molecule);
			
		KHEMCriteria khemCriteria = new KHEMCriteria();
		
		MaterialCriteria materialCriteria  = new MaterialCriteria();
		
		materialCriteria.setMolString(two_bezenes);
		khemCriteria.setStructureCriteria(materialCriteria);
		
		PageCriteria pageCriteria = new PageCriteria();
		
		
		materialCriteria.setPageCriteria(pageCriteria);
		pageCriteria.setSavePagination(false);
		
		String[] sources = {"eMolecules"};
		
		//String[] sources = null;
		
		materialCriteria.setSources(sources);
		
		Paging<Molecule> paging = compondService.findMolecules(khemCriteria);
		
		assertNotNull(paging);

	}
	
	@Test
	public void testEMoleculeLoad()
	throws Exception
	{
		//String filePath ="runtime/input/data/eMolecules.sdf";
		String filePath = "src/test/resources/input/data/example_mols.sdf";
		
		File file = new File(filePath);
		assertTrue(file.exists());
		
		int limit = Integer.MAX_VALUE;
		
		
		
		joelib2.molecule.Molecule joeMolecule;
		
		Region<String,Molecule> region = GeodeClient.connect().getRegion("molecules");
		
		int i =0;
		for (SDFEntry sdfEntry : new SDFileReader(file))
		{
			if(sdfEntry == null)
				continue;
			
			if(i > limit)
				break;
			
			i++;
			
			Molecule molecule = new Molecule();
			
			
			molecule.setStructureKey(sdfEntry.retrieveParamValue("EMOL_VERSION_ID"));
			
			if(molecule.getStructureKey() == null || molecule.getStructureKey().length() ==0)
				continue;
			
			molecule.setMolString(sdfEntry.getMolString());
			
			joeMolecule = JOELib.toMolecule(sdfEntry.getMolString());
			
			molecule.setFormula(String.valueOf(joeMolecule.getAtoms()));
			
			String name = joeMolecule.getTitle();
			if(name ==null || name.length() == 0)
				name = molecule.getStructureKey();

			molecule.setName(name);
			molecule.setSourceCode("eMoleculeFunction");
			molecule.setWeight(JOELib.toWeight(joeMolecule));
			
			molecule.setCanonicalSMILES(JOELib.toSMILES(joeMolecule));
			
			region.put(molecule.getStructureKey(),molecule);
		}
		
		//TODO: test pagination
		KHEMCriteria khemCriteria = new KHEMCriteria();
		
		MaterialCriteria materialCriteria  = new MaterialCriteria();
		
		//String[] sources = {"eMoleculeFunction"};
		String[] sources = {"eMolecules"};
		
		materialCriteria.setSources(sources);
		materialCriteria.setMolString(one_benzene);
		khemCriteria.setStructureCriteria(materialCriteria);
		
		PageCriteria pageCriteria = new PageCriteria();
		pageCriteria.setId(null);
		pageCriteria.setSavePagination(false);
		pageCriteria.setSize(10);

		
		materialCriteria.setPageCriteria(pageCriteria);
		
		Paging<Molecule> paging = compondService.findMolecules(khemCriteria);
		
		assertNotNull(paging);

		
		assertTrue(paging.size() <= pageCriteria.getSize());
		

	}

}
