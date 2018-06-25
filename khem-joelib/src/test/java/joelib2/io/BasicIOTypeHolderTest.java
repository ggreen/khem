package joelib2.io;

import static org.junit.Assert.*;

import org.junit.Test;

public class BasicIOTypeHolderTest
{

	@Test
	public void testInstance()
	{
		BasicIOTypeHolder instance = BasicIOTypeHolder.instance();
		
		assertNotNull(instance);
		
		BasicIOType basicIOType = instance.getIOType("SDF");
		
		assertNotNull(basicIOType);
		
		
	}

}
