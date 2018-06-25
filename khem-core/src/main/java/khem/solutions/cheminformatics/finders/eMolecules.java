package khem.solutions.cheminformatics.finders;

import khem.solutions.cheminformatics.dao.StructureFinderByMol;
import khem.solutions.cheminformatics.dao.gemfire.StructureGemFireDAO;

public class eMolecules extends StructureFinderByMol implements StructureFinder
{

	public eMolecules()
	{
		this.setStructureDAO(new StructureGemFireDAO());
	}
	

}
