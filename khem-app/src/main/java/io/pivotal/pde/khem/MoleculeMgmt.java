package io.pivotal.pde.khem;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import javax.annotation.Resource;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;

import io.pivotal.pde.khem.data.KHEMCriteria;
import io.pivotal.pde.khem.data.MaterialCriteria;
import io.pivotal.pde.khem.data.MaterialCriteria.MaterialCriteriaType;
import joelib2.io.MoleculeIOException;
import io.pivotal.pde.khem.data.Molecule;
import khem.solutions.cheminformatics.joelib.JOELib;

/**
 * Handles data access for molecule information
 * 
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
		
		//calcute weight
	 try
	 {
		 joelib2.molecule.Molecule joeMol = JOELib.toMolecule(molString);
		 double weight = Math.round(JOELib.toWeight(joeMol)*100)/100D;
		 molecule.setWeight(Double.valueOf(weight));
			
	 }
	 catch(MoleculeIOException | IOException e)
	 {
		 e.printStackTrace();
	 }
		
		String id = generateId(sourceCode,name);
		this.moleculesRegion.put(id,molecule);
		
		return molecule;
	}//------------------------------------------------
	/**
	 * Find by various criteria
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
		
		Collection<Molecule> moles = null;
		switch(materialCriteriaType)
		{
		  case BySMILES: moles = querierService.query("select * from /molecules where canonicalSMILES = '"+structureCriteria.getSmiles()+"'");
			break;
	
		  case BySourceAndName: 
			  String source = structureCriteria.getSource();
			  if (source == null || source.length() == 0)
				throw new IllegalArgumentException("source is required");
			  
			  String name = structureCriteria.getName();
			  if (name == null || name.length() == 0)
				throw new IllegalArgumentException("name is required");
			  
			  Molecule molecule = this.moleculesRegion.get(generateId(source, name));
			  if(molecule == null)
				  return null;
			  
			  moles = Collections.singleton(molecule);
	
			  break;
		  case ByWEIGHT:
			  Double weight = structureCriteria.getWeight();
			  
			  if (weight == null)
				throw new IllegalArgumentException("weight is required");
			  
			  moles = this.querierService.query("select * from /molecules where weight = "+weight);
			break;
		  case ByFORMULA:
			  String formula = structureCriteria.getFormula();
			  if (formula == null || formula.length() == 0)
				throw new IllegalArgumentException("formula is required");
			  
			  moles = this.querierService.query("select * from /molecules where formula = '"+formula+"'");
	       break;
	       
		  default: throw new IllegalArgumentException("Unsupported search type:"+materialCriteriaType);
		}
		
		return moles;
	}//------------------------------------------------

	private String generateId(String sourceCode, String name)
	{
		return new StringBuilder().append(sourceCode)
		.append("|").append(name).toString();
	}
	
	@Resource
	Region<String,Molecule> moleculesRegion;
	
	@Autowired
	QuerierService querierService;
}
