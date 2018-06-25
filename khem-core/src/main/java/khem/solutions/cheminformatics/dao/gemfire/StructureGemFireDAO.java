package khem.solutions.cheminformatics.dao.gemfire;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.Execution;
import org.apache.geode.cache.execute.FunctionService;

import gedi.solutions.geode.RegionTemplate;
import gedi.solutions.geode.client.GeodeClient;
import gedi.solutions.geode.io.GemFireIO;
import gedi.solutions.geode.io.Querier;
import khem.solutions.cheminformatics.dao.StructureDAO;
import khem.solutions.cheminformatics.data.MaterialCriteria;
import khem.solutions.cheminformatics.data.Molecule;
import khem.solutions.cheminformatics.data.StructureKey;
import nyla.solutions.core.patterns.iteration.PageCriteria;
import nyla.solutions.core.patterns.iteration.Paging;
import nyla.solutions.core.patterns.iteration.PagingCollection;

public class StructureGemFireDAO implements StructureDAO
{

	private RegionTemplate<String,Molecule> moleculeTemplate = null;

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
	@SuppressWarnings("unchecked")
	public Paging<Molecule> findMoleculesByMol(MaterialCriteria structureCriteria) 
	{
		if(structureCriteria == null)
			return null;
		
		Collection<Molecule> moleculeCollection = new ArrayList<Molecule>();
		
		String queryMol = structureCriteria.getMolString();
		
		PageCriteria pageCriteria = structureCriteria.getPageCriteria();
		
		int pageSize = -1;
		
		if(pageCriteria != null)
		{
			pageCriteria.getSize();			
		}
		
		Region<String,Molecule> region = this.getMoleculeTemplate().getRegion();
		
		
		//Map<String,Molecule> map = this.getMoleculeTemplate().getAll(keys);
		
		
		String [] args = {queryMol };
		Execution<String[], ?, ?> exe = FunctionService.onRegion(region)
			.setArguments(args);
		
		try
		{
			Collection<String> keys = GemFireIO.exeWithResults(exe, "SssJoelibFunction");
			
			if(keys == null || keys.isEmpty())
				return null;
			
			Molecule molecule = null;
			
			for (String key : keys)
			{
				molecule = region.get(key);
				
				moleculeCollection.add(molecule);		
					
				if(pageSize > 0 && moleculeCollection.size() >= pageSize)
				{
						break; //TODO: continue in backup
				}
			}
			
			//TODO: use SDF pageable
			
			PagingCollection<Molecule> pCollect = new PagingCollection<Molecule> 
							(moleculeCollection, pageCriteria);
			
			return pCollect;
		}
		catch(RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
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
	 * @return the moleculeTemplate
	 */
	public RegionTemplate<String,Molecule> getMoleculeTemplate()
	{
		if(moleculeTemplate == null)
			moleculeTemplate = new RegionTemplate<String,Molecule>(GeodeClient.connect().getRegion("molecules"));
		
		return moleculeTemplate;
	}
	
	@Override
	public Collection<Molecule> findMoleculesBySMILE(String smile)
	{
		Collection<Molecule> moles = query("select * from /molecules where canonicalSMILES = '"+smile+"'");
		return moles;
	}//------------------------------------------------

	private <T> Collection<T> query(String query)
	{
		GeodeClient.connect();
		return Querier.query(query);
	}
	/**
	 * @param moleculeTemplate the moleculeTemplate to set
	 */
	public void setMoleculeTemplate(RegionTemplate<String,Molecule> moleculeTemplate)
	{
		this.moleculeTemplate = moleculeTemplate;
	}//------------------------------------------------
	@Override
	public void saveMolecule(Molecule molecule)
	{
		if( molecule == null)
			return;
		
		String id = molecule.getId();
		
		if (id == null || id.length() == 0)
			throw new IllegalArgumentException("id is required");
		
		this.getMoleculeTemplate().put(molecule.getId(), molecule);
	}


	@Override
	public int deleteMoleculesBySMILE(String canonicalSMILES)
	{
		Collection<String> keys = Querier.query("select key from /molecules.entries where value.canonicalSMILES = '"+canonicalSMILES+"'");
		
		if(keys == null || keys.isEmpty())
			return 0;
		
		this.getMoleculeTemplate().removeAll(keys);
		return keys.size();
	}








}
