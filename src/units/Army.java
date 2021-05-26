package units;

import java.util.ArrayList;

public class Army {
	
	private Status currentStatus;
	private ArrayList<Unit> units;
	private int distancetoTarget;
	private String target;
	private String currentLocation;
	private final int maxToHold=10;
	
	public Army(String currentLocation) {
		this.currentLocation=currentLocation;
		this.currentStatus=Status.IDLE;
		this.units= new ArrayList<Unit>();
		this.distancetoTarget=-1;
		this.target="";
	}

	public String toString() {
		String result="";
		for(int i=0;i<units.size();i++) {
			result+=units.get(i).getClass().getName()+" "+units.get(i)+"\n";
		}
		return result;
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
		// Should I check if the length ArrayList<Unit> is
		// smaller than or equal to 10 ?
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
	
	
}
