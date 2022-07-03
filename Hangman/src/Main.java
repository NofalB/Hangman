import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Hangman hangman = new Hangman();

        hangman.InitializeGame();
        hangman.PlayHangman();
    }
}