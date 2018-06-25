package khem.solutions.cheminformatics.dao.gemfire;


import java.util.Collection;

import khem.solutions.cheminformatics.data.MaterialCriteria;
import khem.solutions.cheminformatics.data.Molecule;


public interface FindByMolWithAnnotedFunction
{
	Collection<Molecule> findByMol(MaterialCriteria structureCriteria);
}
