package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class Fighter extends myAbstractCombatShip {
	
	private static final int basicAnnualMaintenanceCost = 2500;

	public Fighter(String name, int commissionYear, float maximalSpeed, Set<? extends CrewMember> crewMembers, List<Weapon> weapons){
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
	}

	public int getAnnualMaintenanceCost() {
		return basicAnnualMaintenanceCost + super.getAnnualMaintenanceCost() + (int) (1000 * getMaximalSpeed());
	}
	
}
