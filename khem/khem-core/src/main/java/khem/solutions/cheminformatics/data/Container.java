package khem.solutions.cheminformatics.data;

import java.io.Serializable;
import java.util.Date;

public class Container implements Serializable
{
	/**
	 * 
	 * @param structKey
	 * @param name
	 * @param container
	 */
	protected Container()
	{}
	/**
	 * 
	 * @param structKey
	 * @param name
	 * @param container
	 */
	public Container(String structKey, String label)
	{
		this.structKey = structKey;
		
		
		this.label = label;
		
	}

	
	/**
	 * @return the structKey
	 */
	public String getStructKey()
	{
		return structKey;
	}
	/**
	 * @param structKey the structKey to set
	 */
	public void setStructKey(String structKey)
	{
		this.structKey = structKey;
	}
	/**
	 * @return the label
	 */
	public String getLabel()
	{
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}
	/**
	 * @return the originalAmount
	 */
	public double getOriginalAmount()
	{
		return originalAmount;
	}
	/**
	 * @param originalAmount the originalAmount to set
	 */
	public void setOriginalAmount(double originalAmount)
	{
		this.originalAmount = originalAmount;
	}
	/**
	 * @return the originalAmountUnits
	 */
	public String getOriginalAmountUnits()
	{
		return originalAmountUnits;
	}
	/**
	 * @param originalAmountUnits the originalAmountUnits to set
	 */
	public void setOriginalAmountUnits(String originalAmountUnits)
	{
		this.originalAmountUnits = originalAmountUnits;
	}
	/**
	 * @return the casNumber
	 */
	public String getCasNumber()
	{
		return casNumber;
	}
	/**
	 * @param casNumber the casNumber to set
	 */
	public void setCasNumber(String casNumber)
	{
		this.casNumber = casNumber;
	}
	/**
	 * @return the containerId
	 */
	public int getContainerId()
	{
		return containerId;
	}
	/**
	 * @param containerId the containerId to set
	 */
	public void setContainerId(int containerId)
	{
		this.containerId = containerId;
	}
	/**
	 * @return the source
	 */
	public String getSource()
	{
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source)
	{
		this.source = source;
	}
	/**
	 * @return the barCode
	 */
	public String getBarCode()
	{
		return barCode;
	}
	/**
	 * @param barCode the barCode to set
	 */
	public void setBarCode(String barCode)
	{
		this.barCode = barCode;
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
	 * @return the vendorName
	 */
	public String getVendorName()
	{
		return vendorName;
	}
	/**
	 * @param vendorName the vendorName to set
	 */
	public void setVendorName(String vendorName)
	{
		this.vendorName = vendorName;
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
	 * @return the lastModDate
	 */
	public Date getLastModDate()
	{
		return lastModDate;
	}
	/**
	 * @param lastModDate the lastModDate to set
	 */
	public void setLastModDate(Date lastModDate)
	{
		this.lastModDate = lastModDate;
	}
	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}
	/**
	 * @return the molKey
	 */
	public String getMolKey()
	{
		return molKey;
	}
	/**
	 * @param molKey the molKey to set
	 */
	public void setMolKey(String molKey)
	{
		this.molKey = molKey;
	}

	/**
	 * @return the structSource
	 */
	public String getStructSource()
	{
		return structSource;
	}
	/**
	 * @param structSource the structSource to set
	 */
	public void setStructSource(String structSource)
	{
		this.structSource = structSource;
	}
	

	/**
	 * @return the molString
	 */
	public String getMolString()
	{
		return molString;
	}
	/**
	 * @param molString the molString to set
	 */
	public void setMolString(String molString)
	{
		this.molString = molString;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8238017763894023006L;
	private String structKey;
	private String structSource;
	private String label;
	private double originalAmount;
	private String originalAmountUnits;
	private String casNumber;
	private int containerId;
	private String source;
	private String barCode;
	private String site;
	private String building;
	private String floor;
	private String room;
	private String subLocation;
	private String vendorName;
	private String locationType;
	private String batchNumber;
	private Date lastModDate;
	private String status;
	private String molKey;
	private String molString;
	
}
