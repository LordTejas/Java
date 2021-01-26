package sudokuCheck;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    Scanner scanner = new Scanner(System.in);
	    int gridSize = scanner.nextInt();
	    int gridLength = gridSize * gridSize;
	    int[][] grid = new int[gridLength][gridLength];

	    for (int i = 0; i < gridLength; i++) {
	        for (int j = 0; j < gridLength; j++) {
	            grid[i][j] = scanner.nextInt();
            }
        }

	    System.out.print(sudokuCheck(gridSize, grid) ? "YES" : "NO");

    }


    public static boolean sudokuCheck( int gridSize, int[][] grid) {
        int n = gridSize * gridSize;
        int[] temp = new int[n];

        // Check Rows
        for (int[] array: grid) {
            if (!Unique(array) || !inBound(n, array)) {
                return false;
            }
        }

        // Check Columns
        for (int col = 0; col < n; col++) {
            for (int row = 0; row < n; row++) {
                temp[row] = grid[row][col];
            }
            if (!Unique(temp) || !inBound(n, temp)) {
                return false;
            }
        }

        // Check subGrid
        for (int row = 0; row < n; row += gridSize) {
            for (int col = 0; col < n; col += gridSize) {

                int i = 0;
                for (int r = row; r < row + gridSize; r++) {
                    for (int c = col; c < col + gridSize; c++) {
                        temp[i] = grid[r][c];
                        i++;
                    }
                }
                if (!Unique(temp)) {
                    return false;
                }
            }
        }


        return true;
    }


    public static boolean Unique(int[] array) {
        // Checks all elements in a array are distinct
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (array[i] == array[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean inBound(int limit, int[] array) {
        // Checks all elements in a array are in bound(1 - limit)
        for (int num: array) {
            if (num <= 0 || num > limit) {
                return false;
            }
        }
        return true;
    }
}

