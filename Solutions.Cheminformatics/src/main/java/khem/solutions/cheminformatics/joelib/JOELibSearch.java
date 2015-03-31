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

}
