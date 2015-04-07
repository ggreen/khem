package khem.solutions.cheminformatics.batch.spring;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import khem.solutions.cheminformatics.CompoundService;
import khem.solutions.cheminformatics.dao.ChemistryDAO;
import khem.solutions.cheminformatics.data.ChemicalAnnotation;
import khem.solutions.cheminformatics.data.MaterialCriteria;
import khem.solutions.cheminformatics.data.StructureKey;
import khem.solutions.cheminformatics.io.SDFEntry;
import nyla.solutions.global.patterns.Disposable;
import nyla.solutions.global.patterns.iteration.PageCriteria;
import nyla.solutions.global.patterns.iteration.Paging;
import nyla.solutions.global.patterns.servicefactory.ServiceFactory;
import nyla.solutions.global.util.Config;

import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;

/**
 * Save chemical annotations and their associated structure keys
 * @author Gregory Green
 *
 */
public class ChemicalAnnotationWriter implements ItemWriter<SDFEntry>, Disposable
{
	/**
	 * Open a connection to the database
	 * @throws SQLException
	 */
	@BeforeStep
	public void open()
	throws SQLException
	{
		
		this.chemistryDAO = ServiceFactory.getInstance().create(ChemistryDAO.class);
	}// --------------------------------------------------------
	
	/**
	 * Close the SQL connection
	 * @see solutions.global.patterns.Disposable#dispose()
	 */
	@Override
	@AfterStep
	public void dispose()
	{	
		if(this.chemistryDAO != null)
		{
			try{this.chemistryDAO.dispose(); } catch(Exception e){}
		}
		
	}// --------------------------------------------------------
	
	

	/***
	 * Insert records into the 
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	@Override
	public void write(List<? extends SDFEntry> entries) throws Exception
	{
		//check mol exists 
		
		
		SDFEntry sdfEntry;
		String molString;
		ChemicalAnnotation chemicalAnnotation;
		
		
		if(this.chemistryDAO == null)
			this.open();
		
		String code;
		String name;
		String operation;
		
		for (Iterator<? extends SDFEntry> iterator = entries.iterator(); iterator.hasNext();)
		{
			sdfEntry = (SDFEntry) iterator.next();
			
			molString = sdfEntry.getMolString();
			
		   code = sdfEntry.retrieveParamValue(this.codeParamName);
		   name = sdfEntry.retrieveParamValue(this.annotationNameParamName);
		   operation = sdfEntry.retrieveParamValue(this.operationParamName);

		   if(operation == null || operation.length() == 0)
			   operation = this.defaultOperation;
				
			// MOL annotation exists?
			chemicalAnnotation = new ChemicalAnnotation();
			chemicalAnnotation.setMolString(molString);
			chemicalAnnotation.setCode(code);
			chemicalAnnotation.setName(name);
			chemicalAnnotation.setOperation(operation);
			
			//select annotation 
			this.chemistryDAO.insertAnnotation(chemicalAnnotation);
			
			
			if(!this.saveKeys)
				continue; //skip to next record
			
			//select structure keys
			MaterialCriteria structureCriteria = new MaterialCriteria();
			PageCriteria pageCriteria = new PageCriteria();
			pageCriteria.setSize(this.pageSize);
			structureCriteria.setMolString(molString);
			structureCriteria.setPageCriteria(pageCriteria);
			
			Paging<StructureKey> structureKeys = this.compoundService.findStructureKeys(structureCriteria);

			while(structureKeys != null && !structureKeys.isEmpty())
			{
	
				this.chemistryDAO.saveAnnotationKeys(chemicalAnnotation,structureKeys,this.deletePreviousAnnotationKeys);
				
				if(structureKeys.isLast())
					break;
				
				structureCriteria.incrementPage();
				
				structureKeys = this.compoundService.findStructureKeys(structureCriteria);
			}
		}		
	}// --------------------------------------------------------
	
	
	/**
	 * @return the annotationNameParamName
	 */
	public String getAnnotationNameParamName()
	{
		return annotationNameParamName;
	}

	/**
	 * @param annotationNameParamName the annotationNameParamName to set
	 */
	public void setAnnotationNameParamName(String annotationNameParamName)
	{
		this.annotationNameParamName = annotationNameParamName;
	}

	/**
	 * @return the codeParamName
	 */
	public String getCodeParamName()
	{
		return codeParamName;
	}

	/**
	 * @param codeParamName the codeParamName to set
	 */
	public void setCodeParamName(String codeParamName)
	{
		this.codeParamName = codeParamName;
	}

	/**
	 * @return the dataSource
	 */
	public DataSource getDataSource()
	{
		return dataSource;
	}

	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	
	/**
	 * @return the pageSize
	 */
	public int getPageSize()
	{
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	/**
	 * @return the compoundService
	 */
	public CompoundService getCompoundService()
	{
		return compoundService;
	}

	/**
	 * @param compoundService the compoundService to set
	 */
	public void setCompoundService(CompoundService compoundService)
	{
		this.compoundService = compoundService;
	}


	/**
	 * @return the operationParamName
	 */
	public String getOperationParamName()
	{
		return operationParamName;
	}

	/**
	 * @param operationParamName the operationParamName to set
	 */
	public void setOperationParamName(String operationParamName)
	{
		this.operationParamName = operationParamName;
	}


	/**
	 * @return the defaultOperation
	 */
	public String getDefaultOperation()
	{
		return defaultOperation;
	}

	/**
	 * @param defaultOperation the defaultOperation to set
	 */
	public void setDefaultOperation(String defaultOperation)
	{
		this.defaultOperation = defaultOperation;
	}
	
	/**
	 * @return the saveKeys
	 */
	public boolean isSaveKeys()
	{
		return saveKeys;
	}

	/**
	 * @param saveKeys the saveKeys to set
	 */
	public void setSaveKeys(boolean saveKeys)
	{
		this.saveKeys = saveKeys;
	}

	/**
	 * @return the deletePreviousAnnotationKeys
	 */
	public boolean isDeletePreviousAnnotationKeys()
	{
		return deletePreviousAnnotationKeys;
	}

	/**
	 * @param deletePreviousAnnotationKeys the deletePreviousAnnotationKeys to set
	 */
	public void setDeletePreviousAnnotationKeys(boolean deletePreviousAnnotationKeys)
	{
		this.deletePreviousAnnotationKeys = deletePreviousAnnotationKeys;
	}

	private int pageSize = Config.getPropertyInteger(getClass(),"pageSize", 300);
	private CompoundService compoundService;
	private String annotationNameParamName;
	private String operationParamName;
	private String defaultOperation = Config.getProperty(ChemicalAnnotationWriter.class,"defaultOperation","");
	private String codeParamName;
	private ChemistryDAO chemistryDAO;
	private DataSource dataSource = null;
	private boolean saveKeys = true;
	private boolean deletePreviousAnnotationKeys = true;
	
}
