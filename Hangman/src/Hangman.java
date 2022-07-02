import java.util.*;

public class Hangman {

    private String selectedWord;
    private char[] guessedWord;
    private StringBuffer displaySecretWord;
    private int noOfTries;
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

        for (int i=0;i<selectedWord.length();i++) {
            guessedWord[i] = '_';
        }

        displaySecretWord = new StringBuffer();
        for (char c :selectedWord.toCharArray()) {
            displaySecretWord.append("*");
        }
        System.out.println("The word to guess is: "+ displaySecretWord);
        System.out.println(selectedWord);
    }

    public List<String> ListOfWords()
    {
        //using array list since it offers more flexibility
        //use file
        List<String> wordList = new ArrayList<>();
        wordList.addAll(Arrays.asList("trains","trainstation","bus","car","dictionary",
                "television","blackboard","projector","rickshaw","haphazard","bandwagon","jawbreaker","bookworm",
                "syndrome","thumbscrew","transgress","unknown","buzzard","microwave","wellspring","zipper"));
        return wordList;
    }

    public String SelectWord(List<String> wordList)
    {
        int rnd = new Random().nextInt(wordList.size());
        return wordList.get(rnd);
    }

    public boolean PlayHangman()
    {
        Scanner player = new Scanner(System.in);
        int remainingTries = MAX_TRIES_ALLOWED - noOfTries;
        while(noOfTries < MAX_TRIES_ALLOWED ) {

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

//                else if (!CheckGuessedWord(guessedChar)) {
            }
            PrintCurrentGuess();

            System.out.println("Tries left: " + remainingTries);
            System.out.print("Already used alpahbets: ");
            for (char c : previousGuesses) {
                System.out.print(c + "\t");
            }
            System.out.println();

            //maybe guessed word ==secret word
            if (IsGameWon()) {
                System.out.println("YOU WON!");
                return true;
            }
        }

        System.out.println("You lose. Would you like to play again?(y/n)");
        return false;
    }

    public void PrintCurrentGuess()
    {
        String printResult ="";
        for (char c: guessedWord) {
            printResult += c;
        }
        System.out.println("Word guessed so far: "+printResult);
    }

    public boolean ValidateUserInput(String guessedChar)
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

    public boolean CheckGuessedWord(char guessedChar)
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

    public boolean IsGameWon()
    {
//        for (char c: guessedWord) {
//            if (c == '_')
//            {
//                return false;
//            }
//        }
//        return true;

        return selectedWord.equals( String.valueOf(guessedWord));
    }


}
