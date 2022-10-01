package F28DA_CW2;

import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class FlyingPlannerMainPartBC {

	public static void main(String[] args) throws ParseException {
		// Your implementation should be in FlyingPlanner.java, this class is only to
		// run the user interface of your programme.

		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter the start airport:");
		String start = scanner.nextLine();
		System.out.println("Please enter the destination airport:");
		String destination = scanner.nextLine();
		printMenu();
		FlyingPlanner fi = new FlyingPlanner();
		try {
			fi.populate(new FlightsReader());
			boolean quit = false;

			while (!quit) {
				int action = scanner.nextInt();
				scanner.nextLine();

				switch (action) {
					case 0:
						System.out.println("You have now exited the program");
						quit = true;
						break;
					case 1:
						fi.leastCost(start, destination).display();
						break;
					case 2:
						fi.leastHop(start, destination).display();
						break;
					case 3:
						List<String> excludingLeastCost = new LinkedList<>();
						boolean exitScanner = false;
						System.out.println("Please enter the airport codes you wish to exclude, type exit to stop.");
						while (!exitScanner) {
							String exclude = scanner.nextLine();
							if (exclude.equals("exit")) {
								exitScanner = true;
								fi.leastCost(start, destination, excludingLeastCost).display();
								break;
							}else {
								excludingLeastCost.add(exclude);
							}
						}
						break;
					case 4:
						List<String> excludingLeastHop = new LinkedList<>();
						exitScanner = false;
						System.out.println("Please enter the airport codes you wish to exclude, type exit to stop.");
						while (!exitScanner) {
							String exclude = scanner.nextLine();
							if (exclude.equals("exit")) {
								exitScanner = true;
								fi.leastCost(start, destination, excludingLeastHop).display();
								break;
							}else {
								excludingLeastHop.add(exclude);
							}
						}
						break;
					case 5:
						String leastCostMeetup = fi.leastCostMeetUp(start, destination);
						System.out.println("The least cost meetup for the two airports privded is: " + leastCostMeetup);
						break;
					case 6:
						String leastHopMeetup = fi.leastHopMeetUp(start, destination);
						System.out.println("The least hop meetup for the two airports privded is: " + leastHopMeetup);
						break;
					case 7:
						Airport airport = fi.airport(start);
						Set<Airport> allAirports = fi.directlyConnected(airport);
						System.out.println("All the directly connected airports to " + start + ":");
						for (Airport a : allAirports) {
							System.out.println(a.getCode());
						}
						break;
					case 8:
						printMenu();
						break;
				}
			}

		} catch (FileNotFoundException | FlyingPlannerException e) {
			e.printStackTrace();
		}

	}

	private static void printMenu() {
		System.out.println("Available actions:\npress");
		System.out.println("0 - to quit\n" +
				"1 - get least cost journey\n" +
				"2 - get least hop journey\n" +
				"3 - get least cost journey excluding airports\n" +
				"4 - get least hop journey excluding airports\n" +
				"5 - get least cost meet up\n" +
				"6 - get least hop meet up\n" +
				"7 - get directly connected flights for aiport 'start'\n" +
				"8 - print available actions");

	}


}
