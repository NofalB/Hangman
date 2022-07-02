import java.util.*;

public class Hangman {

    private String selectedWord;
    private char[] guessedWord;
    private StringBuffer secretWord;
    private int noOfTries;
    private List<String> previousGuesses;
    final int MAX_TRIES_ALLOWED = 8;

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

        secretWord = new StringBuffer();
        for (char c :selectedWord.toCharArray()) {
            secretWord.append("*");
        }
        System.out.println("The word to guess is: "+ secretWord);
        System.out.println(selectedWord);
    }

    public List<String> ListOfWords()
    {
        //using array list since it offers more flexibility
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

    public void PlayHangman(Hangman hangman)
    {
        Scanner player = new Scanner(System.in);
        while(noOfTries < MAX_TRIES_ALLOWED)
        {
            System.out.println("Please enter a word to guess: ");
            String playerInput = player.next();
            char guessedChar = playerInput.charAt(0);
            if (ValidateUserInput(playerInput) && CheckGuessedWord(guessedChar)){
                PrintCurrentGuess();
//                System.out.println("So far: "+ guessedWord.toString());
            }

        }
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
        for (int i=0;i<selectedWord.length();i++) {
            if (guessedChar == selectedWord.charAt(i)){
                guessedWord[i] = guessedChar;
                return true;
            }
        }
        return false;
    }


}
