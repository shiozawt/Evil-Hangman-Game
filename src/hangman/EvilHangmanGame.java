
package hangman;

import java.io.File;
import hangman.EmptyDictionaryException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static java.lang.Character.isLetter;

public class EvilHangmanGame implements IEvilHangmanGame {

    Set<String>	usedLetters = new TreeSet<String>();
    String wordSetup = new String();
    Vector<String> dictionarySet = new Vector<String>();
    Map<String, Vector<String>> vecMap = new TreeMap<String, Vector<String>>();
    Boolean changeBool = false;
   // int guessCheck = 1;

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        dictionarySet.clear();
        wordSetup = "";
        vecMap.clear();


        Vector<String> dictionaryVec = getDictionaryVec(dictionary, wordLength); //creates a vector of possible values


        if (dictionaryVec.isEmpty()){
            System.out.print("error");
            throw new EmptyDictionaryException();
        }//test for empty dictionary file

        if (wordLength == 0){
            System.out.print("error");
            throw new EmptyDictionaryException();
        }//test for empty dictionary file


        for (int i = 0; i < wordLength; i++){
            wordSetup = wordSetup + '-';
        }//gets starting word line setup '-'

    }


    Boolean getBool(){
        return changeBool;
    }

    String getReturnedWord(){
        return dictionarySet.elementAt(0);
    }

    void testForGuesses(int remainingGuesses) throws EmptyDictionaryException {
        if (remainingGuesses == 0){
            System.out.print("error");
            throw new EmptyDictionaryException();
        }//test for empty dictionary file
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        changeBool = false;
        String str =  new String(String.valueOf(guess));
        str = str.toLowerCase();
        if (usedLetters.contains(str)){
            throw new GuessAlreadyMadeException();
        }
        usedLetters.add(str);

        generateMap(str); // creates map from all values remaining

        getBiggestMap(); // finds map with most values in it's vector

        dictionarySet.clear();
        dictionarySet = vecMap.get(wordSetup); // sets dictionarySet equal to the biggest set given in our map

        Set<String> returnSet = getNewSet();

        vecMap.clear(); // clears our map to be used again next round

        return returnSet;
    }

    public Set<String> getUsedLetters(){
        return usedLetters;
    }

    public Set<String> getNewSet(){
        Set<String> newset = new TreeSet<String>();

        for (String transferVal: vecMap.get(wordSetup)){
            newset.add(transferVal);
        }
       // System.out.print(vecMap.get(wordSetup).size() + "::" + dictionarySet.size() + '\n');
        return newset;
    }

    public void getBiggestMap(){
        Set<String> keys = vecMap.keySet();
        int maxVal = 0;

        char[] c = wordSetup.toCharArray();
        int tracker1 = 0;

        for (char e:c){
            if (e != '-'){
                tracker1 += 1;
            }
        }

        for(String key: keys){
            if (vecMap.get(key).size() > maxVal){
                maxVal = vecMap.get(key).size();

                wordSetup = key;
            }


            }

        //System.out.print(wordSetup + " is wordset up " + '\n');
        char[] f = wordSetup.toCharArray();
        int tracker2 = 0;

        for (char e:f){
            if (e != '-'){
                tracker2 += 1;
            }
        }

        if (tracker2 > tracker1){
            changeBool = true;
        }
        }

    public void generateMap(String userGuess){
        //use wordsetup as pattern to compare against
        //System.out.print(dictionarySet.size());
        //for each word, compare each letter to given guess char
        for (int i = 0; i < dictionarySet.size(); ++i){
            String currVal = dictionarySet.elementAt(i);
            String currPattern = new String();
            for (int j = 0; j < currVal.length(); ++j) {
                if (currVal.charAt(j) == userGuess.charAt(0)) {
                    currPattern = currPattern + userGuess;
                } else {
                    String dash = new String("-");
                    currPattern = currPattern + dash;
                }
            }
            addToVecMap(currPattern, dictionarySet.elementAt(i));
            //System.out.print(currPattern + '\n');
            //System.out.print(dictionarySet.elementAt(i) + '\n');
        }

        // if pattern is already a key, add word to set attached to said key

        // if wordmap doesn't contain key, add to wordmap
    }

    void addToVecMap(String inputPattern, String valueToAdd){
        inputPattern = editInputPattern(inputPattern);
       // System.out.print('\n');
        if (vecMap.containsKey(inputPattern)){
            //add valueToAdd to Vector at given key
            Vector<String> editVec = new Vector<String>();
           // System.out.print("one  " + vecMap.get(inputPattern).size() + '\n');
            editVec = vecMap.get(inputPattern);
            if (!editVec.contains(valueToAdd)) {
                editVec.add(valueToAdd);
            }
            vecMap.replace(inputPattern, editVec);
            //System.out.print("two  " + vecMap.get(inputPattern).size() + '\n');
        }
        else{
            Vector<String> newVec = new Vector<String>();
            newVec.add(valueToAdd);
            vecMap.put(inputPattern, newVec);
        }
    }

    String editInputPattern(String inputPattern){
        String newStr = new String();

        for (int i = 0; i < wordSetup.length(); ++i){
            if (wordSetup.charAt(i) == '-'){
                newStr = newStr + inputPattern.charAt(i);
            }
            else{
                newStr = newStr + wordSetup.charAt(i);
            }
        }

        return newStr;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return null;
    }

    public String getCurrWord(){
        return wordSetup;
    }

    Vector<String> getDictionaryVec(File dictionary,int wordLength){
        Scanner input = null;
        Vector<String> DictionaryStorage = new Vector<String>();

        try {
            input = new Scanner(dictionary);

            if (input == null){
                //throw new EmptyDictionaryException;
            }
        }
        catch(FileNotFoundException error){
            error.getStackTrace();
        }

        while (input.hasNextLine()){
            String newStr = input.nextLine();
            if (newStr.length() == wordLength) {
                DictionaryStorage.add(newStr);
                dictionarySet.add(newStr);
            }
            //System.out.print(newStr + '\n');
        }
        input.close();
        return DictionaryStorage;
        //System.out.print(DictionaryStorage.size());
    }


}
