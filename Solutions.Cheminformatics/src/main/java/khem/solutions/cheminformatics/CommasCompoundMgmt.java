package khem.solutions.cheminformatics;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import khem.solutions.cheminformatics.dao.ChemistryDAO;
import khem.solutions.cheminformatics.dao.InventoryDAO;
import khem.solutions.cheminformatics.dao.StructureDAO;
import khem.solutions.cheminformatics.data.ChemicalAnnotation;
import khem.solutions.cheminformatics.data.Container;
import khem.solutions.cheminformatics.data.InventoryCriteria;
import khem.solutions.cheminformatics.data.KHEMCriteria;
import khem.solutions.cheminformatics.data.MaterialCriteria;
import khem.solutions.cheminformatics.data.MaterialCriteria.MaterialCriteriaType;
import khem.solutions.cheminformatics.data.Molecule;
import khem.solutions.cheminformatics.data.StructureKey;
import nyla.solutions.global.exception.RequiredException;
import nyla.solutions.global.exception.SystemException;
import nyla.solutions.global.patterns.command.commas.annotations.CMD;
import nyla.solutions.global.patterns.command.commas.annotations.COMMAS;
import nyla.solutions.global.patterns.command.commas.json.JsonAdvice;
import nyla.solutions.global.patterns.iteration.PageCriteria;
import nyla.solutions.global.patterns.iteration.Pagination;
import nyla.solutions.global.patterns.iteration.Paging;
import nyla.solutions.global.patterns.iteration.PagingCollection;
import nyla.solutions.global.patterns.servicefactory.ServiceFactory;
import nyla.solutions.global.patterns.workthread.ExecutorBoss;
import nyla.solutions.global.patterns.workthread.MemorizedQueue;
import nyla.solutions.global.util.Config;


/**
 * Abstract interface for chemical compound/structure integration points. 
 * @author Gregory Green
 *
 */
@COMMAS(name="CompoundMgmt")
public class CommasCompoundMgmt implements CompoundService
{
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
	 * 
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
	 * @see com.merck.mrl.asap.integration.services.CompoundService#findMolecules(com.merck.mrl.asap.integration.services.data.MaterialCriteria)
	 */
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
	 * @see com.merck.mrl.asap.integration.services.CompoundService#findMoleculeByKey(com.merck.mrl.asap.integration.services.data.StructureKey)
	 */
	@Override
	@CMD(advice=JsonAdvice.JSON_ADVICE_NAME)
	public Molecule findMoleculeByKey(StructureKey structureKey)
	{
		
		StructureDAO dao = null	;
		  try
		   {
			  try
			  {
				  dao = ServiceFactory.getInstance().create(structureKey.getSourceCode());
			  }
			  catch(NullPointerException nullException)
			  {
				  throw new RequiredException("structureKey.sourceCode");
			  }
				
			   String key = structureKey.getId();
			   
			   if (key == null || key.length() == 0)
				throw new RequiredException("key");
			   
			   return dao.findMoleculeByStructureKey(key);
		   }
		   finally
		   {
			   if(dao != null)
				   try{ dao.dispose(); } catch(Exception e){}
		   }
	}// --------------------------------------------------------
	/**
	 * @see com.merck.mrl.asap.integration.services.CompoundService#findMolecules(com.merck.mrl.asap.integration.services.data.MaterialCriteria)
	 */
	@Override
	@CMD(advice=JsonAdvice.JSON_ADVICE_NAME)
	public Paging<Molecule> findMolecules(KHEMCriteria asapCriteria)
	{  
		  //Find structures
		  String[] sources = null;
		
		  
		  MaterialCriteria materialCriteria = asapCriteria.getStructureCriteria();
		   
		  PageCriteria pageCriteria = materialCriteria.getPageCriteria();
				
		   if(pageCriteria == null)
			throw new RequiredException("pageCriteria");
				
			String molString = materialCriteria.getMolString();
				
			if (molString == null || molString.length() == 0)
					throw new RequiredException("molString");
				
			sources = materialCriteria.getSources();
				
			if(sources == null || sources.length == 0)
				throw new RequiredException("structureCriteria.sources");
		    
			
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
			ServiceFactory.getInstance().createForNames(sources,finders);
		
			return findPagedMolecules(materialCriteria, pageCriteria, finders);
	}// --------------------------------------------------------
	/**
	 * 
	 * @param materialCriteria
	 * @param pageCriteria
	 * @param finders
	 * @return
	 */
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
			molecules = (PagingCollection<Molecule>)collection;
			
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
	/**
	 * @see com.merck.mrl.asap.integration.services.CompoundService#findContainers(com.merck.mrl.asap.integration.services.data.ASAPCriteria)
	 */
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
		structureCriteria.setStructureKeys(structureKeySet);
		
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

	
	private final ExecutorBoss executorBoss;
	private static int threadCount = Config.getPropertyInteger(CommasCompoundMgmt.class,"threadCount",3);

}
