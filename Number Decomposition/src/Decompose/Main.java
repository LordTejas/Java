package Decompose;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
	    int n = scanner.nextInt();
	    printPartitions(n);
    }

    private static void printPartitions(int target, int max, String suffix)
    {
        if (target == 0)
            System.out.println(suffix);
        else
        {
            for (int i = 1; i <= max && i <= target; i++)
                printPartitions(target - i, i, i + " " + suffix);
        }
    }

    public static void printPartitions(int target)
    {
        printPartitions(target, target, "");
    }
}
