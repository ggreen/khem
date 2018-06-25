package khem.solutions.cheminformatics.io;


import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import nyla.solutions.core.exception.ConnectionException;
import nyla.solutions.core.exception.NotImplementedException;
import nyla.solutions.core.util.Text;
import nyla.solutions.global.io.IO;


/**
 * MOL SD file reader.
 * 
 * An MDL Molfile is a file format created by MDL, for holding 
 * information about the atoms, bonds, connectivity and coordinates of a molecule. The molfile consists of some header information, 
 * the Connection Table (CT) containing atom info, then bond connections and types.
 * 
 * Example file content
 * 
 *  -ISIS-  09071210002D


 14 15  0  0  0  0  0  0  0  0999 V2000

    1.7292    0.1750    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0

    2.6042    1.1750    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0

    0.4292    0.4333    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0

   -0.4333   -0.5542    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0

   -1.7333   -0.2917    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0

    3.9125    0.9208    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0

    2.1542   -1.0625    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0

   -2.1625    0.9458    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0

   -2.6125   -1.2917    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0

    4.3417   -0.3167    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0

    3.4625   -1.3167    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0

   -3.9208   -1.0375    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0

   -3.4708    1.2000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0

   -4.3458    0.2000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0

  2  1  2  0  0  0  0

  3  1  1  0  0  0  0

  4  3  1  0  0  0  0

  5  4  1  0  0  0  0

  6  2  1  0  0  0  0

  7  1  1  0  0  0  0

  8  5  2  0  0  0  0

  9  5  1  0  0  0  0

 10 11  1  0  0  0  0

 11  7  2  0  0  0  0

 12  9  2  0  0  0  0

 13  8  1  0  0  0  0

 14 12  1  0  0  0  0

  6 10  2  0  0  0  0

 13 14  2  0  0  0  0

M  END

>  <ID> (1)

1

>  <NAME> (1)

GREEN PILLS

 * 
 * @author Gregory Green
 *
 */
public class SDUrlReader implements Closeable, Iterable<SDFEntry>, Iterator<SDFEntry>
{
	/**
	 * END_OF_ENTRY = "$$$$"
	 */
	private static final String END_OF_ENTRY = "$$$$";
	
	/**
	 * PARAM_NAME_REGEXP = "^> *<.*>.*"
	 */
	private static final String PARAM_NAME_REGEXP = "^> *<.*>.*";
	
	
	/**
	 * Marks the end of the MOL file
	 */
	private static final String END_MOL = "M  END";
	
	/**
	 * The parsing states for a MOL file
	 * @author Gregory Green
	 *
	 */
	private enum ParseState{
		MOL,
		PARAMATERS,
		PARAM_NAME,
		PARAM_VALUE,
		EOF
	};
	
	/**
	 * Constructor
	 * @param url
	 */
	public SDUrlReader(URL url)
	{
		this.url = url;
	}// --------------------------------------------------------

	/**
	 * Open the file
	 * @throws IOException
	 */
	public void open()
	throws IOException
	{
		reader = new BufferedReader(IO.toReader(url.openStream()));
	}// --------------------------------------------------------
	/**
	 * 
	 * @throws FileNotFoundException
	 * @return the SD file
	 * @throws IOException
	 */
	public SDFile readAll()
	throws FileNotFoundException, IOException
	{
			
		if(reader == null)
		{
			open();
			state = ParseState.MOL;

		}
		
		SDFile sdFile = new SDFile();
		SDFEntry entry = null;
		
		while((entry = this.nextSDFEntry()) != null)
		{
			sdFile.addEntry(entry);
		}
		
		if(sdFile.isEmpty())
			return null;
		
		return sdFile;
	}// --------------------------------------------------------
	/**
	 * Close the open reader
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException
	{
		if(this.reader == null)
			return;
		
		this.reader.close();
		this.reader = null;
	}// --------------------------------------------------------
	/**
	 * Reset or open the reader
	 * @throws IOException
	 */
	public void reset()
	throws IOException
	{
		if(this.reader == null)
			this.open();
		else
			this.reader.reset();
		
	}// --------------------------------------------------------
	/**
	 * Retrieve the next entry in the file
	 * @return the null if no next values
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public  SDFEntry nextSDFEntry()
	throws FileNotFoundException, IOException
	{
		if(reader == null)
		{
			this.open();
		}
		
		if(state == ParseState.EOF)
		{
			 doesHaveNext = false; 
			 return null;
		}
		
		SDFEntry  entry = new SDFEntry();
		
		state = processMOL(reader,entry);
			
		if(state == ParseState.PARAMATERS)
			state = processPARAMETERS(reader,entry);
	
		String molString = entry.getMolString();
		
		if(molString == null || molString.length() == 0)
		{
			doesHaveNext = false;
			
			return null;
		}
		
		return entry;
	}// --------------------------------------------------------
	/**
	 * Example parsing
	 * 
			>  <ID> (2)
			
			2
			
			>  <NAME> (2)
			
			N-BENZYL-N-(SEC-BUTYL)AMINE

	 * @param reader the file reader
	 * @param entry the current entry
	 * @return the current STATE
	 */
	private ParseState processPARAMETERS(BufferedReader reader, SDFEntry entry)
	throws IOException
	{
		String line;
		String name = null;
		String value = null;
		ParseState paramState = ParseState.PARAM_NAME;
		while((line= reader.readLine()) != null && !END_OF_ENTRY.equals(line = line.trim()))
		{
			if(line.length() == 0)
				continue; //skip blanks
			
			//Check if this is a param NAME
			if(paramState != ParseState.PARAM_NAME &&
			   line.matches(PARAM_NAME_REGEXP))
				paramState = ParseState.PARAM_NAME;
			
			switch(paramState)
			{
				case PARAM_NAME: 
	
					name = Text.parseRE(line, "<", ">");
					paramState = ParseState.PARAM_VALUE;
					
					entry.addParamName(name);
					
				break;
				case PARAM_VALUE: 
					if(line.length() > 0)
					{
						value = line;
						
						entry.addParamValue(name,value);
						paramState = ParseState.PARAM_NAME;
					}
				default: break; //nothing
			}
		}
		
		return nextState(line, ParseState.MOL);
		
	}// --------------------------------------------------------
	/**
	 * 
	 * @param reader the file reader
	 * @param entry the current entry
	 * @return
	 * @throws IOException
	 */
	private ParseState processMOL(BufferedReader reader, SDFEntry entry)
	throws IOException
	{
		String line = null;
		
		while((line= reader.readLine()) != null && !END_OF_ENTRY.equals(line.trim()))
		{
			entry.appendMol(line);
			
			//Debugger.println(this,"line="+line);
			
			if(END_MOL.equals(line.trim()))
			{
				break;
			}
		}
		
		return nextState(line, ParseState.PARAMATERS);

	}// --------------------------------------------------------
	@Override
	public boolean hasNext()
	{	
		return doesHaveNext;
	}

	@Override
	public SDFEntry next()
	{
		
		try
		{
			return this.nextSDFEntry();
		}
		catch (FileNotFoundException e)
		{
			throw new ConnectionException("Cannot read file:"+this.url,e);
		}
		catch (IOException e)
		{
			throw new ConnectionException("Error reading file:"+this.url,e);
		}
	}// --------------------------------------------------------

	@Override
	public void remove()
	{
		throw new NotImplementedException();
	}// --------------------------------------------------------

	@Override
	public Iterator<SDFEntry> iterator()
	{
		return this;
	}
	/**
	 * 
	 * @param line the line to parse
	 * @param defaultNextState the default next state
	 * @return the parse state
	 */
	private ParseState nextState(String line, ParseState defaultNextState)
	{
		if(line == null)
			return ParseState.EOF;
		else if(END_OF_ENTRY.equals(line))
			return ParseState.MOL; //look for next MOL
		else
			return defaultNextState;	
	}// --------------------------------------------------------
	private boolean doesHaveNext = true;
	private ParseState state = ParseState.MOL;
	private BufferedReader reader = null;
	private final URL url;
	
}
