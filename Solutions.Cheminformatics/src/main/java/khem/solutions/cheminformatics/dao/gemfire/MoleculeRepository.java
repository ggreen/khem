package khem.solutions.cheminformatics.dao.gemfire;

import org.springframework.data.gemfire.repository.GemfireRepository;
import khem.solutions.cheminformatics.data.Molecule;


public interface MoleculeRepository extends GemfireRepository<Molecule, String>
{
	public Molecule findByMolKey(String molKey);
	public Molecule findByStructureKey(String structureKey);
	public Molecule findMoleculeByStructureKey(String structureKey);
}
