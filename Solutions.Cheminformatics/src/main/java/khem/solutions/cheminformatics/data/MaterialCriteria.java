package khem.solutions.cheminformatics.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import nyla.solutions.global.patterns.iteration.PageCriteria;



/**
 * The Material criteria
 * @author Gregory Green
 *
 */
public class MaterialCriteria implements Serializable
{
	public enum MaterialCriteriaType
	{
		DEFAULT,
		ByCasAndLocTypeAndInvSource,
		ByLocTypeAndInvSource
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
	 * @return the structureKeys
	 */
	public StructureKey[] getStructureKeys()
	{
		return structureKeys;
	}// --------------------------------------------------------
	/**
	 * @param structureKeys the structureKeys to set
	 */
	public void setStructureKeys(Collection<StructureKey> structureKeys)
	{
		if(structureKeys  == null || structureKeys.isEmpty())
			this.structureKeys  = null;
		else
		{
			this.structureKeys = null; //garbage collect
			
			this.structureKeys = new StructureKey[structureKeys.size()];
			
			ArrayList<StructureKey> al = new ArrayList<StructureKey>(structureKeys);
			al.toArray(this.structureKeys);
		}
		
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
	public void setStructureKeys(StructureKey[] structureKeys)
	{
		this.structureKeys = structureKeys;
	}

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
	 * @return the inventoryCriteria
	 */
	public InventoryCriteria getInventoryCriteria()
	{
		return inventoryCriteria;
	}
	/**
	 * @param inventoryCriteria the inventoryCriteria to set
	 */
	public void setInventoryCriteria(InventoryCriteria inventoryCriteria)
	{
		this.inventoryCriteria = inventoryCriteria;
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
	
	
	/**
	 * 
	 * @return the chemical annotation
	 */
	public ChemicalAnnotation[] getChemicalAnnotations()
	{
		return chemicalAnnotations;
	}// --------------------------------------------------------
	
	/**
	 * 
	 * @param chemicalAnnotations the chemical annotations
	 */
	public void setChemicalAnnotations(ChemicalAnnotation[] chemicalAnnotations)
	{
		this.chemicalAnnotations = chemicalAnnotations;
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((casNum == null) ? 0 : casNum.hashCode());
		result = prime * result + Arrays.hashCode(casNumbers);
		result = prime * result + Arrays.hashCode(chemicalAnnotations);
		result = prime * result
				+ ((dataSource == null) ? 0 : dataSource.hashCode());
		result = prime
				* result
				+ ((inventoryCriteria == null) ? 0 : inventoryCriteria
						.hashCode());
		result = prime * result
				+ ((molString == null) ? 0 : molString.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((operation == null) ? 0 : operation.hashCode());
		result = prime * result
				+ ((pageCriteria == null) ? 0 : pageCriteria.hashCode());
		result = prime * result
				+ ((searchType == null) ? 0 : searchType.hashCode());
		result = prime * result + Arrays.hashCode(sources);
		result = prime * result + Arrays.hashCode(structureKeys);
		return result;
	}// --------------------------------------------------------
	
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
		if (!Arrays.equals(chemicalAnnotations, other.chemicalAnnotations))
			return false;
		if (dataSource == null)
		{
			if (other.dataSource != null)
				return false;
		}
		else if (!dataSource.equals(other.dataSource))
			return false;
		if (inventoryCriteria == null)
		{
			if (other.inventoryCriteria != null)
				return false;
		}
		else if (!inventoryCriteria.equals(other.inventoryCriteria))
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
		if (!Arrays.equals(sources, other.sources))
			return false;
		if (!Arrays.equals(structureKeys, other.structureKeys))
			return false;
		return true;
	}


	@Override
	public String toString()
	{
		return "MaterialCriteria [pageCriteria=" + pageCriteria
				+ ", molString=" + molString + ", sources="
				+ Arrays.toString(sources) + ", structureKeys="
				+ Arrays.toString(structureKeys) + ", chemicalAnnotations="
				+ Arrays.toString(chemicalAnnotations) + ", casNum=" + casNum
				+ ", name=" + name + ", operation=" + operation
				+ ", inventoryCriteria=" + inventoryCriteria + ", casNumbers="
				+ Arrays.toString(casNumbers) + ", searchType=" + searchType
				+ ", dataSource=" + dataSource + "]";
	}





	private PageCriteria pageCriteria;
	private String molString;
	private String[] sources;
	private StructureKey[] structureKeys;
	private ChemicalAnnotation[] chemicalAnnotations;
	private String casNum;
	private String name;
	private String operation;
	private InventoryCriteria inventoryCriteria;
	private String[] casNumbers;
	private MaterialCriteriaType searchType = MaterialCriteriaType.DEFAULT;
	private String dataSource;
	
}
