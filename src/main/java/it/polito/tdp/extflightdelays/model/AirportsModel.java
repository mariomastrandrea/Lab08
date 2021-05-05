package it.polito.tdp.extflightdelays.model;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class AirportsModel 
{
	private final ExtFlightDelaysDAO dao;
	private Graph<Airport, DefaultWeightedEdge> graph;
	private Map<Integer, Airport> airportsById;
	private Set<Link> edges;
	
	
	public AirportsModel()
	{
		this.dao = new ExtFlightDelaysDAO();
		this.airportsById = this.dao.loadAllAirports();
	}
	
	/**
	 * this method retrieves directly from the DB the avg distances between DISTINCT airports couples.
	 * As 'minMiles' decreases, it spends approximately the same time
	 * @param minMiles
	 */
	public void createGraph(int minMiles)
	{
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.graph, this.airportsById.values());
		
		Set<Link> connections = this.dao.loadAirportsConnections((double)minMiles, airportsById);
		this.edges = connections;
		
		for(Link l : connections)
			Graphs.addEdge(this.graph, l.getA1(), l.getA2(), l.getAvgDistance());
	}
	
	/**
	 * this method retrieves from the DB the avg distances between all airports couples, even repeated couples;
	 * then, for each link, it checks if it already exists this link, and it updates
	 * the previous link with the new Avg Distance, if it already existed.
	 * As 'minMiles' decrease, it spends more time
	 * @param minMiles
	 */
	public void createGraph2(int minMiles)
	{
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.graph, this.airportsById.values());
		
		Set<Link> connections = this.dao.loadAirportsConnections2((double)minMiles, airportsById);
		this.edges = connections;
		
		for(Link l : connections)
			Graphs.addEdge(this.graph, l.getA1(), l.getA2(), l.getAvgDistance());
	}
	
	public int getNumVertices()
	{
		return this.graph.vertexSet().size();
	}
	
	public int getNumEdges()
	{
		return this.graph.edgeSet().size();
	}
	
	public Collection<Link> getEdges()
	{
		return this.edges;
	}

}
