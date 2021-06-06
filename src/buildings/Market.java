package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;

public class Market extends EconomicBuilding {

	public Market() {
		super(1500, 700);
	}
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException {
	       super.upgrade();
		if (getLevel() == 1) {
			setLevel(2); setUpgradeCost(1000); setCoolDown(true);
		}
		else if (getLevel() == 2) {
			setLevel(3); setCoolDown(true);
		}
	}
	public int harvest() {
		if ( getLevel() == 1)
			return 1000;
		if ( getLevel() == 2)
			return 1500;
		if ( getLevel() == 3)
			return 2000;
		return 0;
	}

}
