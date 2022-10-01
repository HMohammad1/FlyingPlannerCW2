package F28DA_CW2;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.HashSet;

public class Flight implements IFlight {

	private final HashSet<String[]> flights;
	private final String code;
	private final HashSet<String[]> airports;
	private final SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> flightInfo;

	public Flight(String code, HashSet<String[]> flights, HashSet<String[]> airports, SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> flightInfo) {
		this.flights = flights;
		this.code = code;
		this.airports = airports;
		this.flightInfo = flightInfo;
	}

	@Override
	public String getFlightCode() {
		return this.code;
	}

	@Override
	public Airport getTo() {
		for (String[] flight : this.flights) {
			if (flight[0].equals(this.code)) {
				return new Airport(flight[3], airports, flightInfo);
			}
		}
		return null;
	}

	@Override
	public Airport getFrom() {
		for (String[] flight : this.flights) {
			if (flight[0].equals(this.code)) {
				return new Airport(flight[1], airports, flightInfo);
			}
		}
		return null;
	}

	@Override
	public String getFromGMTime() {
		for (String[] flight : this.flights) {
			if (flight[0].equals(this.code)) {
				return flight[2];
			}
		}
		return null;
	}

	@Override
	public String getToGMTime() {
		for (String[] flight : this.flights) {
			if (flight[0].equals(this.code)) {
				return flight[4];
			}
		}
		return null;
	}

	@Override
	public int getCost() {
		for (String[] flight : this.flights) {
			if (flight[0].equals(this.code)) {
				return Integer.parseInt(flight[5]);
			}
		}
		return 0;
	}


}
