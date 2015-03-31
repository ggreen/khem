package khem.solutions.cheminformatics;



import java.io.File;

import khem.solutions.cheminformatics.dao.gemfire.MoleculeRepository;
import khem.solutions.cheminformatics.data.KHEMCriteria;
import khem.solutions.cheminformatics.data.MaterialCriteria;
import khem.solutions.cheminformatics.data.Molecule;
import khem.solutions.cheminformatics.data.StructureKey;
import khem.solutions.cheminformatics.io.SDFEntry;
import khem.solutions.cheminformatics.io.SDFileReader;
import khem.solutions.cheminformatics.joelib.JOELib;
import nyla.solutions.global.io.IO;
import nyla.solutions.global.patterns.command.Command;
import nyla.solutions.global.patterns.command.commas.CommasServiceFactory;
import nyla.solutions.global.patterns.iteration.PageCriteria;
import nyla.solutions.global.patterns.iteration.Paging;
import nyla.solutions.global.patterns.servicefactory.ServiceFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.gemstone.gemfire.cache.Region;



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
			two_bezenes = IO.readFile("runtime/input/data/two-bezenes.mol");
			one_benzene = IO.readFile("runtime/input/data/one-benzene.mol");
		}
	}

	@Before
	public void setUp() throws Exception
	{
	}
	
	

	@Test
	public void testAInit()
	{
		CommasServiceFactory factory = CommasServiceFactory.getCommasServiceFactory();
		
		Assert.assertNotNull(factory);
		
		Command<Paging<Molecule>,KHEMCriteria> command = factory.createCommand("CompoundMgmt.findMolecules");
		Assert.assertNotNull(command);
		
		command= null;
		
	}// --------------------------------------------------------

	
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
		
		
		Region<String, Molecule> molecules = ServiceFactory.getInstance().create("molecules");
				
		molecules.put(molecule.getId(),molecule);
		
		Molecule resultMolecule = compondService.findMoleculeByKey(structureKey);
		
		Assert.assertNotNull(resultMolecule);
		Assert.assertEquals(molecule.getFormula(), resultMolecule.getFormula());
		
		structureKey.setId("Q#$@#$@$");
		Assert.assertNull(compondService.findMoleculeByKey(structureKey));
	}
	
	@Test
	public void testFindMolecules()
	{
		String id = "IDKEHM01";
		Molecule molecule = new Molecule();
		
		
		molecule.setStructureKey(id);
		molecule.setMolString(two_bezenes);
		molecule.setFormula("H20");
		molecule.setName("2+Benzene");
		molecule.setSourceCode("eMolecules");
		molecule.setWeight("18.01528 g/mol");
		
		
		MoleculeRepository moleculeRepository = ServiceFactory.getInstance().create("moleculeRepository");
		moleculeRepository.save(molecule);
			
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
		
		Assert.assertNotNull(paging);

	}
	
	@Test
	public void testEMoleculeLoad()
	throws Exception
	{
		String filePath ="runtime/input/data/eMolecules.sdf";
		File file = new File(filePath);
		Assert.assertTrue(file.exists());
		
		int limit = Integer.MAX_VALUE;
		
		MoleculeRepository moleculeRepository = ServiceFactory.getInstance().create("moleculeRepository");
		
		joelib2.molecule.Molecule joeMolecule;
		
		int i =0;
		for (SDFEntry sdfEntry : new SDFileReader(file))
		{
			if(i > limit)
				break;
			
			i++;
			
			Molecule molecule = new Molecule();
			
			
			molecule.setStructureKey(sdfEntry.retrieveParamValue("EMOL_VERSION_ID"));
			molecule.setMolString(sdfEntry.getMolString());
			
			joeMolecule = JOELib.toMolecule(sdfEntry.getMolString());
			
			molecule.setFormula(String.valueOf(joeMolecule.getAtoms()));
			
			String name = joeMolecule.getTitle();
			if(name ==null || name.length() == 0)
				name = molecule.getStructureKey();

			molecule.setName(name);
			molecule.setSourceCode("eMoleculeFunction");
			molecule.setWeight(String.valueOf(JOELib.toWeight(joeMolecule)));
			
			molecule.setCanonicalSMILES(JOELib.toSMILES(joeMolecule));
			
			moleculeRepository.save(molecule);
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
		pageCriteria.setId("JUNIT");
		pageCriteria.setSavePagination(true);
		pageCriteria.setSize(10);

		
		materialCriteria.setPageCriteria(pageCriteria);
		
		Paging<Molecule> paging = compondService.findMolecules(khemCriteria);
		
		Assert.assertNotNull(paging);

		
		Assert.assertTrue(paging.size() <= pageCriteria.getSize());
		

	}

}
