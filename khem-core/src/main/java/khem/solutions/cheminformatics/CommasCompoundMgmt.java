package khem.solutions.cheminformatics;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import khem.solutions.cheminformatics.dao.ChemistryDAO;
import khem.solutions.cheminformatics.dao.InventoryDAO;
import khem.solutions.cheminformatics.dao.StructureDAO;
import khem.solutions.cheminformatics.dao.gemfire.StructureGemFireDAO;
import khem.solutions.cheminformatics.data.ChemicalAnnotation;
import khem.solutions.cheminformatics.data.Container;
import khem.solutions.cheminformatics.data.InventoryCriteria;
import khem.solutions.cheminformatics.data.KHEMCriteria;
import khem.solutions.cheminformatics.data.MaterialCriteria;
import khem.solutions.cheminformatics.data.MaterialCriteria.MaterialCriteriaType;
import khem.solutions.cheminformatics.data.Molecule;
import khem.solutions.cheminformatics.data.StructureKey;
import khem.solutions.cheminformatics.exception.KhemException;
import khem.solutions.cheminformatics.finders.StructureFinder;
import nyla.solutions.commas.annotations.CMD;
import nyla.solutions.commas.json.JsonAdvice;
import nyla.solutions.core.exception.RequiredException;
import nyla.solutions.core.exception.SystemException;
import nyla.solutions.core.operations.ClassPath;
import nyla.solutions.core.patterns.iteration.PageCriteria;
import nyla.solutions.core.patterns.iteration.Pagination;
import nyla.solutions.core.patterns.iteration.Paging;
import nyla.solutions.core.patterns.iteration.PagingCollection;
import nyla.solutions.core.patterns.servicefactory.ServiceFactory;
import nyla.solutions.core.patterns.workthread.ExecutorBoss;
import nyla.solutions.core.patterns.workthread.MemorizedQueue;
import nyla.solutions.core.util.Config;



/**
 * Abstract interface for chemical compound/structure integration points. 
 * @author Gregory Green
 *
 */
public class CommasCompoundMgmt implements CompoundService
{
	private StructureDAO dao = ServiceFactory.getInstance().create(StructureDAO.class);
	private final ExecutorBoss executorBoss;
	private static int threadCount = Config.getPropertyInteger(CommasCompoundMgmt.class,"threadCount",3);

	/**
	 * Constructor with initialization of the executor boss
	 */
	public CommasCompoundMgmt()
	{
		this.executorBoss =   new ExecutorBoss(threadCount);
	}// --------------------------------------------------------
	
	/**
	 * Retrieve list of chemical annotations
	 * @param criteria the annotation code
	 * @return all is criteria is or first annotation code is null
	 */
	@Override
	@CMD(advice=JsonAdvice.JSON_ADVICE_NAME)
	public Collection<ChemicalAnnotation> listChemicalAnnotions(
			MaterialCriteria criteria)
	{
		ChemistryDAO dao = null;
		try
		{
			dao = ServiceFactory.getInstance().create(ChemistryDAO.class);
			
			
			String annotationCode = null;
			
			if(criteria != null)
			{
				ChemicalAnnotation[] annotations = criteria.getChemicalAnnotations();
				
				if(annotations != null && annotations.length > 0)
				{
					annotationCode = annotations[0].getCode();
					
					//cleanup input
					if(annotationCode!= null && annotationCode.trim().length() == 0)
						annotationCode = null;
				}
				
				
							
			}
			return dao.selectChemicalAnnotationsByCode(annotationCode);
	    }
		catch(SQLException e)
		{
			throw new SystemException(e);
		}
	    finally
		{
			   if(dao != null)
				   try{ dao.dispose(); } catch(Exception e){}
		}
		
	}// --------------------------------------------------------
	/**
	 * @param height the height
	 * @param width the width
	 * @param molString the molecule String
	 * @return the molecule
	 */
	public byte[] toImageFromMolString(String molString, int width,int height)
	{
		ChemistryDAO dao = null;
		try
		{
			dao = ServiceFactory.getInstance().create(ChemistryDAO.class);
			
			return dao.selectMolImageByMolString(molString,width,height);
	    }
	    finally
		{
			   if(dao != null)
				   try{ dao.dispose(); } catch(Exception e){}
		}
	}// -------------------------------------------------------- 
	public Paging<StructureKey> findStructureKeys(MaterialCriteria structureCriteria)
	{
		  //Find structures
		  String[] sources = null;
			    
		  PageCriteria pageCriteria = structureCriteria.getPageCriteria();
				
		   if(pageCriteria == null)
			throw new RequiredException("pageCriteria");
				
			String molString = structureCriteria.getMolString();
				
			if (molString == null || molString.length() == 0)
					throw new RequiredException("molString");
				
			sources = structureCriteria.getSources();
				
			if(sources == null || sources.length == 0)
					throw new RequiredException("structureCriteria.sources");
				
			//create finder
			MemorizedQueue queue = new MemorizedQueue();
				
			StructureFinder[] finders  = new StructureFinder[sources.length];
			ServiceFactory.getInstance().createForNames(sources,finders);
				
			//Initialize finders and queue: 
			for (int i = 0; i < finders.length; i++)
			{
					finders[i].setCriteria(structureCriteria);
					queue.add(finders[i]);
			}
				
			executorBoss.startWorking(queue); //TODO: accept array Runnable
				
			//get structure keys
			//Reduce
			Set<StructureKey> structureKeySet = new HashSet<StructureKey>();
			PagingCollection<StructureKey> structureKeys;
			
			boolean isLast = true;
			boolean isFirst = true;
			
			for (int i = 0; i < finders.length; i++)
			{
				structureKeys = (PagingCollection<StructureKey>)finders[i].getResults();
				
				if(structureKeys == null || structureKeys.isEmpty())
					continue;
				
				if(!structureKeys.isLast())
					isLast = false;
				
				if(!structureKeys.isFirst())
					isFirst = false;
				
				structureKeySet.addAll(structureKeys);
			}
			
			if(structureKeySet.isEmpty())
				return null;
				
			PagingCollection<StructureKey> paging = new PagingCollection<StructureKey>(structureKeySet, pageCriteria);
			paging.setFirst(isFirst);
			paging.setLast(isLast);
			return paging;
	}// --------------------------------------------------------
	/**
	 * Retrieve page structure keys
	 * @param pageCriteria the page criteria
	 * @return the molecule paging information 
	 */
	public Paging<StructureKey> retrieveStructureKeysPage(PageCriteria pageCriteria)
	{
		 Pagination pagination = Pagination.getPagination(pageCriteria);
		 
		 if(pagination == null)
			 return null;
		 
		 return pagination.getPaging(pageCriteria,StructureKey.class);
	
	}// ---------------------------------------------------
	/**
	 * Find a molecule
	 * @param structureKey the structure key
	 * @return the molecule
	 */
	@Override
	@CMD(advice=JsonAdvice.JSON_ADVICE_NAME)
	public Molecule findMoleculeByKey(StructureKey structureKey)
	{
		
		try(StructureDAO dao = new StructureGemFireDAO())
		{		
			   String key = structureKey.getId();
			   
			   if (key == null || key.length() == 0)
				throw new RequiredException("key");
			   
			   return dao.findMoleculeByStructureKey(key);
		 }
		 catch(Exception e)
		{
			 throw new KhemException(e);
		}

	}// --------------------------------------------------------
	
	@Override
	@CMD(advice=JsonAdvice.JSON_ADVICE_NAME)
	public Paging<Molecule> findMolecules(KHEMCriteria khemCriteria)
	{  
		  //Find structures
		  String[] sources = null;
		
		  
		  MaterialCriteria materialCriteria = khemCriteria.getStructureCriteria();
		  if (materialCriteria == null)
			throw new IllegalArgumentException("materialCriteria is required");
		  
		  PageCriteria pageCriteria = materialCriteria.getPageCriteria();
			
		   if(pageCriteria == null)
			throw new RequiredException("KHEMCriteria.materialCriteria.pageCriteria");
		  
		  if(materialCriteria.getSearchType().equals(MaterialCriteria.MaterialCriteriaType.BySMILES))
			  return this.findMoleculesBySMILE(materialCriteria.getSmiles(),pageCriteria);
		  
		  
				
			String molString = materialCriteria.getMolString();
				
			if (molString == null || molString.length() == 0)
					throw new RequiredException("molString");
				
			sources = materialCriteria.getSources();
				
			if(sources == null || sources.length == 0)
				throw new RequiredException("KHEMCriteria.materialCriteria.structureCriteria.sources");
		    
			
			//clear previous page data
			Pagination pagination = Pagination.getPagination(pageCriteria);
			if(pagination != null)
				pagination.clear();
			
			boolean hasCasNumbers =  materialCriteria.hasCasNumbers();
			
			//Processing Inventory Criteria if it exists (i.e. by Cas and or Location Type and or SOURCE etc.
			InventoryCriteria inventoryCriteria = materialCriteria.getInventoryCriteria();
			
			if(inventoryCriteria != null)
			{	
				boolean haslocTypes = inventoryCriteria.hasLocationTypes();
				boolean hasinvSources = inventoryCriteria.hasSources();
				
				if(hasCasNumbers && haslocTypes && hasinvSources)
					materialCriteria.setSearchType(MaterialCriteriaType.ByCasAndLocTypeAndInvSource);
				else if(haslocTypes && hasinvSources)
					materialCriteria.setSearchType(MaterialCriteriaType.ByLocTypeAndInvSource);				
			}
			
			//create finder
				
			StructureFinder[] finders  = new StructureFinder[sources.length];
			
			//khem.solutions.cheminformatics.finders
			for (int i = 0; i < finders.length; i++)
			{
				finders[i] = ClassPath.newInstance("khem.solutions.cheminformatics.finders."+sources[i]);
			}
			//ServiceFactory.getInstance().createForNames(sources,finders);
		
			return findPagedMolecules(materialCriteria, pageCriteria, finders);
	}// --------------------------------------------------------
	/**
	 * 
	 * @param materialCriteria the material criteria
	 * @param pageCriteria the page criteris
	 * @param finders teh finders
	 * @return the paging of molecule results
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Paging<Molecule> findPagedMolecules(MaterialCriteria materialCriteria,
			PageCriteria pageCriteria, StructureFinder[] finders)
	{
		for (int i = 0; i < finders.length; i++)
		{
			finders[i].setCriteria(materialCriteria);
		}
		
		
		Collection<Collection<Molecule>> moleculeCollection = executorBoss.startWorking(finders);
			

		//get structure keys
		//Reduce
		Set<Molecule> moleculeSet = new TreeSet<Molecule>();
		PagingCollection<Molecule> molecules;
		
		boolean isLast = true;
		boolean isFirst = true;
		
		for (Collection<Molecule> collection : moleculeCollection)
		{
			
			if(collection instanceof ArrayList)
			{
				ArrayList<Object> al = (ArrayList)collection;
				
				collection = (Collection<Molecule>)al.iterator().next();
			}
			
	
			
			 molecules = (PagingCollection<Molecule>) collection;
			 
			if(molecules == null || molecules.isEmpty())
					continue;
			
			if(!molecules.isLast())
				isLast = false;
			
			if(!molecules.isFirst())
				isFirst = false;
			
			moleculeSet.addAll(molecules);
		}
		
		if(moleculeSet.isEmpty())
			return null;
			
		PagingCollection<Molecule> paging = new PagingCollection<Molecule>(moleculeSet, pageCriteria);
		paging.setFirst(isFirst);
		paging.setLast(isLast);
		return paging;
	}// --------------------------------------------------------
	@Override
	@CMD(advice=JsonAdvice.JSON_ADVICE_NAME)
	public Collection<Container> findContainers(KHEMCriteria containerCriteria)
	{  
		
	    //Find structures
		String[] sources = null;
		
	    MaterialCriteria structureCriteria = containerCriteria.getStructureCriteria();
		if (structureCriteria == null)
			throw new RequiredException("structureCriteria");
	    
		PageCriteria containerPageCriteria = containerCriteria.getPageCriteria();
		
		
		
		
		if(containerPageCriteria == null)
			throw new RequiredException("containerCriteria's pageCriteria");
		
		String molString = structureCriteria.getMolString();
		
		if (molString == null || molString.length() == 0)
			throw new RequiredException("molString");
		
		sources = structureCriteria.getSources();
		
		
		PageCriteria pageCriteria = structureCriteria.getPageCriteria();
		if (pageCriteria == null)
			throw new RequiredException("structureCriteria's pageCriteria");
		
		if(sources == null || sources.length == 0)
			throw new RequiredException("structureCriteria.sources");
		
		//create finder
		MemorizedQueue queue = new MemorizedQueue();
		
		StructureFinder[] finders  = new StructureFinder[sources.length];
		ServiceFactory.getInstance().createForNames(sources,finders);
		
		
		//Initialize finders and queue: 
		for (int i = 0; i < finders.length; i++)
		{
			finders[i].setCriteria(structureCriteria);
			queue.add(finders[i]);
		}
		
		executorBoss.startWorking(queue); //TODO: accept array Runnable
		
		//get structure keys
		//Reduce
		Set<StructureKey> structureKeySet = new HashSet<StructureKey>();
		Collection<StructureKey> structureKeys;
		for (int i = 0; i < finders.length; i++)
		{
			structureKeys = finders[i].getResults();
			
			if(structureKeys != null)
				structureKeySet.addAll(structureKeys);
			
		}
		
		//Add structure key to material criteria
		structureCriteria.assignStructureKeys(structureKeySet);
		
		//find material in use systems
		
		InventoryDAO dao = null;
		try
		{
			dao =  ServiceFactory.getInstance().create(InventoryDAO.class);
			
			Collection<Container> containers = dao.findByCriteria(containerCriteria);
			
			if(containers == null || containers.isEmpty())
				return null; //empty list
			
			return containers;
		}
		finally
		{
			if(dao != null)
				try{ dao.dispose(); } catch(Exception e){}
		}
	}// --------------------------------------------------------
	public Paging<Molecule> findMoleculesBySMILE(String smile,PageCriteria pageCriteria)
	{
		Collection<Molecule> collection = this.dao.findMoleculesBySMILE(smile);
		
		if(collection == null || collection.isEmpty())
			return null;
		
		return new PagingCollection<>(collection, pageCriteria);
	}//------------------------------------------------
	public Molecule saveMolecule(Molecule molecule)
	{
		dao.saveMolecule(molecule);
		
		return molecule;
	}

	public int removeMoleculeBySMILE(String canonicalSMILES)
	{
		return this.dao.deleteMoleculesBySMILE(canonicalSMILES);
	}
	


}
