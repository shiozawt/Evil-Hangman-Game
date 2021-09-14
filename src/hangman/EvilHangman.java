package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.Character.isAlphabetic;
import static java.lang.Character.isLetter;

public class EvilHangman {

    public static void main(String[] args) throws IOException, EmptyDictionaryException {

        //IF IMPLEMENTING FROM A COMMAND LINE, USE THIS INTERFACE
       // /*

        //File dictionaryName = new File(args[0]);
        //int wordLength = Integer.parseInt(args[1]);
        //int num_guesses = Integer.parseInt(args[2]);

       //  */

        //System.out.print(dictionaryName.length());

        //GET FILE, WORD LENGTH, AND NUMBER OF GUESSES
        File myFile = new File("dictionary.txt");
        int wordLength = 6;
        int remainingGuesses = 26;

        EvilHangmanGame game = new EvilHangmanGame();

        game.startGame(myFile, wordLength);

        playGame(game, remainingGuesses);

    }

    public static void playGame(EvilHangmanGame game, int remainingGuesses) throws EmptyDictionaryException {
        //keep track of letters that have been used with a sorted set
        Set<String> userGuesses = new TreeSet<String>(); // store this info in an EvilHangmanGame function

        String currentWord = new String();

        //call gameInterface here
        currentWord = game.getCurrWord();
        gameInterface(currentWord, remainingGuesses, userGuesses);
        Scanner inputChar = new Scanner(System.in);
        //String inputStr = new String(String.valueOf(System.in));
        String inputStr = new String();
        //System.out.print(inputStr);


        while (remainingGuesses > 0){
            inputStr = inputChar.nextLine().toLowerCase();

            if(inputStr.length() != 1){
                System.out.print("Error. Please try again" + '\n');
                continue;
            }
            if (!Character.isAlphabetic(inputStr.charAt(0))) {
                System.out.print("Error. Please try again" + '\n');
                continue;
            }

            char guessChar = inputStr.charAt(0);

            try {
                game.makeGuess(guessChar);
                boolean decrement = game.getBool();
                userGuesses = game.getUsedLetters();
                if (decrement == false) {
                    remainingGuesses = remainingGuesses - 1;
                }
            } catch (GuessAlreadyMadeException e) {
                System.out.print("Error. You already guessed character [" + guessChar + "]. Please try again." + '\n');
            }

            String winTest = game.getCurrWord();
            boolean winCheck = true;
            for (int i = 0; i < winTest.length(); ++i){
                if (winTest.charAt(i) == '-'){
                    winCheck = false;
                }
            }

            if (winCheck == true){
                System.out.print("YOU WIN!" + '\n' + "The word was: " + winTest + '\n');
                break;
            }



            if (remainingGuesses == 0){
            System.out.print("You Lose!" + '\n');
            System.out.print("The word was:" + game.getReturnedWord() + '\n');
            }
            else {
                currentWord = game.getCurrWord();
                gameInterface(currentWord, remainingGuesses, userGuesses);
            }

        }

           // System.out.print(userGuesses);


        //
    }

    public static void gameInterface(String currentWord, int remainingGuesses, Set<String> usedLetters){

        System.out.print("You have " + remainingGuesses + " guesses left" +'\n');

        System.out.print("Used letters:" + usedLetters.toString() +'\n'); // output list of used letters here

        System.out.print("Word: " + currentWord + '\n');

        System.out.print("Enter guess: ");
    }

}
