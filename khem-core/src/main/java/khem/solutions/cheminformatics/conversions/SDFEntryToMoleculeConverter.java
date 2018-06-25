package khem.solutions.cheminformatics.conversions;

import java.io.IOException;

import joelib2.io.MoleculeIOException;
import khem.solutions.cheminformatics.data.Molecule;
import khem.solutions.cheminformatics.io.SDFEntry;
import khem.solutions.cheminformatics.joelib.JOELib;
import nyla.solutions.core.patterns.conversion.Converter;

public class SDFEntryToMoleculeConverter implements Converter<SDFEntry, Molecule>
{

	@Override
	public Molecule convert(SDFEntry sdfEntry)
	{
		try
		{
			Molecule molecule = new Molecule();
			
			
			molecule.setStructureKey(sdfEntry.retrieveParamValue("EMOL_VERSION_ID"));
			
			if(molecule.getStructureKey() == null || molecule.getStructureKey().length() ==0)
				return null;
			
			molecule.setMolString(sdfEntry.getMolString());
			
			joelib2.molecule.Molecule joeMolecule = JOELib.toMolecule(sdfEntry.getMolString());
			
			molecule.setFormula(String.valueOf(joeMolecule.getAtoms()));
			
			String name = joeMolecule.getTitle();
			if(name ==null || name.length() == 0)
				name = molecule.getStructureKey();

			molecule.setName(name);
			molecule.setSourceCode("eMoleculeFunction");
			molecule.setWeight(JOELib.toWeight(joeMolecule));
			
			molecule.setCanonicalSMILES(JOELib.toSMILES(joeMolecule));
			return molecule;
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		catch (MoleculeIOException e)
		{
			throw new RuntimeException(e);
		}
		
		
	}
	
	

}
