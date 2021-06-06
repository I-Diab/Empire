package units;

import java.util.ArrayList;

import exceptions.MaxCapacityException;


public class Army{
	private Status currentStatus;
	private ArrayList<Unit> units;
	private int distancetoTarget;
	private String target;
	private String currentLocation;
	@SuppressWarnings("unused")
	private final int maxToHold=10;

	public Army(String currentLocation) {
		this.currentLocation=currentLocation;
		this.currentStatus=Status.IDLE;
		this.units=new ArrayList<Unit>();
		this.distancetoTarget=-1;
		this.target="";
	}
	public Status getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Status currentStatus) {
		this.currentStatus = currentStatus;
	}

	public ArrayList<Unit> getUnits() {
		return units;
	}

	public void setUnits(ArrayList<Unit> units) {
		this.units = units;
	}

	public int getDistancetoTarget() {
		return distancetoTarget;
	}

	public void setDistancetoTarget(int distancetoTarget) {
		this.distancetoTarget = distancetoTarget;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	public int getMaxToHold() {
		return maxToHold;
	}
	
	public double foodNeeded() {
		int size = units.size();
		int total = 0;
		if(currentStatus==Status.IDLE) {
			for (int i = 0; i < size; i++) {
				total+=this.units.get(i).getIdleUpkeep()*this.units.get(i).getCurrentSoldierCount();
			}
		}else if(currentStatus==Status.MARCHING) {
			for (int i = 0; i < size; i++) {
				total+=this.units.get(i).getMarchingUpkeep()*this.units.get(i).getCurrentSoldierCount();
			}
		}else {
			for (int i = 0; i < size; i++) {
				total+=this.units.get(i).getSiegeUpkeep()*this.units.get(i).getCurrentSoldierCount();
			}
		}
		return total;
	}
	
	public void handleAttackedUnit(Unit u) {
		if(u.getCurrentSoldierCount()<=0) {
			units.remove(u);
		}
		Archer archer = new Archer(1,2,3,4,5);
		archer.getFactor(archer);
	}
	
	public void relocateUnit(Unit unit) throws MaxCapacityException{
		if(this.units.size()==10) {
			throw new MaxCapacityException("The unit can not be added to this army because, the army has reached its maximum capacity.");
		}else {
			unit.getParentArmy().getUnits().remove(unit); // removing using reference.
			this.units.add(unit);
			unit.setParentArmy(this);
		}
	}
	
}
