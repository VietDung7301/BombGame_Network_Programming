package service;

import model.common.Direction;
import network.ServerConnector;

import java.util.regex.Pattern;

import model.bomb.Boom;
import model.character.Character;

public class GameService {
	private static final String NEW_USER = "#c000#";
	private static final String GET_USER_INFOR = "#c001#";
	private static final String CHANGE_USER_NAME = "#c002#";
	private static final String GET_ROOM_LIST = "#c003#";
	private static final String GET_ROOM_DETAIL = "#c007#";
	private static final String ADD_ROOM = "#c004#";
	private static final String JOIN_ROOOM = "#c005#";
	private static final String START_GAME = "#c006#";
	private static final String PLAY_GAME = "#c008#";
	
	private static final String SERVER_ERROR = "#serr#";
	private static final String NEW_USER_RES = "#s000#";
	private static final String GET_USER_INFOR_RES = "#s001#";
	private static final String CHANGE_USER_NAME_RES = "#s002#";
	private static final String GET_ROOM_LIST_RES = "#s003#";
	private static final String GET_ROOM_DETAIL_RES = "#s007#";
	private static final String ADD_ROOM_RES = "#s004#";
	private static final String JOIN_ROOOM_RES = "#s005#";
	private static final String START_GAME_RES = "#s008#";
	private static final String PLAY_GAME_RES = "#s008#";
	
	ServerConnector connector;
	Converter converter;
	
	public GameService() {
		this.connector = new ServerConnector();
		this.converter = new Converter();
	}
	
	public void testService() {
		System.out.println("Khoi tao ngoi choi: ");
		String req = converter.paramToRequest(NEW_USER);
		String res = connector.sendData(req);
		String userName = res.split("&")[1];
		System.out.println("User name: " + userName);
		
		System.out.println("Tao phong: ");
		req = converter.paramToRequest(ADD_ROOM, "Phong 1");
		res = connector.sendData(req);
		System.out.println("response: " + res);
	}
	
	public GameResponse startGame(int numPlayer) {
		String req = converter.paramToRequest(START_GAME);
		String res = connector.sendData(req);

		String[] resList = res.split("&");
		
		GameResponse gameResponse = new GameResponse();
		
		if (!resList[0].equals(START_GAME_RES)) {
			System.out.println("Got error on start game response");
			return null;
		}
		
		setMap(resList[1], gameResponse);
		
		setPlayerList(resList[2], gameResponse, numPlayer);
		
		setBoomList(resList[3], gameResponse);
		
		setTimeLeft(resList[4], gameResponse);
		
		System.out.println("player List: " + gameResponse.getPlayerList()[0].getPosX());
		
		return gameResponse;
	}
	
	public GameResponse sendPlayerAction(int numPlayer, boolean platingBomb, Direction direction) {
		String req = converter.paramToRequest(PLAY_GAME, platingBomb? "1" : "0", direction.toString());
		String res = connector.sendData(req);

		GameResponse gameResponse = new GameResponse();
		
		String[] resList = res.split("&");
		
		if (!resList[0].equals(START_GAME_RES)) {
			System.out.println("Got error on start game response");
			return null;
		}
		
		setMap(resList[1], gameResponse);
		setPlayerList(resList[2], gameResponse, numPlayer);
		setBoomList(resList[3], gameResponse);
		setTimeLeft(resList[4], gameResponse);
		
		return gameResponse;
	}
	
	public void quitGame() {
		
	}
	
	private void setMap(String str, GameResponse gameResponse) {
		String[] cellList = str.split(Pattern.quote("|"));
		System.out.println("str in map: " + cellList[0] + cellList[1]);
		
		int index = 0;
		for (int i=0; i<17; i++)
			for (int j=0; j<17; j++) {
				gameResponse.setMap(i, j, Integer.parseInt(cellList[index]) );
				index++;
			}
	}
	
	private void setPlayerList(String str, GameResponse gameResponse, int numPlayer) {
		String[] cellList = str.split(Pattern.quote("|"));
		
		for (int i=0; i<numPlayer; i+= 8) {
			Character character = new Character (
					Integer.parseInt(cellList[i]),
					Integer.parseInt(cellList[i + 1]),
					Integer.parseInt(cellList[i + 2]),
					Integer.parseInt(cellList[i + 3]),
					Double.parseDouble(cellList[i + 4]),
					Double.parseDouble(cellList[i + 5]),
					Direction.parseDirection(Integer.parseInt(cellList[i + 6])),
					Integer.parseInt(cellList[i + 7])
				);
			gameResponse.setPlayerInfor(i, character);
		}
	}
	
	private void setBoomList(String str, GameResponse gameResponse) {
		String[] cellList = str.split(Pattern.quote("|"));
		
		int numBoom = cellList.length - cellList.length % 3;
		for (int i=0; i<numBoom; i+= 3) {
			Boom boom = new Boom(
					Integer.parseInt(cellList[i]),
					Integer.parseInt(cellList[i + 1]),
					Integer.parseInt(cellList[i + 2])
				);
			gameResponse.addBoom(boom);
		}
	}
	
	private void setTimeLeft(String str, GameResponse gameResponse) {
		String strTime = str.split(Pattern.quote("$$"))[0];
		gameResponse.setTimeLeft(Double.parseDouble(strTime));
	}
}