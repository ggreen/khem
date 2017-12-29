package khem.solutions.cheminformatics.dao;

import java.util.Collection;

import khem.solutions.cheminformatics.data.MaterialCriteria;
import khem.solutions.cheminformatics.data.Molecule;
import khem.solutions.cheminformatics.data.StructureKey;
import khem.solutions.cheminformatics.finders.StructureFinder;
import nyla.solutions.core.data.Nameable;
import nyla.solutions.core.exception.SystemException;
import nyla.solutions.core.patterns.servicefactory.ServiceFactory;

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
		
	}//------------------------------------------------

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
