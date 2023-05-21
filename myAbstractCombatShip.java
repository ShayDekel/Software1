package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public abstract class myAbstractCombatShip extends myAbstractSpaceShip {

    private List<Weapon> weapons;

    public myAbstractCombatShip(String name, int commissionYear, float maximalSpeed, Set<? extends CrewMember> crewMembers, List<Weapon> weapons) {
        super(name, commissionYear, maximalSpeed, crewMembers);
        this.weapons = List.copyOf(weapons);
        setFirePower(calculateFirePower());
    }

    private int calculateFirePower() {
        int firePower = super.getFirePower();
        for (Weapon weapon : this.weapons) {
            firePower += weapon.getFirePower();
        }
        return firePower;
    }

    public int getAnnualMaintenanceCost() {
        int cost = 0;
        for (Weapon weapon : weapons) {
            cost += weapon.getAnnualMaintenanceCost();
        }
        return cost;
    }
}
