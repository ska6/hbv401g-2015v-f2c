package models;

import java.util.List;
import java.util.ArrayList;

public class Roster {
	
	public static int MIN_GOAL = 1;
	public static int MIN_DEFENCE = 3;
	public static int MIN_MIDFIELD = 3;
	public static int MIN_DEF_AND_MID = 7;
	public static int MIN_FORWARD = 1;
	public static PlayerPosition[] positions = new PlayerPosition[] {PlayerPosition.GOAL, PlayerPosition.DEFENCE, PlayerPosition.MIDFIELD, PlayerPosition.FORWARD};
	
	private String name;
	private List<Player> players = new ArrayList<>();
	
	public Roster() {}
	
	public Roster(String name) {
		this.name = name;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
	}
	
	public boolean isLegal() {
		if(players.size()!=14) {
			return false;
		}
		if(getPositionCount(PlayerPosition.GOAL) < MIN_GOAL) {
			return false;
		}
		if(getPositionCount(PlayerPosition.DEFENCE) < MIN_DEFENCE) {
			return false;
		}
		if(getPositionCount(PlayerPosition.MIDFIELD) < MIN_MIDFIELD) {
			return false;
		}
		if(getPositionCount(PlayerPosition.FORWARD) < MIN_FORWARD) {
			return false;
		}
		if(getPositionCount(PlayerPosition.DEFENCE) + getPositionCount(PlayerPosition.MIDFIELD) < MIN_DEF_AND_MID) {
			return false;
		}
		return true;
	}
	
	public int getPositionCount(PlayerPosition position) {
		int count = 0;
		for(Player p : players) {
			count += p.getPosition()==position ? 1 : 0;
		}
		return count;
	}
	
	public List<Player> getPlayersByPosition(PlayerPosition pos) {
		List<Player> playersByPos = new ArrayList<>();
		for(Player p : players) {
			if(p.getPosition()==pos) {
				playersByPos.add(p);
			}
		}
		return playersByPos;
	}

}
