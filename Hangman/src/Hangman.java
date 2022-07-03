import java.util.*;

public class Hangman {

    private String selectedWord;
    private char[] guessedWord;
    private StringBuffer displaySecretWord;
    private int noOfTries;
    private int remainingTries;
    private boolean gameWon;
    private List<Character> previousGuesses;
    private boolean continuePlaying;
    final int MAX_TRIES_ALLOWED = 7;

    //will replace with constructor
    public void InitializeGame()
    {
        noOfTries = 0;
        List<String> wordList = ListOfWords();
        selectedWord = SelectWord(wordList);
//        PrintCharacters(selectedWord);
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
        //System.out.println(selectedWord);
    }

    private List<String> ListOfWords()
    {
        //using array list since it offers more flexibility
        //use file
        List<String> wordList = new ArrayList<>();
        wordList.addAll(Arrays.asList("trains","trainstation","bus","car","dictionary",
                "television","blackboard","projector","rickshaw","haphazard","bandwagon","jawbreaker","bookworm",
                "syndrome","thumbscrew","transgress","unknown","buzzard","microwave","wellspring","zipper"));
        return wordList;
    }

    private String SelectWord(List<String> wordList)
    {
        int rnd = new Random().nextInt(wordList.size());
        return wordList.get(rnd);
    }

    public void PlayHangman()
    {
        Scanner player = new Scanner(System.in);
        boolean continuePlaying =true;

        while(continuePlaying) {
            remainingTries = MAX_TRIES_ALLOWED - noOfTries;
            if(noOfTries < MAX_TRIES_ALLOWED && !gameWon)
            {
                System.out.println("Please enter a word to guess: ");
                String playerInput = player.next().toLowerCase();
                char guessedChar = playerInput.charAt(0);

                if (ValidateUserInput(playerInput)) {
                    if (previousGuesses.contains(guessedChar)) {
                        System.out.println("Already guessed this letter. Please try again.");
                    } else {
                        if (!CheckGuessedWord(guessedChar)) {
                            remainingTries = MAX_TRIES_ALLOWED - (++noOfTries);
                        }
                    }
                }
                PrintCurrentGuess();

                System.out.println("Tries left: " + remainingTries);
                System.out.print("Already used alpahbets: ");
                for (char c : previousGuesses) {
                    System.out.print(c + "\t");
                }
                System.out.println();

                if (IsGameWon()) {
                    System.out.println("YOU WON!");
                    gameWon =true;
                } else if (remainingTries ==0) {
                    System.out.println("You lose!. The word to guess was: "+selectedWord);
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

    private boolean IsGameWon()
    {
        return selectedWord.equals( String.valueOf(guessedWord));
    }

}
