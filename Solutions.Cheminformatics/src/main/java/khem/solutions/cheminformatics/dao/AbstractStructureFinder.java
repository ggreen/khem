package khem.solutions.cheminformatics.dao;

import java.util.Collection;
import nyla.solutions.global.data.Nameable;
import nyla.solutions.global.exception.SystemException;

import nyla.solutions.global.patterns.servicefactory.ServiceFactory;
import khem.solutions.cheminformatics.StructureFinder;
import khem.solutions.cheminformatics.data.MaterialCriteria;
import khem.solutions.cheminformatics.data.Molecule;
import khem.solutions.cheminformatics.data.StructureKey;

/**
 * 
 * @author Gregory Green
 *
 */
public abstract class AbstractStructureFinder implements StructureFinder, Nameable
{


	/**
	 * @return the structureCriteria
	 */
	public MaterialCriteria getStructureCriteria()
	{
		return structureCriteria;
	}

	
	@Override
	public void setCriteria(MaterialCriteria structureCriteria)
	{
		this.structureCriteria = structureCriteria;
		
	}

	/**
	 * @see khem.solutions.cheminformatics.StructureFinder#getResults()
	 */
	/**
	 * 
	 * @see com.merck.mrl.asap.integration.services.structure.StructureFinder#getResults()
	 */
	@Override
	public Collection<StructureKey> getResults()
	{
		if(this.exception != null)
			throw this.exception;
		
		return results;
	}// --------------------------------------------------------
	
	/**
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Collection<Molecule> call() throws Exception
	{
		return this.searchMolecules(structureCriteria);

	}
	/**
	 * Gather the results
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		StructureDAO dao = null;
		
		try
		{
			dao = ServiceFactory.getInstance().create(name);
			
			this.results = dao.findStructKeysByMol(this.getStructureCriteria());
		}
		catch(RuntimeException e)
		{
			this.exception = e;
		}
		catch(Exception e)
		{
			this.exception =  new SystemException(e);
		}
		finally
		{
			if(dao != null)
						try{ dao.dispose(); } catch(Exception e){}
		}
	}// --------------------------------------------------------
	
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
	
	
	public abstract Collection<Molecule> searchMolecules(MaterialCriteria structureCriteria);
	private RuntimeException exception = null;
	private Collection<StructureKey> results;
	private String name;
	private MaterialCriteria structureCriteria;

}
