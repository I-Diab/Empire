package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import units.Cavalry;
import units.Unit;

public class Stable extends MilitaryBuilding {

	public Stable() {
		super(2500, 1500, 600);

	}
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException {
	       super.upgrade();
		if (getLevel() == 1) {
			setLevel(2); setUpgradeCost(2000); setCoolDown(true); setRecruitmentCost(650);
		}
		else if (getLevel() == 2) {
			setLevel(3); setCoolDown(true); setRecruitmentCost(700);
		}
	}
	public Unit recruit() throws BuildingInCoolDownException, MaxRecruitedException {
		if ( isCoolDown() )
			throw new BuildingInCoolDownException();
		if (getCurrentRecruit() == getMaxRecruit())
			throw new MaxRecruitedException();
		setCurrentRecruit(getCurrentRecruit() + 1);
		Unit newUnit = null;
		if (getLevel() == 1 || getLevel() == 2 )
			newUnit= new Cavalry(getLevel(),40,0.6,0.7,0.75);
		if (getLevel() == 3)
			newUnit= new Cavalry(3,60,0.7,0.8,0.9);
		return newUnit;
	}
	

}
