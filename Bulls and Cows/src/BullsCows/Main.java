package BullsCows;

import java.util.*;

class BullsAndCows {
    final Scanner scanner = new Scanner(System.in);
    private String secretCode;
    private boolean state; // state of game is running or not

    private boolean getState() {
        return this.state;
    }

    private void setState(boolean state) {
        this.state = state;
    }

    public String getSecretCode() {
        return this.secretCode;
    }

    public void setSecretCode(String code) {
        this.secretCode = code;
    }


    public void startGame() {
        generateRandomCode();
        String guess = null;
        int turns = 1;

        if (getState()) {
            System.out.println("Okay, let's start a game!");
        }

        while (getState()) {
            // System.out.println();
            System.out.printf("Turn %d:\n", turns);
            guess = scanner.nextLine();
            setState(checkGuess(guess));
            turns++;
        }
    }


    public void generateRandomCode() {
        String s = "0123456789abcdefghijklmnopqrstuvwxyz";
        String input = null;

        System.out.println("Input the length of the secret code:");
        int codeLength; // length of secret code
        try {
            input = scanner.nextLine();
            codeLength = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.printf("Error: \"%s\" isn't a valid number.", input);
            setState(false);
            return;
        }

        if (codeLength < 1 || codeLength > 36)  {
            System.out.println("Error: code length must be between 1 - 36!");
            setState(false);
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        int level; // size of symbols to be used
        try {
            input = scanner.nextLine();
            level = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.printf("Error: \"%s\" isn't a valid number.", input);
            setState(false);
            return;
        }

        if (level > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            setState(false);
            return;
        }

        if (codeLength > level) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.\n", codeLength, level);
            setState(false);
            return;
        }


        List<Character> randomSymbolsList = new ArrayList<>(level);
        for (int i = 0; i < level; i++) {
            randomSymbolsList.add(s.charAt(i));
        }

        Collections.shuffle(randomSymbolsList);
        StringBuilder stringBuilder = new StringBuilder();

        for (var ch : randomSymbolsList.subList(0, codeLength)) {
            stringBuilder.append(ch);
        }

        String symbolsUsed = level < 11 ? String.format("(0-%d)", level - 1) : String.format("(0-9, a-%s)", s.charAt(level-1));
        System.out.printf("The secret is prepared: %s %s.\n", "*".repeat(Math.max(0, codeLength)), symbolsUsed);

        setSecretCode(stringBuilder.toString());
        setState(true);
    }

    private boolean checkGuess(String guess) {
        String Secret = getSecretCode();
        int Bulls = 0;
        int Cows = 0;
        int n = Secret.length();
        String grade = "None";

        if (guess.length() < n || guess.length() > n) {
            System.out.printf("Guess must be consisted of %d symbols", n);
            return false;
        }

        // Count Bulls
        for (int i = 0; i < n; i++) {
            Bulls += Secret.charAt(i) == guess.charAt(i) ? 1 : 0;
        }

        if (Bulls == n) {
            System.out.printf("Grade: %d bulls.\n", n);
            System.out.println("Congratulations! You guessed the secret code.");
            return false;
        }

        // Count Cows
        for (int i = 0; i < n; i ++) {
            for (int j = 0; j < n; j++) {
                Cows += Secret.charAt(i) == guess.charAt(j) ? 1 : 0;
            }
        }

        Cows -= Bulls;

        String cowsGrade = Cows == 1 ? "1 cow" : String.format("%d cows", Cows);
        if (Bulls == 0 && Cows > 0) {
            grade = cowsGrade;
        } else {
            String bullsGrade = Bulls == 1 ? "1 bull" : String.format("%d bulls", Bulls);
            if (Bulls > 0 && Cows == 0) {
                grade = bullsGrade;
            } else if (Bulls > 0 && Cows > 0) {
                grade = bullsGrade + " and " + cowsGrade;
            }
        }

        System.out.printf("Grade: %s.\n", grade);
        return true;
    }
}


public class Main {
    public static void main(String[] args) {
        BullsAndCows myGame = new BullsAndCows();
        myGame.startGame();
    }
}