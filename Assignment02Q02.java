

public class Assignment02Q02 {

	public static void main(String[] args) {
		// do not change this part below
		double piEstimation = 0.0;
		
		// *** your code goes here below ***
		double currOdd = 1.0;
		for (int i = 0; i < Integer.parseInt(args[0]); i++) {
			piEstimation += Math.pow(-1, i) * (1 / currOdd);
			currOdd += 2;
		}
		piEstimation *= 4;
		
		// do not change this part below
		System.out.println(piEstimation + " " + Math.PI);

	}

}
