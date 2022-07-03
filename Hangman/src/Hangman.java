import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Hangman {

    private String selectedWord;
    private char[] guessedWord;
    private StringBuffer displaySecretWord;
    private int noOfTries;
    private int remainingTries;
    private boolean gameWon;
    //made RANDOM static and final to make the seed of number generator persist throughout the application
    private static final Random RANDOM = new Random();
    private List<Character> previousGuesses;
    final int MAX_TRIES_ALLOWED = 7;


    public Hangman() {
        noOfTries = 0;
        remainingTries = 0;
        gameWon = false;
    }

    //to reset/initialize the game and to start the game with a new round
    public void InitializeGame() {
        noOfTries = 0;
        List<String> wordList = ListOfWords();
        selectedWord = SelectWord(wordList);
        previousGuesses = new ArrayList<>();
        guessedWord = new char[selectedWord.length()];
        gameWon=false;

        for (int i=0;i<selectedWord.length();i++) {
            guessedWord[i] = '_';
        }

        displaySecretWord = new StringBuffer();
        for (char c :selectedWord.toCharArray()) {
            displaySecretWord.append("*");
        }
        System.out.println("The word to guess is: "+ displaySecretWord);
    }

    private List<String> ListOfWords() {
        //using array list since it offers more flexibility
        List<String> wordList = new ArrayList<>();
        try{
            Scanner reader = new Scanner(new File("words.txt"));
            while (reader.hasNext()){
                wordList.add(reader.next());
            }
            reader.close();

        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found. Please make sure the file is present."+e);
        }
        return wordList;
    }

    private String SelectWord(List<String> wordList)
    {
        int rnd =RANDOM.nextInt(wordList.size());
        return wordList.get(rnd);
    }

    public void PlayHangman() {
        try {
            Scanner player = new Scanner(System.in);
            boolean continuePlaying =true;

            //loop continues until the player does not want to play anymore
            while(continuePlaying) {
                remainingTries = MAX_TRIES_ALLOWED - noOfTries;
                if(noOfTries < MAX_TRIES_ALLOWED && !gameWon)
                {
                    System.out.println("Please enter a word to guess: ");
                    String playerInput = player.next().toLowerCase();
                    char guessedChar = playerInput.charAt(0);

                    //to check if the input is valid and contains only alphabets
                    if (ValidateUserInput(playerInput)) {
                        //check if the word already has been guessed before
                        if (previousGuesses.contains(guessedChar)) {
                            System.out.println("Already guessed this letter. Please try again.");
                        } else {
                            //otherwise increase the try counter if guessed wrong
                            if (!CheckGuessedWord(guessedChar)) {
                                remainingTries = MAX_TRIES_ALLOWED - (++noOfTries);
                            }
                        }
                    }
                    //to keep printing the currently guessed word so far
                    PrintCurrentGuess();

                    System.out.println("Tries left: " + remainingTries);
                    System.out.print("Already used alpahbets: ");
                    for (char c : previousGuesses) {
                        System.out.print(c + "\t");
                    }
                    System.out.println();

                    if (IsGameWon()) {
                        System.out.println("YOU WON!!!! CONGRATULATIONS!");
                        gameWon =true;
                    } else if (remainingTries == 0) {
                        System.out.println("You lose!. The word to guess was: " + selectedWord);
                    }

                }else {
                    System.out.println("Would you like to play again?(y/n)");
                    String choice = player.next();
                    if (choice.equals("n"))
                    {
                        continuePlaying = false;
                    } else if (choice.equals("y")) {
                        InitializeGame();
                    }
                }
            }
            System.out.println("Thank you for playing! See you later!");
        }catch (Exception e)
        {
            System.out.println("An error occured with message:" + e);
        }

    }

    private void PrintCurrentGuess()
    {
        String printResult ="";
        for (char c: guessedWord) {
            printResult += c;
        }
        System.out.println("Word guessed so far: "+printResult);
    }

    private boolean ValidateUserInput(String guessedChar)
    {
        if (guessedChar.length() != 1 || guessedChar.equals("") || guessedChar == null)
        {
            System.out.println("Please enter one character at a time.");
            return false;
        } else if (!guessedChar.matches("[a-zA-Z]+")) {
            System.out.println("Please enter only valid characters(alphabets).");
            return false;
        }

        return true;

    }

    //goes over the selected word and replaces the dashes"_" with the correct guess if present
    private boolean CheckGuessedWord(char guessedChar)
    {
        boolean wordFound = false;
        previousGuesses.add(guessedChar);
        for (int i=0;i<selectedWord.length();i++) {
            if (guessedChar == selectedWord.charAt(i)){
                guessedWord[i] = guessedChar;
                wordFound = true;
            }
        }
        return wordFound;
    }

    //checks if the selected word has been guessed
    private boolean IsGameWon()
    {
        return selectedWord.equals( String.valueOf(guessedWord));
    }

}
