package engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import buildings.EconomicBuilding;
import buildings.Market;
import buildings.MilitaryBuilding;
import exceptions.FriendlyFireException;
import exceptions.TargetNotReachedException;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Status;
import units.Unit;

public class Game {
	private Player player;
	private ArrayList<City> availableCities;
	private ArrayList<Distance> distances;
	private final int maxTurnCount = 30;
	private int currentTurnCount;

	public Game(String playerName, String playerCity) throws IOException {

		player = new Player(playerName);
		availableCities = new ArrayList<City>();
		distances = new ArrayList<Distance>();
		currentTurnCount = 1;
		loadCitiesAndDistances();
		for (City c : availableCities) {
			if (c.getName().equals(playerCity))

				player.getControlledCities().add(c);

			else
				loadArmy(c.getName(), c.getName().toLowerCase() + "_army.csv");

		}
	}

	private void loadCitiesAndDistances() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("distances.csv"));
		String currentLine = br.readLine();
		ArrayList<String> names = new ArrayList<String>();

		while (currentLine != null) {

			String[] content = currentLine.split(",");
			if (!names.contains(content[0])) {
				availableCities.add(new City(content[0]));
				names.add(content[0]);
			} else if (!names.contains(content[1])) {
				availableCities.add(new City(content[1]));
				names.add(content[1]);
			}
			distances.add(new Distance(content[0], content[1], Integer.parseInt(content[2])));
			currentLine = br.readLine();

		}
		br.close();
	}

	public void loadArmy(String cityName, String path) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(path));
		String currentLine = br.readLine();
		Army resultArmy = new Army(cityName);
		while (currentLine != null) {
			String[] content = currentLine.split(",");
			String unitType = content[0].toLowerCase();
			int unitLevel = Integer.parseInt(content[1]);
			Unit u = null;
			if (unitType.equals("archer")) {

				if (unitLevel == 1)
					u = (new Archer(1, 60, 0.4, 0.5, 0.6));

				else if (unitLevel == 2)
					u = (new Archer(2, 60, 0.4, 0.5, 0.6));
				else
					u = (new Archer(3, 70, 0.5, 0.6, 0.7));
			} else if (unitType.equals("infantry")) {
				if (unitLevel == 1)
					u = (new Infantry(1, 50, 0.5, 0.6, 0.7));

				else if (unitLevel == 2)
					u = (new Infantry(2, 50, 0.5, 0.6, 0.7));
				else
					u = (new Infantry(3, 60, 0.6, 0.7, 0.8));
			} else if (unitType.equals("cavalry")) {
				if (unitLevel == 1)
					u = (new Cavalry(1, 40, 0.6, 0.7, 0.75));

				else if (unitLevel == 2)
					u = (new Cavalry(2, 40, 0.6, 0.7, 0.75));
				else
					u = (new Cavalry(3, 60, 0.7, 0.8, 0.9));
			}
			resultArmy.getUnits().add(u);
			u.setParentArmy(resultArmy);
			currentLine = br.readLine();
		}
		br.close();
		for (City c : availableCities) {
			if (c.getName().toLowerCase().equals(cityName.toLowerCase()))
				c.setDefendingArmy(resultArmy);
		}
	}

	public ArrayList<City> getAvailableCities() {
		return availableCities;
	}

	public ArrayList<Distance> getDistances() {
		return distances;
	}

	public int getCurrentTurnCount() {
		return currentTurnCount;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getMaxTurnCount() {
		return maxTurnCount;
	}

	public void setCurrentTurnCount(int currentTurnCount) {
		this.currentTurnCount = currentTurnCount;
	}

	public void targetCity(Army army, String targetName) throws TargetNotReachedException {
		if (army.getCurrentStatus() != Status.IDLE) {
			throw new TargetNotReachedException(
					"You can not change your path while you are on the road to another city.");
		} else {
			String currentLocation = army.getCurrentLocation();
			int noOfDistances = this.distances.size();
			for (int i = 0; i < noOfDistances; i++) {

				String from = this.distances.get(i).getFrom();
				String to = this.distances.get(i).getTo();
				int distance = this.distances.get(i).getDistance();

				if (from.equals(currentLocation) && to.equals(targetName)
						|| from.equals(targetName) && to.equals(currentLocation)) {
					army.setDistancetoTarget(distance);
					army.setTarget(targetName);
					break;
				}
			}
		}
	}

	public void endTurn() {
		endTurnBuildings();
		endTurnArmies();
		endTurnCities();
		this.currentTurnCount++;
	}
	
	private void endTurnBuildings() {
		int noOfPlayerCities = player.getControlledCities().size();
		for (int i = 0; i < noOfPlayerCities; i++) {
			City curCity = player.getControlledCities().get(i);
			ArrayList<EconomicBuilding> economicBuildings = curCity.getEconomicalBuildings();
			ArrayList<MilitaryBuilding> militaryBuildings = curCity.getMilitaryBuildings();
			int noOfEconomicBuildings = economicBuildings.size();
			int noOfMilitaryBuildings = militaryBuildings.size();

			for (int j = 0; j < noOfEconomicBuildings; j++) {
				EconomicBuilding theBuilding = economicBuildings.get(j);
				theBuilding.setCoolDown(false);
				if (theBuilding instanceof Market) {
					this.player.setTreasury(this.player.getTreasury() + theBuilding.harvest());
				} else {
					this.player.setFood(this.player.getFood() + theBuilding.harvest());
				}
			}

			for (int j = 0; j < noOfMilitaryBuildings; j++) {
				militaryBuildings.get(j).setCoolDown(false);
				militaryBuildings.get(j).setCurrentRecruit(0);
			}

		}
	}
	
	private void endTurnCities() {
		int noOfAvailableCities = availableCities.size();
		for(int i=0;i<noOfAvailableCities;i++) {
			City curCity = availableCities.get(i);
			if (curCity.isUnderSiege()) { // check if isUnderSiege and turnsUnderSeige will be updated to false and 0
				curCity.setTurnsUnderSiege(curCity.getTurnsUnderSiege()+1); // respectively if turnsUnderSeige is 3
				decCurSoldierCount(curCity.getDefendingArmy());
				if (curCity.getTurnsUnderSiege() == 3) {
					curCity.setUnderSiege(false);
					curCity.setTurnsUnderSiege(-1);
				}
			}
		}
		
	}
	
	private void endTurnArmies() {
		int noOfPlayerArmies = player.getControlledArmies().size();
		for (int i = 0; i < noOfPlayerArmies; i++) {
			Army army = this.player.getControlledArmies().get(i);
			int distanceToTarget = army.getDistancetoTarget();
			if (!army.getTarget().equals("")) { // why is it wrong to check if(distanceToTarget>0) ?
				army.setDistancetoTarget(--distanceToTarget);
				if (distanceToTarget == 0) {
					army.setCurrentLocation(army.getTarget());
					army.setCurrentStatus(Status.IDLE);
					army.setDistancetoTarget(-1);
					army.setTarget("");
				}
			}
			if (this.player.getFood() > army.foodNeeded())
				this.player.setFood(this.player.getFood() - army.foodNeeded());
			else {
				this.player.setFood(0);
				decCurSoldierCount(army);
			}
		}
	}

	private void decCurSoldierCount(Army army) {
		ArrayList<Unit> units = army.getUnits();
		for (Unit u : units) {
			u.setCurrentSoldierCount((int) (0.9 * u.getCurrentSoldierCount()));
		}
	}

	/*
	 * public void changeArmyStatus(String cityUnderSiege) { ArrayList<Army>
	 * playerArmies = this.player.getControlledArmies(); int size =
	 * playerArmies.size(); for(int i=0;i<size;i++) {
	 * if(playerArmies.get(i).getTarget().equals(cityUnderSiege)) {
	 * playerArmies.get(i) } } }
	 */

	public void occupy(Army a, String cityName) {
		for (City c : availableCities) {
			if (c.getName().equals(cityName)) {
				this.player.getControlledCities().add(c);
				c.setDefendingArmy(a);
				c.setUnderSiege(false);
				c.setTurnsUnderSiege(-1);
				//a.setDistancetoTarget(-1);
				//a.setTarget("");
			}
		}
	}

	public void autoResolve(Army attacker, Army defender) throws FriendlyFireException {
		boolean flag=true;
		while (true) {
			int Idx1 = (int)(Math.random()*attacker.getUnits().size());
			int Idx2 = (int)(Math.random()*defender.getUnits().size());
			if(flag) {
				attacker.getUnits().get(Idx1).attack(defender.getUnits().get(Idx2));
				if(defender.getUnits().size()==0) {
					occupy(attacker,defender.getCurrentLocation());
					break;
				}
				flag=false;
			}else {
				defender.getUnits().get(Idx2).attack(attacker.getUnits().get(Idx1));
				if(attacker.getUnits().size()==0) {
					this.player.getControlledArmies().remove(attacker);
					break;
				}
				flag=true;
			}
		}
	}

	public boolean isGameOver() {
		return this.player.getControlledCities().size() == availableCities.size()
				|| currentTurnCount == maxTurnCount + 1;
	}

}
