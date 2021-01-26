package tictactoe;

import java.util.Arrays;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Creating and Initializing gird
        char[][] grid = new char[3][3];
        for (char[] array: grid) {
            Arrays.fill(array, '_');
        }

        // System.out.print("Enter cells: ");
        // String s = scanner.nextLine();

        // Initialize grid
        setGrid(grid);

        int flag = -1; // Game in progress
        int player = 1; // current player

        printGrid(grid);

        while (flag == -1) {
            setMove(grid, player % 2);
            printGrid(grid);
            flag = checkWin(grid);
            player++;
        }

        if (flag == 1) {
            System.out.println("X wins");
        } else if (flag == 2) {
            System.out.println("O wins");
        } else if (flag == 3) {
            System.out.println("Draw");
        } else {
            System.out.println("Impossible");
        }
    }



    public static void setGrid(char[][] grid, String s) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = s.charAt(i * 3 + j);
            }
        }
    }

    public static void setGrid(char[][] grid) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = '_';
            }
        }
    }

    public static void setMove(char[][] grid, int x) {
        Scanner scanner = new Scanner(System.in);
        String input;
        String[] inputStream;
        int row;
        int col;

        do {
            System.out.print("Enter the coordinates: ");

            input = scanner.nextLine();
            inputStream = input.split(" ", 2);

            if (inputStream[0].matches("\\d+")) {
                row = Integer.parseInt(inputStream[0]);
                if (inputStream[1].matches("\\d+")) {
                    col = Integer.parseInt(inputStream[1]);
                } else {
                    System.out.println("You should enter numbers!");
                    continue;
                }
            } else {
                System.out.println("You should enter numbers!");
                continue;
            }

            if (row < 1 || row > 3 || col < 1 || col > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else if (grid[row - 1][col - 1] != '_') {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                grid[row - 1][col - 1] = (x == 1) ? 'X' : 'O';
                break;
            }
        } while (true);
    }

    public static void printGrid(char[][] grid) {
        System.out.println("---------");
        for (char[] array: grid) {
            System.out.print("| ");
            for (char ch: array) {
                System.out.print(ch + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public static int checkWinner() {
        return -1;
    }

    public static int checkWin(char[][] grid) {
        int playerX = 0;
        int playerO = 0;
        int emptyPlaces = 0;
        boolean playerX_wins = isMatch(grid, 'X');
        boolean playerO_wins = isMatch(grid, 'O');

        for (char[] array: grid) {
            for (char ch : array) {
                if (ch == 'X') {
                    playerX += 1;
                } else if (ch == 'O') {
                    playerO += 1;
                } else {
                    emptyPlaces += 1;
                }
            }
        }

        if (Math.abs(playerX - playerO) > 1) {
            // System.out.println("Impossible");
            return -2;
        } else if (playerX_wins && playerO_wins) {
            // System.out.println("Impossible");
            return -3;
        } else if (playerX_wins) {
            // System.out.println("X wins");
            return 1;
        } else if (playerO_wins) {
            // System.out.println("O wins");
            return 2;
        } else {
            if (emptyPlaces == 0) {
                // System.out.println("Draw");
                return 3;
            } else {
                // System.out.println("Game not finished");
                return -1;
            }
        }
    }

    public static boolean isMatch(char[][] grid, char player) {
        // Check Rows
        for (int i = 0; i < 3; i++) {
            if (grid[i][0] == player && grid[i][1] == player &&  grid[i][2] == player) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (grid[0][i] == player && grid[1][i] == player &&  grid[2][i] == player) {
                return true;
            }
        }

        // Check Diagonals
        if (grid[0][0] == player && grid[1][1] == player &&  grid[2][2] == player) {
            return true;
        }
        if (grid[0][2] == player && grid[1][1] == player &&  grid[2][0] == player) {
            return true;
        }

        return false;
    }

}
