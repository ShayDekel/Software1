
public class Assignment02Q03 {

	public static void main(String[] args) {
		int numOfOdd = 0;
		int n = -1;
		// *** your code goes here below ***
		n = Integer.parseInt(args[0]);
		int[] fibb = new int[n];
		fibb[0] = 1;
		fibb[1] = 1;
		numOfOdd = 2;
		for (int i = 2; i < n; i++) {
			fibb[i] = fibb[i - 1] + fibb[i - 2];
			if (fibb[i] % 2 != 0) {
				numOfOdd++;
			}
		}
		
		System.out.println("The first "+ n +" Fibonacci numbers are:");
		// *** your code goes here below ***
		String fibb_string = "";
		for (int i = 0; i < n; i++) {
			fibb_string += fibb[i] + " ";
		}
		System.out.println(fibb_string);

		System.out.println("The number of odd numbers is: "+numOfOdd);
	}

}
