package model;

import java.util.Queue;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

public class Round {
	private Queue<Player> playerQueue = new LinkedList<Player>();
	
	public Player getCurrentPlayer() {
		return playerQueue.peek();
	}
}