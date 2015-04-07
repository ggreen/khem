package khem.solutions.cheminformatics.batch.spring;


import khem.solutions.cheminformatics.dao.InventoryDAO;
import khem.solutions.cheminformatics.dao.StructureDAO;
import khem.solutions.cheminformatics.data.StructureKey;
import nyla.solutions.dao.spring.batch.AbstractArrayableItemProcessor;
import nyla.solutions.global.data.DataRow;
import nyla.solutions.global.exception.ConfigException;
import nyla.solutions.global.exception.FatalException;
import nyla.solutions.global.exception.RequiredException;
import nyla.solutions.global.patterns.Disposable;
import nyla.solutions.global.patterns.servicefactory.ServiceFactory;
import nyla.solutions.global.util.Config;
import nyla.solutions.global.util.Debugger;
import nyla.solutions.global.util.Text;

import org.springframework.batch.core.annotation.BeforeStep;



/**
 * Local CC inventory will be imported into the ASAP database from Excel files. 
 * The directory where the files are located will be used to determine the location
 *  (SITE -> BUILDING -> FLOOR -> LOCATION_TYPE -> ROOM). It will also be used to 
 *  determine the container amounts. All location and amount information will be 
 *  configured in the batch XML files (also the Spring batch framework).
 *  
 *  

The following is the mapping to process Local CC containers.

 <bean id="localCCProcessor" class="com.merck.mrl.asap.integration.batch.InventoryProcessor">
		<property name="batchNumberPos" value="1"/>
		<property name="structureKeyPos" value="2"/>
		<property name="locationTypePos" value="6"/>
		<property name="containerLabelPos" value="9"/>
		<property name="barcodePos" value="10"/>
		<property name="vendorNamePos" value="11"/>
		<property name="casNumberPos" value="16"/>
		<property name="inventorySourcePos" value="17"/>
		<property name="structureSourceCodePos" value="18"/>
		<property name="molKeyPos" value="19"/>
		<property name="dataRowSize" value="20"/>

        <property name="acdSourceCode" value="ACD"/>
        <property name="mcidbSoureCode" value="MCIDB"/>
         <property name="merckAcdSourceCode" value="MERCKACD"/>
         
		<property name="defaultLnumberVendor" value="MERCK"/>
		<property name="defaultMfcdVendor" value="Sigma-Aldrich"/>
		<property name="skipFilePath" value="${report.dir}/LOCAL_CC_SKIPS.csv"/>
		<property name="skipFileHeader" value="BATCHNUMBER,STRUCTUREKEYS,SITE,BUILDING,FLOOR,LOCATIONTYPE,ROOM,SUBLOCATION,CONTAINERLABEL,BARCODE,VENDOR,ORIGINALAMOUNT,ORIGINALAMOUNTUNITS,STATUS,INVENTORY_ID,CASNUMBER,INV_SOURCE_CD,STRUCT_SOURCE_CD"/>
		
	</bean>

 * @author Gregory Green
 *
 */
public class InventoryProcessor extends AbstractArrayableItemProcessor<String[],DataRow> implements Disposable
{
	/**
	 * MERCK_VENDOR = Config.getProperty(LocalCCProcessor.class,"MERCK_VENDOR","MERCK")
	 */
	public static final String MERCK_VENDOR = Config.getProperty(InventoryProcessor.class,"MERCK_VENDOR","MERCK");
	
	/**
	 * Setup connections
	 */
	@BeforeStep
	public void setup()
	{
		if(this.inventorySource == null || this.inventorySource.length() == 0)
			throw new ConfigException(this.getClass().getName()+".inventorySource required");
		
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
			   
			   this.inventoryDAO.removeByBarCode(this.inventorySource);
		   }
	       catch(Exception e)
	       {
	    	   throw new FatalException(e);
	       }
		   
		   
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
	 * Mapping
	 * Column	Mapping
		BATCHNUMBER	Last 3 three digits of SHEET [1] column[C] for non-commercial
		INVENTORY_SRC_CD	 �CC�
		BUILDING	Configuration
		FLOOR	Configuration
		LOCATION_CLASS	Configuration
		ORIGINALAMOUNT	Configuration
		ORIGINALAMOUNTUNITS	Configuration
		ROOM	Configuration
		SITE 	Configuration
		INVENTORY_ID	 INVENTORY_SEQ.NEXTVAL
		CASNUMBER	ACD.CASNUM
		SUBLOCATION	Sheet [1] column [B]
		CONTAINERLABEL	Sheet [1] column [C]
		BARCODE	Sheet [1] column [D]
		VENDORNAME	Sheet [1] column [E]
		STRUCTUREKEY	SPLIT (Sheet [1] column [A],�;�)[0]
		LAST_MOD_DT	SYSDATE
		MOLKEY	

	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public DataRow process(String[] item) throws Exception
	{
		
		DataRow dataRow = new DataRow(rowNum++,this.dataRowSize);
		
		//Copy from items
		for (int i = 0; i < item.length; i++)
		{
			dataRow.assignObject(i+1, item[i]);
		}
		
				
		//INVENTORY_SRC_CD �CC�
		dataRow.assignObject(this.inventorySourcePos,inventorySource);
		
		String locationType = item[this.locationTypePos - 1];
		
		//LOCATION TYPE
		if(locationType == null || locationType.length() == 0)
		{
			//assign default location type
			dataRow.assignObject(this.locationTypePos,this.defaultLocationType);
		}
		
		//STRUCTUREKEY	SPLIT (Sheet [1] column [A],�;�)[0]
		String structureKeyFromFile = item[this.structureKeyPos-1];
		
		String [] structureKeys = Text.split(structureKeyFromFile, structureKeyDelimitor);
		
		//L-number, MERK#, MFCD#
		String lnumber = null;
		String merknumber = null;
		String mfcdnumber = null;
		String molKey = null;
		StructureKey structureKey = null;
		for (int i = 0; i < structureKeys.length; i++)
		{
			if(structureKeys[i] == null)
				continue; //skip
			
			structureKeys[i] = structureKeys[i].trim();
			if(structureKeys[i].length() == 0)
				continue; //skip
			
			if(structureKeys[i].matches(lNumberPattern))
			{
				lnumber = structureKeys[i];
				
				structureKey = this.mcidbDAO.findStructureKeyById(lnumber);
				
				if(structureKey == null)
				{
					lnumber = null;
				}
				else
				{
					molKey = structureKey.getMolKey();		
				}
			}
			else if(structureKeys[i].matches(merkNumberPattern))
			{
				merknumber = structureKeys[i];
				structureKey = this.merckACDDAO.findStructureKeyById(merknumber);
				
				if( structureKey == null)
					merknumber = null;
				else
				{
					molKey = structureKey.getMolKey();		
				}
			}
			else if(structureKeys[i].matches(mfcdPattern))
			{
				mfcdnumber = structureKeys[i];
				
				structureKey = this.acdDAO.findStructureKeyById(mfcdnumber);
				if(structureKey == null)
					mfcdnumber = null;
				else
				{
					molKey = structureKey.getMolKey();				}
			}
			else
			{
				continue;
			}  
		}
		
		String structureKeyId = lnumber;
		String structureSourceCode = this.mcidbSourceCode;
		
		if(structureKeyId == null)
		{
			structureKeyId = merknumber;
			structureSourceCode = this.merckAcdSourceCode;
		}
		
		if(structureKeyId == null)
		{
			structureKeyId = mfcdnumber;
			structureSourceCode = this.acdSourceCode;
		}
		
		if(structureKey == null)
		{
			Debugger.printError(this,"SKIPPING Unknown structure key:"+structureKeyFromFile+" in "+Debugger.toString(item));
			this.auditSkip(dataRow);
			return null;
		
		}
		
		//Assign structure key and source code
		dataRow.assignObject(this.structureKeyPos, structureKeyId);
		dataRow.assignObject(this.structureSourceCodePos, structureSourceCode);
		dataRow.assignObject(this.molKeyPos,molKey);
		
		//BATCHNUMBER	Last 3 three digits of SHEET [1] column[C] for non-commercial
		String vendorname = item[vendorNamePos -1];
		if(vendorname != null)
			vendorname = vendorname.trim().toUpperCase();
		
		//Process default Vendor name
		if(vendorname == null || vendorname.length() == 0)
		{
			if(lnumber != null)
				vendorname = this.defaultLnumberVendor;
			else if(mfcdnumber != null)
				vendorname = this.defaultMfcdVendor;
			
	
			dataRow.assignObject(this.vendorNamePos, vendorname);
		}
		
		String batchNumber = item[this.batchNumberPos  - 1];
		
		if((batchNumber == null || batchNumber.length() == 0 ) && MERCK_VENDOR.equals(vendorname) && structureKeyFromFile.indexOf("L-") > -1)
		{
			//Get batch from container label
				String containerLabel = item[containerLabelPos-1];

						
				if(containerLabel != null && containerLabel.length() > 3)
				{
					batchNumber = containerLabel.substring(containerLabel.length()-3, containerLabel.length());
						
					if(!Text.isInteger(batchNumber))
							batchNumber = null; //invalid batch
				}
					
				dataRow.assignObject(this.batchNumberPos, batchNumber);			
		 }

		//INVENTORY_ID	Offset + Row number
		//int inventoryId = offset+ dataRow.getRowNum();

		//dataRow.assignObject(this.inventoryIDPos, inventoryId);
		

		//LAST_MOD_DT	SYSDATE
		
		//CASNUMBER	ACD.CASNUM
		if(mfcdnumber != null)
		{
			StructureKey mfcdnumberInput = new StructureKey(mfcdnumber);
	
			
			String[] casNums = acdDAO.findCasNumsByStructureKey(mfcdnumberInput);
			
			if(casNums != null && casNums.length > 0)
			{
				dataRow.assignObject(this.casNumberPos, casNums[0]); //assign first CASNUM (P(s) are first=primary)
			}
			
		}
		
		//check if previous records exist with same barcode
		String barcode = dataRow.retrieveString(this.barcodePos);
		
		if(barcode != null && barcode.length()> 0)
		{
			//delete previous barcode
			this.inventoryDAO.removeByBarCode(barcode);
		}
		
		return dataRow;
	}// --------------------------------------------------------
	/**
	 * @return the structureKeyDelimitor
	 */
	public String getStructureKeyDelimitor()
	{
		return structureKeyDelimitor;
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
	 * @param structureKeyDelimitor the structureKeyDelimitor to set
	 */
	public void setStructureKeyDelimitor(String structureKeyDelimitor)
	{
		this.structureKeyDelimitor = structureKeyDelimitor;
	}
	/**
	 * @return the lNumberPattern
	 */
	public String getlNumberPattern()
	{
		return lNumberPattern;
	}
	/**
	 * @param lNumberPattern the lNumberPattern to set
	 */
	public void setlNumberPattern(String lNumberPattern)
	{
		this.lNumberPattern = lNumberPattern;
	}
	/**
	 * @return the merkNumberPattern
	 */
	public String getMerkNumberPattern()
	{
		return merkNumberPattern;
	}
	/**
	 * @param merkNumberPattern the merkNumberPattern to set
	 */
	public void setMerkNumberPattern(String merkNumberPattern)
	{
		this.merkNumberPattern = merkNumberPattern;
	}
	/**
	 * @return the mfcdPattern
	 */
	public String getMfcdPattern()
	{
		return mfcdPattern;
	}
	/**
	 * @param mfcdPattern the mfcdPattern to set
	 */
	public void setMfcdPattern(String mfcdPattern)
	{
		this.mfcdPattern = mfcdPattern;
	}
	/**
	 * @return the rowNum
	 */
	public int getRowNum()
	{
		return rowNum;
	}
	/**
	 * @param rowNum the rowNum to set
	 */
	public void setRowNum(int rowNum)
	{
		this.rowNum = rowNum;
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
	 * @return the defaultLocationType
	 */
	public String getDefaultLocationType()
	{
		return defaultLocationType;
	}
	/**
	 * @param defaultLocationType the defaultLocationType to set
	 */
	public void setDefaultLocationType(String defaultLocationType)
	{
		this.defaultLocationType = defaultLocationType;
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
	 * @return the defaultLnumberVendor
	 */
	public String getDefaultLnumberVendor()
	{
		return defaultLnumberVendor;
	}
	/**
	 * @param defaultLnumberVendor the defaultLnumberVendor to set
	 */
	public void setDefaultLnumberVendor(String defaultLnumberVendor)
	{
		this.defaultLnumberVendor = defaultLnumberVendor;
	}
	/**
	 * @return the defaultMfcdVendor
	 */
	public String getDefaultMfcdVendor()
	{
		return defaultMfcdVendor;
	}
	/**
	 * @param defaultMfcdVendor the defaultMfcdVendor to set
	 */
	public void setDefaultMfcdVendor(String defaultMfcdVendor)
	{
		this.defaultMfcdVendor = defaultMfcdVendor;
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
	 * @return the mcidbSourceCode
	 */
	public String getMcidbSourceCode()
	{
		return mcidbSourceCode;
	}
	/**
	 * @param mcidbSourceCode the mcidbSoureCode to set
	 */
	public void setMcidbSourceCode(String mcidbSourceCode)
	{
		this.mcidbSourceCode = mcidbSourceCode;
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


	private String structureKeyDelimitor = Config.getProperty(InventoryProcessor.class,"structureKeyDelimitor", ";");
	private String lNumberPattern = Config.getProperty(InventoryProcessor.class,"lNumberPattern","[A-L]-.*");
	private String merkNumberPattern =  Config.getProperty(InventoryProcessor.class,"merkNumberPattern","MERK.*");
	private String mfcdPattern = Config.getProperty(InventoryProcessor.class,"mfcdPattern","MFCD.*");
	private int dataRowSize;
	private int rowNum = 1;
	private int batchNumberPos;
	private int inventorySourcePos;
	private int casNumberPos;
	private int containerLabelPos;
	private int molKeyPos;
	private int vendorNamePos;
	private int structureKeyPos;
	private int structureSourceCodePos;
	private String acdSourceCode;
	private String mcidbSourceCode;
	private String merckAcdSourceCode;
	private String defaultLocationType = Config.getProperty(InventoryProcessor.class,"defaultLocationType","LAB");
	private int locationTypePos;
	private int barcodePos;
	private String defaultLnumberVendor;
	private String defaultMfcdVendor;
	private StructureDAO acdDAO;
	private StructureDAO merckACDDAO;
	private StructureDAO mcidbDAO;
	private InventoryDAO inventoryDAO;
	private String inventorySource = Config.getProperty(InventoryProcessor.class,"inventorySource","CC");
}
