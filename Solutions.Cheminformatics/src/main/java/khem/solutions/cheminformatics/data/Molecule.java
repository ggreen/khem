package khem.solutions.cheminformatics.data;

import java.io.Serializable;

import nyla.solutions.global.data.Identifier;
import nyla.solutions.global.data.Nameable;



/**
 * @author Gregory Green
 *
 */
public class Molecule implements Serializable, Comparable<Molecule>, Nameable, Identifier
{
	/**
	 * @return the weight
	 */
	public String getWeight()
	{
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String weight)
	{
		this.weight = weight;
	}

	/**
	 * @return the formula
	 */
	public String getFormula()
	{
		return formula;
	}

	/**
	 * @param formula the formula to set
	 */
	public void setFormula(String formula)
	{
		this.formula = formula;
	}// --------------------------------------------------------
	
	
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
	
	
	@Override
	public int compareTo(Molecule other)
	{
		if(other == null || other.name == null)
			return 1;
		
		
		int sourceCompare = 0;
		
		if(this.sourceCode != null)
		{
			sourceCompare = this.sourceCode.compareTo(other.sourceCode+"");
			
			if(sourceCompare == 0)
		    	return (other.name+"").compareTo(this.name+"");
			else
				return sourceCompare;
		}
		
		return (other.name+"").compareTo(this.name+"");
	}// --------------------------------------------------------

	


	/**
	 * @return the sourceCode
	 */
	public String getSourceCode()
	{
		return sourceCode;
	}

	/**
	 * @param sourceCode the sourceCode to set
	 */
	public void setSourceCode(String sourceCode)
	{
		this.sourceCode = sourceCode;
	}



	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((canonicalSMILES == null) ? 0 : canonicalSMILES.hashCode());
		result = prime * result + ((formula == null) ? 0 : formula.hashCode());
		result = prime * result + ((molKey == null) ? 0 : molKey.hashCode());
		result = prime * result
				+ ((molString == null) ? 0 : molString.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((sourceCode == null) ? 0 : sourceCode.hashCode());
		result = prime * result
				+ ((structureKey == null) ? 0 : structureKey.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
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
		Molecule other = (Molecule) obj;
		if (canonicalSMILES == null)
		{
			if (other.canonicalSMILES != null)
				return false;
		}
		else if (!canonicalSMILES.equals(other.canonicalSMILES))
			return false;
		if (formula == null)
		{
			if (other.formula != null)
				return false;
		}
		else if (!formula.equals(other.formula))
			return false;
		if (molKey == null)
		{
			if (other.molKey != null)
				return false;
		}
		else if (!molKey.equals(other.molKey))
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
		if (sourceCode == null)
		{
			if (other.sourceCode != null)
				return false;
		}
		else if (!sourceCode.equals(other.sourceCode))
			return false;
		if (structureKey == null)
		{
			if (other.structureKey != null)
				return false;
		}
		else if (!structureKey.equals(other.structureKey))
			return false;
		if (weight == null)
		{
			if (other.weight != null)
				return false;
		}
		else if (!weight.equals(other.weight))
			return false;
		return true;
	}

	

	/**
	 * @return the molKey
	 */
	public String getMolKey()
	{
		return molKey;
	}

	/**
	 * @param molKey the molKey to set
	 */
	public void setMolKey(String molKey)
	{
		this.molKey = molKey;
	}

	/**
	 * @return the structureKey
	 */
	public String getStructureKey()
	{
		return structureKey;
	}

	/**
	 * @param structureKey the structureKey to set
	 */
	public void setStructureKey(String structureKey)
	{
		this.structureKey = structureKey;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Molecule [name=" + name + ", molKey=" + molKey + ", weight="
				+ weight + ", formula=" + formula + ", molString=" + molString
				+ ", structureKey=" + structureKey + ", sourceCode="
				+ sourceCode + ", canonicalSMILES=" + canonicalSMILES + "]";
	}
	
	
	/**
	 * @return return this.getStructureKey()
	 * @see solutions.global.data.Identifier#getId()
	 */
	@Override
	public String getId()
	{
		return this.getStructureKey();
	}// --------------------------------------------------------
	
	/**
	 * this.setStructureKey(id);
	 * @see solutions.global.data.Identifier#setId(java.lang.String)
	 */
	@Override
	public void setId(String id)
	{
		this.setStructureKey(id);
	}// --------------------------------------------------------

	/**
	 * @return the canonicalSMILES
	 */
	public String getCanonicalSMILES()
	{
		return canonicalSMILES;
	}

	/**
	 * @param canonicalSMILES the canonicalSMILES to set
	 */
	public void setCanonicalSMILES(String canonicalSMILES)
	{
		this.canonicalSMILES = canonicalSMILES;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 5791503838849751260L;
	private String name;
	private String molKey;
	private String weight;
	private String formula;
	private String molString;
	private String structureKey;
	private String sourceCode;
	private String canonicalSMILES;

}
