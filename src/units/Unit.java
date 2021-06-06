package units;

import exceptions.FriendlyFireException;

public abstract class Unit {
	private int level;
	private int maxSoldierCount;
	private int currentSoldierCount;
	private double idleUpkeep;
	private double marchingUpkeep;
	private double siegeUpkeep;
	private Army parentArmy;

	public Unit(int level, int maxSoldierConunt, double idleUpkeep, double marchingUpkeep, double siegeUpkeep) {
		this.level = level;
		this.maxSoldierCount = maxSoldierConunt;
		this.currentSoldierCount = maxSoldierCount;
		this.idleUpkeep = idleUpkeep;
		this.marchingUpkeep = marchingUpkeep;
		this.siegeUpkeep = siegeUpkeep;

	}

	public int getCurrentSoldierCount() {
		return currentSoldierCount;
	}

	public void setCurrentSoldierCount(int currentSoldierCount) {
		this.currentSoldierCount = currentSoldierCount;
	}

	public int getLevel() {
		return level;
	}

	public int getMaxSoldierCount() {
		return maxSoldierCount;
	}

	public double getIdleUpkeep() {
		return idleUpkeep;
	}

	public double getMarchingUpkeep() {
		return marchingUpkeep;
	}

	public double getSiegeUpkeep() {
		return siegeUpkeep;
	}

	public Army getParentArmy() {
		return parentArmy;
	}

	public void setParentArmy(Army parentArmy) {
		this.parentArmy = parentArmy;
	}
	
	public void attack(Unit target) throws FriendlyFireException { ////// if it is the first turn to attack the defending army should i change underSiege
																	// of the attacked city to false ? because if it was sieged and the player decides
		if (isFriendly(target)) {									// to attack in the 2nd turn of sieging the defending armies will decrease by 10%
			throw new FriendlyFireException("You can not attack your own army."); // every turn due to the endTurn method.
		}
		double factor = this.getFactor(target);
		if(target.getCurrentSoldierCount() - (int) (factor * this.currentSoldierCount) <=0)
			target.setCurrentSoldierCount(0);
		else
			target.setCurrentSoldierCount(target.getCurrentSoldierCount() - (int) (factor * this.currentSoldierCount));
		target.getParentArmy().handleAttackedUnit(target);
	}
	
	protected abstract double getFactor(Unit target);

	public boolean isFriendly(Unit target) {
		return this.parentArmy==target.parentArmy;
	}
	
}
