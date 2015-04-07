package khem.solutions.cheminformatics.batch;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import khem.solutions.cheminformatics.io.SDFileReader;

@Component
public class SdReaderSource
{
	public SDFileReader openFile(File file)
	throws IOException
	{
		
	
		
		SDFileReader reader = new SDFileReader(file);
		
		reader.open();
		
		System.out.println(this.getClass().getName()+" opened file:"+file);
		
		return reader;
		
		
	}

}
