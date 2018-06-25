package khem.solutions.cheminformatics.dao;

import java.util.Collection;

import org.springframework.stereotype.Component;

import khem.solutions.cheminformatics.data.MaterialCriteria;
import khem.solutions.cheminformatics.data.Molecule;
import khem.solutions.cheminformatics.finders.StructureFinder;

/**
 * @author Gregory Green
 *
 */
@Component(value="eMoleculesStructure")
public class StructureFinderByMol extends AbstractStructureFinder implements StructureFinder
{	
	
	/**
	 * @return the structureDAO
	 */
	public StructureDAO getStructureDAO()
	{
		return structureDAO;
	}

	/**
	 * @param structureDAO the structureDAO to set
	 */
	public void setStructureDAO(StructureDAO structureDAO)
	{
		this.structureDAO = structureDAO;
	}// --------------------------------------------------------

	@Override
	public Collection<Molecule> searchMolecules(
			MaterialCriteria structureCriteria)
	{
		return structureDAO.findMoleculesByMol(this.getStructureCriteria());
	}

	private StructureDAO structureDAO;
}
