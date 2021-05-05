package it.polito.tdp.extflightdelays.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectDB 
{
	private static final String jdbcURL = "jdbc:mariadb://localhost/extflightdelays";
	private static final HikariDataSource dataSource;
	
	static
	{
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(jdbcURL);
		config.setUsername("root");
		config.setPassword("root");
		
		// configurazione MySQL
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		
		dataSource = new HikariDataSource(config);
	}
	
	public static Connection getConnection() 
	{
		try 
		{	
			return dataSource.getConnection();
		} 
		catch (SQLException sqle) 
		{
			System.err.println("Errore connessione al DB, url: " + jdbcURL);
			throw new RuntimeException(sqle);
		}
	}
	
	public static void close(AutoCloseable... resources) throws SQLException
	{
		for(var v : resources)
		{
			try
			{
				v.close();
			}
			catch(Exception e)
			{
				System.err.println("Errore chiusura risorse DB");
				throw new SQLException(e);
			}
		}
	}

}
