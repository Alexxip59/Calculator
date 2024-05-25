import java.util.*;
import java.util.regex.*;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение из двух чисел от 1 до 10 или от i до x, оператор +, -, /, *: ");
        String input = scanner.nextLine();

        String regexA = "\\b([1-9]|10)([-+*/])([1-9]|10)\\b";
        Pattern patternA = Pattern.compile(regexA);

        String regexR = "\\b(i|ii|iii|iv|v|vi|vii|viii|ix|x)([-+*/])(i|ii|iii|iv|v|vi|vii|viii|ix|x)\\b";
        Pattern patternR = Pattern.compile(regexR);

        Matcher mA = patternA.matcher(input);
        Matcher mR = patternR.matcher(input.toLowerCase());

        boolean foundA = mA.find();
        boolean foundR = mR.find();

        Matcher matcher = foundA ? mA : foundR ? mR : null;
        //Matcher matcher = null;
        //if (foundA) {
        //    matcher = mA;
        //} else if (foundR) {
        //    matcher = mR;
        //}

        if (foundA || foundR) {
            int firstOperand = getOperand(matcher.group(1));
            String operator = matcher.group(2);
            int secondOperand = getOperand(matcher.group(3));
            int result = 0;

            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "*":
                    result = firstOperand * secondOperand;
                    break;
                case "/":
                    result = firstOperand / secondOperand;
                    break;
                default:
                    break;
            }

            if (result != 0) {
                if (foundA) {
                    System.out.println("Результат: " + result);
                } else {
                    System.out.println("Результат: " + convertToRoman(result));
                }
            }
        } else {
            throw new InputMismatchException("Повторите ввод (напр. 10+9 или ix/iii)");
            //System.out.println("Повторите ввод (напр. 10+9 или ix/iii)");
        }
    }

    static int getOperand(String opStr) {
        int result;

        try {
           result = Integer.parseInt(opStr);
        } catch (NumberFormatException e) {
            result = switch (opStr) {
                case "i" -> 1;
                case "ii" -> 2;
                case "iii" -> 3;
                case "iv" -> 4;
                case "v" -> 5;
                case "vi" -> 6;
                case "vii" -> 7;
                case "viii" -> 8;
                case "ix" -> 9;
                case "x" -> 10;
                default -> throw e;
            };
        }
        return result;
    }

    static String convertToRoman(int number) {
        if (number < 0 || number > 100) {
            throw new InputMismatchException("Это число не может быть преобразовано");
            //return "Это число не может быть преобразовано";
        }

        String romanOnes = romanDigit(number % 10, "I", "V", "X");
        number /= 10;

        String romanTens = romanDigit(number % 10, "X", "L", "C");
        number /= 10;

        String romanHundreds = romanDigit(number % 10, "C", "", "");

        String result = "";
        if (!romanHundreds.equals("0")) {
            result = result + romanHundreds;
        }
        if (!romanTens.equals("0")) {
            result = result + romanTens;
        }
        return result + romanOnes;
    }

    static String romanDigit(int n, String one, String five, String ten) {
        if (n >= 1) {
            switch (n) {
                case 1:
                    return one;
                case 2:
                    return one + one;
                case 3:
                    return one + one + one;
                case 4:
                    return one + five;
                case 5:
                    return five;
                case 6:
                    return five + one;
                case 7:
                    return five + one + one;
                case 8:
                    return   five + one + one + one ;
                case  9 :
                    return   one + ten ;
                default:
                    break ;
            }
        }
        return "0";
    }
}
