package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import units.Archer;
import units.Unit;

public class ArcheryRange extends MilitaryBuilding {

	public ArcheryRange() {
		super(1500, 800, 400);

	}
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException {
	       super.upgrade();
		if (getLevel() == 1) {
			setLevel(2); setUpgradeCost(700); setCoolDown(true); setRecruitmentCost(450);
		}
		else if (getLevel() == 2) {
			setLevel(3); setCoolDown(true); setRecruitmentCost(500);
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
			newUnit= new Archer((getLevel()),60,0.4,0.5,0.6);
		if (getLevel() == 3)
			newUnit= new Archer(3,70,0.5,0.6,0.7);
		return newUnit;
	}
/* public static void main ( String [] args) {
 	Building x = new ArcheryRange();
 	System.out.print(x.isCoolDown());
 }*/
}


