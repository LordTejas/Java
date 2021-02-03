package cinema;

import java.util.*;

public class Cinema {
    private final Scanner scanner = new Scanner(System.in);
    private ArrayList<ArrayList<String>> room;
    private int totalRows;
    private int seatsPerRows;
    private int totalSeats;
    private int partition;
    private int seatsBooked;
    private int currentIncome;
    private int maxProfit;

    public int getBalance() {
        return this.currentIncome;
    }

    private void setBalance() {
        this.currentIncome = 0;
    }

    private void setBalance(int value) {
        this.currentIncome = value;
    }

    public void addMoney(int money) {
        this.currentIncome += money;
    }

    public int getMaxProfit() {
        return this.maxProfit;
    }

    private void setMaxProfit() {
        this.maxProfit = getTotalProfit(this.totalRows, this.seatsPerRows);
    }

    public int getTotalsRows() {
        return this.totalRows;
    }

    public int getSeatsPerRows() {
        return this.seatsPerRows;
    }

    public int getTotalSeats() {
        return this.totalSeats;
    }

    private void setTotalsRows(int value) {
        this.totalRows = value;
    }

    private void setSeatsPerRows(int value) {
        this.seatsPerRows = value;
    }

    private void setTotalsSeats(int value) {
        this.totalSeats = value;
    }

    public int getPartition() {
        return this.partition;
    }

    public void setPartition(int value) {
        this.partition = value;
    }

    public int getSeatsBooked() {
        return this.seatsBooked;
    }

    private void incSeatsBooked() {
        this.seatsBooked += 1;
    }

    public String getSeat(int row, int col) {
        return room.get(row).get(col);
    }

    public void setSeat(int row, int col, String value) {
        this.room.get(row).set(col, value);
    }

    public ArrayList<String> getRow(int row) {
        return this.room.get(row);
    }

    private void setRow(int row, ArrayList<String> value) {
        this.room.set(row, value);
    }

    private void addRow(ArrayList<String> value) {
        this.room.add(new ArrayList<>(value));
    }


    public void createRoom() {
        System.out.println("Enter the number of rows:");
        int rows = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the number of seats in each row:");
        int columns = Integer.parseInt(scanner.nextLine());
        createRoom(rows, columns);
    }

    public void createRoom (int rows, int columns) {
        // Creating room structure
        this.room = new ArrayList<>();
        setTotalsRows(rows);
        setSeatsPerRows(columns);
        setTotalsSeats(rows * columns);
        setPartition(getTotalSeats() > 60 ? rows / 2 : 0);
        setBalance();
        setMaxProfit();


        ArrayList<String> defaultList = new ArrayList<>(columns);
        for (int i = 0; i < columns; i++) {
            defaultList.add("S");
        }
        for (int i = 0; i < rows; i++) {
            addRow(defaultList);
        }

    }


    public int getSeatCost(int row, int column) {
        return getPartition() == 0 ? 10 : row > getPartition() ? 8 : 10;
    }


    public void getTotalProfit() {
        System.out.println("Enter the number of rows:");
        int row = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the number of seats in each row:");
        int col = Integer.parseInt(scanner.nextLine());

        int profit = getTotalProfit(row, col);

        System.out.printf("Total income:\n$%d\n", profit);
    }

    public int getTotalProfit(int row, int col) {
        int totalSeats = row * col;

        if (totalSeats <= 60) {
            return totalSeats * 10;
        }

        int partition = row / 2;
        return partition * col * 10 + (row - partition) * col * 8;
    }


    public void bookSeat() {
        int row;
        int col;

        do {
            System.out.println("Enter a row number:");
            try {
                row = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Please enter a number!\n");
                continue;
            }

            System.out.println("Enter a seat number in that row:");
            try {
                col = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Please enter a number!\n");
                continue;
            }

            System.out.println();

            if (row < 1 || row > getTotalsRows() || col < 1 || col > getSeatsPerRows()) {
                System.out.println("Wrong input!\n");
            } else if ("B".equals(getSeat(row - 1, col - 1))) {
                System.out.println("That ticket has already been purchased!\n");
            } else {
                break;
            }
        } while (true);

        bookSeat(row, col);
    }

    public void bookSeat(int row, int column) {
        int price = getSeatCost(row, column);

        setSeat(row - 1, column - 1, "B");
        addMoney(price);
        incSeatsBooked();

        System.out.printf("Ticket price: $%d\n", price);
    }


    public void printRoomStructure() {
        System.out.println("Cinema:");

        // print 1st row
        System.out.print("  ");
        for (int i = 1; i <= getSeatsPerRows(); i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // print rest rows with indices
        for (int i = 0; i <= getTotalsRows() - 1; i++) {
            for (int j = 0; j <= getSeatsPerRows(); j++) {
                if (j == 0) {
                    System.out.print((i + 1) + " ");
                } else {
                    System.out.print(getSeat(i, j - 1) + " ");
                }
            }
            System.out.println();
        }
    }


    public void getStatistics() {
        double percentage = (double) getSeatsBooked() / getTotalSeats() * 100;
        System.out.printf("Number of purchased tickets: %d\nPercentage: %.2f%%\n", getSeatsBooked(), percentage);
        System.out.printf("Current income: $%d\nTotal income: $%d\n", getBalance(), getMaxProfit());
    }


    public void openMenu() {
        String input;

        do {
            System.out.println("\n1. Show the seats\n" +
                    "2. Buy a ticket\n" +
                    "3. Statistics\n" +
                    "0. Exit");


            input = scanner.nextLine();
            if (input.equals("0")) {
                break;
            }
            System.out.println();

            switch (input) {
                case "1" :
                    printRoomStructure();
                    break;

                case "2" :
                    bookSeat();
                    break;

                case "3" :
                    getStatistics();
                    break;

                default :
                    System.out.println("Invalid option!");
            }

        } while (true);
    }
}
