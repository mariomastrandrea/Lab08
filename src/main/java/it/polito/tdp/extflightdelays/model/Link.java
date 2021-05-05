package it.polito.tdp.extflightdelays.model;

public class Link
{
	private final Airport a1;
	private final Airport a2;
	private double avgDistance;
	private int numLinks;
	
	
	public Link(Airport a1, Airport a2, double avgDistance)
	{
		this.a1 = a1;
		this.a2 = a2;
		this.avgDistance = avgDistance;
	}
	
	public Link(Airport a1, Airport a2, double avgDistance, int numLinks)
	{
		this.a1 = a1;
		this.a2 = a2;
		this.avgDistance = avgDistance;
		this.numLinks = numLinks;
	}
	
	public Airport getA1()
	{
		return this.a1;
	}
	
	public Airport getA2()
	{
		return this.a2;
	}
	
	public double getAvgDistance()
	{
		return this.avgDistance;
	}
	
	public int getNumLinks()
	{
		return this.numLinks;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a1 == null) ? 0 : a1.hashCode());
		result = prime * result + ((a2 == null) ? 0 : a2.hashCode());
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
		Link other = (Link) obj;
		if (a1 == null)
		{
			if (other.a1 != null)
				return false;
		}
		else
			if (!a1.equals(other.a1))
				return false;
		if (a2 == null)
		{
			if (other.a2 != null)
				return false;
		}
		else
			if (!a2.equals(other.a2))
				return false;
		return true;
	}

	public void setAvgDistance(double newAvgDistance)
	{
		this.avgDistance = newAvgDistance;
	}
	
	public void setNumLinks(int newNumLinks)
	{
		this.numLinks = newNumLinks;
	}
	
	
}
