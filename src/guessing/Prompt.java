package guessing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Prompt {

	public ArrayList<String> getWordList() throws FileNotFoundException {
		ArrayList<String> wordList = new ArrayList<String>();
		Scanner scan = new Scanner(new File("src/resources/dictionary.txt"));

		while (scan.hasNextLine()) { // populate list
			wordList.add(scan.nextLine());
		}

		return wordList;
	}

	public int setWordIndex(ArrayList<String> wordList) {
		Random rand = new Random();
		int index = rand.nextInt(wordList.size());
		return index;
	}

	public String getWord(ArrayList<String> wordList) {
		String word = wordList.get(setWordIndex(wordList));
		return word;
	}
	

}
