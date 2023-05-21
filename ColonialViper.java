package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ColonialViper extends Fighter {

	private static final int basicAnnualMaintenanceCostExtra = 1500;

	public ColonialViper(String name, int commissionYear, float maximalSpeed, Set<CrewWoman> crewMembers,
			List<Weapon> weapons) {
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
	}

	public int getAnnualMaintenanceCost() {
		int cost = super.getAnnualMaintenanceCost() + basicAnnualMaintenanceCostExtra;
		cost -= (500 * getMaximalSpeed());
		cost += 500 * getCrewMembers().size();
		return cost;
	}

}
