package solutions.cheminformatics.joelib;

import java.io.IOException;

import joelib2.io.BasicIOTypeHolder;
import khem.solutions.cheminformatics.joelib.JOELibSearch;
import nyla.solutions.global.io.IO;

import org.junit.Assert;
import org.junit.Test;

public class JOELibQueryTest
{

	
	@Test
	public void testMatch()
	throws Exception
	{
		JOELibSearch q = new JOELibSearch();
		
		String query = IO.readFile("runtime/input/two-bezenes.mol");
		String mol = IO.readFile("runtime/input/four-bezenes.mol");
		
		String cyclopentane = IO.readFile("runtime/input/cyclopentane.mol");
		
		
		Assert.assertTrue(q.isSubSearch(query, mol));
		
		Assert.assertTrue(!q.isSubSearch(mol,query));
		
		Assert.assertTrue(!q.isSubSearch(query, cyclopentane));
		
	}// --------------------------------------------------------
	
	@Test
	public void test()
	throws IOException
	{
		JOELibSearch q = new JOELibSearch();
		
		//String smart = IO.readFile("runtime/input/matchTarget.mol");
		
		String smart = "c1ccccc1";
		String  molURL  = "runtime/input/query.mol";
		
		
		Assert.assertTrue(q.test(molURL, smart, BasicIOTypeHolder.instance().filenameToType("MOL"),
                BasicIOTypeHolder.instance().filenameToType("MOL")));
	}

}
