package engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Unit;

public class Game {
	private Player player;
	private ArrayList<City> availableCities;
	private ArrayList<Distance> distances;
	private final int maxTurnCount = 30;
	private int currentTurnCount = 1;
	
	
	
	public Game(String playerName,String playerCity) throws IOException{
		this.player = new Player(playerName);
		availableCities = new ArrayList<City>();
		distances = new ArrayList<Distance>();
		loadCitiesAndDistances();
		for(int i=0;i<availableCities.size();i++) {
			if(!availableCities.get(i).getName().equals(playerCity)) {
				loadArmy(availableCities.get(i).getName(), (availableCities.get(i).getName()+"_army.csv").toLowerCase());
				//System.out.println(availableCities.get(i).getName());
				//System.out.println(availableCities.get(i).getDefendingArmy());
			}else {
				this.player.getControlledCities().add(availableCities.get(i));
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		Game theGame = new Game("Islam","Cairo");
//		for(int i =0;i<theGame.getAvailableCities().size();i++) {
//			System.out.println(theGame.getAvailableCities().get(i).getName());
//			for(int j=0;j<theGame.getAvailableCities().get(i).getDefendingArmy().getUnits().size();j++) {	
//				System.out.println(theGame.getAvailableCities().get(i).getDefendingArmy().getUnits().get(j) instanceof Infantry);
//				System.out.println(theGame.getAvailableCities().get(i).getDefendingArmy().getUnits().get(j));
//			}
//		}
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public int getCurrentTurnCount() {
		return currentTurnCount;
	}
	
	public void setCurrentTurnCount(int currentTurnCount) {
		this.currentTurnCount = currentTurnCount;
	}
	
	public ArrayList<City> getAvailableCities() {
		return availableCities;
	}
	
	public ArrayList<Distance> getDistances() {
		return distances;
	}
	
	public int getMaxTurnCount() {
		return maxTurnCount;
	}
	
	private void loadCitiesAndDistances() throws IOException{
		FileReader fileReader = new FileReader("distances.csv");
		BufferedReader br = new BufferedReader(fileReader);
		String currentLine="";
		while(br.ready()) {
			currentLine=br.readLine();
			//System.out.println(currentLine);
			String[] citiesAndDistances = currentLine.split(",");
			if(!cityFound(citiesAndDistances[0])) {
				City newCity = new City(citiesAndDistances[0]);
				availableCities.add(newCity);
			}
			if(!cityFound(citiesAndDistances[1])) {
				City newCity = new City(citiesAndDistances[1]);
				availableCities.add(newCity);
			}
			Distance distance = new Distance(citiesAndDistances[0],citiesAndDistances[1],Integer.parseInt(citiesAndDistances[2]));
			distances.add(distance);
		}
	}
	
	public boolean cityFound(String city) {
		for(int i=0;i<availableCities.size();i++) {
			if(availableCities.get(i).getName().equals(city)) {
				return true;
			}
		}
		return false;
	}
	
	
	public void loadArmy(String cityName,String path) throws IOException{
		FileReader fileReader = new FileReader(path);
		BufferedReader br = new BufferedReader(fileReader);
		String currentLine="";
		int i=0;
		for(i=0;i<availableCities.size();i++) {
			if(availableCities.get(i).getName().equals(cityName)) {
				break;
			}
		}
		Army newArmy = new Army(cityName);
		while(br.ready()) {
			currentLine=br.readLine();
			String[] unit = currentLine.split(",");
			Unit newUnit=null;
			if(unit[0].equals("Archer")) {
				if(Integer.parseInt(unit[1])==1 || Integer.parseInt(unit[1])==2) {
					newUnit= new Archer(Integer.parseInt(unit[1]),60,0.4,0.5,0.6);
				}else{
					newUnit= new Archer(3,70,0.5,0.6,0.7);
				}
			}else if(unit[0].equals("Infantry")) {
				if(Integer.parseInt(unit[1])==1 || Integer.parseInt(unit[1])==2) {
					newUnit= new Infantry(Integer.parseInt(unit[1]),50,0.5,0.6,0.7);
				}else{
					newUnit= new Infantry(3,60,0.6,0.7,0.8);
				}
			}else {
				if(Integer.parseInt(unit[1])==1 || Integer.parseInt(unit[1])==2) {
					newUnit= new Cavalry(Integer.parseInt(unit[1]),40,0.6,0.7,0.75);
				}else{
					newUnit= new Cavalry(3,60,0.7,0.8,0.9);
				}
			}
			newArmy.getUnits().add(newUnit);
			//availableCities.get(i).getDefendingArmy().getUnits().add(newUnit);
		}
		availableCities.get(i).setDefendingArmy(newArmy);
	}
	


}
