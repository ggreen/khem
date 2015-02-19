package khem.solutions.cheminformatics.data;

import java.io.Serializable;

import nyla.solutions.global.data.Identifier;


public class StructureKey implements Identifier, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2582557055315760427L;
	/**
	 * Default constructor
	 */
	public StructureKey()
	{
	}// --------------------------------------------------------
	/**
	 * 
	 * @param id the ID
	 */
	public StructureKey(String id)
	{
		this.id = id;
	}// --------------------------------------------------------
	/**
	 * 
	 * @param id the structure key
	 * @param molKey the MOL KEY
	 * @param sourceCode the structure database
	 * @param isActive the active
	 */
	public StructureKey(String id, String molKey, String sourceCode,
			boolean isActive)
	{
		super();
		this.id = id;
		this.molKey = molKey;
		this.sourceCode = sourceCode;
		this.isActive = isActive;
	}// --------------------------------------------------------
	/**
	 * Get STRUCTKEY
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * STRUCTKEY
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
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
	 * @return the isActive
	 */
	public boolean isActive()
	{
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "StructureKey [id=" + id + ", molKey=" + molKey
				+ ", sourceCode=" + sourceCode + ", isActive=" + isActive + "]";
	}


	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + ((molKey == null) ? 0 : molKey.hashCode());
		result = prime * result
				+ ((sourceCode == null) ? 0 : sourceCode.hashCode());
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
		StructureKey other = (StructureKey) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (isActive != other.isActive)
			return false;
		if (molKey == null)
		{
			if (other.molKey != null)
				return false;
		}
		else if (!molKey.equals(other.molKey))
			return false;
		if (sourceCode == null)
		{
			if (other.sourceCode != null)
				return false;
		}
		else if (!sourceCode.equals(other.sourceCode))
			return false;
		return true;
	}


	private String id;
	private String molKey;
	private String sourceCode;
	private boolean isActive = true;
}
