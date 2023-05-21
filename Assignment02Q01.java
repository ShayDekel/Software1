
public class Assignment02Q01 {

	public static void main(String[] args) {
		// *** your code goes here below ***
		for (String arg : args) {
			int currAscii = (int) arg.charAt(0);
			if (currAscii % 5 == 0) {
				System.out.println(arg.charAt(0));
			}
		}
	}

}
