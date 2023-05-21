

public class Assignment1 {
	public static void main(String[] args){
		char newChar = args[0].charAt(0);
		int num = Integer.parseInt(args[1]);
		newChar += num;
		System.out.println("New char is " + newChar + ".");
	}
}
