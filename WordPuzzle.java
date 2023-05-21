package il.ac.tau.cs.sw1.ex4;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class WordPuzzle {
	public static final char HIDDEN_CHAR = '_';
	public static final char[] ALPHABET = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
											'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	public static final int ASCII_MARGIN = 97;
	
	/*
	 * @pre: template is legal for word
	 */
	public static char[] createPuzzleFromTemplate(String word, boolean[] template) { // Q - 1

		char[] puzzleArray = new char[word.length()];
		for (int i = 0; i < word.length(); i++) {
			if (!template[i]) {
				puzzleArray[i] = word.charAt(i);
			} else {
				puzzleArray[i] = HIDDEN_CHAR;
			}
		}

		return puzzleArray;
	}

	public static boolean checkLegalTemplate(String word, boolean[] template) { // Q - 2

		// Checks that the lengths are equal
		if (word.length() != template.length) {
			 return false;
		}

		// Checks that every letter is all hidden or all not hidden
		for (int i = 0; i < word.length(); i++) {
			for (int j = 0; j < word.length(); j++) {
				if (word.charAt(i) == word.charAt(j)) {
					if (template[i] != template[j]) {
						return false;
					}
				}
			}
		}

		for (int i = 0; i < word.length() - 1; i++) {
			if (template[i] != template[i + 1]) {
				return true;
			}
		}

		return false;
	}
	
	/*
	 * @pre: 0 < k < word.lenght(), word.length() <= 10
	 */
	public static boolean[][] getAllLegalTemplates(String word, int k){  // Q - 3

		// Check for every legal option with k changes
		boolean[][] everyOptions = new boolean[(int) Math.pow(2, word.length())][word.length()];
		int legalCount = 0;
		for (int i = 0; i < Math.pow(2, word.length()); i++) {
			String currBinary = createBinaryWithPadding(i, word.length());
			boolean[] currTemplate = createPuzzleFromBinary(word, currBinary);
			if ((isNumOfTrue(currTemplate, k)) && (checkLegalTemplate(word, currTemplate))) {
				legalCount++;
				everyOptions[i] = currTemplate;
			} else {
				everyOptions[i] = null;
			}
		}

		// Create all legal options array
		boolean[][] legalTemplates = new boolean[legalCount][word.length()];
		int index = 0;
		for (int i = 0; i < Math.pow(2, word.length()); i++) {
			if (everyOptions[i] != null) {
				legalTemplates[index] = everyOptions[i];
				index++;
			}
		}

		return legalTemplates;
	}

	private static String createBinaryWithPadding(int n, int pad) {
		return String.format("%" + pad + "s", Integer.toBinaryString(n)).replace(" ", "0");
	}

	private static boolean[] createPuzzleFromBinary(String word, String binary) {

		boolean[] template = new boolean[word.length()];
		for (int i = 0; i < binary.length(); i++) {
			if (binary.charAt(i) == '1') {
				template[i] = true;
			}
		}

		return template;
	}

	private static boolean isNumOfTrue(boolean[] template, int k) {

		int count = 0;
		for (int i = 0; i < template.length; i++) {
			if (template[i]) {
				count++;
			}
		}

		return (count == k);
	}

	/*
	 * @pre: puzzle is a legal puzzle constructed from word, guess is in [a...z]
	 */
	public static int applyGuess(char guess, String word, char[] puzzle) { // Q - 4

		int changes = 0;

		for (int i = 0; i < word.length(); i++) {
			if ((puzzle[i] == HIDDEN_CHAR) && (word.charAt(i) == guess)) {
				changes++;
				puzzle[i] = guess;
			}
		}

		return changes;
	}
	

	/*
	 * @pre: puzzle is a legal puzzle constructed from word
	 * @pre: puzzle contains at least one hidden character. 
	 * @pre: there are at least 2 letters that don't appear in word, and the user didn't guess
	 */
	public static char[] getHint(String word, char[] puzzle, boolean[] already_guessed) { // Q - 5

		Random rnd = new Random();
		int correctHint = getCorrectHint(word, puzzle, already_guessed);
		int wrongHint = getWrongHint(word, puzzle, already_guessed);

		// Return the hints in lexicographical order
		if (correctHint <= wrongHint) {
			return new char[] {ALPHABET[correctHint], ALPHABET[wrongHint]};
		} else {
			return new char[] {ALPHABET[wrongHint], ALPHABET[correctHint]};
		}
	}

	// Randomly roll a correct hint
	private static int getCorrectHint(String word, char[] puzzle, boolean[] already_guessed) {

		Random rnd = new Random();
		int correctHint = -1;

		int temp = 0;

		while (correctHint == -1) {
			temp = rnd.nextInt(ALPHABET.length);
			if (!already_guessed[temp]) {
				int i = word.indexOf(ALPHABET[temp]);
				if (i != -1) {
					if (puzzle[i] == HIDDEN_CHAR) {
						correctHint = temp;
					}
				}
			}
		}

		return correctHint;
	}

	// Randomly roll an incorrect hint
	private static int getWrongHint(String word, char[] puzzle, boolean[] already_guessed) {

		Random rnd = new Random();
		int wrongHint = -1;

		int temp = 0;

		while (wrongHint == -1) {
			temp = rnd.nextInt(ALPHABET.length);
			if (!already_guessed[temp]) {
				int i = word.indexOf(ALPHABET[temp]);
				if (i == -1) {
					wrongHint = temp;
				}
			}
		}

		return wrongHint;
	}

	public static char[] mainTemplateSettings(String word, Scanner inputScanner) { // Q - 6

		printSettingsMessage();

		while (true) {
			printSelectTemplate();
			if (inputScanner.nextInt() == 1) {
				printSelectNumberOfHiddenChars();
				while (true) {
					int k = inputScanner.nextInt();
					boolean[][] legalTemplates = getAllLegalTemplates(word, k);
					if (legalTemplates.length >= 1) {
						Random rnd = new Random();
						return createPuzzleFromTemplate(word, legalTemplates[rnd.nextInt(legalTemplates.length)]);
					} else {
						printWrongTemplateParameters();
					}
				}
			} else {
				printEnterPuzzleTemplate();
				String input = inputScanner.next();
				String[] templateArray = input.split(",");
				boolean[] template = new boolean[word.length()];
				for (int i = 0; i < template.length; i++) {
					if (templateArray[i].equals(Character.toString(HIDDEN_CHAR))) {
						template[i] = true;
					}
				}
				if (checkLegalTemplate(word, template)) {
					return createPuzzleFromTemplate(word, template);
				} else {
					printWrongTemplateParameters();
				}
			}
		}
	}
	
	public static void mainGame(String word, char[] puzzle, Scanner inputScanner){ // Q - 7

		printGameStageMessage();

		int guesses = 3;
		int changesNeeded = 0;
		boolean[] already_guessed = new boolean[ALPHABET.length];

		// Scan puzzle to calculate number of guesses and already guessed letters
		for (int i = 0; i < puzzle.length; i++) {
			if (puzzle[i] != HIDDEN_CHAR) {
				already_guessed[((int) puzzle[i]) - 97] = true;
			} else {
				guesses++;
				changesNeeded++;
			}
		}

		String currGuess;
		while (guesses > 0) {
			printPuzzle(puzzle);
			printEnterYourGuessMessage();
			currGuess = inputScanner.next();
			if (currGuess.equals("H")) {
				printHint(getHint(word, puzzle, already_guessed));
			} else {
				guesses--;
				if (already_guessed[((int) currGuess.charAt(0)) - ASCII_MARGIN]) {
					printWrongGuess(guesses);
				} else {
					already_guessed[((int) currGuess.charAt(0)) - ASCII_MARGIN] = true;
					if (word.contains(currGuess)) {
						changesNeeded -= applyGuess(currGuess.charAt(0), word, puzzle);
						if (changesNeeded < 1) {
							printWinMessage();
							return;
						} else {
							printCorrectGuess(guesses);
						}
					} else {
						printWrongGuess(guesses);
					}
				}
			}
		}

		printGameOver();
	}

/*************************************************************/
/********************* Don't change this ********************/
/*************************************************************/

	public static void main(String[] args) throws Exception { 
		if (args.length < 1){
			throw new Exception("You must specify one argument to this program");
		}
		String wordForPuzzle = args[0].toLowerCase();
		if (wordForPuzzle.length() > 10){
			throw new Exception("The word should not contain more than 10 characters");
		}
		Scanner inputScanner = new Scanner(System.in);
		char[] puzzle = mainTemplateSettings(wordForPuzzle, inputScanner);
		mainGame(wordForPuzzle, puzzle, inputScanner);
		inputScanner.close();
	}


	public static void printSettingsMessage() {
		System.out.println("--- Settings stage ---");
	}

	public static void printEnterWord() {
		System.out.println("Enter word:");
	}
	
	public static void printSelectNumberOfHiddenChars(){
		System.out.println("Enter number of hidden characters:");
	}
	public static void printSelectTemplate() {
		System.out.println("Choose a (1) random or (2) manual template:");
	}
	
	public static void printWrongTemplateParameters() {
		System.out.println("Cannot generate puzzle, try again.");
	}
	
	public static void printEnterPuzzleTemplate() {
		System.out.println("Enter your puzzle template:");
	}


	public static void printPuzzle(char[] puzzle) {
		System.out.println(puzzle);
	}


	public static void printGameStageMessage() {
		System.out.println("--- Game stage ---");
	}

	public static void printEnterYourGuessMessage() {
		System.out.println("Enter your guess:");
	}

	public static void printHint(char[] hist){
		System.out.println(String.format("Here's a hint for you: choose either %s or %s.", hist[0] ,hist[1]));

	}
	public static void printCorrectGuess(int attemptsNum) {
		System.out.println("Correct Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWrongGuess(int attemptsNum) {
		System.out.println("Wrong Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWinMessage() {
		System.out.println("Congratulations! You solved the puzzle!");
	}

	public static void printGameOver() {
		System.out.println("Game over!");
	}

}
