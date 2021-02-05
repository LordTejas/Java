package numsystem;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            int sourceRadix = Integer.parseInt(scanner.nextLine());
            String number = scanner.nextLine();
            if (!checkNumber(number, sourceRadix)) {
                System.out.println("error");
            } else {
                int targetRadix = Integer.parseInt(scanner.nextLine());
                System.out.println(convertNumber(number, sourceRadix, targetRadix));
            }
        } catch (Exception e) {
            System.out.println("error");
        }

    }

    public static String convertNumber(String number, int sourceRadix, int targetRadix) {
        // Converts any number from sourceRadix System to targetRadix system
        if (sourceRadix < 1 || sourceRadix > 36 || targetRadix < 1 || targetRadix > 36) {
            return "error.";
        }

        if (!checkNumber(number, sourceRadix)) {
            return "error";
        }

        if (sourceRadix == targetRadix) {
            return number;
        }

        if (number.contains(".")) {
            String[] parts = number.split("\\.", 2);
            return convertNumber(parts[0], sourceRadix, targetRadix) + "." + convertFractional(parts[1], sourceRadix, targetRadix);
        }

        long decimalConverted = Long.parseLong((toDecimal(number, sourceRadix)));

        if (targetRadix == 1) {
            return "1".repeat((int) decimalConverted);
        }

        return Long.toString(decimalConverted, targetRadix);
    }

    public static String convertFractional(String number, int sourceRadix, int targetRadix) {
        // Converts fractional part of number from sourceRadix System to targetRadix system
        String decimalConverted = fractionalToDecimal(number, sourceRadix);

        double n = Double.parseDouble("0." + decimalConverted);
        String[] parts;
        ArrayList<Integer> digits = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            n *= targetRadix;
            parts = String.valueOf(n).split("\\.", 2);
            digits.add(Integer.parseInt(parts[0]));
            n = Double.parseDouble("0." + parts[1]);
        }

        StringBuilder result = new StringBuilder();

        for (int i : digits) {
            result.append(convertNumber(String.valueOf(i), 10, targetRadix));
        }

        return result.toString();
    }


    public static String toDecimal(String number, int sourceRadix) {
        // Converts given number from sourceRadix system to decimal system
        if (sourceRadix == 1) {
            return String.valueOf(number.length());
        }

        if (sourceRadix == 10) {
            return number;
        }

        return String.valueOf(Integer.parseInt(number, sourceRadix));
    }

    public static String fractionalToDecimal(String number, int sourceRadix) {
        // Converts fractional part from sourceRadix system to Decimal number system
        if (sourceRadix == 10) {
            return number;
        }

        String s = null;
        ArrayList<Integer> digits = new ArrayList<>();

        for (int i = 0; i < number.length(); i++) {
            digits.add(Integer.valueOf(toDecimal(number.substring(i, i + 1), sourceRadix)));
        }

        double result = 0.0f;

        for (int i = 0; i < digits.size(); i++) {
            result += digits.get(i) / Math.pow(sourceRadix, i + 1);
        }

        return String.valueOf(result).substring(2);
    }

    public static boolean checkNumber(String number, int sourceRadix) {
        String digits = "0123456789abcdefghijklmnopqrstuvwxyz";
        String digitsUsed = digits.substring(0, sourceRadix);
        if (sourceRadix == 1) {
            digitsUsed = "1";
        }

        if (number.contains(".")) {
            try {
                String[] parts = number.split("\\.", 2);
                return checkNumber(parts[0], sourceRadix) && checkNumber(parts[1], sourceRadix);
            } catch (Exception e) {
                return false;
            }
        }

        for (int i = 0; i < number.length(); i++) {
            if (!digitsUsed.contains(number.substring(i, i + 1))) {
                return false;
            }
        }

        return true;
    }
}
