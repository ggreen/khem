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
	Paging<Molecule> selectMoleculesByMol(MaterialCriteria structureCriteria)
	throws SQLException;
	

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
	Paging<StructureKey> selectStructKeysByMol(MaterialCriteria structureCriteria)
	throws SQLException;
	
	
	/**
	 * 
	 * @param structureKey the structure key
	 * @return the Molecule details
	 * @throws SQLException
	 */
	Molecule selectMoleculeByStructureKey(String structureKey)
	throws SQLException;
	
	
	/**
	 * Select structure keys by CASNUM or MOLNAME
	 * @param structureCriteria the structure criteria
	 * @return the structure key
	 */
	Collection<StructureKey> selectStructKeysByCasOrMolName(
			MaterialCriteria structureCriteria)
	throws SQLException;
	
	/**
	 * Select the CAS Numbers by a structure key
	 * @param materialCriteria the material criteria
	 * @return the list of CAS numbers
	 */
	public String[] selectCasNumsByStructureKey(StructureKey structureKey)
	throws SQLException;
	
	
	/**
	 * Select the structure keys by molname
	 * @param materialCriteria the structure criteria
	 * @return the structure keys
	 */
	Collection<StructureKey> selectStructKeysByMolName(MaterialCriteria materialCriteria)
	throws SQLException;
	
	/**
	 * Select Structure key
	 * @param structureKeyId the molecular number identifier (L-number,MFCD#, MERCK#)
	 * @return the structure key
	 */
	StructureKey selectStructKeyById(String structureKeyId)
	throws SQLException;
	
	
	/**
	 * 
	 * @param molString the MOL string to compare
	 * @return
	 */
	Collection<StructureKey> flexMatchStructureKeysByMol(String molString)
	throws SQLException;
}
