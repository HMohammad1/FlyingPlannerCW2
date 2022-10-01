package F28DA_CW2;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Journey implements IJourneyPartB<Airport, Flight>, IJourneyPartC<Airport, Flight> {

	private final GraphPath<String, DefaultWeightedEdge> graph;
	private final HashSet<String[]> flights;
	private final HashSet<String[]> airports;
	private final SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> flightInfo;
	private final List<String> departureTime = new LinkedList<>();
	private final List<String> arrivalTIme = new LinkedList<>();
	private final List<String> stopNames = new LinkedList<>();

	public Journey(GraphPath<String, DefaultWeightedEdge> graph, HashSet<String[]> flights, SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> flightInfo, HashSet<String[]> airports) {
		this.graph = graph;
		this.flights = flights;
		this.flightInfo = flightInfo;
		this.airports = airports;
	}

	@Override
	public List<String> getStops() throws FlyingPlannerException {
		List<String> stops = new LinkedList<>();
		if(graph == null) {
			throw new FlyingPlannerException("empty");
		}
		for (DefaultWeightedEdge edge : graph.getEdgeList()) {
			String startAirport = flightInfo.getEdgeSource(edge);
			String destinationAirport = flightInfo.getEdgeTarget(edge);
			stops.add(startAirport);
			stops.add(destinationAirport);
		}
		// for displaying the airport name is required too, not just the code
		for (String getStops : stops) {
			for (String[] addAirports : airports) {
				if (addAirports[0].equals(getStops)) {
					this.stopNames.add(addAirports[1]);
				}
			}

		}
		return stops;
	}

	@Override
	public List<String> getFlights() throws FlyingPlannerException {
		List<String> flightCode = new LinkedList<>();
		List<String> stops = getStops();
		int i = 0;
		for (int b=1; b < stops.size(); b++) {
			for (String[] addFlights : flights) {
				if (addFlights[1].equals(stops.get(i)) && addFlights[3].equals(stops.get(i+1))) {
					flightCode.add(addFlights[0]);
					// for displaying these are added in here as it saves on having to make another loop for it
					this.departureTime.add(addFlights[2]);
					this.arrivalTIme.add(addFlights[4]);
				}
			}
			i++;
		}
		return flightCode;
	}

	@Override
	public int totalHop() {
		return graph.getEdgeList().size();
	}

	@Override
	public int totalCost() throws FlyingPlannerException {
		List<String> codes = getFlights();
		int total = 0;
		for (String getCOde : codes) {
			for (String[] addFlights : flights) {
				if (addFlights[0].equals(getCOde)) {
					total += Integer.parseInt(addFlights[5]);
				}
			}
		}
		return total;
	}

	@Override
	public int airTime() throws FlyingPlannerException {
		List<String> codes = getFlights();
		int total = 0;
		for (String getCOde : codes) {
			for (String[] addFlights : flights) {
				if (addFlights[0].equals(getCOde)) {
					// need to convert to minute before calculating the total time by getting the first 2 numbers and multiplying by 60, since the
					// last 2 are already in minute they can just be added on
					int getToFirst2 = Integer.parseInt(addFlights[4].substring(0,2));
					int getToLast2 = Integer.parseInt(addFlights[4].substring(2,4));
					int getFromFirst2 = Integer.parseInt(addFlights[2].substring(0,2));
					int getFromLast2 = Integer.parseInt(addFlights[2].substring(2,4));
					int calculation = (getToFirst2*60 + getToLast2) - (getFromFirst2*60 + getFromLast2);
					if (calculation < 0) {
						calculation += 1440; //24h in minutes
					}
					total += calculation;
				}
			}
		}
		return total;
	}

	@Override
	public int connectingTime() throws FlyingPlannerException {
		List<String> codes = getFlights();
		LinkedList<Integer> getFrom = new LinkedList<>();
		LinkedList<Integer> getTo = new LinkedList<>();
		int total = 0;
		for (String getCOde : codes) {
			for (String[] addFlights : flights) {
				if (addFlights[0].equals(getCOde)) {
					int getToFirst2 = Integer.parseInt(addFlights[4].substring(0,2));
					int getToLast2 = Integer.parseInt(addFlights[4].substring(2,4));
					int getFromFirst2 = Integer.parseInt(addFlights[2].substring(0,2));
					int getFromLast2 = Integer.parseInt(addFlights[2].substring(2,4));
					getFrom.add(getFromFirst2);
					getFrom.add(getFromLast2);
					getTo.add(getToFirst2);
					getTo.add(getToLast2);

				}
			}
		}
		// the very first time and the very last time are not needed to find the connecting time, and it's easier to just delete them rather than
		// figuring out a way to not add them in the loop above. 4 deletes since its added 2 numbers at a time, the hours that need to be
		// converted and the minutes
		getFrom.removeFirst();
		getFrom.removeFirst();
		getTo.removeLast();
		getTo.removeLast();
		for (int i=0; i<getFrom.size(); i+=2) {
			int calc = (getFrom.get(i) * 60 + getFrom.get(i+1) - (getTo.get(i) * 60 + getTo.get(i+1)));
			if (calc < 0) {
				calc += 1440;
			}
			total += calc;
		}
		return total;
	}

	@Override
	public int totalTime() throws FlyingPlannerException {
		int airTime = airTime();
		int connectingTime = connectingTime();
		return airTime + connectingTime;
	}

	// this method displays all the data in a nice way
	public void display() throws FlyingPlannerException {
		List<String> stops = getStops();
		List<String> flightCode = getFlights();
		int hops = totalHop();
		int totalCost = totalCost();
		int airTime = airTime();
		// had an issue where the zeroes at the start didn't return which effects the Date class being used, so it needs to be padded to 4 characters long
		String padded = String.format("%04d" , airTime);

		Date date = null;
		DateFormat inFormat = new SimpleDateFormat("mm"); //format it's currently in
		DateFormat outFormat = new SimpleDateFormat("HH:mm"); // format to make it
		try {
			date = inFormat.parse(padded);
		} catch (ParseException ignored) {
		}

		//format the times for arrival and departure
		List<String> formattedDepartureTime = new LinkedList<>();
		List<String> formattedArrivalTime = new LinkedList<>();
		for (int i = 0; i<this.departureTime.size(); i++) {
			Date addDate;
			DateFormat format = new SimpleDateFormat("hhmm");
			try {
				addDate = format.parse(this.departureTime.get(i));
				String formattedDate = outFormat.format(addDate);
				formattedDepartureTime.add(formattedDate);
				addDate = format.parse(this.arrivalTIme.get(i));
				formattedDate = outFormat.format(addDate);
				formattedArrivalTime.add(formattedDate);
			} catch (ParseException ignored) {
			}

		}

		String totalTime = outFormat.format(date);

		int c = 0;
		System.out.format("%-5s%-20s%-10s%-10s%-20s%-10s%n", "Leg", "Leave", "At", "On", "Arrive", "At");
		for (int i=0; i<hops; i++) {
			System.out.format("%-5d%-10s%-10s%-10s%-10s%-10s%-10s%-10s%n", i+1, this.stopNames.get(c), " (" + stops.get(c) + ")", formattedDepartureTime.get(i), flightCode.get(i), this.stopNames.get(c+1), " (" + stops.get(c+1) + ")", formattedArrivalTime.get(i));
			c+=2;
		}
		System.out.println("Total journey cost    = £" + totalCost);
		System.out.println("Total time in the air = " + totalTime);

	}

}
