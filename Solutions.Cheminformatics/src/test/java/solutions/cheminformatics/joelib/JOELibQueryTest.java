package solutions.cheminformatics.joelib;

import java.io.IOException;

import joelib2.io.BasicIOTypeHolder;
import khem.solutions.cheminformatics.joelib.JOELibSearch;
import nyla.solutions.global.io.IO;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
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
		
	}// --------------------------------------------------------
	
	@Test
	public void test()
	throws IOException
	{
		JOELibSearch q = new JOELibSearch();
		
		//String smart = IO.readFile("runtime/input/matchTarget.mol");
		
		String smart = "c1ccccc1";
		String  molURL  = "runtime/input/data/query.mol";
		
		
		Assert.assertTrue(q.test(molURL, smart, BasicIOTypeHolder.instance().filenameToType("MOL"),
                BasicIOTypeHolder.instance().filenameToType("MOL")));
	}

}
