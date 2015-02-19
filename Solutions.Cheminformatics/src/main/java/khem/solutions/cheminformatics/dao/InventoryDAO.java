package khem.solutions.cheminformatics.dao;

import java.util.Collection;

import khem.solutions.cheminformatics.data.Container;
import khem.solutions.cheminformatics.data.KHEMCriteria;
import nyla.solutions.global.patterns.Disposable;


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
	Collection<Container> selectContainers(KHEMCriteria containerCriteria)
	;
	
	/**
	 * Delete all containers associate with a given source
	 * @param sourceCode the source
	 * @return the number of records deleted
	 */
	int deleteContainersBySource(String sourceCode)
	;
	
	/**
	 * Delete containers associated with a bar code in source
	 * @param barcode the BAR CODE
	 * @param sourceCode the containers
	 * @return number of records deleted
	 * @
	 */
	int deleteContainersByBarCode(String barcode)
	;
	
	/**
	 * Select container by is bar code
	 * @param barCode the BAR CODE
	 * @return the single associated container
	 * @
	 */
	Container selectContainerByBarCode(String barCode)
	;
	
	/**
	 * Delete an inventory record by the source and ID
	 * @param inventorySource (ex: COSMIC)
	 * @param inventoryId INVENTORY identifier
	 * @return number of records deleted
	 */
	int deleteContainerBySoureCodeAndId(String inventorySource,int inventoryId)
	;	
	
	/**
	 * 
	 * @param sourceCode the source code
	 * @param inventoryId the inventory ID
	 * @return the container that matches
	 * @
	 */
	public Container selectContainer(String sourceCode, int inventoryId)
	;
}
