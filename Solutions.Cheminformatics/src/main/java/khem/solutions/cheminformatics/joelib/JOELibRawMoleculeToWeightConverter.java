package khem.solutions.cheminformatics.joelib;


import joelib2.molecule.Molecule;
import khem.solutions.cheminformatics.data.RawMoleculeInfo;
import khem.solutions.cheminformatics.exception.KhemException;
import nyla.solutions.global.patterns.conversion.NameableConverter;

/**
 * USed JOELib.toWeight(molecule) to convert molstring to weight
 * @author Gregory Green
 *
 */
public class JOELibRawMoleculeToWeightConverter implements NameableConverter<RawMoleculeInfo, Double>
{
	
	@Override
	public String getName()
	{
		return name;
	}// --------------------------------------------------------

	@Override
	public void setName(String name)
	{
		this.name = name;
		
	}// --------------------------------------------------------
	/**
	 * Convert to raw molecule to SMILES
	 * @see nyla.solutions.global.patterns.conversion.Converter#convert(java.lang.Object)
	 */
	@Override
	public Double convert(RawMoleculeInfo rawMoleculeInfo)
	{		
		if(rawMoleculeInfo == null)
			return null;
		
		try
		{
			Molecule molecule = JOELib.toMolecule(rawMoleculeInfo.getMolString());
			
			if(molecule == null)
				return null;
			
			return JOELib.toWeight(molecule);
		}
		catch (Exception e)
		{
			throw new KhemException(e.getMessage(),e);
		}
	}// --------------------------------------------------------


	private String name ="weight";

}
