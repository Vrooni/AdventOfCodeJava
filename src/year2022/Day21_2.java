package year2022;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21_2 {
    public String part2(List<String> lines) {
        Map<String, String> monkeys = new HashMap<>();

        for (String line : lines) {
            String[] splitLine = line.split(": ");

            String name = splitLine[0];
            String number = splitLine[1];

            monkeys.put(name, number);
        }

        monkeys.put("root", getEqualsCheck(monkeys.get("root")));
        monkeys.put("humn", "humn");

        String equation = getNumberOfRoot(monkeys, "root");
        return String.valueOf(getResult(equation));
    }

    private String getNumberOfRoot(Map<String, String> monkeys, String monkey) {
        String number = monkeys.get(monkey);

        if (number.equals("humn")) {
            return "(humn)";
        }

        if (isNumeric(number)) {
            return number;
        }

        if (number.contains("*")) {
            String[] operands = number.split(" [*] ");
            String operand1 = getNumberOfRoot(monkeys, operands[0]);
            String operand2 = getNumberOfRoot(monkeys, operands[1]);

            if (isNumeric(operand1) && isNumeric(operand2)) {
                long result = Long.parseLong(operand1) * Long.parseLong(operand2);
                return String.valueOf(result);
            } else {
                return "(" + operand1 + "*" + operand2 + ")";
            }

        } else if (number.contains("/")) {
            String[] operands = number.split(" / ");
            String operand1 = getNumberOfRoot(monkeys, operands[0]);
            String operand2 = getNumberOfRoot(monkeys, operands[1]);

            if (isNumeric(operand1) && isNumeric(operand2)) {
                long result = Long.parseLong(operand1) / Long.parseLong(operand2);
                return String.valueOf(result);
            } else {
                return "(" + operand1 + "/" + operand2 + ")";
            }

        } else if (number.contains("+")) {
            String[] operands = number.split(" [+] ");
            String operand1 = getNumberOfRoot(monkeys, operands[0]);
            String operand2 = getNumberOfRoot(monkeys, operands[1]);

            if (isNumeric(operand1) && isNumeric(operand2)) {
                long result = Long.parseLong(operand1) + Long.parseLong(operand2);
                return String.valueOf(result);
            } else {
                return "(" + operand1 + "+" + operand2 + ")";
            }

        } else if (number.contains("-")) {
            String[] operands = number.split(" - ");
            String operand1 = getNumberOfRoot(monkeys, operands[0]);
            String operand2 = getNumberOfRoot(monkeys, operands[1]);

            if (isNumeric(operand1) && isNumeric(operand2)) {
                long result = Long.parseLong(operand1) - Long.parseLong(operand2);
                return String.valueOf(result);
            } else {
                return "(" + operand1 + "-" + operand2 + ")";
            }

        } else if (number.contains("=")) {
            String[] operands = number.split("=");
            String operand1 = getNumberOfRoot(monkeys, operands[0]);
            String operand2 = getNumberOfRoot(monkeys, operands[1]);

            return operand1 + "=" + operand2;
        }

        else {
            throw new RuntimeException("No operator");
        }
    }

    private boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private String getEqualsCheck(String calc) {
        if (calc.contains("*")) {
            return calc.replace(" * ", "=");
        } else if (calc.contains("/")) {
            return calc.replace(" / ", "=");
        } else if (calc.contains("+")) {
            return calc.replace(" + ", "=");
        } else if (calc.contains("-")) {
            return calc.replace(" - ", "=");
        } else {
            throw new RuntimeException("No operator");
        }
    }

    private long getResult(String equation) {
        while (true) {
            long wanted = getWanted(equation);
            String expression = getExpression(equation);

            if (expression.equals("(humn)")) {
                return wanted;
            }

            expression = expression.substring(1, expression.length() - 1); //remove ()

            String operand;
            String operator;

            if (!expression.startsWith("(")) {
                int indexOfBrace = expression.indexOf("(");

                operator = expression.substring(indexOfBrace - 1, indexOfBrace);

                if (operator.equals("-") || operator.equals("/")) {
                    operand = expression.substring(indexOfBrace);
                    expression = expression.substring(0, indexOfBrace-1);
                } else {
                    operand = expression.substring(0, indexOfBrace-1);
                    expression = expression.substring(indexOfBrace);
                }

            } else if (!expression.endsWith(")")) {
                int indexOfBrace = expression.lastIndexOf(")");

                operator = expression.substring(indexOfBrace + 1, indexOfBrace + 2);
                operand = expression.substring(indexOfBrace + 2);
                expression = expression.substring(0, indexOfBrace+1);
            } else {
                throw new RuntimeException("Klammern hat so ned funksioniert");
            }

            String newWanted = getNewWanted(operator, wanted, operand);
            equation = newWanted + "=" + expression;
        }
    }

    private String getNewWanted(String operator, long wanted, String operand) {
        String newWanted;

        switch (operator) {
            case "*" -> newWanted = String.valueOf(wanted / Long.parseLong(operand));
            case "+" -> newWanted = String.valueOf(wanted - Long.parseLong(operand));
            case "/" -> {
                if (isNumeric(operand)) {
                    newWanted = String.valueOf(wanted * Long.parseLong(operand));
                } else {
                    newWanted = "(" + wanted + "*" + operand + ")";
                }
            }
            case "-" -> {
                if (isNumeric(operand)) {
                    newWanted = String.valueOf(wanted + Long.parseLong(operand));
                } else {
                    newWanted = "(" + wanted + "+" + operand + ")";
                }
            }
            default -> throw new RuntimeException("No operator found");
        }
        return newWanted;
    }

    private long getWanted(String equation) {
        String[] splitEquation = equation.split("=");

        if (!splitEquation[0].contains("humn")) {
            return Long.parseLong(splitEquation[0]);
        } else if (!splitEquation[1].contains("humn")) {
            return Long.parseLong(splitEquation[1]);
        } else {
            throw new RuntimeException("No result given");
        }
    }

    private String getExpression(String equation) {
        String[] splitEquation = equation.split("=");

        if (!splitEquation[0].contains("humn")) {
            return splitEquation[1];
        } else if (!splitEquation[1].contains("humn")) {
            return splitEquation[0];
        } else {
            throw new RuntimeException("No result given");
        }
    }
}
