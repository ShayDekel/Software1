
public class StringUtils {

	public static String findSortedSequence(String str) {

		String[] words = str.split(" ");
		int max = 0;
		int maxStartingIndex = 0;
		int maxFinishingIndex = 0;

		for (int i = 0; i < words.length; i++) {
			int temp = 0;
			for (int j = i; j < words.length - 1; j++) {
				if (isLexicographicallyOrdered(words[j], words[j + 1])) {
					temp ++;
				} else {
					break;
				}
			}
			if (temp >= max) {
				max = temp;
				maxStartingIndex = i;
				maxFinishingIndex = i + max;
			}
		}

		String newString = "";
		for (int j = maxStartingIndex; j <= maxFinishingIndex; j++) {
			newString += words[j] + " ";
		}
		newString = newString.substring(0, newString.length() - 1);

		return newString;

	}

	/**
	 * Returns true if a and b are ordered in lexicograpical order
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isLexicographicallyOrdered(String a, String b) {
		for (int i = 0; i < a.length() && i < b.length(); i++) {
			int a_curr = (int)a.charAt(i);
			int b_curr = (int)b.charAt(i);
			if (a_curr == b_curr) {
				continue;
			} else {
				if (a_curr < b_curr) {
					return true;
				} else {
					return false;
				}
			}
		}

		if (a.length() <= b.length()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEditDistanceOne(String a, String b){

		if (a.length() == b.length()) {
			return isChange(a, b);
		}

		if (Math.abs(a.length() - b.length()) == 1) {
			if (a.length() > b.length()) {
				return isAddRemove(a, b);
			} else {
				return isAddRemove(b, a);
			}
		}

		return false;
	}

	private static boolean isAddRemove(String longer, String shorter) {

		int firstDifferent = shorter.length();
		for (int i = 0; i < shorter.length(); i++) {
			if (longer.charAt(i) != shorter.charAt(i)) {
				firstDifferent = i;
				break;
			}
		}

		for (int i = firstDifferent; i < longer.length() - 1; i++) {
			if (longer.charAt(i + 1) != shorter.charAt(i)) {
				return false;
			}
		}

		return true;
	}

	private static boolean isChange(String a, String b) {

		int changes = 0;
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) != b.charAt(i)) {
				changes++;
			}
			if (changes > 1) {
				return false;
			}
		}

		return true;
	}
}
