package io.pivotal.pde.khem;

import java.util.Collection;

import javax.annotation.Resource;
import org.apache.geode.cache.Region;

import gedi.solutions.geode.io.Querier;
import io.pivotal.pde.khem.data.KHEMCriteria;
import io.pivotal.pde.khem.data.MaterialCriteria;
import io.pivotal.pde.khem.data.MaterialCriteria.MaterialCriteriaType;
import io.pivotal.pde.khem.data.Molecule;

/**
 * @author Gregory Green
 *
 */
public class MoleculeMgmt implements MoleculeService
{

	/* (non-Javadoc)
	 * @see io.pivotal.pde.khem.MoleculeService#saveMolecule(io.pivotal.pde.khem.data.Molecule)
	 */
	@Override
	public Molecule saveMolecule(Molecule molecule)
	{
		if(molecule == null)
			return null;
			
		String sourceCode = molecule.getSourceCode();
		if (sourceCode == null || sourceCode.length() == 0)
			throw new IllegalArgumentException("sourceCode is required");

		String name = molecule.getName();
		if (name == null || name.length() == 0)
			throw new IllegalArgumentException("name is required");
		
		String smiles = molecule.getCanonicalSMILES();
		if (smiles == null || smiles.length() == 0)
			throw new IllegalArgumentException("smiles is required");
		
		String molString = molecule.getMolString();
		if (molString == null || molString.length() == 0)
			throw new IllegalArgumentException("molString is required");
		
		
		String id = generateId(sourceCode,name);
		this.moleculesRegion.put(id,molecule);
		
		return molecule;
	}//------------------------------------------------
	/**
	 * Find by variaous criteria
	 * @param criteria the search criteria
	 * @return the molecule results
	 */
	@Override
	public Collection<Molecule> findMolecules(KHEMCriteria criteria)
	{
		if(criteria == null)
			return null;
		
		MaterialCriteria structureCriteria = criteria.getStructureCriteria();
		
		if (structureCriteria == null)
			throw new IllegalArgumentException("structureCriteria is required");
		
		MaterialCriteriaType materialCriteriaType = structureCriteria.getSearchType();
		if (materialCriteriaType == null)
			throw new IllegalArgumentException("materialCriteriaType is required");
		
		switch(materialCriteriaType)
		{
		  case BySMILES: Collection<Molecule> moles = Querier.query("select * from /molecules where canonicalSMILES = '"+structureCriteria.getSmiles()+"'");
			return moles;
			default: throw new IllegalArgumentException("Unsupported search type:"+materialCriteriaType);
		}
				
	}//------------------------------------------------

	private String generateId(String sourceCode, String name)
	{
		return new StringBuilder().append(sourceCode)
		.append("|").append(name).toString();
	}
	
	@Resource
	Region<String,Molecule> moleculesRegion;
}
