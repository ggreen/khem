package khem.solutions.cheminformatics.dao;

import java.sql.SQLException;
import java.util.Collection;

import khem.solutions.cheminformatics.data.MaterialCriteria;
import khem.solutions.cheminformatics.data.Molecule;
import khem.solutions.cheminformatics.data.StructureKey;
import nyla.solutions.global.patterns.Disposable;
import nyla.solutions.global.patterns.iteration.Paging;


/**
 * The Structure data access object
 * @author Gregory Green
 *
 */
public interface StructureDAO extends Disposable
{
	/**
	 * SSS_OPERATION = "SSS"
	 */
	public static final String SSS_OPERATION = "SSS";
	
	/**v
	 * Select molecule for pagination
	 * @param structureCriteria
	 * @return
	 */
	Paging<Molecule> findMoleculesByMol(MaterialCriteria structureCriteria);
	

	/**
	 * Select aggregated and page molecule
	 * @param asapCriteria the criteria with indicated sources
	 * @return the page molecules
	 * @throws SQLException
	 */
	//Paging<Molecule> selectMoleculesByCriteria(ASAPCriteria asapCriteria)
	//throws SQLException;

	
	/**
	 * Select structure keys by mol name
	 * @param structureCriteria the structure criteria
	 * @return the collection of structure keys
	 */
	Paging<StructureKey> findStructKeysByMol(MaterialCriteria structureCriteria);
	
	
	/**
	 * 
	 * @param structureKey the structure key
	 * @return the Molecule details
	 * @throws SQLException
	 */
	Molecule findMoleculeByStructureKey(String structureKey);
	
	
	/**
	 * Select structure keys by CASNUM or MOLNAME
	 * @param structureCriteria the structure criteria
	 * @return the structure key
	 */
	Collection<StructureKey> findStructKeysByCasOrMolName(
			MaterialCriteria structureCriteria);
	
	/**
	 * Select the CAS Numbers by a structure key
	 * @param materialCriteria the material criteria
	 * @return the list of CAS numbers
	 */
	public String[] findCasNumsByStructureKey(StructureKey structureKey);
	
	
	/**
	 * Select the structure keys by molname
	 * @param materialCriteria the structure criteria
	 * @return the structure keys
	 */
	Collection<StructureKey> findStructKeysByMolName(MaterialCriteria materialCriteria);
	
	/**
	 * Select Structure key
	 * @param structureKeyId the molecular number identifier (L-number,MFCD#, MERCK#)
	 * @return the structure key
	 */
	StructureKey findStructureKeyById(String structureKeyId);
	
	/**
	 * 
	 * @param molString the MOL string to compare
	 * @return
	 */
	Collection<StructureKey> findFlexMatchStructureKeysByMol(String molString);
}
