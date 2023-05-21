package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Set;

public class TransportShip extends myAbstractSpaceShip {

	private int cargoCapacity;
	private int passengerCapacity;

	private static final int basicAnnualMaintenanceCost = 3000;
	
	public TransportShip(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, int cargoCapacity, int passengerCapacity){
		super(name, commissionYear, maximalSpeed, crewMembers);
	}

	@Override
	public int getAnnualMaintenanceCost() {
		return basicAnnualMaintenanceCost + (5 * cargoCapacity) + (3 * passengerCapacity);
	}
}
