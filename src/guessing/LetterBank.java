package guessing;

//Rose Lin 
//Mr. Radulovic
//June 18th, 2019
//Culminating Project: LetterBank (methods to create arrays that populate the letter bank in the guessing gui)

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import guessing.Prompt;

public class LetterBank extends Prompt { 

	public char getRandomChar() {
		return (char) ((new Random()).nextInt(26) + 'A');
	}
	
	public char[] CharArray(String word){
		return (word.toCharArray());
	}

	public ArrayList<Character> getLetterBank(char[] word) {
		
		ArrayList<Character> bank = new ArrayList<Character>();
	
		for (int i = 0; i < word.length; i++) { //populate letter bank with characters from word prompt
			bank.add(Character.toUpperCase(word[i]));
		}
		
		for (int i = word.length; i<16; i++) { //populate the rest of the array with random letters
			bank.add(getRandomChar());
		}

		Collections.shuffle(bank); //shuffle the list
		
		return bank;
	}
	
	
	

}
