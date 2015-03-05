package khem.solutions.cheminformatics;

import java.util.Collection;
import khem.solutions.cheminformatics.data.ChemicalAnnotation;
import khem.solutions.cheminformatics.data.Container;
import khem.solutions.cheminformatics.data.KHEMCriteria;
import khem.solutions.cheminformatics.data.MaterialCriteria;
import khem.solutions.cheminformatics.data.Molecule;
import khem.solutions.cheminformatics.data.StructureKey;
import nyla.solutions.global.patterns.iteration.PageCriteria;
import nyla.solutions.global.patterns.iteration.Paging;


public interface CompoundService
{
	
	/**
	 * 
	 * @param structureKey the structure key
	 * @return
	 */
	public Molecule findMoleculeByKey(StructureKey structureKey);

	
	
	/**
	 * 
	 * @param criteria the code of the annotation
	 * @return the collection of annotations
	 */
	public Collection<ChemicalAnnotation> listChemicalAnnotions(MaterialCriteria criteria);
	
	/**
	 * 
	 * @param materialCriteria
	 * @return
	 */
	public abstract Paging<Molecule> findMolecules(
			KHEMCriteria materialCriteria);
	
	/**
	 * 
	 * @param pageCriteria the page criteria
	 * @return the paging molecule
	 */
	public Paging<StructureKey> retrieveStructureKeysPage(PageCriteria pageCriteria);
	
	
	/**
	 * 
	 * @param materialCriteria the material criteria
	 * @return the containers
	 */
	public abstract Collection<Container> findContainers(
			KHEMCriteria containerCriteria);
	
	
	/**
	 * Find structure keys by molecule
	 * @param structureCriteria 
	 * @return
	 */
	public Paging<StructureKey> findStructureKeys(MaterialCriteria structureCriteria);

}
