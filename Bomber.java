package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class Bomber extends myAbstractCombatShip {

	private int numberOfTechnicians;
	private static final int basicAnnualMaintenanceCost = 5000;

	public Bomber(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons, int numberOfTechnicians){
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
		this.numberOfTechnicians = numberOfTechnicians;
	}

	public int getNumberOfTechnicians() {
		return numberOfTechnicians;
	}

	@Override
	public int getAnnualMaintenanceCost() {
		int cost = super.getAnnualMaintenanceCost();
		cost = (int) (cost * (0.1 * numberOfTechnicians));
		cost += basicAnnualMaintenanceCost;
		return cost;
	}
}
