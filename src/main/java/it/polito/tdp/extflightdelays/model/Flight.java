package it.polito.tdp.extflightdelays.model;

import java.time.LocalDateTime;

public class Flight 
{
	private int id;
	private int airlineId;
	private int flightNumber;
	private String tailNumber;
	private int originAirportId;
	private int destinationAirportId;
	private LocalDateTime scheduledDepartureDate;
	private Double departureDelay;
	private Double elapsedTime;
	private int distance;
	private LocalDateTime arrivalDate;
	private Double arrivalDelay;

	
	public Flight(int id, int airlineId, int flightNumber, String tailNumber, int originAirportId,
			int destinationAirportId, LocalDateTime scheduledDepartureDate, Double departureDelay, Double elapsedTime,
			int distance, LocalDateTime arrivalDate, Double arrivalDelay) 
	{
		this.id = id;
		this.airlineId = airlineId;
		this.flightNumber = flightNumber;
		this.tailNumber = tailNumber;
		this.originAirportId = originAirportId;
		this.destinationAirportId = destinationAirportId;
		this.scheduledDepartureDate = scheduledDepartureDate;
		this.departureDelay = departureDelay;
		this.elapsedTime = elapsedTime;
		this.distance = distance;
		this.arrivalDate = arrivalDate;
		this.arrivalDelay = arrivalDelay;
	}

	public int getId() 
	{
		return this.id;
	}
	
	public int getAirlineId() 
	{
		return this.airlineId;
	}

	public int getFlightNumber() 
	{
		return this.flightNumber;
	}

	public String getTailNumber() 
	{
		return this.tailNumber;
	}

	public int getOriginAirportId() 
	{
		return this.originAirportId;
	}

	public int getDestinationAirportId() 
	{
		return this.destinationAirportId;
	}

	public LocalDateTime getScheduledDepartureDate() 
	{
		return this.scheduledDepartureDate;
	}
	
	public Double getDepartureDelay() 
	{
		return this.departureDelay;
	}

	public Double getElapsedTime()
	{
		return this.elapsedTime;
	}

	public int getDistance() 
	{
		return this.distance;
	}

	public LocalDateTime getArrivalDate() 
	{
		return this.arrivalDate;
	}

	public Double getArrivalDelay() 
	{
		return this.arrivalDelay;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + this.airlineId;
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		if (this.airlineId != other.airlineId)
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "Flight [id=" + this.id + ", flightNumber=" + this.flightNumber + ", originAirportId=" + this.originAirportId
				+ ", destinationAirportId=" + this.destinationAirportId + "]";
	}

}
