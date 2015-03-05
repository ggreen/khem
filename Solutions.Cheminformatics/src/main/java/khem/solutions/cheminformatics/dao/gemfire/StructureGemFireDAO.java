package khem.solutions.cheminformatics.dao.gemfire;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.gemfire.GemfireTemplate;

import com.gemstone.gemfire.cache.Region;

import nyla.solutions.global.patterns.iteration.Paging;
import nyla.solutions.global.patterns.iteration.PagingCollection;
import khem.solutions.cheminformatics.dao.StructureDAO;
import khem.solutions.cheminformatics.data.MaterialCriteria;
import khem.solutions.cheminformatics.data.Molecule;
import khem.solutions.cheminformatics.data.StructureKey;
import khem.solutions.cheminformatics.joelib.JOELibSearch;

public class StructureGemFireDAO implements StructureDAO
{


	public StructureGemFireDAO()
	{
		
		
	}// --------------------------------------------------------
	
	@Override
	public void dispose()
	{	
	}
	/**
	 * 
	 * @see khem.solutions.cheminformatics.dao.StructureDAO#findMoleculesByMol(khem.solutions.cheminformatics.data.MaterialCriteria)
	 */
	public Paging<Molecule> findMoleculesByMol(MaterialCriteria structureCriteria) 
	{
		
		JOELibSearch joeLibSearch = new JOELibSearch();
		Collection<Molecule> moleculeCollection = new ArrayList<Molecule>();
		
		String queryMol = structureCriteria.getMolString();
		
		for (Molecule molecule : moleculeRepository.findAll())
		{
			if(joeLibSearch.isSubSearch(queryMol, molecule.getMolString())){
				moleculeCollection.add(molecule);		
			}
			
		}
				
		
		PagingCollection<Molecule> pCollect = new PagingCollection<Molecule> 
						(moleculeCollection, structureCriteria.getPageCriteria());
		
		return pCollect;
	}// --------------------------------------------------------

	@Override
	public Paging<StructureKey> findStructKeysByMol(
			MaterialCriteria structureCriteria) 
	{
		return null;
		//return null;
	}

	@Override
	public Collection<StructureKey> findStructKeysByCasOrMolName(
			MaterialCriteria structureCriteria) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] findCasNumsByStructureKey(StructureKey structureKey)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<StructureKey> findStructKeysByMolName(
			MaterialCriteria materialCriteria) 
	{
		
		return null;
	}

	@Override
	public StructureKey findStructureKeyById(String structureKeyId)		
	{
		return null;
	}

	@Override
	public Collection<StructureKey> findFlexMatchStructureKeysByMol(
			String molString) 
	{
		return null;
	}

	@Override
	public Molecule findMoleculeByStructureKey(String structureKey)
	{
		return this.moleculeRepository.findMoleculeByStructureKey(structureKey);
	}// --------------------------------------------------------

	/**
	 * @param moleculeRepository the moleculeRepository to set
	 */
	public void setMoleculeRepository(MoleculeRepository moleculeRepository)
	{
		this.moleculeRepository = moleculeRepository;
	}
		
	
	/**
	 * @param moleculeTemplate the moleculeTemplate to set
	 */
	public void setMoleculeTemplate(GemfireTemplate moleculeTemplate)
	{
		this.moleculeTemplate = moleculeTemplate;
	}



	private GemfireTemplate moleculeTemplate;

	@Autowired
	private MoleculeRepository moleculeRepository;
	
}
