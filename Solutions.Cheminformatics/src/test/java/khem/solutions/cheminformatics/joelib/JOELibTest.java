package khem.solutions.cheminformatics.joelib;

import joelib2.molecule.Molecule;
import khem.solutions.cheminformatics.joelib.JOELib;
import nyla.solutions.global.io.IO;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class JOELibTest
{

	public JOELibTest()
	{
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testToMolecule()
	throws Exception
	{
		String moleculeText = IO.readFile("runtime/input/four-bezenes.mol");
		Molecule molecule = JOELib.toMolecule(moleculeText);
		
		
		Assert.assertNotNull(molecule);
		
		System.out.println("sssr:"+molecule.getSSSR());
		System.out.println("atomsSize:"+molecule.getAtomsSize());
		
		System.out.println("SMILES:"+JOELib.toSMILES(moleculeText));
	}

}
