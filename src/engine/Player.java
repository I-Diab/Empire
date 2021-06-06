package engine;

import java.util.ArrayList;

import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.Building;
import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import buildings.Stable;
import exceptions.BuildingInCoolDownException;
import exceptions.FriendlyCityException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;
import exceptions.TargetNotReachedException;
import units.Army;
import units.Status;
import units.Unit;

public class Player {
	private String name;
	private ArrayList<City> controlledCities;
	private ArrayList<Army> controlledArmies;
	private double treasury;
	private double food;

	public Player(String name) {
		this.name = name;
		this.controlledCities = new ArrayList<City>();
		this.controlledArmies = new ArrayList<Army>();
	}

	public double getTreasury() {
		return treasury;
	}

	public void setTreasury(double treasury) {
		this.treasury = treasury;
	}

	public double getFood() {
		return food;
	}

	public void setFood(double food) {
		this.food = food;
	}

	public String getName() {
		return name;
	}

	public ArrayList<City> getControlledCities() {
		return controlledCities;
	}

	public ArrayList<Army> getControlledArmies() {
		return controlledArmies;
	}
	public void recruitUnit(String type,String cityName) throws BuildingInCoolDownException, MaxRecruitedException, NotEnoughGoldException{
	       int i ;
		for(i=0;i<controlledCities.size();i++) {
			if(controlledCities.get(i).getName().equals(cityName)) {
				break;
			}
			} 
		if(i!=controlledCities.size()) {
			int j;
			for(j=0; j < controlledCities.get(i).getMilitaryBuildings().size() ; j++) {
				 if (controlledCities.get(i).getMilitaryBuildings().get(j).getRecruitmentCost() > treasury ) {
					 throw new NotEnoughGoldException();
			     }
				 if (controlledCities.get(i).getMilitaryBuildings().get(j).isCoolDown()) {
					 throw new BuildingInCoolDownException();
				 }
				 if (controlledCities.get(i).getMilitaryBuildings().get(j).getCurrentRecruit() == controlledCities.get(i).getMilitaryBuildings().get(j).getMaxRecruit()) {
					 throw new MaxRecruitedException();
			     }
				 int upgradeCost = controlledCities.get(i).getMilitaryBuildings().get(j).getRecruitmentCost();
				 Unit newUnit = controlledCities.get(i).getMilitaryBuildings().get(j).recruit();
		   if (type.equals("Archer") && controlledCities.get(i).getMilitaryBuildings().get(j) instanceof ArcheryRange) {
			   treasury = treasury - upgradeCost;
			   controlledCities.get(i).getDefendingArmy().getUnits().add(newUnit);
			   newUnit.setParentArmy(controlledCities.get(i).getDefendingArmy());
		   }  
		   if (type.equals("Infantry") && controlledCities.get(i).getMilitaryBuildings().get(j) instanceof Barracks) {
			   treasury = treasury - upgradeCost;
			   controlledCities.get(i).getDefendingArmy().getUnits().add(newUnit);
			   newUnit.setParentArmy(controlledCities.get(i).getDefendingArmy());
		   }  
		   if (type.equals("Cavalry") && controlledCities.get(i).getMilitaryBuildings().get(j) instanceof Stable ) {
			   treasury = treasury - upgradeCost;
			   controlledCities.get(i).getDefendingArmy().getUnits().add(newUnit);
			   newUnit.setParentArmy(controlledCities.get(i).getDefendingArmy());
		   }  	   
		  }
		 }
	    }

	
	public void build(String type,String cityName) throws NotEnoughGoldException{
		 int i ;
			for(i=0;i<controlledCities.size();i++) {
				if(controlledCities.get(i).getName().equals(cityName)) {
					break;
				}
			}
			MilitaryBuilding x = null;
			EconomicBuilding y = null;
			if (type.equals("ArcheryRange")) {
				int k ; boolean flag = true;
				for (k = 0 ; k < controlledCities.get(i).getMilitaryBuildings().size() ; k++) {
					if (controlledCities.get(i).getMilitaryBuildings().get(k) instanceof ArcheryRange) {
						flag = false ;
						break;
					}
				}
				if (flag) {
				 x= new ArcheryRange();
				 if (treasury < x.getCost())
					 throw new NotEnoughGoldException();
				 treasury = treasury - x.getCost();
				 controlledCities.get(i).getMilitaryBuildings().add(x);
			}
		}
			if (type.equals("Barracks")) {
				int k ; boolean flag = true;
				for (k = 0 ; k < controlledCities.get(i).getMilitaryBuildings().size() ; k++) {
					if (controlledCities.get(i).getMilitaryBuildings().get(k) instanceof Barracks) {
						flag = false ;
						break;
					}
				}
				if (flag) {
				x= new Barracks();
				if (treasury < x.getCost())
					 throw new NotEnoughGoldException();
				 treasury = treasury - x.getCost();
				 controlledCities.get(i).getMilitaryBuildings().add(x);
			}
		}
			if (type.equals("Stable")) {
				int k ; boolean flag = true;
				for (k = 0 ; k < controlledCities.get(i).getMilitaryBuildings().size() ; k++) {
					if (controlledCities.get(i).getMilitaryBuildings().get(k) instanceof Stable) {
						flag = false ;
						break;
					}
				}
				if (flag) {
				x= new Stable();
				if (treasury < x.getCost())
					 throw new NotEnoughGoldException();
				 treasury = treasury - x.getCost();
				 controlledCities.get(i).getMilitaryBuildings().add(x);
				}
			}
			if (type.equals("Farm")) {
				int k ; boolean flag = true;
				for (k = 0 ; k < controlledCities.get(i).getEconomicalBuildings().size() ; k++) {
					if (controlledCities.get(i).getEconomicalBuildings().get(k) instanceof Farm) {
						flag = false ;
						break;
					}
				}
				if (flag) {
				 y= new Farm();
				 if (treasury < y.getCost())
					 throw new NotEnoughGoldException();
				 treasury = treasury - y.getCost();
				 controlledCities.get(i).getEconomicalBuildings().add(y);
			}
		}
			if (type.equals("Market")) {
				int k ; boolean flag = true;
				for (k = 0 ; k < controlledCities.get(i).getEconomicalBuildings().size() ; k++) {
					if (controlledCities.get(i).getEconomicalBuildings().get(k) instanceof Market) {
						flag = false ;
						break;
					}
				}
				if (flag) {
				 y= new Market();
				 if (treasury < y.getCost())
					 throw new NotEnoughGoldException();
				 treasury = treasury - y.getCost();
				 controlledCities.get(i).getEconomicalBuildings().add(y);
			}
		}
	}
	public void upgradeBuilding(Building b) throws NotEnoughGoldException,
	BuildingInCoolDownException, MaxLevelException {
		if (treasury < b.getUpgradeCost())
			throw new NotEnoughGoldException();
		int upgradeCost = b.getUpgradeCost();
		 b.upgrade();
		treasury = treasury - upgradeCost;
	}
	public void initiateArmy(City city,Unit unit) {
		Army newArmy = new Army (city.getName());
		newArmy.getUnits().add(unit);
		for(int i = 0 ; i < city.getDefendingArmy().getUnits().size() ; i++) {
			if( newArmy.getUnits().get(i).equals(unit)) {
				city.getDefendingArmy().getUnits().remove(i);
			}
			unit.setParentArmy(newArmy);
			controlledArmies.add(newArmy);
		}
	}
	public void laySiege(Army army,City city) throws TargetNotReachedException,FriendlyCityException{
		if (! (army.getCurrentLocation().equals(city.getName()))){
			throw new TargetNotReachedException();
		}
		for (int i = 0 ; i < controlledCities.size() ; i++ ) {
			if (controlledCities.get(i).equals(city)) {
				throw new FriendlyCityException();
		     }
		}
			army.setCurrentStatus(Status.BESIEGING);
			city.setUnderSiege(true);
			city.setTurnsUnderSiege(0);
		}
	}
