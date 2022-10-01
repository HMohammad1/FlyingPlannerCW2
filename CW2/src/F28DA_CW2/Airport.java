package F28DA_CW2;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.HashSet;
import java.util.Set;

public class Airport implements IAirportPartB, IAirportPartC {

	private final String code;
	private final HashSet<String[]> airports;
	private final SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> flightInfo;

	public Airport(String code, HashSet<String[]> airports, SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> flightInfo) {
		this.code = code;
		this.airports = airports;
		this.flightInfo = flightInfo;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getName() {
		String name = null;
		for(String[] showAirports : this.airports) {
			if(showAirports[0].equals(this.code)) {
				name = showAirports[1];
				System.out.println("Airport code: " + showAirports[0] + "\n" + "Airport City: " + showAirports[1] + "\n" + "Airport Name: " + showAirports[2]);
			}
		}
		return name;
	}

	@Override
	public void setDicrectlyConnected(Set<Airport> dicrectlyConnected) {
	}

	@Override
	public Set<Airport> getDicrectlyConnected() {
		Set<Airport> directlyConnected = new HashSet<>();
		Set<DefaultWeightedEdge> edges = flightInfo.edgesOf(code);
		for(DefaultWeightedEdge edge : edges) {
			String source = flightInfo.getEdgeSource(edge);
			String target = flightInfo.getEdgeTarget(edge);
			if (source.equals(getCode())) {
				// make sure each flight has a flight that comes back
				if (flightInfo.containsEdge(source, target) && flightInfo.containsEdge(target, source)) {
					directlyConnected.add(new Airport(target, airports, flightInfo));
				}
			}
		}
		return directlyConnected;
	}

	@Override
	public void setDicrectlyConnectedOrder(int order) {
		// TODO Auto-generated method stub
	}

	@Override
	public int getDirectlyConnectedOrder() {
		Set<Airport> directlyConnected = getDicrectlyConnected();
		int i = 0;
		for(Airport airport : directlyConnected) {
			Set<Airport> dc = new HashSet<>();
			Set<DefaultWeightedEdge> edges = flightInfo.edgesOf(airport.getCode());
			for(DefaultWeightedEdge edge : edges) {
				String source = flightInfo.getEdgeSource(edge);
				String target = flightInfo.getEdgeTarget(edge);
				if (source.equals(airport.getCode())) {
					if (flightInfo.containsEdge(source, target) && flightInfo.containsEdge(target, source)) {
						dc.add(new Airport(target, airports, flightInfo));
					}
				}
			}
			System.out.println(dc.size());
			i += dc.size();
		}
		System.out.println(i);
		return i;
	}
}
