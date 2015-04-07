package khem.solutions.cheminformatics.batch.spring;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import khem.solutions.cheminformatics.io.SDFEntry;
import khem.solutions.cheminformatics.io.SDFileReader;
import nyla.solutions.dao.DAO;
import nyla.solutions.dao.SQL;
import nyla.solutions.global.data.DataRow;

import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;


/**
 * Perform a select from a database using attributes from the SDF file.
 * Return the results in the DataRow. 
 * 
 * 	<bean id="CHEMISTRY_ANNOT_KEYS_MCIDB_IMPORT_READER" class="khem.solutions.cheminformatics.batch.spring.DataRowSqlSdfMolInputReader">
		<property name="filePath" value="runtime/input/chem_annotations.sdf"/>
		<property name="sql">
			<value>
			select molnemakey(ctab) as MOLKEY , COMPOUND_ID,  ? as ANNOATION_NM, ? as CHEM_ANNOTATION_CD , 'MCIDB' from ecdspic60.rcg_stuff_moltable 
			where sss(CTAB, ?)=1 and COMPOUND_ID is not null
			</value>
		</property>
		<property name="paramXIndex" value="1"/>
		<property name="paramXName" value="NAME"/>
		
		<property name="paramYIndex" value="2"/>
		<property name="paramYName" value="CHEM_ANNOTATION_CD"/>
		
		<property name="molIndex" value="3"/>
		
		<property name="dataSource" ref="mcidbDataSource"/>
	</bean>	
 * @author Gregory Green
 *
 */
public class DataRowSqlSdfMolInputReader implements ItemReader<DataRow>
{
	/**
	 * Open connections
	 * @throws IOException
	 * @throws SQLException
	 */
	@BeforeStep
	public void open()
	throws IOException, SQLException
	{
		reader = new SDFileReader(new File(this.filePath));
		
		reader.open();
		
		this.sqlDAO = SQL.connect(this.dataSource.getConnection());
	}// --------------------------------------------------------
	/**
	 * Read entries from the SDF file
	 * @see org.springframework.batch.item.ItemReader#read()
	 */
	@Override
	public DataRow read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException
	{
		if(reader == null)
			this.open();
		
		//read next 
	
		if(this.rs == null)
		{
			 entry = reader.nextSDFEntry();
		
			 if(entry == null)
			 {
				 dispose();
				 
				 return null;
			 }
			 
			 if(this.ps == null)
			 {
				 this.ps = sqlDAO.prepareCall(this.sql);
			 }
			
			 //set next MOLSTRING
			 if(molIndex > 0)
				 this.ps.setString(this.molIndex, entry.getMolString());
		     
			 //Additional parameters
		     if(this.paramXIndex  > 0 && this.paramXName != null)
		    	 this.ps.setString(this.paramXIndex, entry.retrieveParamValue(this.paramXName));
		     
		     
		     if(this.paramYIndex  > 0 && this.paramYName != null)
		    	 this.ps.setString(this.paramYIndex, entry.retrieveParamValue(this.paramYName));
		     
		     if(this.paramZIndex  > 0 && this.paramZName != null)
		    	 this.ps.setString(this.paramZIndex, entry.retrieveParamValue(this.paramZName));
		     
			//execute select
		     try
		     {
		    	 this.rs= sqlDAO.select(ps);
		     }
		     catch(SQLException e)
		     {
		    	 throw new SQLException(this.sql+" "+e.getMessage(),e);
		     }
		}
		
		if(rs.next())
			return DAO.toDataRow(rs);
		else
		{
			//NO more results, close result set
			try{ rs.close(); } catch(Exception e){}			
			this.rs = null;
		}

		//recursive call
		return read();
	}// --------------------------------------------------------
	/**
	 * Dispose the connections
	 */
	@AfterStep
	public void dispose()
	{
		//close everything
		if(this.rs != null)
			try{ this.rs.close();} catch(Exception e){}
		 
		 if(this.ps != null)
			 try{  this.ps.close();} catch(Exception e){}
		 
		 if(this.sqlDAO != null)
			 try{ this.sqlDAO.dispose();} catch(Exception e){}
		
		 if(reader != null)
			 try{ reader.close();} catch(Exception e){}
		 
		 this.rs = null;
		 this.ps = null;
		 this.sqlDAO = null;
		 this.reader = null;
	}// --------------------------------------------------------
	/**
	 * @return the filePath
	 */
	public String getFilePath()
	{
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	/**
	 * @return the paramXName
	 */
	public String getParamXName()
	{
		return paramXName;
	}
	/**
	 * @param paramXName the paramXName to set
	 */
	public void setParamXName(String paramXName)
	{
		this.paramXName = paramXName;
	}
	/**
	 * @return the paramXIndex
	 */
	public int getParamXIndex()
	{
		return paramXIndex;
	}
	/**
	 * @param paramXIndex the paramXIndex to set
	 */
	public void setParamXIndex(int paramXIndex)
	{
		this.paramXIndex = paramXIndex;
	}
	/**
	 * @return the paramYName
	 */
	public String getParamYName()
	{
		return paramYName;
	}
	/**
	 * @param paramYName the paramYName to set
	 */
	public void setParamYName(String paramYName)
	{
		this.paramYName = paramYName;
	}
	/**
	 * @return the paramYIndex
	 */
	public int getParamYIndex()
	{
		return paramYIndex;
	}
	/**
	 * @param paramYIndex the paramYIndex to set
	 */
	public void setParamYIndex(int paramYIndex)
	{
		this.paramYIndex = paramYIndex;
	}
	/**
	 * @return the paramZName
	 */
	public String getParamZName()
	{
		return paramZName;
	}
	/**
	 * @param paramZName the paramZName to set
	 */
	public void setParamZName(String paramZName)
	{
		this.paramZName = paramZName;
	}
	/**
	 * @return the paramZIndex
	 */
	public int getParamZIndex()
	{
		return paramZIndex;
	}
	/**
	 * @param paramZIndex the paramZIndex to set
	 */
	public void setParamZIndex(int paramZIndex)
	{
		this.paramZIndex = paramZIndex;
	}
	/**
	 * @return the molIndex
	 */
	public int getMolIndex()
	{
		return molIndex;
	}
	/**
	 * @param molIndex the molIndex to set
	 */
	public void setMolIndex(int molIndex)
	{
		this.molIndex = molIndex;
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
	 * @return the sql
	 */
	public String getSql()
	{
		return sql;
	}
	/**
	 * @param sql the sql to set
	 */
	public void setSql(String sql)
	{
		this.sql = sql;
	}



	private String paramXName = null;
	private int paramXIndex =  -1;
	
	private String paramYName = null;
	private int paramYIndex =  -1;
	
	private String paramZName = null;
	private int paramZIndex =  -1;
	
	private int molIndex = -1;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	private SDFileReader reader = null; 
	private String filePath;
	private SDFEntry entry = null;
	private DataSource dataSource = null;
	private SQL sqlDAO = null;
	private String sql;
}
