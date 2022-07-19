package model;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

import controller.GameViewManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import javafx.util.Pair;

public class Bot extends Character {
	private Player targetPlayer;
	
	private static final int BOMB_LENGTH = 3;
	
	private static final int ar[] = {-1, 1, 0, 0};
	private static final int ac[] = {0, 0, -1, 1};
	
	private int[][] bombMap = new int[17][17];
	private int[][] dist;
	private int[][] pre;
	
	private Random rand;
	
	private int inRow, inCol;
	private int nextRow, nextCol;
	
	private boolean isMoveDone;
	

	public Bot(int direction, double posX, double posY) {
		super(direction, posX, posY);
		rand = new Random();
		bombMap = new int[17][17];
		dist = new int[17][17];
		pre = new int[17][17];
		isMoveDone = true;
	}
	
	public void autoPlay(Character[] playerList, int[][] map) {
		Timeline t = new Timeline();
		t.setCycleCount(Timeline.INDEFINITE);
		t.getKeyFrames().add(new KeyFrame(Duration.millis(30), 
				(ActionEvent e) -> {
					if (this.getLives() <= 0) {
						t.setCycleCount(0);
						t.stop();
						t.pause();
						
					}
					inRow = getCharacterRow(getLayoutY() + 30);
					inCol = getCharacterCol(getLayoutX() + 20);
					
					checkBomb(map);
					checkTarget();
					BFS(map);
					
					runTo(map, 5, 7);
				}
		));
		t.play();
	}
	
	private void BFS(int[][] map) {
		for (int i=0; i<17; i++)
			for (int j=0; j<17; j++) {
				dist[i][j] = 0;
				pre[i][j] = -1;
			}
		Deque<Pair<Integer, Integer>> deque = new LinkedList<Pair<Integer, Integer>>();
		
		dist[inRow][inCol] = 1;
		deque.addLast(new Pair<>(inRow, inCol));
		while (!deque.isEmpty()) {
			int r = deque.getFirst().getKey();
			int c = deque.getFirst().getValue();
			deque.removeFirst();
			for (int i=0; i<4; i++) {
				int tr = r + ar[i];
				int tc = c + ac[i];
				
				if (map[tr][tc] != 1 && map[tr][tc] != 2 && map[tr][tc] != 9 && dist[tr][tc] == 0 && bombMap[tr][tc] == 0) {
					dist[tr][tc] = dist[r][c] + 1;
					pre[tr][tc] = i;
					deque.addLast(new Pair<>(tr, tc));
				}
			}
		}
//		for (int i=0; i<17; i++) {
//			for (int j=0; j<17; j++) {
//				System.out.printf("%3d", dist[i][j]);
//			}
//			System.out.println();
//		}
//		System.out.println("PREEEEE");
//		for (int i=0; i<17; i++) {
//			for (int j=0; j<17; j++) {
//				System.out.printf("%3d", pre[i][j]);
//			}
//			System.out.println();
//		}
	}
	
	private void findNewTarget() {
		
	}
	
	private void checkTarget() {
	}
	
	private void findPath(int row, int col) {
		while (row != inRow || col != inCol) {
			if (pre[row][col] == -1) {
				nextRow = -1;
				nextCol = -1;
				return;
			}
			int preRow = row - ar[pre[row][col]];
			int preCol = col - ac[pre[row][col]];
			nextRow = row;
			nextCol = col;
			row = preRow;
			col = preCol;
		}
	}
	
	private void checkBomb(int[][] map) {
		for (int row=0; row<17; row++)
		for (int col=0; col<17; col++) {
			bombMap[row][col] = 0;
		}
		int[] ar = {-1, 0, 1, 0};
		int[] ac = {0, -1, 0, 1};
		boolean[] isBlocked = {false, false, false, false};
		
		for (int row=0; row<17; row++)
			for (int col=0; col<17; col++) {
				if (map[row][col] != 7) continue;
				
				bombMap[row][col] = 1;
				
				for (int i = 1; i<=BOMB_LENGTH; i++) {
					for (int j=0; j<4; j++) {
						if (!isBlocked[j]) {
							if (map[row + ar[j]*i][col + ac[j]*i] == 1 || map[row + ar[j]*i][col + ac[j]*i] == 2 || map[row + ar[j]*i][col + ac[j]*i] == 9) {
								isBlocked[j] = true;
								continue;
							}
							if (map[row + ar[j]*i][col + ac[j]*i] == 3 || map[row + ar[j]*i][col + ac[j]*i] == 4 || map[row + ar[j]*i][col + ac[j]*i] == 5) {
								isBlocked[j] = true;
								continue;
							}
							bombMap[row + ar[j]*i][ col + ac[j]*i] = 1;						
						}
					}
				}
			}
	}
	
	private void runTo(int[][] map, int row, int col) {
		if (!isMoveDone) {
			moveToNextCell(nextRow, nextCol);
			return;
		}
		
		if (Math.abs(row-inRow) + Math.abs(col-inCol) <= 1) {
			moveToNextCell(row, col);
		} else {
			findPath(row, col);
			if (nextRow > 0 && nextCol > 0) {
				if (map[nextRow][nextCol] >= 3 && map[nextRow][nextCol] <= 5) {
					setPlantingBomb(true);
					int minDist = 100;
					int nR = -1, nC = -1;
					for (int i=0; i<17; i++)
						for (int j=0; j<17; j++)
							if (dist[i][j] < minDist && dist[i][j] != 0) {
								nR = i;
								nC = j;
							}
					if (nR != -1) {
						findPath(nR, nC);
						isMoveDone = false;
						moveToNextCell(nextRow, nextCol);
					}
				} else {
					isMoveDone = false;
					moveToNextCell(nextRow, nextCol);
				}
			}
		}
	}
	
	private void moveToNextCell(int row, int col) {
		double posX = GameViewManager.CELL_SIZE * (col + 0.5);
		double posY = GameViewManager.CELL_SIZE * (row + 0.5);
		
		System.out.println("" + row + ' ' + col);
		System.out.println("" + inRow + ' ' + inCol);
		
		if (getPosX() + getDisMove() + 1 < posX) {
			
			setGoRight(true);
			setGoLeft(false);
			setGoUp(false);
			setGoDown(false);
		} 
		else if (getPosX() - getDisMove() - 1 > posX) {
			setGoRight(false);
			setGoLeft(true);
			setGoUp(false);
			setGoDown(false);
		} 
		else if (getPosY() + getDisMove() + 1< posY) {
			System.out.println("Down");
			setGoRight(false);
			setGoDown(true);
			setGoLeft(false);
			setGoUp(false);
		}
		else if (getPosY() - getDisMove() - 2 > posY) {
			System.out.println("Up");
			
			setGoRight(false);
			setGoDown(false);
			setGoLeft(false);
			setGoUp(true);
		} else {
			isMoveDone = true;
			setGoRight(false);
			setGoDown(false);
			setGoLeft(false);
			setGoUp(false);
		}
	}
}
