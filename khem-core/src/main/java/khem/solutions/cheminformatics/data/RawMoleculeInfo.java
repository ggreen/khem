package khem.solutions.cheminformatics.data;


public interface RawMoleculeInfo
{

	/**
	 * @return the molFile
	 */
	public abstract String getMolString();

	public String retrieveParamValue(String name);
	
}