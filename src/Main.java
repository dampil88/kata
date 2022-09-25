import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{
        Scanner in = new Scanner(System.in);
        System.out.println("Я могу произвести арифметическую операцию между двумя операндами (от 1 до 10) и одним оператором(+, -, /, *). Пожалуйста введите выражение:");
        String input = in.nextLine();
        String modifiedInput = "";

        if (checkingString(input, "+") || (checkingString(input, "-") || (checkingString(input, "*") || (checkingString(input, "/"))))) {
            if (input.contains(" ")) {
                String finalNumber = calc(input);
                System.out.println(finalNumber);
                in.close();
            } else {
                if (input.contains("+") || input.contains("-") || input.contains("*") || input.contains("/")) {
                    modifiedInput = input.replace("+", " + ").replace("-"," - ").replace("*"," * ").replace("/"," / ");
                }
                String finalNumber = calc(modifiedInput);
                System.out.println(finalNumber);
                in.close();
            }
        } else {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Cтрока не является математической операцией");
            }
            }
        }

    public static String calc(String calculate) throws Exception{
        int result = 0;
        int number1 = 0;
        int number2 = 0;
        boolean roman = false;


        String[] expression = calculate.replace(" ","").split("\\+|-|\\*|\\/");
        if (expression.length >= 3) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Формат математической операции не верны - должно быть два операнда и один оператор, также я не работаю с отрицательными числами");
                System.exit(1);
            }
        }
        String regex = "\\d+";
        if (!(expression[0].matches(regex) && expression[1].matches(regex)) && !(checkingStringFromEnum(expression[0], RomanNumber.values()) && checkingStringFromEnum(expression[1], RomanNumber.values()))) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Неверный формат ввода данных");
                System.exit(1);
        }}


        if (checkingStringFromEnum(expression[0], RomanNumber.values()) && checkingStringFromEnum(expression[1], RomanNumber.values())){
            number1 = romanToArabic(expression[0]);
            number2 = romanToArabic(expression[1]);
            roman = true;
        } else if (!checkingStringFromEnum(expression[0], RomanNumber.values()) && !checkingStringFromEnum(expression[1], RomanNumber.values())) {
            number1 = Integer.parseInt(expression[0]);
            number2 = Integer.parseInt(expression[1]);
        } else {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Используются одновременно разные системы счисления");
                System.exit(1);
            }
        }


        if ((number1 > 10 || number1 <= 0) || (number2 > 10 || number2 <= 0)){
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Я умею работать только с числами от 1 до 10");
                System.exit(1);
            }
        }

        if (calculate.contains("+")) {
            result = number1 + number2;
        }
        if (calculate.contains("-")) {
            result = number1 - number2;
        }
        if (calculate.contains("/")) {
            result = number1 / number2;
        }
        if (calculate.contains("*")) {
            result = number1 * number2;
        }

        if (roman) {
            return arabicToRoman(result);
        } else {
            return Integer.toString(result);
        }
    }

    public static boolean checkingString(String input, String check){
        return input.contains(check);
    }

    public static boolean checkingStringFromEnum(String input, RomanNumber[] number){
        for (RomanNumber value : RomanNumber.values()) {
            if (input.contains(value.toString())) {
                return true;
            }
        }
        return false;
    }

    public static int romanToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;

        List<RomanNumber> romanNumerals = RomanNumber.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumber symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(input + " не может быть конвертировано");
        }

        return result;
    }

    public static String arabicToRoman(int number) {
        if ((number <= 0)) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("В римской системе нет отрицательных чисел и 0");
                System.exit(1);
            }
        }

        List<RomanNumber> romanNumerals = RomanNumber.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumber currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }
}