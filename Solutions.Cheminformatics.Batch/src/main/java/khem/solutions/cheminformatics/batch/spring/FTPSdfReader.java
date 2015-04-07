package khem.solutions.cheminformatics.batch.spring;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import khem.solutions.cheminformatics.io.SDFEntry;
import khem.solutions.cheminformatics.io.SDFile;
import khem.solutions.cheminformatics.io.SDFileReader;
import nyla.solutions.global.net.ftp.Ftp;
import nyla.solutions.global.util.Config;
import nyla.solutions.global.util.Debugger;

import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;



/**
 * Spring Example configuration
 * 	<bean id="catalogWUXIReader" class="com.merck.mrl.asap.integration.batch.FTPSdfReader">
		<property name="host" value="Inetftp"/>
		<property name="username" value="pharmatech01"/>
		<property name="password" value="{cryption}64 -46 -27 -96 16 89 -58 -33 -35 -24 115 10 116 -72 41 101"/>
		<property name="localFilePath" value="runtime/input/ministore.sdf"/>
		<property name="remoteDirectory" value="/"/>
		<property name="remoteFileName" value="Ministore.sdf"/>
	</bean>
 * @author Gregory Green
 *
 */
public class FTPSdfReader implements ItemReader<SDFEntry>
{

	
	@BeforeStep
	public void open()
	throws IOException
	{
		//FTP file
		Ftp ftp = Ftp.getInstance();
		
		
		ftp.setUsername(Config.interpret(username));
		ftp.setPassword(Config.interpret(password).toCharArray());
		ftp.setHost(Config.interpret(host));
		ftp.setBinaryTransfer(true);
		
		//previous local file
		String localFilePath = Config.interpret(this.localFilePath);
		
		new File(localFilePath).delete();
		
		String remoteDirectory = Config.interpret(this.remoteDirectory);
		String remoteFileName = Config.interpret(this.remoteFileName);
		if(!ftp.existFile(remoteDirectory, remoteFileName))
		{
			Debugger.printInfo(this,"File not found path:"+remoteDirectory+" file:"+remoteFileName);
			return;
		}
		
		
		//FTP file
		ftp.executeGet(localFilePath, new StringBuilder(remoteDirectory).append("/").append(remoteFileName).toString());
		
		
		SDFileReader reader = null;
		
		
		try
		{
			reader = new SDFileReader(new File(localFilePath));
			
			this.sdFile = reader.readAll();
		}
		finally
		{
			if(reader != null)
				try{ reader.close(); } catch(Exception e){}
		}	
	}// --------------------------------------------------------
	/**
	 * Read entries from the SDF file
	 * @see org.springframework.batch.item.ItemReader#read()
	 */
	@Override
	public SDFEntry read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException
	{
		if(this.sdFile == null)
			return null;
		
		 if( this.entries == null)
			 this.entries = this.sdFile.getEntries();
		
		if(entries == null)
			return null;
		
		if(iterator == null)
			iterator = this.entries.iterator();
		
		if(iterator == null)
			return null;
		
		if(!iterator.hasNext())
			return null;
		
		return iterator.next(); 
		
	}// --------------------------------------------------------
	
	
	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
	/**
	 * @return the host
	 */
	public String getHost()
	{
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(String host)
	{
		this.host = host;
	}
	/**
	 * @return the localFilePath
	 */
	public String getLocalFilePath()
	{
		return localFilePath;
	}
	/**
	 * @param localFilePath the localFilePath to set
	 */
	public void setLocalFilePath(String localFilePath)
	{
		this.localFilePath = localFilePath;
	}
	/**
	 * @return the remoteDirectory
	 */
	public String getRemoteDirectory()
	{
		return remoteDirectory;
	}
	/**
	 * @param remoteDirectory the remoteDirectory to set
	 */
	public void setRemoteDirectory(String remoteDirectory)
	{
		this.remoteDirectory = remoteDirectory;
	}
	/**
	 * @return the remoteFileName
	 */
	public String getRemoteFileName()
	{
		return remoteFileName;
	}
	/**
	 * @param remoteFileName the remoteFileName to set
	 */
	public void setRemoteFileName(String remoteFileName)
	{
		this.remoteFileName = remoteFileName;
	}


	private String username;
	private String password;
	private String host;
	private String localFilePath;
	private String remoteDirectory;
	private String remoteFileName;
	private Collection<SDFEntry> entries = null;
	private Iterator<SDFEntry> iterator = null;
	private SDFile sdFile = null;

}
