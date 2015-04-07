package khem.solutions.cheminformatics.joelib;


import khem.solutions.cheminformatics.data.RawMoleculeInfo;
import khem.solutions.cheminformatics.exception.KhemException;
import nyla.solutions.global.patterns.conversion.NameableConverter;

public class JOELibRawMoleculeCanonicalSMILESConverter implements NameableConverter<RawMoleculeInfo, String>
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
	public String convert(RawMoleculeInfo rawMoleculeInfo)
	{		
		try
		{
			return JOELib.toSMILES(rawMoleculeInfo.getMolString());
		}
		catch (Exception e)
		{
			throw new KhemException(e.getMessage(),e);
		}
	}// --------------------------------------------------------


	private String name ="canonicalSMILES";

}
