package khem.solutions.cheminformatics.data;

import java.io.Serializable;

public class Location implements Serializable
{
	

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
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 8710071201153320800L;

	private String site;
	
	private String building;
	
	private String floor;
	
	private String room;
	
	private String subLocation;
	
	
	private String name;
}
