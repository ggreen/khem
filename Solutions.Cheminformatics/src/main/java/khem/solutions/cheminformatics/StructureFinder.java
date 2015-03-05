package khem.solutions.cheminformatics;

import java.util.Collection;
import java.util.concurrent.Callable;

import khem.solutions.cheminformatics.data.MaterialCriteria;
import khem.solutions.cheminformatics.data.Molecule;
import khem.solutions.cheminformatics.data.StructureKey;


public interface StructureFinder extends Runnable, Callable<Collection<Molecule>>
{
	/**
	 * 
	 * @param structureCriteria the criteria to use
	 */
	void setCriteria(MaterialCriteria structureCriteria);
	
	/**
	 * 
	 * @return the execution results
	 */
	Collection<StructureKey> getResults();
}
