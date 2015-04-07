package khem.solutions.cheminformatics.batch.spring;
import khem.solutions.cheminformatics.data.StructureKey;
import khem.solutions.cheminformatics.io.SDFEntry;
import nyla.solutions.global.data.DataRow;
import nyla.solutions.global.util.Text;

import org.springframework.batch.core.annotation.BeforeStep;


/**
 * This object allows inventory import processor from an SD files.

 * @author Gregory Green
 *
 */
public class InventorySdfProcessor extends AbstractInventoryProcessor<SDFEntry,DataRow>
{
	/**
	 * Turn off delete on initialize by default
	 */
	public InventorySdfProcessor()
	{
		this.setDeleteContainersOnInit(false);
	}// --------------------------------------------------------
	/**
	 * Setup connections
	 */
	@BeforeStep
	public void setUp()
	{
		super.init();
	}// --------------------------------------------------------
	/**
	 * Mapping based on content in a SD file
	 * 
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public DataRow process(SDFEntry sdfEntry) throws Exception
	{
		if(this.getRowNum() == 1 && this.deleteContainersOnFirstRecord)
			this.deleteContainers();
			
		DataRow dataRow = new DataRow(this.incrementRowNum(),this.getDataRowSize());
		
		//INVENTORY_ID	Offset + Row number
		int inventoryId = dataRow.getRowNum();

			
		dataRow.assignObject(this.getInventoryIDPos(), inventoryId);
			
			
		//container label
		if(this.getContainerLabelParam() != null &&  this.getContainerLabelParam().length() > 0)
		{
				dataRow.assignObject(this.getContainerLabelPos(),sdfEntry.retrieveParamValue(this.getContainerLabelParam()));
		}
			
		//vendor name
		String vendorParamName = this.getVendorParamName();
			
		String vendorName = this.getDefaultVendorname();
		if(vendorParamName != null && vendorParamName.length() > 0)
		{
				String vendorNameFromSdf = sdfEntry.retrieveParamValue(vendorParamName);
				
				if(vendorNameFromSdf != null)
					vendorName = vendorNameFromSdf;
		}
			
		dataRow.assignObject(this.getVendorNamePos(), vendorName);
					
		//locationType
		dataRow.assignObject(this.getLocationTypePos(),this.getLocationType());
					
		//site
		dataRow.assignObject(this.getSitePos(),this.getSite());
					
		//building
		dataRow.assignObject(this.getBuildingPos(),this.getBuilding());

		//floor
		dataRow.assignObject(this.getFloorPos(),this.getFloor());

		//room
		dataRow.assignObject(this.getRoomPos(),this.getRoom());
					
		//statusCodePos
		String statusCode = this.getDefaultStatusCode();
			
		String disposedStatusCodeParamName = this.getDisposedStatusParamName();
			
		if(disposedStatusCodeParamName != null && disposedStatusCodeParamName.length() == 0)
		{
				String isDisposed = sdfEntry.retrieveParamValue(disposedStatusCodeParamName);
				
				if(isDisposed != null && Boolean.TRUE.toString().equalsIgnoreCase(isDisposed.trim()))
					statusCode = "disposed";
				else
					statusCode = "not_disposed";
		}
			
		//Assign status code
		dataRow.assignObject(this.getStatusCodePos(),statusCode);
					
		//subLocation
		dataRow.assignObject(this.getSubLocationPos(),this.getSubLocation());
					
		//INVENTORY_SRC_CD �CC�
		dataRow.assignObject(this.getInventorySourcePos(),this.getInventorySource());
			
		//L-number
		String structureKeyId = null;
		if(this.getCompoundIdParamName() != null)
				structureKeyId = sdfEntry.retrieveParamValue(getCompoundIdParamName());
			
			
		StructureKey structureKey = null;
		String structureSourceCode = null;
			
		//Search by structure key by COMPOUND ID
		if(structureKeyId != null && structureKeyId.length() >  0)
		{
//				if(structureKeyId.matches("L.*"))
//				{
//					structureKey = this.mcidbDAO.selectStructKeyById(structureKeyId);
//					
//					if(structureKey != null )
//						structureSourceCode = this.getMcidbSourceCode();
//				}
//				else if(structureKeyId.matches("MERK.*"))
//				{
//					structureKey = this.merckACDDAO.selectStructKeyById(structureKeyId);
//					
//					if(structureKey != null )
//						structureSourceCode = this.getMerckAcdSourceCode();
//				}				
//				else if(structureKeyId.matches("MFCD.*"))
//				{
//					structureKey = this.acdDAO.selectStructKeyById(structureKeyId);
//					
//					if(structureKey != null )
//						structureSourceCode = this.getAcdSourceCode();
//				}
		}		
			
			
		//Search by structure key by MFCD#
		if(structureKey ==  null)
		{
				//Get MFCD from parameter
				structureKeyId = sdfEntry.retrieveParamValue(this.getMfcdParamName());
				
				if(structureKeyId != null && structureKeyId.length() >  0)
				{
					if(structureKeyId.matches("L.*"))
					{
						structureKey = this.mcidbDAO.findStructureKeyById(structureKeyId);
						
						if(structureKey != null )
							structureSourceCode = this.getMcidbSourceCode();
					}
					else if(structureKeyId.matches("MERK.*"))
					{
						structureKey = this.merckACDDAO.findStructureKeyById(structureKeyId);
						
						if(structureKey != null )
							structureSourceCode = this.getMerckAcdSourceCode();
					}					
					else if(structureKeyId.matches("MFCD.*"))
					{
						structureKey = this.acdDAO.findStructureKeyById(structureKeyId);
						
						if(structureKey != null )
							structureSourceCode = this.getAcdSourceCode();
					}
				}		
			}
			
			String casNum = null;
			String casNumParamName = this.getCasParamName();
			
			//Search by CAS
			if(structureKey == null && casNumParamName != null && casNumParamName.length() > 0)
			{
				casNum = sdfEntry.retrieveParamValue(casNumParamName);
				
				if(casNum != null)
				{
//					//search by CAS name
//					MaterialCriteria structureKeyCriteria = new MaterialCriteria();
//					
//					structureKeyCriteria.setCasNum(casNum);
//					Collection<StructureKey> collection = this.acdDAO.selectStructKeysByCasOrMolName(structureKeyCriteria);
//					
//					if(collection != null && !collection.isEmpty())
//				    {
//						structureKey = collection.iterator().next();
//				    }
//					else
//						casNum = null;
				}
			}

			if(structureKey == null)
			{
				try
				{
//					//searching by MOLSTRING
//					Collection<StructureKey> structureKeys = this.acdDAO.flexMatchStructureKeysByMol(sdfEntry.getMolString());
//					if(structureKeys != null && !structureKeys.isEmpty())
//					{
//						structureKey =  structureKeys.iterator().next();
//						structureSourceCode = this.getAcdSourceCode();
//					}
//					else
//					{
//						Debugger.printError(this,"SKIPPING Unknown structure key:"+sdfEntry+" in "+Debugger.toString(sdfEntry));
//						this.auditSkip(dataRow, "Unknown structure key:"+ structureKeyId);
//						return null;
//					}
				}
				catch (Exception e)
				{
					super.auditSkip(dataRow,"CANNOT MATCH structure "+e.toString());
					return null;
						
				}
			}
			
			String molKey = structureKey.getMolKey();
			
			//original amounts
			String rawOriginalAmounts = sdfEntry.retrieveParamValue(this.getOriginalAmountsParamName());
			
			String originalAmounts = null;
			String originalAmountsUnits = null;
			
			//Process original amounts
			if(rawOriginalAmounts != null)
			{
				int unitStart = Text.indexOfRegExp(rawOriginalAmounts, "[a-zA-Z]+");
				
				if(unitStart > -1)
				{
					originalAmounts = rawOriginalAmounts.substring(0, unitStart);
					originalAmountsUnits = rawOriginalAmounts.substring(unitStart,rawOriginalAmounts.length());
				}
				else
					originalAmounts = rawOriginalAmounts;
			}
			
			if(originalAmountsUnits != null)
				originalAmountsUnits = originalAmountsUnits.trim().toUpperCase();
			else
		    {
				//determine if original amount can be parsed
				String amountUnitsParamName = this.getOriginalAmountUnitsParamName();
				if(amountUnitsParamName != null  && amountUnitsParamName.length() > 0)
				{
					//Get from SDF
					String amountUnitsFromSdf = sdfEntry.retrieveParamValue(amountUnitsParamName);
					
					if(amountUnitsFromSdf != null && amountUnitsFromSdf.length() > 0)
					{
						originalAmountsUnits = amountUnitsFromSdf;
					}
				}
		    }
			
			//Original amounts unit
			if(originalAmountsUnits == null || originalAmountsUnits.length() == 0)
				originalAmountsUnits = this.getDefaultAmountUnits();
			
			if(originalAmounts == null || !Text.isNumber(originalAmounts))
			{
				dataRow.assignObject(this.getOriginalAmountsPos(), this.getDefaultOriginalAmounts());
			}
			else
				dataRow.assignObject(this.getOriginalAmountsPos(), Double.valueOf(originalAmounts));

			dataRow.assignObject(this.getOriginalAmountUnitsPos(), originalAmountsUnits);			
			
			//Assign structure key and source code
			dataRow.assignObject(this.getStructureKeyPos(), structureKeyId);
			dataRow.assignObject(this.getStructureSourceCodePos(), structureSourceCode);
			dataRow.assignObject(this.getMolKeyPos(),molKey);
			dataRow.assignObject(this.getBatchNumberPos(), this.getBatchNumber());			


			//CASNUMBER	ACD.CASNUM
			structureKeyId = structureKey.getId();
			if(casNum != null && casNum.length() > 0)
			{
				dataRow.assignObject(this.getCasNumberPos(),casNum);
			}
			else if(structureKeyId.matches("MFCD.*") )
			{
				//StructureKey mfcdnumberInput = new StructureKey(structureKeyId);

//				String[] casNums = acdDAO.selectCasNumsByStructureKey(mfcdnumberInput);
//				
//				if(casNums != null && casNums.length > 0)
//				{
//					dataRow.assignObject(this.getCasNumberPos(), casNums[0]); //assign first CASNUM (P(s) are first=primary)
//				}
			}
			
			//check if previous records exist with same BARCODE
			String barcodeParamName = this.getBarcodeParamName();
			
			if(barcodeParamName != null)
			{
				String barcode = sdfEntry.retrieveParamValue(this.getBarcodeParamName());
				
				if(barcode != null && barcode.length()> 0)
				{
					//delete previous BARCODE
					this.inventoryDAO.removeByBarCode(barcode);
				}
				
				dataRow.assignObject(this.getBarcodePos(), barcode);
			}
			
			return dataRow;
	}// --------------------------------------------------------
	
	/**
	 * @return the deleteContainersOnFirstRecord
	 */
	public boolean isDeleteContainersOnFirstRecord()
	{
		return deleteContainersOnFirstRecord;
	}
	/**
	 * @param deleteContainersOnFirstRecord the deleteContainersOnFirstRecord to set
	 */
	public void setDeleteContainersOnFirstRecord(boolean deleteContainersOnFirstRecord)
	{
		this.deleteContainersOnFirstRecord = deleteContainersOnFirstRecord;
	}

	private boolean deleteContainersOnFirstRecord = true;

}
