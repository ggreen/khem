package khem.solutions.cheminformatics.joelib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import joelib2.feature.types.MolecularWeight;
import joelib2.io.BasicIOTypeHolder;
import joelib2.io.BasicReader;
import joelib2.io.IOType;
import joelib2.io.MoleculeFileHelper;
import joelib2.io.MoleculeFileIO;
import joelib2.io.MoleculeIOException;
import joelib2.molecule.BasicConformerMolecule;
import joelib2.molecule.Molecule;

public class JOELib
{
	public static IOType SDF_TYPE = BasicIOTypeHolder.instance().getIOType("SDF");
	public static IOType SMILES_TYPE = BasicIOTypeHolder.instance().getIOType("SMILES");
	
	/**
	 * Convert from MDL to SMILES
	 * @param moleculeText
	 * @return the SMILE object
	 * @throws MoleculeIOException
	 * @throws IOException
	 */
	public static String toSMILES(String moleculeText)
	throws MoleculeIOException, IOException
	{
		return toSMILES(toMolecule(moleculeText));
		
	}// --------------------------------------------------------

	public static String toSMILES(Molecule molecule)
    throws MoleculeIOException, IOException
	{			
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		MoleculeFileIO moleculeFileIO = MoleculeFileHelper.getMolWriter(byteArrayOutputStream, SMILES_TYPE);
				
		if(!moleculeFileIO.write(molecule))
					throw new RuntimeException("Cannot convert to SMILES moleculeSmiles:"+molecule);
				
		String smiles = new String(byteArrayOutputStream.toByteArray());
				
		if(smiles.endsWith(" Undefined\n"))
		{
			smiles = smiles.substring(0, smiles.length() - 11); 
		}
				
		return smiles;
   }// --------------------------------------------------------
	public static double  toWeight(Molecule molecule)
	{
		return MolecularWeight.getMolecularWeight(molecule);
	}// --------------------------------------------------------
	/**
	 * Convert from MDL to to a molecule object
	 * @param moleculeText
	 * @return the molecule
	 * @throws IOException
	 * @throws MoleculeIOException
	 */
	public static Molecule toMolecule(String moleculeText)
	throws IOException, MoleculeIOException
	{
		if(moleculeText == null || moleculeText.length() == 0) {
			return null;
		}
		
		try
		{
			ByteArrayInputStream bytearrayinputstream= new ByteArrayInputStream(moleculeText.getBytes());
				
			BasicConformerMolecule basicConformerMolecule = new BasicConformerMolecule(SDF_TYPE,SDF_TYPE);
				
			BasicReader basicreader = new BasicReader(bytearrayinputstream, SDF_TYPE);
				
			if(!basicreader.readNext(basicConformerMolecule))
					throw new RuntimeException("Cannot read molecule:"+moleculeText);
				
			return basicConformerMolecule;
		}
		catch (IOException| MoleculeIOException| RuntimeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		catch(Throwable t)
		{
			t.printStackTrace();
			throw new RuntimeException(t);
		}


	}// --------------------------------------------------------

}
