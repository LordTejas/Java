package readability;

import java.lang.invoke.SwitchPoint;
import java.nio.file.Files;
import java.util.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    final static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        // checkReadability("This is the front page of the Simple English Wikipedia. Wikipedias are places where people work together to write encyclopedias in different languages. We use Simple English words and grammar here. The Simple English Wikipedia is for everyone! That includes children and adults who are learning English. There are 142,262 articles on the Simple English Wikipedia. All of the pages are free to use. They have all been published under both the Creative Commons License and the GNU Free Documentation License. You can help here! You may change these pages and make new pages. Read the help pages and other good pages to learn how to write pages here. If you need help, you may ask questions at Simple talk. Use Basic English vocabulary and shorter sentences. This allows people to understand normally complex terms or phrases.");

        try {
            File file = new File(args[0]);

            Scanner scan = new Scanner(file);
            String s = new String(Files.readAllBytes(Paths.get(file.getName())));
            System.out.printf("The text is:\n%s\n\n", s);
            checkReadability(s);
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    public static void checkReadability(String s) {
        // Sentences
        ArrayList<String> sentences = getSentences(s);

        // Words
        ArrayList<ArrayList<String>> words = new ArrayList<>();
        for (String sentence : sentences) {
            words.add(new ArrayList<>(getWords(sentence)));
        }

        int totalWords = 0;
        for (ArrayList<String> innerArray : words) {
            totalWords += innerArray.size();
        }

        // Syllables
        ArrayList<Integer> syllables = new ArrayList<>();
        for (ArrayList<String> innerArray : words) {
            for (String word : innerArray) {
                syllables.add(countSyllables(word));
            }
        }

        int totalSyllables = 0;
        int polysyllables = 0;

        for (int i : syllables) {
            if (i > 2) {
                polysyllables++;
            }
            totalSyllables += i;
        }

        // Characters
        int chars = countChars(s);


        // Output
        System.out.println("The text is:\n" + s); // text
        System.out.println();

        System.out.printf("Words: %d\nSentences: %d\nCharacters: %d\nSyllables: %d\nPolysyllables: %d\n", totalWords, sentences.size(), chars, totalSyllables, polysyllables);

        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");

        String command = scan.next();
        System.out.println();

        double ariScore = automatedReadabilityTest(chars, totalWords, sentences.size());
        double fkScore = fleschKincaidReadablityTest(totalSyllables, totalWords, sentences.size());
        double smogScore = calculateSMOGIndex(polysyllables, sentences.size());
        double clScore = calculateColemanLiauIndex(chars, totalWords, sentences.size());
        int ariAge = (int) Math.ceil(ariScore) + 6;
        int fkAge = (int) Math.ceil(fkScore) + 6;
        int smogAge = (int) Math.ceil(smogScore) + 6;
        int clAge = (int) Math.ceil(clScore) + 6;

        switch (command) {
            case "ARI":
                System.out.printf("Automated Readability Index: %.2f (about %d-year-olds).\n", ariScore, ariAge);
                break;

            case "FK":
                System.out.printf("Flesch–Kincaid readability tests: %.2f (about %d-year-olds).\n", fkScore, fkAge);
                break;

            case "SMOG":
                System.out.printf("Simple Measure of Gobbledygook: %.2f (about %d-year-olds).\n", smogScore, smogAge);
                break;

            case "CL":
                System.out.printf("Coleman–Liau index: %.2f (about %d-year-olds).\n", clScore, clAge);
                break;

            case "all":
                System.out.printf("Automated Readability Index: %.2f (about %d-year-olds).\n", ariScore, ariAge);
                System.out.printf("Flesch–Kincaid readability tests: %.2f (about %d-year-olds).\n", fkScore, fkAge);
                System.out.printf("Simple Measure of Gobbledygook: %.2f (about %d-year-olds).\n", smogScore, smogAge);
                System.out.printf("Coleman–Liau index: %.2f (about %d-year-olds).\n", clScore, clAge);

                System.out.println();
                double meanAge =  (ariAge + fkAge + smogAge + clAge) / 4.0;
                System.out.printf("This text should be understood in average by 14.25-year-olds.\n", meanAge);
                break;

            default:
                System.out.println("error");
                break;
        }


    }


    public static int countChars(String s) {
        int c = 0; // counter

        for (int i = 0; i < s.length(); i++) {
            if (s.substring(i, i + 1).matches("[^\\s]")) {
                c++;
            }
        }

        return c;
    }

    public static ArrayList<String> getWords(String s) {
        return new ArrayList<String>(Arrays.asList(s.split("\\s")));
    }

    public static ArrayList<String> getSentences(String s) {
        ArrayList<String> sentences = new ArrayList<String>(Arrays.asList(s.split("[.!?]")));
        for (int i =0; i < sentences.size(); i++) {
            sentences.set(i, sentences.get(i).trim()); // removing unnecessary whitespace from beginning and ending
        }
        return sentences;
    }

    public static int countSyllables(String word) {
        int c = 0;

        String newWord = word.replaceAll("e\\b", "");
        newWord = newWord.replaceAll("[aeiouyAEIOUY]{2,3}", "a");
        newWord = newWord.replaceAll(",", "");

        for (int i = 0; i < newWord.length(); i++) {
            if (newWord.substring(i, i + 1).matches("[aeiouyAEIOUY]")) {
                c++;
            }
        }
        if (c == 0) {
            return 1;
        }
        return c;
    }


    public static double automatedReadabilityTest(int characters, int words, int sentences) {
        return 4.71 * characters / words + 0.5 * words / sentences - 21.43;
    }

    public static double fleschKincaidReadablityTest(int syllables, int words, int sentences) {
        return 0.39 * words / sentences + 11.8 * syllables / words - 15.59;
    }

    public static double calculateSMOGIndex(int polysyllables, int sentences) {
        return 1.043 * Math.sqrt(polysyllables * 30.0 / sentences) + 3.1291;
    }

    public static double calculateColemanLiauIndex(int character, int words, int sentences) {
        double l = (double) character / words * 100; // character per 100 words
        double s = (double) sentences / words * 100;// sentences per 100 words
        return 0.0588 * l - 0.296 * s - 15.8;
    }
}
