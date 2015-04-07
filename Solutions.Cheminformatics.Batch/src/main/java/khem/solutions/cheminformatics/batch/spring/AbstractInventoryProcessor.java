package khem.solutions.cheminformatics.batch.spring;

import java.sql.SQLException;

import javax.sql.DataSource;

import khem.solutions.cheminformatics.dao.InventoryDAO;
import khem.solutions.cheminformatics.dao.StructureDAO;
import nyla.solutions.dao.spring.batch.AbstractArrayableItemProcessor;
import nyla.solutions.global.exception.ConfigException;
import nyla.solutions.global.exception.FatalException;
import nyla.solutions.global.exception.RequiredException;
import nyla.solutions.global.patterns.Disposable;
import nyla.solutions.global.patterns.servicefactory.ServiceFactory;




/**
 * 
 * @author Gregory Green
 *
 * 
 * @param <I> the input type
 * @param <O> the output type
 */
public abstract class AbstractInventoryProcessor<I,O> extends AbstractArrayableItemProcessor<I,O> implements Disposable
{

	public void init()
	{
		if(this.inventorySource == null || this.inventorySource.length() == 0)
			throw new ConfigException(this.getClass().getName()+".inventorySource required");
		
		if(this.compoundIdParamName == null && this.mfcdParamName == null)
			throw new ConfigException(this.getClass().getName()+".compoundIdParamName or mfcdParamName required");
		
		if(this.originalAmountsParamName == null)
			throw new ConfigException(this.getClass().getName()+".originalAmountsParamName required");
	
		this.rowNum = 0;
			
		if(this.containerLabelPos < 1)
				throw new RequiredException("this.containerLabelPos < 1");
			
		ServiceFactory factory = ServiceFactory.getInstance();
			
		this.acdDAO = factory.create("acdDAO");
			
		this.mcidbDAO = factory.create("mcidbDAO");
		   
		 this.merckACDDAO = factory.create("merckACDDAO");
		   
		this.deleteSkipFile();
		   
		//delete previous run
		   
		try
		{
			   this.inventoryDAO = factory.create("inventoryDAO");
			   
			   if(deleteContainersOnInit)
				deleteContainers();
		}
	    catch(SQLException e)
	    {
	    	   throw new FatalException(e);
	    }
	}// --------------------------------------------------------

	protected void deleteContainers() throws SQLException
	{
		this.inventoryDAO.removeByBarCode(this.inventorySource);
	}// --------------------------------------------------------
	
	/**
	 * Close DAO connections
	 * @see solutions.global.patterns.Disposable#dispose()
	 */
	@Override
	public void dispose()
	{
		if(this.acdDAO != null)
		{
			try
			{ 
				if(this.acdDAO != null)
					this.acdDAO.dispose(); 
				this.acdDAO = null; 
			} 
			catch(Exception e){}
		}
		
		
		try
		{
			if(this.mcidbDAO != null )
				this.mcidbDAO.dispose();
				
			this.mcidbDAO =  null;
			
		}
		catch(Exception e)
		{}
		   
		try
		{
			if(this.merckACDDAO != null )
				this.merckACDDAO.dispose();
				
			this.merckACDDAO =  null;
			
		}
		catch(Exception e)
		{}
		
		
		if(this.inventoryDAO != null )
		{
				try{ 
					this.inventoryDAO.dispose(); 
					this.inventoryDAO = null;
				} 
			    catch(Exception e){}
		}
	}// --------------------------------------------------------

	/**
	 * 
	 * @return this.rowNum++
	 */
	protected int incrementRowNum()
	{
		return this.rowNum++;
	}// --------------------------------------------------------
	
	/**
	 * @return the acdSourceCode
	 */
	public String getAcdSourceCode()
	{
		return acdSourceCode;
	}
	/**
	 * @param acdSourceCode the acdSourceCode to set
	 */
	public void setAcdSourceCode(String acdSourceCode)
	{
		this.acdSourceCode = acdSourceCode;
	}
	/**
	 * @return the vendorname
	 */
	public String getDefaultVendorname()
	{
		return defaultVendorname;
	}
	/**
	 * @param vendorname the vendorname to set
	 */
	public void setDefaultVendorname(String vendorname)
	{
		this.defaultVendorname = vendorname;
	}
	/**
	 * @return the dataRowSize
	 */
	public int getDataRowSize()
	{
		return dataRowSize;
	}
	/**
	 * @param dataRowSize the dataRowSize to set
	 */
	public void setDataRowSize(int dataRowSize)
	{
		this.dataRowSize = dataRowSize;
	}
	/**
	 * @return the batchNumberPos
	 */
	public int getBatchNumberPos()
	{
		return batchNumberPos;
	}
	/**
	 * @param batchNumberPos the batchNumberPos to set
	 */
	public void setBatchNumberPos(int batchNumberPos)
	{
		this.batchNumberPos = batchNumberPos;
	}
	/**
	 * @return the inventorySourcePos
	 */
	public int getInventorySourcePos()
	{
		return inventorySourcePos;
	}
	/**
	 * @param inventorySourcePos the inventorySourcePos to set
	 */
	public void setInventorySourcePos(int inventorySourcePos)
	{
		this.inventorySourcePos = inventorySourcePos;
	}
	
	/**
	 * @return the sitePos
	 */
	public int getSitePos()
	{
		return sitePos;
	}
	/**
	 * @param sitePos the sitePos to set
	 */
	public void setSitePos(int sitePos)
	{
		this.sitePos = sitePos;
	}
	/**
	 * @return the site
	 */
	public String getSite()
	{
		return site;
	}
	/**
	 * @param site the site to set
	 */
	public void setSite(String site)
	{
		this.site = site;
	}
	/**
	 * @return the casNumberPos
	 */
	public int getCasNumberPos()
	{
		return casNumberPos;
	}
	/**
	 * @param casNumberPos the casNumberPos to set
	 */
	public void setCasNumberPos(int casNumberPos)
	{
		this.casNumberPos = casNumberPos;
	}
	/**
	 * @return the inventoryIDPos
	 */
	public int getInventoryIDPos()
	{
		return inventoryIDPos;
	}
	/**
	 * @param inventoryIDPos the inventoryIDPos to set
	 */
	public void setInventoryIDPos(int inventoryIDPos)
	{
		this.inventoryIDPos = inventoryIDPos;
	}
	/**
	 * @return the containerLabelPos
	 */
	public int getContainerLabelPos()
	{
		return containerLabelPos;
	}
	/**
	 * @param containerLabelPos the containerLabelPos to set
	 */
	public void setContainerLabelPos(int containerLabelPos)
	{
		this.containerLabelPos = containerLabelPos;
	}
	/**
	 * @return the molKeyPos
	 */
	public int getMolKeyPos()
	{
		return molKeyPos;
	}
	/**
	 * @param molKeyPos the molKeyPos to set
	 */
	public void setMolKeyPos(int molKeyPos)
	{
		this.molKeyPos = molKeyPos;
	}
	/**
	 * @return the batchNumber
	 */
	public String getBatchNumber()
	{
		return batchNumber;
	}
	/**
	 * @param batchNumber the batchNumber to set
	 */
	public void setBatchNumber(String batchNumber)
	{
		this.batchNumber = batchNumber;
	}
	/**
	 * @return the vendorNamePos
	 */
	public int getVendorNamePos()
	{
		return vendorNamePos;
	}
	/**
	 * @param vendorNamePos the vendorNamePos to set
	 */
	public void setVendorNamePos(int vendorNamePos)
	{
		this.vendorNamePos = vendorNamePos;
	}
	/**
	 * @return the structureKeyPos
	 */
	public int getStructureKeyPos()
	{
		return structureKeyPos;
	}
	/**
	 * @param structureKeyPos the structureKeyPos to set
	 */
	public void setStructureKeyPos(int structureKeyPos)
	{
		this.structureKeyPos = structureKeyPos;
	}
	/**
	 * @return the structureSourceCodePos
	 */
	public int getStructureSourceCodePos()
	{
		return structureSourceCodePos;
	}
	/**
	 * @param structureSourceCodePos the structureSourceCodePos to set
	 */
	public void setStructureSourceCodePos(int structureSourceCodePos)
	{
		this.structureSourceCodePos = structureSourceCodePos;
	}
	/**
	 * @return the originalAmountsPos
	 */
	public int getOriginalAmountsPos()
	{
		return originalAmountsPos;
	}
	/**
	 * @param originalAmountsPos the originalAmountsPos to set
	 */
	public void setOriginalAmountsPos(int originalAmountsPos)
	{
		this.originalAmountsPos = originalAmountsPos;
	}
	/**
	 * @return the originalAmountUnitsPos
	 */
	public int getOriginalAmountUnitsPos()
	{
		return originalAmountUnitsPos;
	}
	/**
	 * @param originalAmountUnitsPos the originalAmountUnitsPos to set
	 */
	public void setOriginalAmountUnitsPos(int originalAmountUnitsPos)
	{
		this.originalAmountUnitsPos = originalAmountUnitsPos;
	}
	/**
	 * @return the mcidbSourceCode
	 */
	public String getMcidbSourceCode()
	{
		return mcidbSourceCode;
	}
	/**
	 * @param mcidbSoureCode the mcidbSoureCode to set
	 */
	public void setMcidbSourceCode(String mcidbSoureCode)
	{
		this.mcidbSourceCode = mcidbSoureCode;
	}
	/**
	 * @return the compoundIdParamName
	 */
	public String getCompoundIdParamName()
	{
		return compoundIdParamName;
	}
	/**
	 * @param compoundIdParamName the compoundIdParamName to set
	 */
	public void setCompoundIdParamName(String compoundIdParamName)
	{
		this.compoundIdParamName = compoundIdParamName;
	}
	/**
	 * @return the mfcdParamName
	 */
	public String getMfcdParamName()
	{
		return mfcdParamName;
	}
	/**
	 * @param mfcdParamName the mfcdParamName to set
	 */
	public void setMfcdParamName(String mfcdParamName)
	{
		this.mfcdParamName = mfcdParamName;
	}
	/**
	 * @return the originalAmountsParamName
	 */
	public String getOriginalAmountsParamName()
	{
		return originalAmountsParamName;
	}
	/**
	 * @param originalAmountsParamName the originalAmountsParamName to set
	 */
	public void setOriginalAmountsParamName(String originalAmountsParamName)
	{
		this.originalAmountsParamName = originalAmountsParamName;
	}
	/**
	 * @return the locationType
	 */
	public String getLocationType()
	{
		return locationType;
	}
	/**
	 * @param locationType the locationType to set
	 */
	public void setLocationType(String locationType)
	{
		this.locationType = locationType;
	}
	/**
	 * @return the locationTypePos
	 */
	public int getLocationTypePos()
	{
		return locationTypePos;
	}
	/**
	 * @param locationTypePos the locationTypePos to set
	 */
	public void setLocationTypePos(int locationTypePos)
	{
		this.locationTypePos = locationTypePos;
	}
	/**
	 * @return the barcodePos
	 */
	public int getBarcodePos()
	{
		return barcodePos;
	}
	/**
	 * @param barcodePos the barcodePos to set
	 */
	public void setBarcodePos(int barcodePos)
	{
		this.barcodePos = barcodePos;
	}
	/**
	 * @return the inventorySource
	 */
	public String getInventorySource()
	{
		return inventorySource;
	}
	/**
	 * @param inventorySource the inventorySource to set
	 */
	public void setInventorySource(String inventorySource)
	{
		this.inventorySource = inventorySource;
	}


	/**
	 * @return the defaultAmountUnits
	 */
	public String getDefaultAmountUnits()
	{
		return defaultAmountUnits;
	}
	/**
	 * @param defaultAmountUnits the defaultAmountUnits to set
	 */
	public void setDefaultAmountUnits(String defaultAmountUnits)
	{
		this.defaultAmountUnits = defaultAmountUnits;
	}
	

	/**
	 * @return the buildingPos
	 */
	public int getBuildingPos()
	{
		return buildingPos;
	}
	/**
	 * @param buildingPos the buildingPos to set
	 */
	public void setBuildingPos(int buildingPos)
	{
		this.buildingPos = buildingPos;
	}
	/**
	 * @return the floorPos
	 */
	public int getFloorPos()
	{
		return floorPos;
	}
	
	
	/**
	 * @return the containerLabelParam
	 */
	public String getContainerLabelParam()
	{
		return containerLabelParam;
	}
	/**
	 * @param containerLabelParam the containerLabelParam to set
	 */
	public void setContainerLabelParam(String containerLabelParam)
	{
		this.containerLabelParam = containerLabelParam;
	}
	/**
	 * @param floorPos the floorPos to set
	 */
	public void setFloorPos(int floorPos)
	{
		this.floorPos = floorPos;
	}
	/**
	 * @return the roomPos
	 */
	public int getRoomPos()
	{
		return roomPos;
	}
	/**
	 * @param roomPos the roomPos to set
	 */
	public void setRoomPos(int roomPos)
	{
		this.roomPos = roomPos;
	}
	/**
	 * @return the subLocationPos
	 */
	public int getSubLocationPos()
	{
		return subLocationPos;
	}
	/**
	 * @param subLocationPos the subLocationPos to set
	 */
	public void setSubLocationPos(int subLocationPos)
	{
		this.subLocationPos = subLocationPos;
	}
	/**
	 * @return the subLocation
	 */
	public String getSubLocation()
	{
		return subLocation;
	}
	/**
	 * @param subLocation the subLocation to set
	 */
	public void setSubLocation(String subLocation)
	{
		this.subLocation = subLocation;
	}
	/**
	 * @return the room
	 */
	public String getRoom()
	{
		return room;
	}
	/**
	 * @param room the room to set
	 */
	public void setRoom(String room)
	{
		this.room = room;
	}
	/**
	 * @return the floor
	 */
	public String getFloor()
	{
		return floor;
	}
	/**
	 * @param floor the floor to set
	 */
	public void setFloor(String floor)
	{
		this.floor = floor;
	}
	/**
	 * @return the building
	 */
	public String getBuilding()
	{
		return building;
	}
	/**
	 * @param building the building to set
	 */
	public void setBuilding(String building)
	{
		this.building = building;
	}

	/**
	 * @return the statusCodePos
	 */
	public int getStatusCodePos()
	{
		return statusCodePos;
	}
	/**
	 * @param statusCodePos the statusCodePos to set
	 */
	public void setStatusCodePos(int statusCodePos)
	{
		this.statusCodePos = statusCodePos;
	}
	/**
	 * @return the statusCode
	 */
	public String getDefaultStatusCode()
	{
		return defaultStatusCode;
	}
	
	/**
	 * @param defaultStatusCode the statusCode to set
	 */
	public void setDefaultStatusCode(String defaultStatusCode)
	{
		this.defaultStatusCode = defaultStatusCode;
	}
	
	/**
	 * @return the inventoryDataSource
	 */
	public DataSource getInventoryDataSource()
	{
		return inventoryDataSource;
	}

	/**
	 * @param inventoryDataSource the inventoryDataSource to set
	 */
	public void setInventoryDataSource(DataSource inventoryDataSource)
	{
		this.inventoryDataSource = inventoryDataSource;
	}

	/**
	 * @return the casParamName
	 */
	public String getCasParamName()
	{
		return casParamName;
	}
	/**
	 * @param casParamName the casParamName to set
	 */
	public void setCasParamName(String casParamName)
	{
		this.casParamName = casParamName;
	}


	/**
	 * @return the defaultOriginalAmounts
	 */
	public String getDefaultOriginalAmounts()
	{
		return defaultOriginalAmounts;
	}
	/**
	 * @param defaultOriginalAmounts the defaultOriginalAmounts to set
	 */
	public void setDefaultOriginalAmounts(String defaultOriginalAmounts)
	{
		this.defaultOriginalAmounts = defaultOriginalAmounts;
	}

	/**
	 * @return the rowNum
	 */
	public int getRowNum()
	{
		return rowNum;
	}
	
	
	/**
	 * @return the deleteContainersOnInit
	 */
	public boolean isDeleteContainersOnInit()
	{
		return deleteContainersOnInit;
	}
	
	

	/**
	 * @return the merckAcdSourceCode
	 */
	public String getMerckAcdSourceCode()
	{
		return merckAcdSourceCode;
	}

	/**
	 * @param merckAcdSourceCode the merckAcdSourceCode to set
	 */
	public void setMerckAcdSourceCode(String merckAcdSourceCode)
	{
		this.merckAcdSourceCode = merckAcdSourceCode;
	}

	/**
	 * @param deleteContainersOnInit the deleteContainersOnInit to set
	 */
	public void setDeleteContainersOnInit(boolean deleteContainersOnInit)
	{
		this.deleteContainersOnInit = deleteContainersOnInit;
	}

	
    /**
	 * @return the vendorParamName
	 */
	public String getVendorParamName()
	{
		return vendorParamName;
	}

	/**
	 * @param vendorParamName the vendorParamName to set
	 */
	public void setVendorParamName(String vendorParamName)
	{
		this.vendorParamName = vendorParamName;
	}

	/**
	 * @return the amountUnitsParamName
	 */
	public String getOriginalAmountUnitsParamName()
	{
		return originalAmountUnitsParamName;
	}

	/**
	 * @param originalAmountUnitsParamName the amountUnitsParamName to set
	 */
	public void setOriginalAmountUnitsParamName(String originalAmountUnitsParamName)
	{
		this.originalAmountUnitsParamName = originalAmountUnitsParamName;
	}

	/**
	 * @return the statusParamName
	 */
	public String getDisposedStatusParamName()
	{
		return disposedStatusParamName;
	}

	/**
	 * @param statusParamName the statusParamName to set
	 */
	public void setDisposedStatusParamName(String disposedStatusParamName)
	{
		this.disposedStatusParamName = disposedStatusParamName;
	}
	
	/**
	 * @return the barcodeParamName
	 */
	public String getBarcodeParamName()
	{
		return barcodeParamName;
	}

	/**
	 * @param barcodeParamName the barcodeParamName to set
	 */
	public void setBarcodeParamName(String barcodeParamName)
	{
		this.barcodeParamName = barcodeParamName;
	}// --------------------------------------------------------

	private boolean deleteContainersOnInit = true;
    
	private DataSource inventoryDataSource;
	private int dataRowSize;
	private int rowNum = 1;
	private int batchNumberPos;
	private int inventorySourcePos;
	private int casNumberPos;
	//private int subLocationPos;
	private int inventoryIDPos;
	private int sitePos;
	private int locationTypePos;
	private int containerLabelPos;
	private int molKeyPos;
	private String batchNumber;
	private int vendorNamePos;
	private int structureKeyPos;
	private int structureSourceCodePos;
	private int originalAmountsPos;
	private int originalAmountUnitsPos;
	private int barcodePos;
	private int buildingPos;
	private int floorPos;
	private int roomPos;
	private int subLocationPos;
	private int statusCodePos;
	
	private String defaultStatusCode;
	private String subLocation;
	private String room;
	private String floor;
	private String building;
	private String defaultVendorname;
	private String site;
	private String inventorySource;
	private String acdSourceCode;
	private String mcidbSourceCode;
	private String merckAcdSourceCode;
	private String locationType;
	private String compoundIdParamName;
	private String mfcdParamName;
	private String originalAmountsParamName;
	private String vendorParamName;
	private String containerLabelParam;
	private String casParamName;
	private String originalAmountUnitsParamName;
	private String disposedStatusParamName;
	private String barcodeParamName;

	private String defaultOriginalAmounts = "-1";
	private String defaultAmountUnits;
	

	protected StructureDAO acdDAO;
	protected StructureDAO merckACDDAO;
	protected StructureDAO mcidbDAO;
	protected InventoryDAO inventoryDAO;
}
