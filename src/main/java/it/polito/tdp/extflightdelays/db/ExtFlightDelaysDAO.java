package it.polito.tdp.extflightdelays.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Link;

public class ExtFlightDelaysDAO 
{
	public Map<Integer,Airport> loadAllAirports() 
	{
		String sql = "SELECT * FROM airports";
		Map<Integer,Airport> result = new HashMap<>();

		try
		{
			Connection connection = ConnectDB.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) 
			{
				int id = resultSet.getInt("ID");
				Airport airport = new Airport(id, resultSet.getString("IATA_CODE"), 
											  resultSet.getString("AIRPORT"), resultSet.getString("CITY"), 
											  resultSet.getString("STATE"), resultSet.getString("COUNTRY"), 
											  resultSet.getDouble("LATITUDE"), resultSet.getDouble("LONGITUDE"), 
											  resultSet.getDouble("TIMEZONE_OFFSET"));
				result.put(id,airport);			}

			resultSet.close();
			statement.close();
			connection.close();
		} 
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			System.out.println("Errore dao in loadAllAirports()");
			throw new RuntimeException("Errore dao in loadAllAirports()", sqle);
		}
		
		return result;
	}

	public Set<Link> loadAirportsConnections(double minDistance, Map<Integer, Airport> airportsById)
	{
		String sqlQuery = String.format("%s %s %s %s %s %s %s %s %s",
						"SELECT origin, destination, AVG(miles) avgMiles",
						"FROM (SELECT ORIGIN_AIRPORT_ID AS origin, DESTINATION_AIRPORT_ID AS destination, DISTANCE AS miles",
							  "FROM flights",
							  "UNION ALL",
							  "SELECT DESTINATION_AIRPORT_ID, ORIGIN_AIRPORT_ID, DISTANCE",
							  "FROM flights) AS allflights",
						"WHERE allflights.origin < allflights.destination",
						"GROUP BY origin, destination",
						"HAVING avgMiles > ?");
		
		Set<Link> result = new HashSet<>();
		
		try
		{
			Connection connection = ConnectDB.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setDouble(1, minDistance);
			ResultSet queryRes = statement.executeQuery();
			
			while(queryRes.next())
			{
				int id1 = queryRes.getInt("origin");
				Airport a1 = airportsById.get(id1);
				
				int id2 = queryRes.getInt("destination");
				Airport a2 = airportsById.get(id2);

				double avgDistance = queryRes.getDouble("avgMiles");
				
				Link l = new Link(a1, a2, avgDistance);
				
				result.add(l);
			}
			
			ConnectDB.close(queryRes, statement, connection);
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
			System.out.println("Errore dao in loadAirportsConnections()");
			throw new RuntimeException("Errore dao in loadAirportsConnections()", sqle);
		}
		
		return result;
	}

	public Set<Link> loadAirportsConnections2(double minDistance, Map<Integer, Airport> airportsById)
	{
		String sqlQuery = String.format("%s %s %s %s",
				"SELECT ORIGIN_AIRPORT_ID AS origin, DESTINATION_AIRPORT_ID AS destination, AVG(DISTANCE) avgMiles, COUNT(*) AS numFlights",
				"FROM flights",
				"GROUP BY origin, destination",
				"HAVING avgMiles > ?");

		Set<Link> result = new HashSet<>();
		
		try
		{
			Connection connection = ConnectDB.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setDouble(1, minDistance);
			ResultSet queryRes = statement.executeQuery();
			
			while(queryRes.next())
			{
				int id1 = queryRes.getInt("origin");
				Airport a1 = airportsById.get(id1);
				
				int id2 = queryRes.getInt("destination");
				Airport a2 = airportsById.get(id2);
		
				double avgDistance = queryRes.getDouble("avgMiles");
				int numFlights = queryRes.getInt("numFlights");
				boolean exists = false;
				
				for(Link link : result)
				{
					if(link.getA1().equals(a1) && link.getA2().equals(a2) ||
						link.getA1().equals(a2) && link.getA2().equals(a1))
					{
						double prevTot = (double)link.getNumLinks() * link.getAvgDistance();
						double newTot = (double)numFlights * avgDistance;
						
						int newNum = numFlights + link.getNumLinks();
						double newAvg = (prevTot + newTot)/(double)(newNum);
						
						link.setAvgDistance(newAvg);
						link.setNumLinks(newNum);
						
						exists = true;
						break;
					}
				}

				if(!exists)
				{
					Link l = new Link(a1, a2, avgDistance, numFlights);
					result.add(l);
				}
			}
			
			ConnectDB.close(queryRes, statement, connection);
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
			System.out.println("Errore dao in loadAirportsConnections2()");
			throw new RuntimeException("Errore dao in loadAirportsConnections2()", sqle);
		}
		
		return result;
	}
}
