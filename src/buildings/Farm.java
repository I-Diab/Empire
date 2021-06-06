package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;

public class Farm extends EconomicBuilding {

	public Farm() {
		super(1000, 500);

	}
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException{
        super.upgrade();
		if (getLevel() == 1) {
			setLevel(2); setUpgradeCost(700); setCoolDown(true);
		}
		else if (getLevel() == 2) {
			setLevel(3); setCoolDown(true);
		}
}
/*public static void main (String [] args ) throws BuildingInCoolDownException, MaxLevelException {
Building z = new Farm ();
z.upgrade();
System.out.println(z.getLevel());
z.upgrade();
System.out.println(z.getLevel());
z.upgrade();
System.out.println(z.getLevel());
}*/

public int harvest() {
if ( getLevel() == 1)
	return 500;
if ( getLevel() == 2)
	return 700;
if ( getLevel() == 3)
	return 1000;
return 0;
}

}
