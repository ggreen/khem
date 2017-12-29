package khem.solutions.cheminformatics.dao.gemfire;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.data.gemfire.GemfireTemplate;

import gedi.solutions.geode.client.GeodeClient;
import khem.solutions.cheminformatics.dao.StructureDAO;
import khem.solutions.cheminformatics.data.MaterialCriteria;
import khem.solutions.cheminformatics.data.Molecule;
import khem.solutions.cheminformatics.data.StructureKey;
import khem.solutions.cheminformatics.joelib.JOELibSearch;
import nyla.solutions.core.patterns.iteration.PageCriteria;
import nyla.solutions.core.patterns.iteration.Paging;
import nyla.solutions.core.patterns.iteration.PagingCollection;

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
		if(structureCriteria == null)
			return null;
		
		JOELibSearch joeLibSearch = new JOELibSearch();
		Collection<Molecule> moleculeCollection = new ArrayList<Molecule>();
		
		String queryMol = structureCriteria.getMolString();
		
		PageCriteria pageCriteria = structureCriteria.getPageCriteria();
		
		int pageSize = -1;
		
		if(pageCriteria != null)
		{
			pageCriteria.getSize();			
		}
		
		Collection<Object> keys = this.getMoleculeTemplate().getRegion().keySetOnServer();
		
		if(keys == null || keys.isEmpty())
			return null;
		
		Map<String,Molecule> map = this.getMoleculeTemplate().getAll(keys);
		Molecule molecule = null;
		for (Map.Entry<String, Molecule> entry : map.entrySet())
		{
			molecule = entry.getValue();
			
			if(joeLibSearch.isSubSearch(queryMol, molecule.getMolString())){
				moleculeCollection.add(molecule);		
				
				if(pageSize > 0 && moleculeCollection.size() >= pageSize)
				{
					break; //TODO: continue in backup
				}
			}
		}
		
		//TODO: use SDF pageable
		
		PagingCollection<Molecule> pCollect = new PagingCollection<Molecule> 
						(moleculeCollection, pageCriteria);
		
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
		return this.getMoleculeTemplate().get(structureKey);
	}// --------------------------------------------------------

	
	/**
	 * @param moleculeTemplate the moleculeTemplate to set
	 */
	public void setMoleculeTemplate(GemfireTemplate moleculeTemplate)
	{
		this.moleculeTemplate = moleculeTemplate;
	}



	/**
	 * @return the moleculeTemplate
	 */
	public GemfireTemplate getMoleculeTemplate()
	{
		if(this.moleculeTemplate == null)
			this.moleculeTemplate = new GemfireTemplate(GeodeClient.connect().getRegion("molecules"));
		
		return moleculeTemplate;
	}



	private GemfireTemplate moleculeTemplate = null;

	
}
