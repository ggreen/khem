package khem.solutions.cheminformatics.io;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author Gregory Green
 *
 */
public class SDFile implements Serializable
{
	
	public void addEntry(SDFEntry entry)
	{
		this.entries.add(entry);
	}// --------------------------------------------------------
	/**
	 * 
	 * @return the added entry
	 */
	public SDFEntry newEntry()
	{
		SDFEntry entry = new SDFEntry();
		entries.add(entry);
		
		return entry;
	}// --------------------------------------------------------
	
	/**
	 * @return
	 * @see java.util.Collection#isEmpty()
	 */
	public boolean isEmpty()
	{
		return entries.isEmpty();
	}
	/**
	 * @return
	 * @see java.util.Collection#size()
	 */
	public int entryCount()
	{
		return entries.size();
	}// --------------------------------------------------------

	/**
	 * @return the entries
	 */
	public Collection<SDFEntry> getEntries()
	{
		return entries;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = -4855468127608811110L;
	
	private final Collection<SDFEntry> entries = new ArrayList<SDFEntry>();
}
