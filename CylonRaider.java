package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class CylonRaider extends Fighter {

	private static final int basicAnnualMaintenanceCostExtra = 1000;

	public CylonRaider(String name, int commissionYear, float maximalSpeed, Set<Cylon> crewMembers,
			List<Weapon> weapons) {
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
	}

	public int getAnnualMaintenanceCost() {
		int cost = super.getAnnualMaintenanceCost() + basicAnnualMaintenanceCostExtra;
		cost += 500 * getCrewMembers().size();
		return cost;
	}

}
