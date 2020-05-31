package model;

import java.util.Arrays;
import java.util.Random;

public class Dice {

	private static Random random = new Random();
	
	/**
	 * Rolls 6-sided dices.
	 * 
	 * @param numberOfDices The number of dices to be rolled.
	 * @return An array containing the each rolled dice value. The returned array
	 * is sorted from least to highest value.
	 */
	public static int[] roll(int numberOfDices) {
		int[] ret = new int[numberOfDices];
		
		synchronized (random) {
			for (int i = 0; i < numberOfDices; ++i) {
				ret[i] = random.nextInt(6) + 1;
			}
		}
		
		Arrays.sort(ret);
		
		return ret;
	}
	
	private Dice() {
	}
}
