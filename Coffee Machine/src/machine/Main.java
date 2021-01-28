package machine;

import java.util.Scanner;

class coffeeMachine {
    Scanner scanner = new Scanner(System.in);
    int water;
    int milk;
    int coffeeBeans;
    int cups;
    int money;

    public coffeeMachine(int water, int milk, int coffeeBeans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;
    }

    public void getStatus() {
        // Gives status of Coffee Machine and money
        System.out.println("The coffee machine has:");
        System.out.printf("%d of water\n%d of milk\n%d of coffee beans\n", water, milk, coffeeBeans);
        System.out.printf("%d of disposable cups\n%d of money\n", cups, money);
    }

    public void takeAction() {
        boolean exit = false;
        while (!exit) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String input = scanner.nextLine();
            System.out.println();

            switch (input) {
                case "buy":
                    buy();
                    System.out.println();
                    break;

                case "fill":
                    fill();
                    System.out.println();
                    break;

                case "take":
                    take();
                    System.out.println();
                    break;

                case "remaining":
                    getStatus();
                    System.out.println();
                    break;

                case "exit":
                    exit = true;
                    break;

                default:
                    break;
            }
        }
    }

    public void buy() {
        // Buy Coffee
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                if (canMakeCoffee(250, 0, 46)) {
                    water -= 250;
                    coffeeBeans -= 16;
                    cups--;
                    money += 4;
                }
                break;

            case "2":
                if (canMakeCoffee(350, 75, 20)) {
                    water -= 350;
                    milk -= 75;
                    coffeeBeans -= 20;
                    cups--;
                    money += 7;
                }
                break;

            case "3":
                if (canMakeCoffee(200, 100, 12)) {
                    water -= 200;
                    milk -= 100;
                    coffeeBeans -= 12;
                    cups--;
                    money += 6;
                }
                break;

            case "back":
                break;

            default:
                System.out.println("Invalid option selected! Please choose between 1 - 3.");
        }
    }

    public void fill() {
        // Fill Coffee Machine
        System.out.println("Write how many ml of water do you want to add:");
        water += scanner.nextInt();

        System.out.println("Write how many ml of milk do you want to add:");
        milk += scanner.nextInt();

        System.out.println("Write how many grams of coffee beans do you want to add:");
        coffeeBeans += scanner.nextInt();

        System.out.println("Write how many disposable cups of coffee do you want to add:");
        cups += scanner.nextInt();
        scanner.nextLine();
    }

    public void take() {
        // Take money from moneyBox
        System.out.println("I gave you $" + money);
        money = 0;
    }

    public boolean canMakeCoffee(int waterNeeded, int milkNeeded, int coffeeBeansNeeded) {
        if (water - waterNeeded < 0) {
            System.out.println("Sorry, not enough water!");
            return false;
        } else if (milk - milkNeeded < 0) {
            System.out.println("Sorry, not enough milk!");
            return false;
        } else if (coffeeBeans - coffeeBeansNeeded < 0) {
            System.out.println("Sorry, not enough coffee beans!");
            return false;
        } else if (cups == 0) {
            System.out.println("Sorry, not enough cups available!");
            return false;
        }

        System.out.println("I have enough resources, making you a coffee!");
        return true;
    }
}


public class Main {
    public static void main(String[] args) {
        coffeeMachine myCoffeeMachine = new coffeeMachine(400, 540, 120, 9, 550);
        myCoffeeMachine.takeAction();
    }
}
