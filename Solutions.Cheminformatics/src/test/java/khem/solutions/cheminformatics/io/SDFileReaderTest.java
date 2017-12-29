package khem.solutions.cheminformatics.io;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SDFileReaderTest
{

	public SDFileReaderTest()
	{
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testSDFileReader()
	throws Exception
	{
		String filePath ="runtime/input/data/eMolecules.sdf";
		File file = new File(filePath);
		Assert.assertTrue(file.exists());
		
		try
		{
			
			int maxCount = 10;
			int i = 0;
			
			for (SDFEntry sdfEntry : new SDFileReader(file))
			{
				if(i >= maxCount)
				{
					break;
				}
				
				System.out.println(sdfEntry.getParameters());

				i++;
			}
		}
		finally
		{
		}
	}
}
