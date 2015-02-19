package solutions.cheminformatics.openbabel;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * MacOSX and Linux
The following instructions describe how to compile and use these bindings on MacOSX and Linux:

openbabel.jar is included in the Open Babel source distribution in scripts/java. To compile a
 Java application that uses this (e.g. the example program shown above), use a command similar to the following:

javac Test.java -cp ../openbabel-2.3.1/scripts/java/openbabel.jar

To run the resulting Test.class on MacOSX or Linux you first need to compile the Java bindings as 
described in  the section Compile language bindings. 
This creates lib/libopenbabel_java.so in the build directory.

Add the location of openbabel.jar to the environment variable CLASSPATH, not forgetting to append the 
location of Test.class (typically �.�):

export CLASSPATH=/home/user/Tools/openbabel-2.3.1/scripts/java/openbabel.jar:.
Add the location of libopenbabel_java.so to the environment variable 
LD_LIBRARY_PATH. 
dditionally, if you have not installed Open Babel globally you should set BABEL_LIBDIR to the location of the Open Babel library and BABEL_DATADIR to the data directory.

Now, run the example application. The output should be as shown above.

 * @author Gregory Green
 *
 */
public class OpenBabelSearchTest
{

	@Test
	public void testIsSubSearch()
	throws Exception
	{
		System.out.println(System.getProperties());
		
		OpenBabelSearch search = new OpenBabelSearch();
		
		Assert.assertTrue(search.isSubSearch("c1ccccc1", "runtime/input/matchTarget.mol"));
		
		
	}// --------------------------------------------------------

}
