package il.ac.tau.cs.sw1.ex5;


import java.io.*;
import java.util.Arrays;

public class BigramModel {
	public static final int MAX_VOCABULARY_SIZE = 14500;
	public static final String VOC_FILE_SUFFIX = ".voc";
	public static final String COUNTS_FILE_SUFFIX = ".counts";
	public static final String SOME_NUM = "some_num";
	public static final int ELEMENT_NOT_FOUND = -1;
	
	String[] mVocabulary;
	int[][] mBigramCounts;
	
	// DO NOT CHANGE THIS !!! 
	public void initModel(String fileName) throws IOException{
		mVocabulary = buildVocabularyIndex(fileName);
		mBigramCounts = buildCountsArray(fileName, mVocabulary);
		
	}
	

	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public String[] buildVocabularyIndex(String fileName) throws IOException{ // Q 1

		File textFile = new File(fileName);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile));

		String[] tempVocabulary = new String[MAX_VOCABULARY_SIZE];
		int words = 0;

		String line;
		while ((line = bufferedReader.readLine()) != null) {
			String[] lineWords = line.split(" "); // Split the line into an array of words, seperated by " "
			for (int i = 0; i < lineWords.length; i++) { // Iterate through every word
				String temp = legalizeWord(lineWords[i]);
				if (!temp.equals("")) { // Word is legal
					if (!isInArray(tempVocabulary, temp)) { // Word is not already in vocabulary
						tempVocabulary[words] = temp; // Add word to temporary vocabulary
						words++; // Advance vocabulary word count
					}
				}
			}
		}

		if (words == MAX_VOCABULARY_SIZE - 1) { // If we found 14,500 words
			return tempVocabulary;
		}

		// Copy temporary vocabulary to a matched sized vocabulary
		String[] vocabulary = new String[words];
		for (int i = 0; i < words; i++) {
			vocabulary[i] = tempVocabulary[i];
		}

		bufferedReader.close(); // Close the reader
		return vocabulary;

	}

	// Checks if a string is a legal word as defined in Q1
	private String legalizeWord(String word) {

		boolean isNumber = true;
		for (char c : word.toCharArray()) {
			if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
				return word.toLowerCase(); // Word is an english lettered word
			} else {
				if (!Character.isDigit(c)) {
					isNumber = false;
				}
			}
		}

		if (isNumber) { // Word is a number
			return SOME_NUM;
		}

		return ""; // Word is not legal
	}

	// Return if String is in an array
	private boolean isInArray(String[] array, String word) {

		for (String s : array) {
			if (s == null) {
				break;
			}
			if (s.equals(word)) {
				return true;
			}
		}

		return false;
	}



	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public int[][] buildCountsArray(String fileName, String[] vocabulary) throws IOException { // Q - 2

		// Create the file reader
		File textFile = new File(fileName);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile));

		int[][] countsArray = new int[vocabulary.length][vocabulary.length];

		String line;
		while ((line = bufferedReader.readLine()) != null) {
			String[] lineWords = line.split(" "); // Split the line into an array of words, seperated by " "
			for (int i = 0; i < lineWords.length - 1; i++) {
				if (isNumber(lineWords[i])) { // If the first word is a number replace with "some_num"
					lineWords[i] = SOME_NUM;
				}
				if (isNumber(lineWords[i + 1])) { // If the second word is a number replace with "some_num"
					lineWords[i + 1] = SOME_NUM;
				}
				int curr = getIndexInArray(vocabulary, lineWords[i].toLowerCase()); // Get index of current word
				int next = getIndexInArray(vocabulary, lineWords[i + 1].toLowerCase()); // Get index of next word
				if (curr != -1 && next != -1) { // If both words are in the array
					countsArray[curr][next]++;
				}
			}
		}

		bufferedReader.close(); // Close the reader
		return countsArray;
	}

	// Get the index of the string in an array, return -1 if not in the array
	private int getIndexInArray(String[] array, String s) {

		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(s)) {
				return i;
			}
		}

		return -1;
	}

	// Return true if String is a number
	private boolean isNumber(String s) {

		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}

		return true;
	}



	/*
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: fileName is a legal file path
	 */
	public void saveModel(String fileName) throws IOException{ // Q-3

		saveVocabulary(fileName);
		saveCounts(fileName);
	}

	// Save voc file
	private void saveVocabulary(String fileName) throws IOException {

		// Create the file writer
		File vocFile = new File(fileName + VOC_FILE_SUFFIX);
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(vocFile));

		String vocString = mVocabulary.length + " words";
		for (int i = 0; i < mVocabulary.length; i++) { // Loop through each word
			vocString += System.lineSeparator() + i + "," + mVocabulary[i]; // Add word's appearance count
		}

		bufferedWriter.write(vocString); // Write the String to the file
		bufferedWriter.close(); // Close the writer
	}

	// Save counts file
	private void saveCounts(String fileName) throws IOException {

		// Create the file writer
		File vocFile = new File(fileName + COUNTS_FILE_SUFFIX);
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(vocFile));

		String countsString = "";
		for (int i = 0; i < mBigramCounts.length; i++) { // Loop through all the words
			for (int j = 0; j < mBigramCounts.length; j++) { // Loop through each word's neighbors count
				if (mBigramCounts[i][j] > 0) {
					countsString += i + "," + j + ":" + mBigramCounts[i][j] + System.lineSeparator(); // Add count to String
				}
			}
		}

		countsString = countsString.replaceAll("(?m)^[ \t]*\r?\n", "");

		bufferedWriter.write(countsString); // Write the String to the file
		bufferedWriter.close(); // Close the writer
	}



	/*
	 * @pre: fileName is a legal file path
	 */
	public void loadModel(String fileName) throws IOException{ // Q - 4

		loadVocabularyModel(fileName + VOC_FILE_SUFFIX);
		loadCountsModel(fileName + COUNTS_FILE_SUFFIX);
	}

	// Load vocabulary from file
	private void loadVocabularyModel(String fileName) throws IOException {

		File vocabularyFile = new File(fileName);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(vocabularyFile));

		String line;
		if ((line = bufferedReader.readLine()) == null) { // Checks that the file is not empty
			return;
		}

		// Create the new vocabulary with the correct length
		String[] lineWords = line.split(" ");
		int newVocabularyLength = Integer.parseInt(lineWords[0]);
		String[] newVocabulary = new String[newVocabularyLength];

		while ((line = bufferedReader.readLine()) != null) { // Go through each word in the file and put it in the array
			lineWords = line.split(",");
			newVocabulary[Integer.parseInt(lineWords[0])] = lineWords[1];
		}

		mVocabulary = Arrays.copyOf(newVocabulary, newVocabularyLength);
	}

	// Load bigram count from file
	private void loadCountsModel(String fileName) throws IOException {

		File vocabularyFile = new File(fileName);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(vocabularyFile));

		int[][] newBigramCount = new int[mVocabulary.length][mVocabulary.length];

		String line;
		while ((line = bufferedReader.readLine()) != null) {
			int[] bigramCount = getCount(line);
			newBigramCount[bigramCount[0]][bigramCount[1]] = bigramCount[2];
		}

		mBigramCounts = Arrays.copyOf(newBigramCount, newBigramCount.length);
	}

	private int[] getCount(String line) {

		int[] bigramCount = new int[3];
		int index = 0;

		// Get the index of the first word
		String firstWordIndex = "";
		while (line.charAt(index) != ',') {
			firstWordIndex += line.charAt(index);
			index++;
		}
		bigramCount[0] = Integer.parseInt(firstWordIndex);

		// Get the index of the second word
		index++;
		String secondWordIndex = "";
		while (line.charAt(index) != ':') {
			secondWordIndex += line.charAt(index);
			index++;
		}
		bigramCount[1] = Integer.parseInt(secondWordIndex);

		// Get the count
		index++;
		String count = "";
		for (int i = index; i < line.length(); i++) {
			count += line.charAt(i);
		}
		bigramCount[2] = Integer.parseInt(count);

		return bigramCount;
	}
	


	/*
	 * @pre: word is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = -1 if word is not in vocabulary, otherwise $ret = the index of word in vocabulary
	 */
	public int getWordIndex(String word){  // Q - 5

		return getIndexInArray(mVocabulary, word); // Use a function made for Q1
	}
	
	
	
	/*
	 * @pre: word1, word2 are in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = the count for the bigram <word1, word2>. if one of the words does not
	 * exist in the vocabulary, $ret = 0
	 */
	public int getBigramCount(String word1, String word2){ //  Q - 6

		int index1 = getWordIndex(word1);
		int index2 = getWordIndex(word2);

		if (index1 == 1 || index2 == -1) { // Return false if one of the words is not in the vocabulary
			return 0;
		}

		return mBigramCounts[index1][index2];
	}
	
	
	/*
	 * @pre word in lowercase, and is in mVocabulary
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post $ret = the word with the lowest vocabulary index that appears most fequently after word (if a bigram starting with
	 * word was never seen, $ret will be null
	 */
	public String getMostFrequentProceeding(String word){ //  Q - 7

		int wordIndex = getWordIndex(word);

		int maxCount = 0;
		String maxCountString = null;
		for (int i = 0; i < mBigramCounts[wordIndex].length; i++) { // Go through each word's counts array
			if (mBigramCounts[wordIndex][i] > maxCount) { // Check if current count is bigger than current maximum count
				maxCount = mBigramCounts[wordIndex][i]; // Replace current maximum
				maxCountString = mVocabulary[i];
			}
		}

		return maxCountString;
	}
	
	
	/* @pre: sentence is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: each two words in the sentence are are separated with a single space
	 * @post: if sentence is is probable, according to the model, $ret = true, else, $ret = false
	 */
	public boolean isLegalSentence(String sentence){  //  Q - 8

		String[] sentenceArray = sentence.split(" "); // Split sentence into words array
		for (int i = 0; i < sentenceArray.length - 1; i++) { // Go through each two words couple in the sentence
			if (getBigramCount(legalizeWord(sentenceArray[i]), legalizeWord(sentenceArray[i + 1])) <= 0) { // Return false if the couple's count is 0
				return false;
			}
		}

		return true;
	}

	
	/*
	 * @pre: arr1.length = arr2.legnth
	 * post if arr1 or arr2 are only filled with zeros, $ret = -1, otherwise calcluates CosineSim
	 */
	public static double calcCosineSim(int[] arr1, int[] arr2){ //  Q - 9

		double aSum = 0;
		double bSum = 0;
		double combinedSum = 0;

		// Calculate sums
		for (int i = 0; i < arr1.length; i++) {
			aSum += arr1[i] * arr1[i];
			bSum += arr2[i] * arr2[i];
			combinedSum += arr1[i] * arr2[i];
		}

		// Return -1 if on of the arrays is only 0's
		if (aSum == 0 || bSum == 0) {
			return -1;
		}

		return combinedSum / (Math.sqrt(aSum) * Math.sqrt(bSum)); // Return the Cosine Similarity
	}



	/*
	 * @pre: word is in vocabulary
	 * @pre: the method initModel was called (the language model is initialized), 
	 * @post: $ret = w implies that w is the word with the largest cosineSimilarity(vector for word, vector for w) among all the
	 * other words in vocabulary
	 */
	public String getClosestWord(String word){ //  Q - 10

		int[] wordVector = Arrays.copyOf(mBigramCounts[getWordIndex(legalizeWord(word))], mVocabulary.length); // Get the word's vector

		double maxCosineSim = 0.0;
		String closestWord = mVocabulary[0];
		for (int i = 0; i < mVocabulary.length; i++) { // Go through all the vocabulary's words
			if (mVocabulary[i].equals(word)) { // Ignore if current word is same as word
				continue;
			}
			int[] currentVector = Arrays.copyOf(mBigramCounts[i], mVocabulary.length); // Get current word's vector
			double currentCosineSim = calcCosineSim(wordVector, currentVector); // Calculate cosine similarity
			if (currentCosineSim > maxCosineSim) { // Check if current cosine is bigger than current maximum
				maxCosineSim = currentCosineSim;
				closestWord = mVocabulary[i];
			}
		}

		return closestWord;
	}



}
