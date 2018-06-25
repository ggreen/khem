package khem.solutions.cheminformatics.dao;

import java.util.Collection;

import khem.solutions.cheminformatics.data.Container;
import khem.solutions.cheminformatics.data.KHEMCriteria;
import nyla.solutions.core.patterns.Disposable;


/**
 * Material data access object
 * @author Gregory Green
 *
 */
public interface InventoryDAO extends Disposable
{

	/**
	 * 
	 * @param containerCriteria the container/structure details
	 * @return the collection of containers
	 */
	Collection<Container> findByCriteria(KHEMCriteria containerCriteria);
	
	/**
	 * Delete all containers associate with a given source
	 * @param sourceCode the source
	 * @return the number of records deleted
	 */
	int removeBySourceCode(String sourceCode);
	
	/**
	 * Delete containers associated with a bar code in source
	 * @param barCode the BAR CODE
	 * @return number of records deleted
	 * 
	 */
	int removeByBarCode(String barCode);
	
	/**
	 * Select container by is bar code
	 * @param barCode the BAR CODE
	 * @return the single associated container
	 */
	Container findByBarCode(String barCode);
	
	/**
	 * Delete an inventory record by the source and ID
	 * @param inventorySource (ex: COSMIC)
	 * @param inventoryId INVENTORY identifier
	 * @return number of records deleted
	 */
	int removeBySoureCodeAndId(String inventorySource,int inventoryId);	
	
	/**
	 * 
	 * @param sourceCode the source code
	 * @param inventoryId the inventory ID
	 * @return the container that matches
	 */
	public Container findBySourceCodeAndInventoryId(String sourceCode, int inventoryId);
}
