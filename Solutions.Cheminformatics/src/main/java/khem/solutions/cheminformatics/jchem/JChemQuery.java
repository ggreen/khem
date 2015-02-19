package khem.solutions.cheminformatics.jchem;


//import chemaxon.formats.MolFormatException;
//import chemaxon.sss.search.MolSearch;
//import chemaxon.sss.search.MolSearchOptions;
//import chemaxon.sss.search.SearchException;
//import chemaxon.sss.search.StandardizedMolSearch;
//import chemaxon.struc.Molecule;
//import chemaxon.util.MolHandler;

/**
 * Timing about 0.023 seconds per molecule
 * 
 * @author Gregory Green
 *
 */
public class JChemQuery
{
//	/**
//	 * 
//	 * @param queryMolString SMILES or mol string
//	 * @throws MolFormatException 
//	 */
//	public JChemQuery(String queryMolString) throws MolFormatException
//	{
//		molQueryHandler = new MolHandler(queryMolString);
//		
//		this.queryMol = molQueryHandler.getMolecule();
//		
//		// aromatization of query molecule 
//		queryMol.aromatize(); 
//	}// --------------------------------------------------------
//
//	/**
//	 * The searching of in-memory molecules (chemaxon.struc.Molecule objects) 
//	 * can be performed by the use of chemaxon.sss.search.MolSearch or 
//	 * chemaxon.sss.search.StandardizedMolSearch classes.
//	 * 
//	 * http://www.chemaxon.com/jchem/doc/dev/search/#searchmem
//	 */
//	/**
//	 *  MolHandler mh = new MolHandler("ClC1CCCCC1Br");
//            Molecule mol1 = mh.getMolecule();
//            echo("mol1 = ClC1CCCCC1Br");
//
//	 * @return
//	 * @throws MolFormatException 
//	 * @throws SearchException 
//	 */
//	public boolean isSubstanceStructutre(String structureText) 
//	throws MolFormatException, SearchException
//	{
//		
//		MolSearch ms = new StandardizedMolSearch(); // search object creation 
//		
//		ms.setQuery(queryMol); // assignment of query to search
//		
//		MolHandler mh = new MolHandler(structureText);
//				
//		Molecule targetMol = mh.getMolecule();
//		
//		targetMol.aromatize(); // aromatization of target molecule 
//		ms.setTarget(targetMol); // assignment of target molecule to search 
//		
//		MolSearchOptions mSearchOptions = new MolSearchOptions(chemaxon.sss.SearchConstants.SUBSTRUCTURE); 
//		ms.setSearchOptions(mSearchOptions); // search type: SUBSTRUCTURE, DUPLICATE, etc.
//		
//		return ms.isMatching();
//			
//		// set other search options. For more info, see MolSearchOptions and its superclass, SearchOptions 
//		// search operation
//	}// --------------------------------------------------------
//	public static int sss(Clob ctab, String mol)
//	{
//		try
//		{
//			if(new JChemQuery(mol).isSubstanceStructutre(DAO.toString(ctab)))
//				return 1;
//			else
//				return 0;
//		}
//		catch (Exception e)
//		{
//			Debugger.printWarn(e);
//			
//			return -1;
//		}
//		
//	}// --------------------------------------------------------
//	
//	private final MolHandler molQueryHandler; 
//	private final Molecule queryMol;
}
