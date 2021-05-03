package it.polito.tdp.extflightdelays.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.extflightdelays.model.Airline;
import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Flight;

public class ExtFlightDelaysDAO 
{
	public List<Airline> loadAllAirlines() 
	{
		String sql = "SELECT * from airlines";
		List<Airline> result = new ArrayList<Airline>();

		try 
		{
			Connection connection = ConnectDB.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) 
			{
				result.add(new Airline( resultSet.getInt("ID"), 
										resultSet.getString("IATA_CODE"), 
										resultSet.getString("AIRLINE")) );
			}

			resultSet.close();
			connection.close();
			connection.close();
			return result;
		} 
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			System.out.println("Errore connessione al database in loadAllAirLines()");
			throw new RuntimeException("Error Connection Database in loadAllAirLines()");
		}
	}

	public List<Airport> loadAllAirports() 
	{
		String sql = "SELECT * FROM airports";
		List<Airport> result = new ArrayList<Airport>();

		try 
		{
			Connection connection = ConnectDB.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) 
			{
				Airport airport = new Airport(resultSet.getInt("ID"), resultSet.getString("IATA_CODE"), 
											  resultSet.getString("AIRPORT"), resultSet.getString("CITY"), 
											  resultSet.getString("STATE"), resultSet.getString("COUNTRY"), 
											  resultSet.getDouble("LATITUDE"), resultSet.getDouble("LONGITUDE"), 
											  resultSet.getDouble("TIMEZONE_OFFSET"));
				result.add(airport);			}

			resultSet.close();
			statement.close();
			connection.close();
			return result;
		} 
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			System.out.println("Errore connessione al database in loadAllAirports()");
			throw new RuntimeException("Error Connection Database in loadAllAirports()");
		}
	}

	public List<Flight> loadAllFlights() 
	{
		String sql = "SELECT * FROM flights";
		List<Flight> result = new LinkedList<Flight>();

		try 
		{
			Connection connection = ConnectDB.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet queryResult = statement.executeQuery();

			while (queryResult.next()) 
			{
				Flight flight = new Flight( queryResult.getInt("ID"), queryResult.getInt("AIRLINE_ID"), queryResult.getInt("FLIGHT_NUMBER"),
											queryResult.getString("TAIL_NUMBER"), queryResult.getInt("ORIGIN_AIRPORT_ID"),
											queryResult.getInt("DESTINATION_AIRPORT_ID"), 
											queryResult.getTimestamp("SCHEDULED_DEPARTURE_DATE").toLocalDateTime(), 
											queryResult.getDouble("DEPARTURE_DELAY"), queryResult.getDouble("ELAPSED_TIME"), 
											queryResult.getInt("DISTANCE"), queryResult.getTimestamp("ARRIVAL_DATE").toLocalDateTime(),
											queryResult.getDouble("ARRIVAL_DELAY"));
				result.add(flight);
			}

			queryResult.close();
			statement.close();
			connection.close();
			return result;
		} 
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			System.out.println("Errore connessione al database in loadAllFlights()");
			throw new RuntimeException("Error Connection Database in loadAllFlights()");
		}
	}
}
