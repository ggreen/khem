package khem.solutions.cheminformatics.conversions;

import khem.solutions.cheminformatics.data.RawMoleculeInfo;
import nyla.solutions.global.patterns.conversion.NameableConverter;

/**
 * Retrieve a param value from raw molecule to use in conversion
 * @author Gregory Green
 *
 */
public class RetrieveParamConverter implements NameableConverter<RawMoleculeInfo, String>
{
	/**
	 * 
	 * @see nyla.solutions.global.patterns.conversion.Converter#convert(java.lang.Object)
	 */
	@Override
	public String convert(RawMoleculeInfo rawMoleculeInfo)
	{

		if(rawMoleculeInfo  == null)
			return null;
		
		return rawMoleculeInfo.retrieveParamValue(parameterName);
	}// --------------------------------------------------------

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}// --------------------------------------------------------




	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}




	/**
	 * @return the parameterName
	 */
	public String getParameterName()
	{
		return parameterName;
	}

	/**
	 * @param parameterName the parameterName to set
	 */
	public void setParameterName(String parameterName)
	{
		this.parameterName = parameterName;
	}




	private String name;
	private String parameterName;
}
