package khem.solutions.cheminformatics.joelib;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import joelib2.feature.FeatureHelper;
import joelib2.feature.FeatureResult;
import joelib2.feature.types.BurdenEigenvalues;
import joelib2.feature.types.IntrinsicState;
import joelib2.feature.types.LogP;
import joelib2.feature.types.MoleculeHashcode;
import joelib2.feature.types.atomlabel.AtomInAcceptor;
import joelib2.feature.types.atomlabel.AtomInAromaticSystem;
import joelib2.feature.types.atomlabel.AtomInTerminalCarbon;
import joelib2.feature.types.atomlabel.AtomKekuleBondOrderSum;
import joelib2.feature.types.atomlabel.AtomMass;
import joelib2.feature.types.bondlabel.BondInRing;
import joelib2.feature.types.count.BasicGroups;
import joelib2.io.IOType;
import joelib2.molecule.Molecule;
import nyla.solutions.core.io.IO;

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
		String moleculeText = IO.readFile("src/test/resources/input/data/four-bezenes.mol");
		Molecule molecule = JOELib.toMolecule(moleculeText);
		
		
		Assert.assertNotNull(molecule);
		
		System.out.println("sssr:"+molecule.getSSSR());
		System.out.println("atomsSize:"+molecule.getAtomsSize());
		System.out.println("atoms:"+molecule.getAtoms());
		System.out.println("bonds:"+molecule.getBonds());
		
		System.out.println("SMILES:"+JOELib.toSMILES(moleculeText));
		
		//System.out.println("molecule:"+molecule);
		
		FeatureResult result = FeatureHelper.instance().featureFrom(molecule,AtomInAcceptor.class.getName());
		
		System.out.println("result:"+result);
	}
	
	@Test
	public void testToMoleculeWeight()
	throws Exception
	{
		String moleculeText = IO.readFile("src/test/resources/input/data/four-bezenes.mol");
		Molecule molecule = JOELib.toMolecule(moleculeText);
		
		double weight = JOELib.toWeight(molecule);
		
		assertTrue(weight > 0);
		
		System.out.println("weight:"+weight);
	}

}
