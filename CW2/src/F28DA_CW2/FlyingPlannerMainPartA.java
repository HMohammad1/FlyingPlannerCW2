package F28DA_CW2;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleGraph;

import java.util.Scanner;

public class FlyingPlannerMainPartA {

	public static void main(String[] args) {

        SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> flights = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        flights.addVertex("edinburgh");
        flights.addVertex("heathrow");
        flights.addVertex("dubai");
        flights.addVertex("sydney");
        flights.addVertex("kuala lumpur");

        DefaultWeightedEdge e1 = flights.addEdge("edinburgh", "heathrow");
        flights.setEdgeWeight(e1, 80);

        DefaultWeightedEdge e2 = flights.addEdge("heathrow", "edinburgh");
        flights.setEdgeWeight(e2, 80);

        DefaultWeightedEdge e3 = flights.addEdge("heathrow", "dubai");
        flights.setEdgeWeight(e3, 130);

        DefaultWeightedEdge e4 = flights.addEdge("dubai", "heathrow");
        flights.setEdgeWeight(e4, 130);

        DefaultWeightedEdge e5 = flights.addEdge("heathrow", "sydney");
        flights.setEdgeWeight(e5, 570);

        DefaultWeightedEdge e6 = flights.addEdge("sydney", "heathrow");
        flights.setEdgeWeight(e6, 570);

        DefaultWeightedEdge e7 = flights.addEdge("dubai", "kuala lumpur");
        flights.setEdgeWeight(e7, 170);

        DefaultWeightedEdge e8 = flights.addEdge("kuala lumpur", "dubai");
        flights.setEdgeWeight(e8, 170);

        DefaultWeightedEdge e9 = flights.addEdge("dubai", "edinburgh");
        flights.setEdgeWeight(e9, 190);

        DefaultWeightedEdge e10 = flights.addEdge("edinburgh", "dubai");
        flights.setEdgeWeight(e10, 190);

        DefaultWeightedEdge e11 = flights.addEdge("kuala lumpur", "sydney");
        flights.setEdgeWeight(e11, 150);

        DefaultWeightedEdge e12 = flights.addEdge("sydney", "kuala lumpur");
        flights.setEdgeWeight(e12, 150);

        System.out.println("The following airports are used :");
        System.out.println("Flight								Cost");
        for (DefaultWeightedEdge edge : flights.edgeSet()) {
            double cost = flights.getEdgeWeight(edge);
            String startAirport = flights.getEdgeSource(edge);
            String destination = flights.getEdgeTarget(edge);
            System.out.format("%-15s%-5s%-15s%5s%n", startAirport, "<->", destination, "£" + cost);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the start airport:");
        String start = scanner.nextLine().toLowerCase();
        System.out.println("Please enter the destination airport:");
        String destination = scanner.nextLine().toLowerCase();

        GraphPath<String, DefaultWeightedEdge> shortestPath = DijkstraShortestPath.findPathBetween(flights, start, destination);
        System.out.println("Shortest ( i . e . cheapest ) path :");

        int i = 0;
        String divider = "-->";
        String dot = ". ";
        double totalCost = 0;
        for (DefaultWeightedEdge edge : shortestPath.getEdgeList()) {
            String startAirport = flights.getEdgeSource(edge);
            String destinationAirport = flights.getEdgeTarget(edge);
            double price = flights.getEdgeWeight(edge);
            totalCost += price;
            i++;
            System.out.format("%d%s%-15s%-5s%-15s%n", i, dot, startAirport, divider, destinationAirport);
        }
        System.out.println("Cost of shortest ( i . e . cheapest ) path = £" + totalCost);
        
	}

}
