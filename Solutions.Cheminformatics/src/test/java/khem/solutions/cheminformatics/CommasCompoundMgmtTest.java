package khem.solutions.cheminformatics;



import khem.solutions.cheminformatics.dao.gemfire.MoleculeRepository;
import khem.solutions.cheminformatics.data.KHEMCriteria;
import khem.solutions.cheminformatics.data.MaterialCriteria;
import khem.solutions.cheminformatics.data.Molecule;
import khem.solutions.cheminformatics.data.StructureKey;
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
	private static CompoundService compondService = new CommasCompoundMgmt();

	
	
	//@Autowired
	//Cache cache;
	
	public CommasCompoundMgmtTest()
	throws Exception
	{
		if(two_bezenes == null)
		{
			two_bezenes = IO.readFile("runtime/input/data/two-bezenes.mol");
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
		String id = "testFindMolecules";
		Molecule molecule = new Molecule();
		
		
		molecule.setStructureKey(id);
		molecule.setMolString(two_bezenes);
		
		
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

}
