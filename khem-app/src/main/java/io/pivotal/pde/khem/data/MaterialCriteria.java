package io.pivotal.pde.khem.data;

import java.io.Serializable;
import java.util.Arrays;

import nyla.solutions.core.patterns.iteration.PageCriteria;



/**
 * The Material criteria
 * @author Gregory Green
 *
 */
public class MaterialCriteria implements Serializable
{
	public enum MaterialCriteriaType
	{
		BySMILES,
		ByWEIGHT
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
	 * @return the sources
	 */
	public String[] getSources()
	{
		return this.sources;
	}// --------------------------------------------------------
	
	/**
	 * @param sources the sources to set
	 */
	public void setSources(String [] sources)
	{
		this.sources = sources;
	}
	
	
	/**
	 * @return the casNum
	 */
	public String getCasNum()
	{
		return casNum;
	}
	/**
	 * @param casNum the casNum to set
	 */
	public void setCasNum(String casNum)
	{
		this.casNum = casNum;
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
	 * @return the pageCriteria
	 */
	public PageCriteria getPageCriteria()
	{
		return pageCriteria;
	}
	/**
	 * @param pageCriteria the pageCriteria to set
	 */
	public void setPageCriteria(PageCriteria pageCriteria)
	{
		this.pageCriteria = pageCriteria;
	}
	/**
	 * @param structureKeys the structureKeys to set
	 */
	//public void setStructureKeys(StructureKey[] structureKeys)
	//{
	//	this.structureKeys = structureKeys;
	//}

	/**
	 * Increment page
	 */
	public void incrementPage()
	{
		if(this.pageCriteria == null)
			return;
		
		this.pageCriteria.incrementPage();
	}// --------------------------------------------------------
	/**
	 * 
	 * @return page criteria is being saved
	 */
	public boolean isSavePagination()
	{
		if(this.pageCriteria == null)
			return false;
		
		return this.pageCriteria.isSavePagination();
		
	}// --------------------------------------------------------
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

	/**
	 * 
	 * @return this.casNumbers != null && this.casNumbers.length > 0
	 */
	public boolean hasCasNumbers()
	{
		return this.casNumbers != null && this.casNumbers.length > 0;
	}// --------------------------------------------------------
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
	/**
	 * @return the casNumbers
	 */
	public String[] getCasNumbers()
	{
		return casNumbers;
	}
	/**
	 * @param casNumbers the casNumbers to set
	 */
	public void setCasNumbers(String[] casNumbers)
	{
		this.casNumbers = casNumbers;
	}
	
	/**
	 * @return the dataSource
	 */
	public String getDataSource()
	{
		return dataSource;
	}
	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(String dataSource)
	{
		this.dataSource = dataSource;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((casNum == null) ? 0 : casNum.hashCode());
		result = prime * result + Arrays.hashCode(casNumbers);
		result = prime * result + ((dataSource == null) ? 0 : dataSource.hashCode());
		result = prime * result + ((formula == null) ? 0 : formula.hashCode());
		result = prime * result + ((molString == null) ? 0 : molString.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((operation == null) ? 0 : operation.hashCode());
		result = prime * result + ((pageCriteria == null) ? 0 : pageCriteria.hashCode());
		result = prime * result + ((searchType == null) ? 0 : searchType.hashCode());
		result = prime * result + ((smiles == null) ? 0 : smiles.hashCode());
		result = prime * result + Arrays.hashCode(sources);
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
		if (casNum == null)
		{
			if (other.casNum != null)
				return false;
		}
		else if (!casNum.equals(other.casNum))
			return false;
		if (!Arrays.equals(casNumbers, other.casNumbers))
			return false;
		if (dataSource == null)
		{
			if (other.dataSource != null)
				return false;
		}
		else if (!dataSource.equals(other.dataSource))
			return false;
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
		if (operation == null)
		{
			if (other.operation != null)
				return false;
		}
		else if (!operation.equals(other.operation))
			return false;
		if (pageCriteria == null)
		{
			if (other.pageCriteria != null)
				return false;
		}
		else if (!pageCriteria.equals(other.pageCriteria))
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
		if (!Arrays.equals(sources, other.sources))
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
		builder.append("MaterialCriteria [pageCriteria=").append(pageCriteria).append(", molString=").append(molString)
		.append(", sources=").append(Arrays.toString(sources)).append(", casNum=").append(casNum).append(", name=")
		.append(name).append(", operation=").append(operation).append(", casNumbers=")
		.append(Arrays.toString(casNumbers)).append(", searchType=").append(searchType).append(", dataSource=")
		.append(dataSource).append(", smiles=").append(smiles).append(", weight=").append(weight).append(", formula=")
		.append(formula).append("]");
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


	private PageCriteria pageCriteria;
	private String molString;
	private String[] sources;
	private String casNum;
	private String name;
	private String operation;
	private String[] casNumbers;
	private MaterialCriteriaType searchType = MaterialCriteriaType.BySMILES;
	private String dataSource;
	private String smiles;
	private Double weight;
	private String formula;
	
}
