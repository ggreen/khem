package io.pivotal.pde.khem;

import java.util.Collection;

import javax.validation.Valid;

import io.pivotal.pde.khem.data.KHEMCriteria;
import io.pivotal.pde.khem.data.Molecule;

public interface MoleculeService
{

	Molecule saveMolecule(@Valid Molecule molecule);

	Collection<Molecule> findMolecules(@Valid KHEMCriteria criteria);
	
	/**
	 * Delete the molecule
	 * @param molecule the molecule to delete
	 */
	void deleteMolecule(Molecule molecule);

}
