package khem.solutions.cheminformatics.data;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Criteria is select material availability data
 * @author Gregory Green
 *
 */
public class InventoryCriteria implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1600697082574666588L;
	
	/**
	 * @return the sources
	 */
	public String[] getSources()
	{
		return sources;
	}
	/**
	 * @param sources the sources to set
	 */
	public void setSources(String[] sources)
	{
		this.sources = sources;
	}
	/**
	 * @return the containerLabels
	 */
	public String[] getContainerLabels()
	{
		return containerLabels;
	}
	/**
	 * @param containerLabels the containerLabels to set
	 */
	public void setContainerLabels(String[] containerLabels)
	{
		this.containerLabels = containerLabels;
	}
	/**
	 * @return the barCodes
	 */
	public String[] getBarCodes()
	{
		return barCodes;
	}
	/**
	 * @param barCodes the barCodes to set
	 */
	public void setBarCodes(String[] barCodes)
	{
		this.barCodes = barCodes;
	}
	/**
	 * @return the sites
	 */
	public String[] getSites()
	{
		return sites;
	}
	/**
	 * @param sites the sites to set
	 */
	public void setSites(String[] sites)
	{
		this.sites = sites;
	}
	/**
	 * @return the buildings
	 */
	public String[] getBuildings()
	{
		return buildings;
	}
	/**
	 * @param buildings the buildings to set
	 */
	public void setBuildings(String[] buildings)
	{
		this.buildings = buildings;
	}
	/**
	 * @return the batchNumbers
	 */
	public String[] getBatchNumbers()
	{
		return batchNumbers;
	}
	/**
	 * @param batchNumbers the batchNumbers to set
	 */
	public void setBatchNumbers(String[] batchNumbers)
	{
		this.batchNumbers = batchNumbers;
	}
	/**
	 * @return the floors
	 */
	public String[] getFloors()
	{
		return floors;
	}
	/**
	 * @param floors the floors to set
	 */
	public void setFloors(String[] floors)
	{
		this.floors = floors;
	}
	/**
	 * @return the rooms
	 */
	public String[] getRooms()
	{
		return rooms;
	}
	/**
	 * @param rooms the rooms to set
	 */
	public void setRooms(String[] rooms)
	{
		this.rooms = rooms;
	}
	/**
	 * @return the subLocations
	 */
	public String[] getSubLocations()
	{
		return subLocations;
	}
	/**
	 * @param subLocations the subLocations to set
	 */
	public void setSubLocations(String[] subLocations)
	{
		this.subLocations = subLocations;
	}

	/**
	 * @return the locationTypes
	 */
	public String[] getLocationTypes()
	{
		return locationTypes;
	}
	/**
	 * @param locationTypes the locationTypes to set
	 */
	public void setLocationTypes(String[] locationTypes)
	{
		this.locationTypes = locationTypes;
	}

	/**
	 * @return the statusCodes
	 */
	public String[] getStatusCodes()
	{
		return statusCodes;
	}
	/**
	 * @param statusCodes the statusCodes to set
	 */
	public void setStatusCodes(String[] statusCodes)
	{
		this.statusCodes = statusCodes;
	}
	/**
	 * @return the structureSourceCodes
	 */
	public String[] getStructureSourceCodes()
	{
		return structureSourceCodes;
	}
	/**
	 * @param structureSourceCodes the structureSourceCodes to set
	 */
	public void setStructureSourceCodes(String[] structureSourceCodes)
	{
		this.structureSourceCodes = structureSourceCodes;
	}// --------------------------------------------------------
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}// --------------------------------------------------------

	
	public boolean hasLocationTypes()
	{
		return  this.locationTypes != null && locationTypes.length > 0;
	}// --------------------------------------------------------
	public boolean hasSources()
	{
		return sources != null && sources.length > 0;
	}// --------------------------------------------------------
	
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(barCodes);
		result = prime * result + Arrays.hashCode(batchNumbers);
		result = prime * result + Arrays.hashCode(buildings);
		result = prime * result + Arrays.hashCode(containerLabels);
		result = prime * result + Arrays.hashCode(floors);
		result = prime * result + Arrays.hashCode(locationTypes);
		result = prime * result + Arrays.hashCode(rooms);
		result = prime * result + Arrays.hashCode(sites);
		result = prime * result + Arrays.hashCode(sources);
		result = prime * result + Arrays.hashCode(statusCodes);
		result = prime * result + Arrays.hashCode(structureSourceCodes);
		result = prime * result + Arrays.hashCode(subLocations);
		return result;
	}
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryCriteria other = (InventoryCriteria) obj;
		if (!Arrays.equals(barCodes, other.barCodes))
			return false;
		if (!Arrays.equals(batchNumbers, other.batchNumbers))
			return false;
		if (!Arrays.equals(buildings, other.buildings))
			return false;
		if (!Arrays.equals(containerLabels, other.containerLabels))
			return false;
		if (!Arrays.equals(floors, other.floors))
			return false;
		if (!Arrays.equals(locationTypes, other.locationTypes))
			return false;
		if (!Arrays.equals(rooms, other.rooms))
			return false;
		if (!Arrays.equals(sites, other.sites))
			return false;
		if (!Arrays.equals(sources, other.sources))
			return false;
		if (!Arrays.equals(statusCodes, other.statusCodes))
			return false;
		if (!Arrays.equals(structureSourceCodes, other.structureSourceCodes))
			return false;
		if (!Arrays.equals(subLocations, other.subLocations))
			return false;
		return true;
	}


	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "InventoryCriteria [sources=" + Arrays.toString(sources)
				+ ", containerLabels=" + Arrays.toString(containerLabels)
				+ ", barCodes=" + Arrays.toString(barCodes) + ", sites="
				+ Arrays.toString(sites) + ", buildings="
				+ Arrays.toString(buildings) + ", batchNumbers="
				+ Arrays.toString(batchNumbers) + ", floors="
				+ Arrays.toString(floors) + ", rooms=" + Arrays.toString(rooms)
				+ ", subLocations=" + Arrays.toString(subLocations)
				+ ", locationTypes=" + Arrays.toString(locationTypes)
				+ ", statusCodes=" + Arrays.toString(statusCodes)
				+ ", structureSourceCodes="
				+ Arrays.toString(structureSourceCodes) + "]";
	}
	
	


	private String[] sources;
	private String[] containerLabels;
	private String[] barCodes;
	private String[] sites;
	private String[]  buildings; 
	private String[] batchNumbers; 
	private String[] floors;
	private String[] rooms;
	private String[] subLocations; 
	private String[] locationTypes;
	private String[] statusCodes; 
	private String[] structureSourceCodes;
	
}
