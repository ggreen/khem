package khem.solutions.cheminformatics.batch.spring;

import java.io.File;
import java.io.IOException;

import khem.solutions.cheminformatics.io.SDFEntry;
import khem.solutions.cheminformatics.io.SDFileReader;
import nyla.solutions.global.util.Config;

import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;


/**
 * Read SDF entries
 * 
 * Example Spring XML
 * 
 * 	<bean id="CHEMISTRY_ANNOTATIONS_READER" class="com.merck.mrl.asap.integration.batch.FileSdfReader">
		<property name="filePath" value="runtime/input/chem_annotations.sdf"/>
	</bean>
 * @author Gregory Green
 *
 */
public class FileSdfReader implements ItemReader<SDFEntry>
{

	
	@BeforeStep
	public void open()
	throws IOException
	{

		reader = new SDFileReader(new File(this.filePath));
		
		reader.open();
	}// --------------------------------------------------------
	/**
	 * Read entries from the SDF file
	 * @see org.springframework.batch.item.ItemReader#read()
	 */
	@Override
	public SDFEntry read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException
	{
		if(reader == null)
			this.open();
		
		return  reader.nextSDFEntry();
	}// --------------------------------------------------------


	/**
	 * @return the filePath
	 */
	public String getFilePath()
	{
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath)
	{
		this.filePath = Config.interpret(filePath);
	}


	private SDFileReader reader; 
	private String filePath;

}
