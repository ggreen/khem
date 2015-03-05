package khem.solutions.cheminformatics.joelib;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import joelib2.io.BasicIOType;
import joelib2.io.BasicIOTypeHolder;
import joelib2.io.BasicReader;
import joelib2.io.IOType;
import joelib2.io.MoleculeFileHelper;
import joelib2.io.MoleculeFileIO;
import joelib2.io.MoleculeIOException;
import joelib2.molecule.BasicConformerMolecule;
import joelib2.molecule.Molecule;
import joelib2.process.types.DistanceCalculation;
import joelib2.smarts.BasicSMARTSPatternMatcher;
import joelib2.smarts.SMARTSPatternMatcher;
import nyla.solutions.global.exception.SystemException;
import nyla.solutions.global.io.IO;

/**
 * <pre>
 * This object is POC for testing
 * the JOELib chemistry API.
 * 
 * See http://sourceforge.net/projects/joelib/
 * </pre>
 * @author Gregory Green
 *
 */
public class JOELibSearch
{
	/**
	 * Check if the query is a substructure of a molecule
	 * @param query the molecules in SMART/SMILES molecule format
	 * @param mol placeholder to molecule to use (MDL Molfile formate)
	 * @return true if the query matches the molecules
	 * @throws Exception
	 */
    public boolean isSubSearch(String queryMol,String mol)
    {
    	if(mol == null || mol.length() == 0) {
    		return false;
    	}
    	
    	if(queryMol == null || queryMol.length() == 0)
    	{
    		return false;
    	}
    	
        try
		{
			BasicSMARTSPatternMatcher basicsmartspatternmatcher = new BasicSMARTSPatternMatcher();
			basicsmartspatternmatcher.init(JOELib.toSMILES(queryMol));
			
			Molecule molecule = JOELib.toMolecule(mol);
			
			return basicsmartspatternmatcher.match(molecule);
		}
		catch (MoleculeIOException e)
		{
			throw new SystemException(e.getMessage(),e);
		}
		catch (IOException e)
		{
			throw new SystemException(e.getMessage(),e);
		}    	
    }
	/**
	 * Check if the query is a substructure of a molecule
	 * @param query the molecules in SMART/SMILES molecule format
	 * @param mol placeholder to molecule to use (MDL Molfile formate)
	 * @return true if the query matches the molecules
	 * @throws Exception
	 */
    public boolean delete(String query,String mol)
    throws Exception
    {
    	
    	IOType sdf = BasicIOTypeHolder.instance().getIOType("SDF");
    	
    	//ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte0);
    	
        BasicSMARTSPatternMatcher basicsmartspatternmatcher = new BasicSMARTSPatternMatcher();
        System.out.println("... generate atom expression...");
        basicsmartspatternmatcher.init(query);
        
        
        System.out.println(basicsmartspatternmatcher);
        BasicReader basicreader = null;
        
        basicreader = new BasicReader("runtime/input/query.mol", "SDF");
        

        BasicConformerMolecule basicconformermolecule = new BasicConformerMolecule(sdf, sdf);
        
        basicreader.readNext(basicconformermolecule);
        
    	//Another one to read the molecule is the following 
        //MoleculeFileIO moleculefileio = MoleculeFileHelper.getMolReader(new FileInputStream(new File("runtime/input/query.mol")), sdf);
    	
    	basicsmartspatternmatcher.init(mol);
    	
    	
    	return basicsmartspatternmatcher.match(basicconformermolecule);

    	
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     *  The main program for SMARTS search testing
     * 
     * @param  args  The command line arguments
     */
    public static void main(String[] args)
    {
    	JOELibSearch joeMolTest = new JOELibSearch();

        try
		{
			if (args.length != 2)
			{
			    joeMolTest.usage();
			    System.exit(0);
			}
			else
			{
			    //        String molURL = new String("joelib/test/test.mol");
			    joeMolTest.test(args[0], args[1],
			        BasicIOTypeHolder.instance().getIOType("SDF"),
			        BasicIOTypeHolder.instance().getIOType("SDF"));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
    }// --------------------------------------------------------
    /**
     *  A unit test for JUnit
     *
     * @param  molURL   Description of the Parameter
     * @param  smart    Description of the Parameter
     * @param  inType   Description of the Parameter
     * @param  outType  Description of the Parameter
     */
    public boolean isMatch(String identifier, String mol)
    throws Exception
    {
    	 MoleculeFileIO moleculefileio;

         BasicConformerMolecule basicconformermolecule;
         
         File outputFile = new File("runtime/output/mol.mol");
         
         IO.writeFile(outputFile, mol);

         int i;
         DistanceCalculation distancecalculation;

         
         Object obj = null;
         moleculefileio = null;

         
         BasicIOType comparisonInType = BasicIOTypeHolder.instance().getIOType("SDF");
         BasicIOType  targetInType= BasicIOTypeHolder.instance().getIOType("SDF");
             ByteArrayInputStream is = new ByteArrayInputStream(mol.getBytes());
             moleculefileio = MoleculeFileHelper.getMolReader(is, comparisonInType);
    
             moleculefileio.initReader(is);
        
         if(!moleculefileio.readable())
         {
            throw new RuntimeException((new StringBuilder()).append(comparisonInType.getRepresentation()).append(" is not readable.").toString());
         }
         
         basicconformermolecule = new BasicConformerMolecule(comparisonInType, comparisonInType);
         boolean flag = true;

         i = 0;
//         List<?> list = BasicResourceLoader.readLines(outputFile.toString());//descriptorNameFile);
//         if(list == null)
//         {
//             throw new RuntimeException("File with descriptor names of the output data could not be found.");
//         }
//         int j = list.size();
//         String as[] = new String[j];
//         for(int k = 0; k < j; k++)
//             as[k] = (String)list.get(k);
//
       distancecalculation = new DistanceCalculation();
          
         
         distancecalculation.init(targetInType, outputFile.toString(), (String[])null); //as);
        
        //"Start comparison calculation ...");
 
           basicconformermolecule.clear();
           
         boolean flag1 = moleculefileio.read(basicconformermolecule);

         
         if(basicconformermolecule.isEmpty())
             throw new RuntimeException("No molecule loaded. Continue...");

         
             distancecalculation.process(basicconformermolecule, null);
             if(identifier != null)
             {
                 joelib2.molecule.types.PairData pairdata = basicconformermolecule.getData(identifier, false);
                 if(pairdata != null)
                     System.out.print(pairdata);
                 else
                	 System.out.print("-1 ");
                 System.out.print(' ');
             }
             double ad[] = distancecalculation.getDistanceValues();
             for(int l = 0; l < ad.length; l++)
             {
            	 System.out.print(' ');
            	 System.out.print(ad[l]);
             }

             System.out.println();
  
             return false;
         
     }// --------------------------------------------------------

    /**
     *  A unit test for JUnit
     *
     * @param  molURL   Description of the Parameter
     * @param  smart    Description of the Parameter
     * @param  inType   Description of the Parameter
     * @param  outType  Description of the Parameter
     */
    public boolean test(String molPath, String smart, BasicIOType inType,
        BasicIOType outType)
    throws IOException
    {
        // get molecules from resource URL
        byte[] bytes = IO.readBinaryFile(molPath);
        		
        		/*BasicResourceLoader.instance()
                                          .getBytesFromResourceLocation(molURL);

        if (bytes == null)
        {
            logger.error("Molecule can't be loaded at \"" + molURL + "\".");
            System.exit(1);
        }

        
        */
        
        ByteArrayInputStream sReader = new ByteArrayInputStream(bytes);
        
        // initialize SMART pattern
        SMARTSPatternMatcher sp = new BasicSMARTSPatternMatcher();
       // System.out.println("... generate atom expression...");
        sp.init(smart);
        //System.out.println(sp);

        // create simple reader
        BasicReader reader = null;

        reader = new BasicReader(sReader, inType);


        // load molecules and handle test
        Molecule mol = new BasicConformerMolecule(inType, outType);
        System.out.println(" ... try to match: '" + smart + "' ...");


           try
		{
			if(!reader.readNext(mol))
				   throw new IOException("File not found.");
		}
		catch (MoleculeIOException e)
		{
			throw new IOException(e.getMessage(),e); 
		}
           
           
            // molecule for debugging purpose
           /*System.out.println(mol.toString(
                    BasicIOTypeHolder.instance().getIOType("SMILES")));

            for (int i = 0; i < mol.getBondsSize(); i++)
            {
                System.out.println("bond " + i + " up " +
                    mol.getBond(i).isUp() + " down " + mol.getBond(i).isDown());
            }*/

            //List maplist;
            //int[] itmp;

            return sp.match(mol);
            
            /*
            {
                //maplist = sp.getMatches();
                maplist = sp.getMatchesUnique();

                if (maplist.size() > 0)
                {
                    System.out.println("Found pattern in " + mol.getTitle());
                }

                //print out the results
                for (int ii = 0; ii < maplist.size(); ii++)
                {
                    itmp = (int[]) maplist.get(ii);

                    for (int j = 0; j < itmp.length; j++)
                    {
                        System.out.print(itmp[j] + " ");
                    }

                    // show detailed atom properties for atom SMARTS pattern
                    if (itmp.length == 1)
                    {
                        Atom atom = mol.getAtom(itmp[0]);
                        System.out.print(" ImplicitValence:" +
                            AtomImplicitValence.getImplicitValence(atom));
                        System.out.print(" Valence:" + atom.getValence());

                        int hatoms =
                            AtomImplicitValence.getImplicitValence(atom) -
                            atom.getValence();
                        System.out.print(" Hydrogens:" + hatoms);
                    }

                    System.out.println("");
                }
            }
            else
            {
                System.out.println("Pattern NOT found in " + mol.getTitle());
            }
        }*/
    }

    /**
     *  Description of the Method
     */
    public void usage()
    {
        StringBuffer sb = new StringBuffer();
        String programName = this.getClass().getName();

        sb.append("\nUsage is : ");
        sb.append("java -cp . ");
        sb.append(programName);
        sb.append(" <SDF file> <SMARTS pattern>");
        sb.append(
            "\n\nThis is version $Revision: 1.10 $ ($Date: 2005/02/17 16:48:29 $)\n");

        System.out.println(sb.toString());

        System.exit(0);
    }
}
