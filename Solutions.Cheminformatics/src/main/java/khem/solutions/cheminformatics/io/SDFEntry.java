package khem.solutions.cheminformatics.io;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import khem.solutions.cheminformatics.data.RawMoleculeInfo;
import nyla.solutions.global.exception.RequiredException;




/**
 * SD File entry that contains parameters and a MOL structure
 * @author Gregory Green
 *
 */
public class SDFEntry implements Serializable, RawMoleculeInfo
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8410075951479523765L;
	/**
	 * @see khem.solutions.cheminformatics.data.RawMoleculeInfo#getMolString()
	 */
	@Override
	public String getMolString()
	{
		return molString.toString();
	}// -------------------------------------------------------- 
	
	/**
	 * @param molString the molFile to set
	 */
	public void setMolString(String molString)
	{
		this.molString= new StringBuilder(molString);
	}// --------------------------------------------------------
	
	/**
	 * 
	 * @param line the lien to append
	 */
	public void appendMol(String line)
	{
		if(line == null)
		{
			this.molString.append('\n');
			return;
		}
		
		this.molString.append(line);
		
		if(!line.endsWith("\n"))
		{
			this.molString.append('\n');
		}
		
	}// --------------------------------------------------------
	/**
	 * 
	 * @param name the parameter name to add
	 */
	public void addParamName(String name)
	{
		this.parameters.put(name, null);
	}// --------------------------------------------------------
	/**
	 * Add a value to a parameter by name
	 * @param name the parameter name
	 * @param value the value
	 */
	public void addParamValue(String name, String value)
	{
		if(name == null)
			throw new RequiredException("name");
		
		Collection<String> values = this.parameters.get(name);
		
		if(values == null)
			values = newValues();
		
		values.add(value);
		
		this.parameters.put(name.toUpperCase(), values);
		
	}// --------------------------------------------------------
	private Collection<String> newValues()
	{
		return new ArrayList<String>();
	}// --------------------------------------------------------
	/**
	 * @return the parameters
	 */
	public Map<String, Collection<String>> getParameters()
	{
		return parameters;
	}// --------------------------------------------------------
	/**
	 * 
	 * @param name the parameter name
	 * @return the first value of the parameter
	 */
	@Override
	public String retrieveParamValue(String name)
	{
		
		Collection<String> values = retrieveParamValues(name);
		
		if(values == null)
			return null;
		
		return values.iterator().next();
		
	}// --------------------------------------------------------
	/**
	 * @see khem.solutions.cheminformatics.data.RawMoleculeInfo#retrieveParamMapValue()
	 */
	public Map<String,String> retrieveParamMapValue()
	{
		if(this.parameters.isEmpty())
			return null;
		
		HashMap<String,String> map = new HashMap<String, String>();
		
		Set<String> keys = this.parameters.keySet();
		for (String name : keys)
		{
			map.put(name,this.retrieveParamValue(name));
		}
		
		return map;
	}// --------------------------------------------------------
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Collection<String> retrieveParamValues(String name)
	{
		if(name == null)
			throw new RequiredException("name");
		
		return this.parameters.get(name.toUpperCase());
	}// --------------------------------------------------------
	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(Map<String, Collection<String>> parameters)
	{
		this.parameters = parameters;
	}
	private StringBuilder molString = new StringBuilder();
	private Map<String, Collection<String>> parameters = new HashMap<String,Collection<String>>();
}
