package khem.solutions.cheminformatics.accelrys;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import nyla.solutions.core.patterns.servicefactory.ServiceFactory;
import nyla.solutions.core.util.Config;
import nyla.solutions.core.util.Debugger;
import nyla.solutions.dao.DAO;
import nyla.solutions.dao.SQL;




/**
 * <!--  Use a single DataSource that contains all batched data -->
  <bean id="khem.solutions.cheminformatics.accelrys.DirectCartridge.dataSource" class="solutions.dao.spring.batch.ConfigDriverMgrDataSource">
		<property name="driverClassName" value="${directCartridgeDS.jdbc.driver}" />
		<property name="url" value="${directCartridgeDS.jdbc.connection.url}" />
		<property name="username" value="${directCartridgeDS.jdbc.user}" />
		<property name="password" value="${directCartridgeDS.jdbc.password}" />
	</bean>	
 * @author Gregory Green
 *
 */
public class DirectCartridge
{
	/**
	 * Sub structure molecule search
	 * @param molecule
	 * @param subMol
	 * @return first integer in result set
	 * @throws SQLException
	 */
	public static int sss(Clob molecule,String subMol)
	throws SQLException
	{
		
			SQL dao = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			
		   try
		   {
				dao = SQL.connect(createConnection());
				
				ps = dao.prepareStatement(sssSQL);
				
				
				Clob moleculeCopy = ps.getConnection().createClob();
				
				DAO.copy(molecule, moleculeCopy);
				
				ps.setClob(1, moleculeCopy);
				ps.setString(2, subMol);
				
				
				rs = dao.select(ps);
					
			    rs.next();
			    
			    return rs.getInt(1);
		   }
			catch(SQLException e)
			{
				String text = null;
				try
				{ 
					text = DAO.toString(molecule);
				}
				catch(Exception exception){}
				
				Debugger.printWarn(DirectCartridge.class,"molecule:"+text+" subMol:"+subMol+" ERROR:"+Debugger.stackTrace(e));
				return 0;		
			}
		   finally
		   {
			   if(rs != null)
				   try{ rs.close(); } catch(Exception e){}
			   
			   if(ps != null)
				   try{ ps.close(); } catch(Exception e){}
			   
			   
			   //if(dao != null)
			   //   try{ dao.dispose(); } catch(Exception e){}
			  
		   }
	}// --------------------------------------------------------
	public static int molnemakey(String molecule)
	throws SQLException
	{

		SQL dao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
						dao = SQL.connect(createConnection());
						
						ps = dao.prepareStatement(molnemakeySQL);
						
						rs = dao.select(ps);
						
						ps.setString(1, molecule);

					    rs.next();
					    
					    return rs.getInt(1);
			}
			finally
			{
					   if(rs != null)
						   try{ rs.close(); } catch(Exception e){}
					   
					   if(ps != null)
						   try{ ps.close(); } catch(Exception e){}
					   
					   if(dao != null)
						   try{ dao.dispose(); } catch(Exception e){}
					  
				}
	}// --------------------------------------------------------
	private static synchronized Connection createConnection()
	throws SQLException
	{
		if(connection == null || connection.isClosed())
		{
			DataSource dataSource = ServiceFactory.getInstance().create(new StringBuilder(DirectCartridge.class.getName()).append(".dataSource").toString());
			
			connection = dataSource.getConnection();
		}
		
		return connection;	
	}// --------------------------------------------------------
	public static int flexmatch(String molecule, String matchMol, String parameters)
	throws SQLException
	{	
		SQL dao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
						dao = SQL.connect(createConnection());
						
						ps = dao.prepareStatement(flexmatchSQL);
						
						rs = dao.select(ps);
						
						ps.setString(1, molecule);
						ps.setString(2, matchMol);
						ps.setString(3, parameters);

					    rs.next();
					    
					    return rs.getInt(1);
			}
			finally
			{
					   if(rs != null)
						   try{ rs.close(); } catch(Exception e){}
					   
					   if(ps != null)
						   try{ ps.close(); } catch(Exception e){}
					   
					   if(dao != null)
						   try{ dao.dispose(); } catch(Exception e){}
					  
				}
	}// --------------------------------------------------------
	
	
	
	
	private static final String sssSQL = Config.getProperty(DirectCartridge.class,"sssSQL","select sss(mol(?),?) from dual");
	private static final String molnemakeySQL = Config.getProperty(DirectCartridge.class,"molnemakeySQL","select molnemakey(mol(?)) from dual");
	private static final String flexmatchSQL =  Config.getProperty(DirectCartridge.class,"flexmatchSQL","select flexmatch(mol(?), ?,?) from dual");
	private static Connection connection = null;

}
