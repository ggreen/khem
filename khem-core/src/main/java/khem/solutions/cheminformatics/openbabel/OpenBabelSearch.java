package khem.solutions.cheminformatics.openbabel;
/*
import org.openbabel.OBConversion;
import org.openbabel.OBFormat;
import org.openbabel.OBMol;
import org.openbabel.OBSmartsPattern;
*/
/**
 * <pre>
 * C++ Example Code
 *
 * OBMol obMol;
OBBond *b1;
OBConversion obConversion;
OBFormat *inFormat;
OBSmartsPattern smarts;
smarts.Init("[#16D2r5][#6D3r5][#6D3r5][#16D2r5]");

string filename;
vector< vector <int> > maplist;
vector< vector <int> >::iterator matches;
double dihedral, bondLength;

for (int i = 1; i < argc; i++)
  {
    obMol.Clear();
    filename = argv[i];
    inFormat = obConversion.FormatFromExt(filename.c_str());
    obConversion.SetInFormat(inFormat);
    obConversion.ReadFile(&obMol, filename);

    if (smarts.Match(obMol))
      {
        dihedral = 0.0;
        bondLength = 0.0;
        maplist = smarts.GetUMapList();
        for (matches = maplist.begin(); matches != maplist.end(); matches++)
          {
            dihedral += fabs(obMol.GetTorsion((*matches)[0],
                                              (*matches)[1],
                                              (*matches)[2],
                                              (*matches)[3]));
            b1 = obMol.GetBond((*matches)[1], (*matches)[2]);
            bondLength += b1->GetLength();
          }
        cout << filename << ": Average Dihedral " << dihedral / maplist.size()
             << " Average Bond Length " << bondLength / maplist.size()
             << " over " << maplist.size() <<  " matches\n";
      }
  }
  </pre>
 */
public class OpenBabelSearch
{
	/**
	 * 
	 * @param query c1ccccc1
	 * @param molFilePath the mole file path
	 * @return boolean if is sub structure
	 * @throws Exception
	 */
    public boolean isSubSearch(String query, String molFilePath)
    throws Exception
    {
    	return false;
    	/*System.loadLibrary("openbabel_java");
    	
    	OBConversion conversion = new OBConversion();
    	conversion.SetInFormat(OBFormat.FindType("MOL"));
    	
    	OBMol molecule = new OBMol();
    	
    	conversion.ReadFile(molecule, molFilePath);
    	
    	OBSmartsPattern smarts = new OBSmartsPattern();
    	smarts.Init(query);
    	
    	return smarts.Match(molecule);
    	*/
    	
     }// --------------------------------------------------------
}
