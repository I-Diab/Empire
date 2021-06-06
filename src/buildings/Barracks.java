package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import units.Infantry;
import units.Unit;

public class Barracks extends MilitaryBuilding {

	public Barracks() {
		super(2000, 1000, 500);

	}
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException {
	       super.upgrade();
		if (getLevel() == 1) {
			setLevel(2); setUpgradeCost(1500); setCoolDown(true); setRecruitmentCost(550);
		}
		else if (getLevel() == 2) {
			setLevel(3); setCoolDown(true); setRecruitmentCost(600);
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
			newUnit= new Infantry(getLevel(),50,0.5,0.6,0.7);
		if (getLevel() == 3)
			newUnit= new Infantry(3,60,0.6,0.7,0.8);
		return newUnit;
	} 

}
