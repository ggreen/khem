package khem.solutions.cheminformatics.dao.gemfire;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import khem.solutions.cheminformatics.dao.AbstractStructureFinder;
import khem.solutions.cheminformatics.data.MaterialCriteria;
import khem.solutions.cheminformatics.data.Molecule;
import khem.solutions.cheminformatics.finders.StructureFinder;

@Component
public class MoleculeFinderByFunction extends AbstractStructureFinder implements StructureFinder
{
	
	@Resource
	private FindByMolWithAnnotedFunction findByMol;
	
	
	
	/**
	 * @return the findByMol
	 */
	public FindByMolWithAnnotedFunction getFindByMol()
	{
		return findByMol;
	}

	/**
	 * @param findByMol the findByMol to set
	 */
	public void setFindByMol(FindByMolWithAnnotedFunction findByMol)
	{
		this.findByMol = findByMol;
	}

	@Override
	public Collection<Molecule> searchMolecules(
			MaterialCriteria structureCriteria)
	{
		return findByMol.findByMol(structureCriteria);
	}
	
}
