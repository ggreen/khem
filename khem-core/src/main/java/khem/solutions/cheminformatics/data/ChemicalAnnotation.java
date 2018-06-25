package khem.solutions.cheminformatics.data;

import java.io.Serializable;

public class ChemicalAnnotation implements Serializable, Comparable<ChemicalAnnotation>
{
	
	
	public ChemicalAnnotation()
	{
	}// --------------------------------------------------------
	/**
	 *  
	 * @param code the context code
	 * @param name the property name
	 */
	public ChemicalAnnotation( String code, String name)
	{
		this.code = code;
		this.name = name;
	}// --------------------------------------------------------

	@Override
	public int compareTo(ChemicalAnnotation o)
	{
		
		int ret = 0;
		
		if(this.code != null)
		{
			ret = this.code.compareTo(o.code);
			
			if(ret != 0)
				return ret;
		}
		
		if(this.name != null)
		{
			ret = this.name.compareTo(o.name);
			
			if(ret != 0)
				return ret;
		}
		
		if(this.operation != null)
		{
			ret = this.operation.compareTo(o.operation);
			
			if(ret != 0)
				return ret;
		}
		
		//code and name are equal
		return 0;  //equals
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "ChemicalAnnotation [code=" + code + ", name=" + name
				+ ", molString=" + molString + ", operation=" + operation + "]";
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((molString == null) ? 0 : molString.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((operation == null) ? 0 : operation.hashCode());
		return result;
	}
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChemicalAnnotation other = (ChemicalAnnotation) obj;
		if (code == null)
		{
			if (other.code != null)
				return false;
		}
		else if (!code.equals(other.code))
			return false;
		if (molString == null)
		{
			if (other.molString != null)
				return false;
		}
		else if (!molString.equals(other.molString))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (operation == null)
		{
			if (other.operation != null)
				return false;
		}
		else if (!operation.equals(other.operation))
			return false;
		return true;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 2543396959024340147L;
	
	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code)
	{
		this.code = code;
	}
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	
	/**
	 * @return the molString
	 */
	public String getMolString()
	{
		return molString;
	}
	/**
	 * @param molString the molString to set
	 */
	public void setMolString(String molString)
	{
		this.molString = molString;
	}
	
	/**
	 * @return the operation
	 */
	public String getOperation()
	{
		return operation;
	}
	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	private String code;
	private String name;
	private String molString;
	private String operation;

}
