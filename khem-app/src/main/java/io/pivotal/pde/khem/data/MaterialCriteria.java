package io.pivotal.pde.khem.data;

import java.io.Serializable;



/**
 * The Material criteria
 * @author Gregory Green
 *
 */
public class MaterialCriteria implements Serializable
{
	public enum MaterialCriteriaType
	{
		BySourceAndName,
		BySMILES,
		ByWEIGHT, 
		ByFORMULA
	};
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1578384819771178208L;
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

	/**
	 * @return the searchType
	 */
	public MaterialCriteriaType getSearchType()
	{
		return searchType;
	}
	/**
	 * @param searchType the searchType to set
	 */
	public void setSearchType(MaterialCriteriaType searchType)
	{
		this.searchType = searchType;
	}

	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formula == null) ? 0 : formula.hashCode());
		result = prime * result + ((molString == null) ? 0 : molString.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((searchType == null) ? 0 : searchType.hashCode());
		result = prime * result + ((smiles == null) ? 0 : smiles.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}
	
	/* (non-Javadoc)
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
		MaterialCriteria other = (MaterialCriteria) obj;
		if (formula == null)
		{
			if (other.formula != null)
				return false;
		}
		else if (!formula.equals(other.formula))
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
		if (searchType != other.searchType)
			return false;
		if (smiles == null)
		{
			if (other.smiles != null)
				return false;
		}
		else if (!smiles.equals(other.smiles))
			return false;
		if (source == null)
		{
			if (other.source != null)
				return false;
		}
		else if (!source.equals(other.source))
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


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("MaterialCriteria [name=").append(name).append(", source=").append(source)
		.append(", searchType=").append(searchType).append(", molString=").append(molString).append(", smiles=")
		.append(smiles).append(", weight=").append(weight).append(", formula=").append(formula).append("]");
		return builder.toString();
	}

	
	/**
	 * @return the smiles
	 */
	public String getSmiles()
	{
		return smiles;
	}
	/**
	 * @return the weight
	 */
	public Double getWeight()
	{
		return weight;
	}
	/**
	 * @return the formula
	 */
	public String getFormula()
	{
		return formula;
	}
	/**
	 * @param smiles the smiles to set
	 */
	public void setSmiles(String smiles)
	{
		this.smiles = smiles;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Double weight)
	{
		this.weight = weight;
	}
	/**
	 * @param formula the formula to set
	 */
	public void setFormula(String formula)
	{
		this.formula = formula;
	}

	
	/**
	 * @return the source
	 */
	public String getSource()
	{
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source)
	{
		this.source = source;
	}

	private String name;
	private String source;
	private MaterialCriteriaType searchType = MaterialCriteriaType.BySMILES;
	private String molString;
	private String smiles;
	private Double weight;
	private String formula;
	
}
