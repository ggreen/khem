package khem.solutions.cheminformatics.dao;

import java.sql.SQLException;
import java.util.Collection;

import khem.solutions.cheminformatics.data.ChemicalAnnotation;
import khem.solutions.cheminformatics.data.Material;
import khem.solutions.cheminformatics.data.StructureKey;
import nyla.solutions.core.patterns.Connectable;
import nyla.solutions.core.patterns.Disposable;



/**
 * Chemistry Data Access Object
 * @author Gregory Green
 *
 */
public interface ChemistryDAO extends Connectable, Disposable
{
	/**
	 * 
	 * @param annotationCode the annotationCode
	 * @return the collection chemical annotations
	 * @throws SQLException when an error occurs
	 */
	public Collection<ChemicalAnnotation> selectChemicalAnnotationsByCode(String annotationCode)
	throws SQLException;
	
	/**
	 * 
	 * @param molString the molecule string
	 * @return the image bytes
	 * @throws SQLException
	 */
	public byte[] selectMolImageByMolString(String molString,int width,int height)
	throws SQLException;
	
	/**
	 * Get summary material information such as molecular properties and inventory
	 * @param molString the molecule string
	 * @return the material summary data
	 */
	public Material selectAnnotatedMaterialByMol(String molString)
	throws SQLException;
	
	/**
	 * Select by MOLKEY
	 * @param molKey the mol KEY
	 * @return
	 * @throws SQLException
	 */
	public Collection<StructureKey> selectStructKeysByMolKey(
			String molKey)
	throws SQLException;
	
	/**
	 * Find an annotation by name and code
	 * @param annotationName the annotation name
	 * @param code the annotation code
	 * @return the chemical annotation
	 * @throws SQLException
	 */
	public ChemicalAnnotation selectAnnotationByNameAndCode(String annotationName, String code)
			throws SQLException;
	
	/**
	 * Insert or update a chemical annotation
	 * @param molAnnotation the chemical annotation to save
	 * @throws SQLException
	 */
	public void saveAnnotation(ChemicalAnnotation molAnnotation)
	throws SQLException;
	
	/**
	 * Insert a given chemical annotation
	 * @param chemicalAnnotation the chemical annotation
	 * @return true if record inserted
	 * @throws SQLException
	 */
	public boolean insertAnnotation(ChemicalAnnotation chemicalAnnotation)
	throws SQLException;
	
	
	/**
	 * 
	 * @param chemicalAnnotation the chemical annotation
	 * @param structureKey the structure keys
	 * @return true if return inserted
	 */
	public boolean insertAnnotationKey(ChemicalAnnotation chemicalAnnotation,  StructureKey structureKey)
	throws SQLException;
	
	
	/**
	 * Save key for a molecule annotation
	 * @param molAnnotation molecule annotation
	 * @param structureKeys structure keys
	 * @throws SQLException
	 */
	public void saveAnnotationKeys(ChemicalAnnotation molAnnotation,Collection<StructureKey> structureKeys, boolean deletePrevious)
	throws SQLException;
}