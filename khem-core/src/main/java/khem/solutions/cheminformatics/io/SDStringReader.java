package khem.solutions.cheminformatics.io;


import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import nyla.solutions.core.exception.NotImplementedException;
import nyla.solutions.core.util.Text;

public class SDStringReader implements Closeable, Iterable<SDFEntry>, Iterator<SDFEntry>
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
	

	public SDStringReader(String content)
	{
		this.content = content;
	}// --------------------------------------------------------
	
	
	public void open()
	throws IOException
	{
		reader = new BufferedReader(new StringReader(this.content));
	}// --------------------------------------------------------

	 /** 
	 * @throws FileNotFoundException
	 * @return the SD file	public void open()
	throws IOException
	{
		reader = new BufferedReader(IO.toReader(url.openStream()));
	}// --------------------------------------------------------
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
			catch (IOException e)
			{
				throw new RuntimeException(e);
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
	private final String content;
	private BufferedReader reader = null;
	
}
