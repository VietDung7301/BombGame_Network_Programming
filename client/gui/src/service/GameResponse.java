package service;

import model.character.Character;

import java.util.ArrayList;

import model.bomb.Boom;

public class GameResponse {
	private int map[][];
	private Character[] playerList;
	private ArrayList<Boom> boomList;
	private double timeLeft;
	
	public GameResponse() {
		this.map = new int[17][17];
		this.playerList = new Character[4];
		this.boomList = new ArrayList<Boom>();
	}
	
	public int[][] getMap() {
		return map;
	}
	public Character[] getPlayerList() {
		return playerList;
	}
	public ArrayList<Boom> getBoomList() {
		return boomList;
	}
	public double getTimeLeft() {
		return this.timeLeft;
	}
	
	public void setPlayerInfor(int i, Character player) {
		this.playerList[i] = player;
	}
	public void addBoom(Boom boom) {
		boomList.add(boom);
	}
	public void setMap(int i, int j, int val) {
		this.map[i][j] = val;
	}
	public void setTimeLeft(double time) {
		this.timeLeft = time;
	}
}
