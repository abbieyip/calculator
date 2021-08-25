/**
 * Filename: Calculator.java
 * Compilation: javac Calculator.java
 * Usage:  java Calculator [mathematical expression]
 *         java Calculator
 *
 *         Note: [mathematical expression] must be in infix order
 * Description: Calculates [mathematical expression] and prints the answer to
 * the terminal; if argument is invalid (i.e. contains variables, double
 * decimals, or invalid double operators), throws an error
 *
 * Assumptions:
 *  1. Numbers are within the range of a double
 *  2. Cannot start or end with an operator
 *          e.g. +1 + 2 and 48 / are invalid
 *               - 1 + 2 is valid
 *  3. Multiplication must be written explicitly using "*"
 *          e.g. -(2 + 4) / 2 is invalid
 *               -1 * (2 + 4) / 2 is valid
 *  4. Parentheses must be filled
 *          e.g. ( ) and (( ) 1 + 2) are invalid
 */

import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;

public class Calculator {
    private static final char OPEN_PARENTHESIS = '(';
    private static final char CLOSED_PARENTHESIS = ')';
    private static final char DECIMAL = '.';
    private static final String PROMPT
            = "Enter a mathematical expression in infix notation.";
    private static final String NO_INPUT = "No input was given.";

  /**
   * Method name: evaluate
   * Description: completes the arithmetic of two numbers using the given
   * operator
   * @param num1, num2, and an operator
   * @throws DivisionByZeroException when trying to divide by zero
   * @return result of arguments based on given operator
   */
 public static double evaluate(double num1, double num2, String operator)
            throws DivisionByZeroException {
     switch (operator) {
         case "+": return num1 + num2;
         case "-": return num1 - num2;
         case "*": return num1 * num2;
         case "/":
             // invalid division by zero
             if (num2 == 0) {
                 throw new DivisionByZeroException();
             }
             return num1 / num2;
        }
        return 0;
  }

  /**
   * Method name: precedence
   * Description: obtains precedence of operator based on PEDMAS; also
   * determines if character is an operator if precedence is > 0
   * @param operator, any character
   * @return 1 if + or -, 2 if * or /, -1 if invalid
   */
  public static int precedence(char operator) {
      if (operator == '+' || operator == '-') {
          return 1;
      }
      else if (operator == '*' || operator == '/') {
          return 2;
      }
      else if (operator == OPEN_PARENTHESIS
              || operator == CLOSED_PARENTHESIS) {
          return 0;
      }
      else return -1;
  }
  /**
   * Method name: checkNumber
   * Description: Checks whether digit is a negative number or if it is longer
   * than one digit
   * @param charArray, to check number in charArray starting at given index
   * @return full digit in string
   */
  public static String checkNumber(char[] charArray, int index) {
      String num = "";
      while (index < charArray.length && (Character.isDigit(charArray[index])
              || charArray[index] == DECIMAL)) {
          num += charArray[index];
          index++;
      }
      return num;
  }
  /**
   * Method name: simpleFormat
   * Description: Helper method to convert double negatives into addition,
   * to remove white spaces, and to check for invalid characters
   * @throws InvalidCharacterException when argument has a non-digit or non-op
   * value
   * @throws SyntaxErrorException when argument has double operators, double
   * decimals, begins/ends with an operator, or is not in infix notation
   * @param input, expression in infix notation
   * @return valid expression without any double negatives or spaces in infix
   * notation
   */
  public static LinkedList<String> simpleFormat(String input)
          throws InvalidCharacterException, SyntaxErrorException {
      // removes all spaces
      String str = input.replaceAll("\\s", "");
      char[] charArray = str.toCharArray();

      LinkedList<String> list = new LinkedList<>();
      int index = 0;
      // iterates through each character
      while (index < charArray.length) {
          char current = charArray[index];
          // checks for doubles
          if (index < charArray.length - 1) {
              char next = charArray[index + 1];
              // removes double negatives
              if (current == '-' && next == '-') {
                  list.add("+");
                  index += 2;
                  continue;
              }
              // invalid double operator
              else if (current != '-' && precedence(current) > 0
                      && precedence(next) > 0) {
                  throw new SyntaxErrorException();
              }
              // valid negative number after operator
              else if (index > 0  && current == '-'
                      && ((charArray[index - 1] == OPEN_PARENTHESIS
                      || precedence(charArray[index - 1]) > 0))) {
                  String number = checkNumber(charArray, index + 1);
                  index += number.length() + 1;
                  list.add("-" + number);
                  continue;
              }
              // subtraction after end parenthesis
              else if (index > 0  && current == '-'
                      && charArray[index - 1] == CLOSED_PARENTHESIS) {
                  String number = checkNumber(charArray, index + 1);
                  index += number.length() + 1;
                  list.add("-");
                  list.add(number);
                  continue;
              }
              // beginning negative number
              else if (index == 0 && current == '-'
                      && checkNumber(charArray, index + 1).length() > 0) {
                  String num = checkNumber(charArray, index + 1);
                  list.add("-" + num);
                  index += num.length() + 1;
                  continue;
              }
              // invalid double decimal
              else if (current == DECIMAL && next == DECIMAL) {
                  throw new SyntaxErrorException();
              }
          }
          // valid operator
          if (precedence(current) >= 0 || current == DECIMAL) {
              list.add("" + current);
          }
          // reached number
          else if (Character.isDigit(current)) {
              String num = checkNumber(charArray, index);
              list.add(num);
              index += num.length() - 1;
          }
          else { // invalid
              String invalidChar = Character.toString(current);
              throw new InvalidCharacterException(invalidChar);
          }
        index++;
      }
      // checks if expression begins or ends with an invalid operator
      if ((charArray[0] != '-' && precedence(charArray[0]) > 0)
              || precedence(charArray[charArray.length - 1]) > 0) {
          throw new SyntaxErrorException();
      }
      return list;
  }

  /**
   * Method name: postfix
   * Description: changes infix expression to postfix expression based
   * on precedence, removes parentheses
   *   Examples: 24 + 2 => 24 2 +
   *             2 + 4 * 3 => 2 4 3 * +
   *             (2 + 4) * 3 => 2 4 + 3 *
   * @param input, expression in linked list format
   * @return String expression in postfix format, numbers are separated by
   * @throws SyntaxErrorException when input is not formatted correctly
   * an empty space
   */
  public static String postfix(LinkedList<String> input)
          throws SyntaxErrorException {
      String output = "";
      Stack<Character> stack = new Stack<>();

    // iterates through character array
    for (int i = 0; i < input.size(); i++) {
        // checks for PEDMAS
        String current = input.get(i);
        char firstChar = current.charAt(0);
        // not a negative number or multi-digit number; check for op precedence
        if (current.length() == 1 && precedence(firstChar) > 0) {
            // stack has higher priority than current, add top to output
            while (!stack.empty()
                    && precedence(stack.peek()) >= precedence(firstChar)) {
                output += stack.pop() + " "; // space out numbers & operators
            }
            stack.push(firstChar);
        }
        // reached a number
        else if (current.length() > 1 || Character.isDigit(firstChar)) {
            output += current + " ";
        }
        else if (firstChar == OPEN_PARENTHESIS) {
            stack.push(firstChar);
        }
        else if (firstChar == CLOSED_PARENTHESIS) {
            if (stack.empty()) {
                throw new SyntaxErrorException();
            }
            char operator = stack.pop();
            // backtracks until an open parenthesis is met
            while (operator != OPEN_PARENTHESIS) {
                output += operator + " ";
                if (stack.empty()) { // unbalanced
                    throw new SyntaxErrorException();
                }
                operator = stack.pop();
            }
        }
    }
    while (!stack.empty()) {
        if (stack.peek() == OPEN_PARENTHESIS) {
            throw new SyntaxErrorException(); // unbalanced
        }
        output += stack.pop() + " ";
    }
    return output;
  }

  /**
   * Method name: parseString
   * Description: evaluates postfix expression
   * @param expression in postfix format
   * @return evaluated mathematical expression
   */
  public static double parseString(String expression)
          throws SyntaxErrorException {
      // separate numbers and operators
      String split[] = expression.split(" ");
      Stack<Double> stack = new Stack<>();

      // iterates through each operator and number
      for (int i = 0; i < split.length; i++) {
          // reached an operator
          if (split[i].equals("+") || split[i].equals("-")
                  || split[i].equals("*") || split[i].equals("/")) {
              if (stack.size() > 1) { // must have two numbers to evaluate
                  // quantity
                  double num2 = stack.pop();
                  double num1 = stack.pop();
                  try {
                      double ans = evaluate(num1, num2, split[i]);
                      stack.push(ans);
                  } catch (DivisionByZeroException e) { // division by 0
                      System.out.println(e.getMessage());
                      System.exit(1);
                  }
              }
              else {
                  throw new SyntaxErrorException();
              }
          }
          // reached a numerical value
          else {
              // invalid empty string
              if (split[i].length() == 0) {
                  throw new SyntaxErrorException();
              }
              stack.push(Double.parseDouble(split[i]));
          }
      }
      if (!stack.empty()) {
          return stack.pop();
      }
      else {
          throw new SyntaxErrorException();
      }
  }

  /**
   * Method name: main
   * Description: checks for number of arguments and prints out evaluated
   * expression if arg is valid; otherwise prints out argument is invalid
   * @param args, string expression
   *
   */
  public static void main(String[] args)  {
      String arg = "";
      // no in-line command given, ask for an expression
      if (args.length == 0) {
          System.out.println(PROMPT);
          Scanner sc = new Scanner(System.in);
          arg  = sc.nextLine();
      }
      // expression given
      else {
          for (String str : args) {
              arg += str;
          }
      }
      if (arg.length() == 0) {
          System.out.println(NO_INPUT);
          System.exit(0);
      }
      // evaluates expression
      try {
          String postfixFormat = postfix(simpleFormat(arg));
          double ans = parseString(postfixFormat);
          System.out.println(arg + " = " + ans);
          System.exit(0);
      }
      catch (InvalidCharacterException | SyntaxErrorException e) {
          System.out.println(e.getMessage());
          System.exit(1);
      }
  }
}
