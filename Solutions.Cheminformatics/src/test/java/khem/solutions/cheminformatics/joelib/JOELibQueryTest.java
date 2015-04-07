package khem.solutions.cheminformatics.joelib;


import joelib2.molecule.Molecule;
import joelib2.process.ProcessFactory;
import joelib2.process.ProcessPipe;
import joelib2.process.filter.DescriptorFilter;
import joelib2.process.filter.FilterFactory;
import joelib2.process.types.FeatureSelectionWriter;
import khem.solutions.cheminformatics.joelib.JOELib;
import khem.solutions.cheminformatics.joelib.JOELibSearch;
import nyla.solutions.global.io.IO;

import org.junit.Assert;
//import org.junit.Ignore;
import org.junit.Test;

//@Ignore
public class JOELibQueryTest
{

	
	@Test
	public void testMatch()
	throws Exception
	{
		JOELibSearch q = new JOELibSearch();
		
		String query = IO.readFile("runtime/input/data/two-bezenes.mol");
		String mol = IO.readFile("runtime/input/data/four-bezenes.mol");
		
		String cyclopentane = IO.readFile("runtime/input/data/cyclopentane.mol");
		
		
		Assert.assertTrue(q.isSubSearch(query, mol));
		Assert.assertTrue(!q.isSubSearch(mol,query));
		Assert.assertTrue(!q.isSubSearch(query, cyclopentane));
		
		Molecule molecule = JOELib.toMolecule(mol);
		
		FeatureSelectionWriter featureselectionwriter = (FeatureSelectionWriter)ProcessFactory.instance().getProcess("FeatureSelectionWriter");
		DescriptorFilter descriptorfilter = (DescriptorFilter)FilterFactory.instance().getFilter("DescriptorFilter");
		//descriptorfilter.init(s3, false);
		
		ProcessPipe processPipe = (ProcessPipe)ProcessFactory.instance().getProcess("ProcessPipe");
		processPipe.addProcess(featureselectionwriter, descriptorfilter);
		
		processPipe.process(molecule, null);
		System.out.println("molecule.title:"+molecule.getTitle());
		
	}// --------------------------------------------------------
	

}
