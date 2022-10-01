package F28DA_CW2;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class FlyingPlannerTest {

	FlyingPlanner fi;

	@Before
	public void initialize() {
		fi = new FlyingPlanner();
		try {
			fi.populate(new FlightsReader());
		} catch (FileNotFoundException | FlyingPlannerException e) {
			e.printStackTrace();
		}
	}

	// Add your own tests here
	// You can get inspiration from FlyingPlannerProvidedTest

	@Test
	public void leastHopCustomTest() {
		FlyingPlanner fp = new FlyingPlanner();
		HashSet<String[]> airports = new HashSet<String[]>();
		String[] a1= {"A1","City1","AirportName1"}; airports.add(a1);
		String[] a2= {"A2","City2","AirportName2"}; airports.add(a2);
		String[] a3= {"A3","City2","AirportName3"}; airports.add(a3);
		String[] a4= {"A4","City4","AirportName4"}; airports.add(a4);
		HashSet<String[]>  flights = new HashSet<String[]>();
		String[] f1= {"F1","A1","1000","A2","1100","500"}; flights.add(f1);
		String[] f2= {"F2","A1","1000","A3","1100","50"}; flights.add(f2);
		String[] f3= {"F3","A3","1000","A2","1100","50"}; flights.add(f3);
		String[] f4= {"F4","A3","1000","A4","1100","50"}; flights.add(f4);
		String[] f5= {"F5","A1","1000","A4","1100","50"}; flights.add(f5);
		fp.populate(airports, flights);
		try {
			System.out.println(fp);
			Journey lc = fp.leastHop("A1", "A4");
			assertEquals(1,lc.totalHop());
		} catch (FlyingPlannerException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void leastCostExcludingCustomTest() {
		FlyingPlanner fp = new FlyingPlanner();
		HashSet<String[]> airports = new HashSet<String[]>();
		String[] a1= {"A1","City1","AirportName1"}; airports.add(a1);
		String[] a2= {"A2","City2","AirportName2"}; airports.add(a2);
		String[] a3= {"A3","City2","AirportName3"}; airports.add(a3);
		String[] a4= {"A4","City4","AirportName4"}; airports.add(a4);
		String[] a5= {"A5","City5","AirportName5"}; airports.add(a5);
		String[] a6= {"A6","City6","AirportName6"}; airports.add(a6);
		HashSet<String[]>  flights = new HashSet<String[]>();
		String[] f1= {"F1","A1","1000","A2","1100","500"}; flights.add(f1);
		String[] f2= {"F2","A1","1000","A3","1100","10"}; flights.add(f2);
		String[] f3= {"F3","A3","1000","A2","1100","50"}; flights.add(f3);
		String[] f4= {"F4","A3","1000","A4","1100","10"}; flights.add(f4);
		String[] f5= {"F5","A1","1000","A4","1100","500"}; flights.add(f5);
		String[] f6= {"F6","A1","1000","A6","1100","10"}; flights.add(f6);
		String[] f7= {"F7","A6","1000","A4","1100","20"}; flights.add(f7);
		fp.populate(airports, flights);
		try {
			System.out.println(fp);
			List<String> excluding = new LinkedList<>();
			excluding.add("A3");
			Journey lc = fp.leastCost("A1", "A4", excluding);
			assertEquals(30,lc.totalCost());
			Journey j2 = fp.leastCost("A1", "A4");
			assertEquals(20,j2.totalCost());
		} catch (FlyingPlannerException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void leastHopExcludingCustomTest() {
		FlyingPlanner fp = new FlyingPlanner();
		HashSet<String[]> airports = new HashSet<String[]>();
		String[] a1= {"A1","City1","AirportName1"}; airports.add(a1);
		String[] a2= {"A2","City2","AirportName2"}; airports.add(a2);
		String[] a3= {"A3","City2","AirportName3"}; airports.add(a3);
		String[] a4= {"A4","City4","AirportName4"}; airports.add(a4);
		String[] a5= {"A5","City5","AirportName5"}; airports.add(a5);
		String[] a6= {"A6","City6","AirportName6"}; airports.add(a6);
		String[] a7= {"A7","City7","AirportName7"}; airports.add(a7);
		HashSet<String[]>  flights = new HashSet<String[]>();
		String[] f1= {"F1","A1","1000","A2","1100","500"}; flights.add(f1);
		String[] f2= {"F2","A1","1000","A3","1100","10"}; flights.add(f2);
		String[] f3= {"F3","A3","1000","A2","1100","50"}; flights.add(f3);
		String[] f4= {"F4","A3","1000","A4","1100","10"}; flights.add(f4);
		String[] f5= {"F5","A1","1000","A4","1100","500"}; flights.add(f5);
		String[] f6= {"F6","A5","1000","A6","1100","10"}; flights.add(f6);
		String[] f7= {"F7","A6","1000","A4","1100","20"}; flights.add(f7);
		String[] f8= {"F8","A3","1000","A7","1100","20"}; flights.add(f8);
		String[] f9= {"F9","A4","1000","A6","1100","20"}; flights.add(f9);
		String[] f10= {"F10","A6","1000","A7","1100","20"}; flights.add(f10);
		fp.populate(airports, flights);
		try {
			System.out.println(fp);
			List<String> excluding = new LinkedList<>();
			excluding.add("A3");
			Journey lc = fp.leastHop("A1", "A7", excluding);
			assertEquals(3,lc.totalHop());
			Journey j2 = fp.leastHop("A1", "A7");
			assertEquals(2,j2.totalHop());
		} catch (FlyingPlannerException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void directlyConnectedCustomTest() {
		FlyingPlanner fp = new FlyingPlanner();
		HashSet<String[]> airports = new HashSet<String[]>();
		String[] a1= {"A1","City1","AirportName1"}; airports.add(a1);
		String[] a2= {"A2","City2","AirportName2"}; airports.add(a2);
		String[] a3= {"A3","City2","AirportName3"}; airports.add(a3);
		String[] a4= {"A4","City4","AirportName4"}; airports.add(a4);
		String[] a5= {"A5","City5","AirportName5"}; airports.add(a5);
		String[] a6= {"A6","City6","AirportName6"}; airports.add(a6);
		String[] a7= {"A7","City7","AirportName7"}; airports.add(a7);
		HashSet<String[]>  flights = new HashSet<String[]>();
		String[] f1= {"F1","A1","1000","A2","1100","500"}; flights.add(f1);
		String[] f2= {"F2","A2","1000","A1","1100","10"}; flights.add(f2);
		String[] f3= {"F3","A1","1000","A3","1100","50"}; flights.add(f3);
		String[] f4= {"F4","A3","1000","A1","1100","10"}; flights.add(f4);
		String[] f5= {"F5","A1","1000","A4","1100","500"}; flights.add(f5);
		String[] f6= {"F6","A4","1000","A1","1100","10"}; flights.add(f6);
		String[] f7= {"F7","A6","1000","A4","1100","20"}; flights.add(f7);
		String[] f8= {"F8","A3","1000","A7","1100","20"}; flights.add(f8);
		String[] f9= {"F9","A4","1000","A6","1100","20"}; flights.add(f9);
		String[] f10= {"F10","A6","1000","A7","1100","20"}; flights.add(f10);
		fp.populate(airports, flights);
		System.out.println(fp);

		Airport a = fp.airport("A1");
		Set<Airport> s = fp.directlyConnected(a);
		assertEquals(3, s.size());
	}

	@Test
	public void leastCostMeetUpCustomTest() {
		FlyingPlanner fp = new FlyingPlanner();
		HashSet<String[]> airports = new HashSet<String[]>();
		String[] a1= {"A1","City1","AirportName1"}; airports.add(a1);
		String[] a2= {"A2","City2","AirportName2"}; airports.add(a2);
		String[] a3= {"A3","City2","AirportName3"}; airports.add(a3);
		String[] a4= {"A4","City4","AirportName4"}; airports.add(a4);
		String[] a5= {"A5","City5","AirportName5"}; airports.add(a5);
		String[] a6= {"A6","City6","AirportName6"}; airports.add(a6);
		String[] a7= {"A7","City7","AirportName7"}; airports.add(a7);
		HashSet<String[]>  flights = new HashSet<String[]>();
		String[] f1= {"F1","A1","1000","A2","1100","500"}; flights.add(f1);
		String[] f2= {"F2","A1","1000","A3","1100","10"}; flights.add(f2);
		String[] f3= {"F3","A3","1000","A2","1100","50"}; flights.add(f3);
		String[] f4= {"F4","A3","1000","A4","1100","10"}; flights.add(f4);
		String[] f5= {"F5","A1","1000","A4","1100","20"}; flights.add(f5);
		String[] f6= {"F6","A2","1000","A4","1100","10"}; flights.add(f6);
		String[] f7= {"F7","A2","1000","A5","1100","200"}; flights.add(f7);
		String[] f8= {"F8","A1","1000","A5","1100","20"}; flights.add(f8);
		String[] f9= {"F9","A4","1000","A6","1100","20"}; flights.add(f9);
		String[] f10= {"F10","A6","1000","A7","1100","20"}; flights.add(f10);
		fp.populate(airports, flights);
		try {
			String airport = fp.leastCostMeetUp("A1", "A2");
			assertEquals("A4",airport);
		} catch (FlyingPlannerException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void leastHopMeetUpCustomTest() {
		FlyingPlanner fp = new FlyingPlanner();
		HashSet<String[]> airports = new HashSet<String[]>();
		String[] a1= {"A1","City1","AirportName1"}; airports.add(a1);
		String[] a2= {"A2","City2","AirportName2"}; airports.add(a2);
		String[] a3= {"A3","City2","AirportName3"}; airports.add(a3);
		String[] a4= {"A4","City4","AirportName4"}; airports.add(a4);
		String[] a5= {"A5","City5","AirportName5"}; airports.add(a5);
		String[] a6= {"A6","City6","AirportName6"}; airports.add(a6);
		String[] a7= {"A7","City7","AirportName7"}; airports.add(a7);
		HashSet<String[]>  flights = new HashSet<String[]>();
		String[] f1= {"F1","A1","1000","A2","1100","500"}; flights.add(f1);
		String[] f2= {"F2","A1","1000","A3","1100","10"}; flights.add(f2);
		String[] f3= {"F3","A2","1000","A3","1100","50"}; flights.add(f3);
		String[] f4= {"F4","A3","1000","A4","1100","10"}; flights.add(f4);
		String[] f5= {"F5","A4","1000","A5","1100","20"}; flights.add(f5);
		String[] f6= {"F6","A2","1000","A5","1100","10"}; flights.add(f6);
		String[] f7= {"F7","A6","1000","A5","1100","200"}; flights.add(f7);
		String[] f8= {"F8","A1","1000","A6","1100","20"}; flights.add(f8);
		String[] f9= {"F9","A4","1000","A6","1100","20"}; flights.add(f9);
		String[] f10= {"F10","A6","1000","A7","1100","20"}; flights.add(f10);
		fp.populate(airports, flights);
		try {
			String airport = fp.leastCostMeetUp("A1", "A2");
			assertEquals("A5",airport);
		} catch (FlyingPlannerException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void flightsCustomTest() {
		FlyingPlanner fp = new FlyingPlanner();
		HashSet<String[]> airports = new HashSet<String[]>();
		String[] a1= {"A1","City1","AirportName1"}; airports.add(a1);
		String[] a2= {"A2","City2","AirportName2"}; airports.add(a2);
		String[] a3= {"A3","City2","AirportName3"}; airports.add(a3);
		String[] a4= {"A4","City4","AirportName4"}; airports.add(a4);
		String[] a5= {"A5","City5","AirportName5"}; airports.add(a5);
		HashSet<String[]>  flights = new HashSet<String[]>();
		String[] f1= {"F1","A1","1000","A2","1100","500"}; flights.add(f1);
		String[] f2= {"F2","A1","1000","A3","1100","10"}; flights.add(f2);
		String[] f3= {"F3","A2","1000","A3","1100","50"}; flights.add(f3);
		String[] f4= {"F4","A3","1000","A4","1100","10"}; flights.add(f4);
		String[] f5= {"F5","A4","1000","A5","1100","20"}; flights.add(f5);

		fp.populate(airports, flights);
		Airport a = fp.flight("F1").getTo();
		assertEquals("City2",a.getName());
		a = fp.flight("F1").getFrom();
		assertEquals("City1",a.getName());
		assertEquals("1000",fp.flight("F1").getFromGMTime());
		assertEquals("1100",fp.flight("F1").getToGMTime());
		assertEquals(500,fp.flight("F1").getCost());
	}

}
