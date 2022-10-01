package F28DA_CW2;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.*;

public class FlyingPlanner implements IFlyingPlannerPartB<Airport,Flight>, IFlyingPlannerPartC<Airport,Flight> {

	private HashSet<String[]> airports;
	private HashSet<String[]> flights;
	private final SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> flightInfo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);


	@Override
	public boolean populate(FlightsReader fr) {
		this.airports = fr.getAirports();
		this.flights = fr.getFlights();
		populate(this.airports, this.flights);
		return true;
	}

	@Override
	public boolean populate(HashSet<String[]> airports, HashSet<String[]> flights) {
		// when using tests these need to be initialised in this method
		this.airports = airports;
		this.flights = flights;

		for(String[] addAirports : airports) {
			flightInfo.addVertex(addAirports[0]);
		}
		for(String[] addFlights : flights) {
			DefaultWeightedEdge edge = flightInfo.addEdge(addFlights[1], addFlights[3]);
			flightInfo.setEdgeWeight(edge, Double.parseDouble(addFlights[5]));
		}
		return true;
	}

	@Override
	public Airport airport(String code) {
		return new Airport(code, airports, flightInfo);
	}

	@Override
	public Flight flight(String code) {
		return new Flight(code, flights, airports, flightInfo);
	}

	@Override
	public Journey leastCost(String from, String to) throws FlyingPlannerException {
		GraphPath<String, DefaultWeightedEdge> shortestPath = DijkstraShortestPath.findPathBetween(flightInfo, from, to);
		return new Journey(shortestPath, flights, flightInfo, airports);
	}

	@Override
	public Journey leastHop(String from, String to) throws FlyingPlannerException {
		GraphPath<String, DefaultWeightedEdge> shortestPath = BFSShortestPath.findPathBetween(flightInfo, from, to);
		return new Journey(shortestPath, flights, flightInfo, airports);
	}

	@Override
	public Journey leastCost(String from, String to, List<String> excluding) throws FlyingPlannerException {
		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> flightInfoClone = (SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>) flightInfo.clone();
		// remove the excluding airports codes from the copied data and then just find the journey normally
		for (String exclude : excluding) {
			flightInfoClone.removeVertex(exclude);
		}
		GraphPath<String, DefaultWeightedEdge> leastCost = DijkstraShortestPath.findPathBetween(flightInfoClone, from, to);
		return new Journey(leastCost, flights, flightInfo, airports);
	}

	@Override
	public Journey leastHop(String from, String to, List<String> excluding) throws FlyingPlannerException {
		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> flightInfoClone = (SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>) flightInfo.clone();
		for (String exclude : excluding) {
			flightInfoClone.removeVertex(exclude);
		}
		GraphPath<String, DefaultWeightedEdge> leastHop = BFSShortestPath.findPathBetween(flightInfoClone, from, to);
		return new Journey(leastHop, flights, flightInfo, airports);
	}

	@Override
	public Set<Airport> directlyConnected(Airport airport) {
		// airport already has this method so no point copying it
		return airport.getDicrectlyConnected();
	}

	@Override
	public int setDirectlyConnected() {
		return 0;
	}

	@Override
	public int setDirectlyConnectedOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<Airport> getBetterConnectedInOrder(Airport airport) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String leastCostMeetUp(String at1, String at2) throws FlyingPlannerException {
		//need two copies of the flight data, one without each of the starting airports for each person to ensure they can't go to those airports
		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> atTwo = (SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>) flightInfo.clone();
		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> atOne = (SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>) flightInfo.clone();

		atTwo.removeVertex(at1);
		atOne.removeVertex(at2);

		// the data is going to be stored in a hasmap with the string being the airport code and the int being the cost
		HashMap<String, Integer> map = new HashMap<>();
		HashMap<String, Integer> map2 = new HashMap<>();
		// this method returns all the paths from the starting airport to all the airports in the flight data
		getLeastCostPaths(at2, atTwo, map);
		getLeastCostPaths(at1, atOne, map2);

		HashMap<String, Integer> map3 = new HashMap<>();
		// once we have all the flights we just need to get the flights that occur in both maps, i.e. airports both people can access
		for (String entry : map.keySet()) {
			for (String entry2 : map2.keySet()) {
				if (entry.equals(entry2)) {
					int i = (map.get(entry) + map2.get(entry)) / 2;
					map3.put(entry, i);
				}
			}
		}
		// return the airport with the lowest number, the one we want
		return Collections.min(map3.entrySet(), Map.Entry.comparingByValue()).getKey();
	}

	private void getLeastCostPaths(String at2, SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> atTwo, HashMap<String, Integer> map) throws FlyingPlannerException {
		for(String vertex : atTwo.vertexSet()) {
			if (!vertex.equals(at2)) {
				GraphPath<String, DefaultWeightedEdge> leastCost = DijkstraShortestPath.findPathBetween(atTwo, at2, vertex);
				// only add paths that aren't null as they aren't really paths
				if (leastCost != null) {
					Journey journey = new Journey(leastCost, flights, flightInfo, airports);
					map.put(leastCost.getEndVertex(), journey.totalCost());
				}
			}
		}
	}

	@Override
	public String leastHopMeetUp(String at1, String at2) throws FlyingPlannerException {
		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> atTwo = (SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>) flightInfo.clone();
		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> atOne = (SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>) flightInfo.clone();

		atTwo.removeVertex(at1);
		atOne.removeVertex(at2);

		HashMap<String, Integer> map = new HashMap<>();
		HashMap<String, Integer> map2 = new HashMap<>();
		getLeastHopPaths(at2, atTwo, map);
		getLeastHopPaths(at1, atOne, map2);

		HashMap<String, Double> map3 = new HashMap<>();

		for (String entry : map.keySet()) {
			for (String entry2 : map2.keySet()) {
				if (entry.equals(entry2)) {
					double i = (double) (map.get(entry) + map2.get(entry)) / 2;
					map3.put(entry, i);
				}
			}
		}

		return Collections.min(map3.entrySet(), Map.Entry.comparingByValue()).getKey();
	}

	private void getLeastHopPaths(String at2, SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> atTwo, HashMap<String, Integer> map) {
		for(String vertex : atTwo.vertexSet()) {
			if (!vertex.equals(at2)) {
				GraphPath<String, DefaultWeightedEdge> leastCost = BFSShortestPath.findPathBetween(atTwo, at2, vertex);
				if (leastCost != null) {
					Journey journey = new Journey(leastCost, flights, flightInfo, airports);
					map.put(leastCost.getEndVertex(), journey.totalHop());
				}
			}
		}
	}

	@Override
	public String leastTimeMeetUp(String at1, String at2, String startTime) throws FlyingPlannerException {
		return null;
	}

}
