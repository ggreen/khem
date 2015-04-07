package khem.solutions.cheminformatics.batch.spring;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import khem.solutions.cheminformatics.io.SDFEntry;
import nyla.solutions.dao.DAO;
import nyla.solutions.dao.SQL;
import nyla.solutions.global.patterns.Disposable;

import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;



/**
 * Write entries from a SDF file into a database
 * @author Gregory Green
 *
 */
public class SDFSqlDaoWriter implements ItemWriter<SDFEntry>, Disposable
{
	/**
	 * Open a connection to the database
	 * @throws SQLException
	 */
	@BeforeStep
	public void open()
	throws SQLException
	{
		
		this.sqlDAO = SQL.connect(dataSource.getConnection());
	}// --------------------------------------------------------

	/**
	 * Close the SQL connection
	 * @see solutions.global.patterns.Disposable#dispose()
	 */
	@Override
	@AfterStep
	public void dispose()
	{	
		if(this.sqlDAO != null)
		{
			try{this.sqlDAO.dispose(); } catch(Exception e){}
		}
		
	}// --------------------------------------------------------
	
	/**
	 * Write SDF entries
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	@Override
	public void write(List<? extends SDFEntry> list) throws Exception
	{
		Map<String,String> map  = null;
		
		String sql;
		
		String[] molStringInputs = new String[this.moleculeInputCount];
		
		for (SDFEntry sdfEntry : list)
		{
			map = sdfEntry.retrieveParamMapValue();
			
			
			sql = SQL.formatSQL(this.formattedSQL, map);
			
			for (int i = 0; i < molStringInputs.length; i++)
			{
				molStringInputs[i] = sdfEntry.getMolString();
			}
			
			sqlDAO.executeUpdate(sql, molStringInputs);
		}
		
	}// --------------------------------------------------------
	
	/**
	 * @return the formattedSQL
	 */
	public String getFormattedSQL()
	{
		return formattedSQL;
	}

	/**
	 * @param formattedSQL the formattedSQL to set
	 */
	public void setFormattedSQL(String formattedSQL)
	{
		this.formattedSQL = formattedSQL;
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
	 * @return the moleculeInputCount
	 */
	public int getMoleculeInputCount()
	{
		return moleculeInputCount;
	}

	/**
	 * @param moleculeInputCount the moleculeInputCount to set
	 */
	public void setMoleculeInputCount(int moleculeInputCount)
	{
		this.moleculeInputCount = moleculeInputCount;
	}

	private int moleculeInputCount;
	private String formattedSQL;
	private DAO sqlDAO;
	private DataSource dataSource;

}
