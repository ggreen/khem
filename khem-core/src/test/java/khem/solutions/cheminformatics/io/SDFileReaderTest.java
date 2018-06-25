package khem.solutions.cheminformatics.io;

import static org.junit.Assert.assertTrue;

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
		String filePath ="src/test/resources/input/data/test.sdf";
		File file = new File(filePath);
		Assert.assertTrue(file.exists());
		
		try(SDFileReader reader = new SDFileReader(file))
		{

			assertTrue(reader.hasNext());
			
			int maxCount = 10;
			int i = 0;
			
			
			for (SDFEntry sdfEntry : reader)
			{
				if(sdfEntry == null)
					continue;
				
				if(i >= maxCount)
				{
					break;
				}
				
				System.out.println(sdfEntry.getParameters());
				System.out.println(sdfEntry.getMolString());

				i++;
			}
			
			assertTrue(i > 0);
		}
		finally
		{
		}
	}
}
