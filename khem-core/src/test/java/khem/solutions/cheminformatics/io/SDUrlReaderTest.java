package khem.solutions.cheminformatics.io;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.Test;

public class SDUrlReaderTest
{

	@Test
	public void testSDUrlReader()
	throws Exception
	{
		String urlPath = "file:///Projects/solutions/Cheminformatics/dev/khem/khem-core/src/test/resources/input/data/test.sdf";
		
		URL url = new URL(urlPath);
		
		try(SDUrlReader reader = new SDUrlReader(url))
		{

			reader.open();
			assertTrue(reader.hasNext());
			for (SDFEntry sdfEntry : reader)
			{
				System.out.println(sdfEntry);
			}
		}
	}//------------------------------------------------

}
