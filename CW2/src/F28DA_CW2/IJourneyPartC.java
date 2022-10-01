package F28DA_CW2;

public interface IJourneyPartC<A extends IAirportPartB,F extends IFlight> {

	/** Returns the total time in connection of the journey */
	int connectingTime() throws FlyingPlannerException;
	
	/** Returns the total travel time of the journey */
	int totalTime() throws FlyingPlannerException;
}
