import java.util.Arrays;

public class ArrayUtils {

	public static int[] shiftArrayCyclic(int[] array, int move, char direction) {

		if (array.length == 0) {
			return array;
		}

		int[] newArray = new int[array.length];

		// Moving right
		if ((direction == 'R' && move > 0) || (direction == 'L' && move < 0)) {
			move = Math.abs(move);
			move = move % array.length;
			for (int i = 0; i < array.length; i++) {
				if (i + move >= array.length) { // Out of bounds
					int temp = array.length - i - 1;
					newArray[move - temp - 1] = array[i];
				} else {
					newArray[i + move] = array[i];
				}
			}
			return newArray;
		} // Moving left
		if ((direction == 'L' && move > 0) || (direction == 'R' && move < 0)) {
			move = Math.abs(move);
			move = move % array.length;
			for (int i = 0; i < array.length; i++) { // Out of bounds
				if (i - move < 0) {
					int temp = move - i - 1;
					newArray[array.length - temp - 1] = array[i];
				} else {
					newArray[i - move] = array[i];
				}
			}
			return newArray;
		}

		return array; //Replace this with the correct returned value

	}

	public static int findShortestPath(int[][] m, int i, int j) {

		// Copy the array
		int[][] copyArray = new int[m.length][m.length];
		for (int k = 0; k < m.length; k++) {
			for (int l = 0; l < m.length; l++) {
				copyArray[k][l] = m[k][l];
			}
		}

		// Calls for a recursion version with extra perm
		return findShortestPathRec(m, i, j, i, copyArray);

	}

	private static int findShortestPathRec(int[][] m, int i, int j, int prev, int[][] copy) {

		// Got to destination
		if (i == j) {
			return 0;
		}

		// Checks for all available paths
		int shortest = m.length + 1;
		for (int k = 0; k < m.length; k++) {
			if ((copy[i][k] == 1) && (k != prev)) {
				if (k != j) {
					copy[i][k] = 0;
				}
				int temp = findShortestPathRec(m, k, j, i, copy);
				if (temp != -1) {
					temp ++;
					shortest = Math.min(shortest, temp);
				}
			}
		}

		// If no path found
		if (shortest == m.length + 1) {
			return -1;
		}

		return shortest;
	}

}