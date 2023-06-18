import java.util.Scanner;
import java.util.TreeMap;

public class Calculator {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String exp = scn.nextLine();
        System.out.println(calc(exp));

    }
    public static String calc(String input){
        Converter converter = new Converter();
        String[] actions = {"+", "-", "/", "*"};
        String[] regexActions = {"\\+", "-", "/", "\\*"};
        String R = "";
        int actionIndex=-1;
        for (int i = 0; i < actions.length; i++) {
            if(input.contains(actions[i])){
                actionIndex = i;
                break;
            }
        }

        if(actionIndex==-1){
            return "строка не является математической операцией";
        }

        String[] data = input.split(regexActions[actionIndex]);
        for (int i = 0; i < data.length; i++) {
            if (i > 1){
                return "формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)";
            }
        }

        if(converter.isRoman(data[0]) == converter.isRoman(data[1])){
            int a,b;
            boolean isRoman = converter.isRoman(data[0]);
            if(isRoman){
                a = converter.romanToInt(data[0]);
                b = converter.romanToInt(data[1]);
                if (a > 10 || b > 10){
                    return "Калкулятор принимает числа толко до десяти!";
                }
            }else{
                a = Integer.parseInt(data[0]);
                b = Integer.parseInt(data[1]);
                if (a > 10 || b > 10){
                    return "Калкулятор принимает числа толко до десяти!";
                }
            }

            int result;
            switch (actions[actionIndex]){
                case "+":
                    result = a+b;
                    break;
                case "-":
                    result = a-b;
                    break;
                case "*":
                    result = a*b;
                    break;
                default:
                    result = a/b;
                    break;
            }

            if(isRoman){
                if (result < 0){
                    return "в римской системе нет отрицательных чисел";
                }
                R=converter.intToRoman(result);
            }
            else{
                R=Integer.toString(result);
            }
        }   else{
            return "используются одновременно разные системы счисления";
        }
        return R;
    }

    static class Converter {
        TreeMap<Character, Integer> rom_map = new TreeMap<>();
        TreeMap<Integer, String> arab_map = new TreeMap<>();

        private Converter(){
            rom_map.put('I',1);
            rom_map.put('V',5);
            rom_map.put('X',10);
            rom_map.put('L',50);
            rom_map.put('C',100);

            arab_map.put(100, "C");
            arab_map.put(90, "XC");
            arab_map.put(40, "XL");
            arab_map.put(10, "X");
            arab_map.put(9, "IX");
            arab_map.put(5, "V");
            arab_map.put(4, "IV");
            arab_map.put(1, "I");
        }

        boolean isRoman(String number){
            return rom_map.containsKey(number.charAt(0));
        }

        String intToRoman(int number) {
            String roman = "";
            int arabianKey;
            do {
                arabianKey = arab_map.floorKey(number);
                roman += arab_map.get(arabianKey);
                number -= arabianKey;
            } while (number != 0);
            return roman;
        }

        int romanToInt(String s) {
            int end = s.length() - 1;
            char[] arr = s.toCharArray();
            int arabian;
            int result = rom_map.get(arr[end]);
            for (int i = end - 1; i >= 0; i--) {
                arabian = rom_map.get(arr[i]);

                if (arabian < rom_map.get(arr[i + 1])) {
                    result -= arabian;
                } else {
                    result += arabian;
                }
            }
            return result;
        }
    }
}

