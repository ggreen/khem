package khem.solutions.cheminformatics.data;

import java.io.Serializable;

import nyla.solutions.core.patterns.iteration.PageCriteria;


/**
 * Container search criteria
 * @author Gregory Green
 *
 */
public class KHEMCriteria implements Serializable
{
	
	/**
	 * Default constructor
	 */
	public KHEMCriteria()
	{
		
	}
	/**
	 * 
	 * @param structureCriteria the structure criteria
	 */
	public KHEMCriteria(MaterialCriteria structureCriteria)
	{
		super();
		this.structureCriteria = structureCriteria;
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
	 * @return the structureCriteria
	 */
	public MaterialCriteria getStructureCriteria()
	{
		return structureCriteria;
	}

	/**
	 * @param structureCriteria the structureCriteria to set
	 */
	public void setStructureCriteria(MaterialCriteria structureCriteria)
	{
		this.structureCriteria = structureCriteria;
	}
	
	private PageCriteria pageCriteria;
	
	
	private MaterialCriteria structureCriteria;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6140200741139462851L;
	

}
