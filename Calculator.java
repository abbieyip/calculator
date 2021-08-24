/**
 * Filename: Calculator.java
 * Compilation: javac Calculator.java
 * Usage: java Calculator [mathematical expression]
 *        java Calculator
 * Description: Calculates [mathematical expression] and prints the answer to
 * the terminal; if argument is invalid (i.e. contains variables, double decimals,
 * or invalid double operators), throws an error
 *
 * Assumptions:
 *  1. Numbers are within the range of a double
 *  2. Cannot start with an operator (e.g. --1 + 2 or + 4 * 8)
 */

import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;

public class Calculator {

  private static final char OPEN_PARENTHESIS = '(';
  private static final char CLOSED_PARENTHESIS = ')';
  private static final char[] NUMBERS = {'0', '1', '2', '3', '4', '5', '6',
    '7', '8', '9'};
  private static final char DECIMAL = '.';

 /**
  * Method name: evaluate
  * Description: completes the arithmetic of two numbers using the given
  * operator
  * @param: two numbers and an operator
  * @throws: DivisionByZeroException when trying to divide by zero
  * @return: evaluated number
  */
  public static double evaluate(double num1, double num2, String operator)
    throws DivisionByZeroException {
      switch (operator) {
        case "+":
          return num1 + num2;
        case "-":
          return num1 - num2;
        case "*":
          return num1 * num2;
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
  * Description: obtains precedence of operator based on PEDMAS; also determines
  * if character is an operator
  * @param: char operator
  * @return: 1 if + or -, 2 if * or /, -1 if invalid
  */
  public static int precedence(char operator) {
    if (operator == '+' || operator == '-') {
      return 1;
    }
    else if (operator == '*' || operator == '/') {
      return 2;
    }
    else if (operator == OPEN_PARENTHESIS || operator == CLOSED_PARENTHESIS) {
      return 0;
    }
    else return -1;
  }

  /**
   * Method name: isDigit
   * Description: Helper method to check if character is a digit
   * @param: char
   * @return: true if character is a digit, otherwise false
   */
   public static boolean isDigit(char character) {
     for (char num : NUMBERS) {
       if (character == num) {
         return true;
       }
     }
     return false;
   }

 /**
  * Method name: simpleFormat
  * Description: Helper method to convert double negatives into addition,
  * to remove white spaces, and to check for invalid characters
  * @throws: InvalidCharacterException, DoubleOperatorException,
  * InvalidInfixOperatorException
  * @param: String mathematical expression
  * @return: valid expression string without any double negatives or
  * spaces
  */
  public static LinkedList<String> simpleFormat(String input)
    throws InvalidCharacterException, SyntaxErrorException {
      // removes all spaces
      String str = input.replaceAll("\\s", "");
      char[] charArray = str.toCharArray();

      LinkedList<String> list = new LinkedList<String>();
      int index = 0;
      // iterates through each character
      while (index < charArray.length) {
          if (index < charArray.length - 1) {
              // removes double negatives
              if (charArray[index] == '-' && charArray[index + 1] == '-') {
                  list.add("+");
                  index += 2;
                  continue;
              }
              // invalid double operator
              else if (charArray[index + 1] != '-' && precedence(charArray[index]) > 0
                  && precedence(charArray[index + 1]) > 0) {
                  throw new SyntaxErrorException();
              }
              // valid negative number after operator
              else if (index > 0 && charArray[index] == '-' && precedence(charArray[index - 1]) > 0
                  && isDigit(charArray[index + 1])) {
                  list.add("-" + charArray[index + 1]);
                  index += 2;
                  continue;
              }
              // beginning negative number
              else if (index == 0 && charArray[index] == '-' && isDigit(charArray[index + 1])) {
                  list.add("-" + charArray[index + 1]);
                  index += 2;
              }
              // invalid double decimal
              else if (charArray[index] == DECIMAL && charArray[index + 1] == DECIMAL) {
                  throw new SyntaxErrorException();
              }
          }
          // valid operator or number
          if (precedence(charArray[index]) > 0 || isDigit(charArray[index]) || charArray[index] == OPEN_PARENTHESIS
          || charArray[index] == CLOSED_PARENTHESIS || charArray[index] == DECIMAL) {
              list.add("" + charArray[index]);
          }
          else { // invalid
              throw new InvalidCharacterException(Character.toString(charArray[index]));
          }
        index++;
      }
        // checks if expression begins or ends with an invalid operator
      if ((list.indexOf(0) != '-' && precedence(((list.get(0)).charAt(0))) > 0)
              || precedence(list.getLast().charAt(0)) > 0) {
          throw new SyntaxErrorException();
      }
      return list;
  }

 /**
  * Method name: postfix
  * Description: changes infix expression to postfix expression based
  * on precedence
  *   Examples: 24 + 2 => 24 2 +
  *             2 + 4 * 3 => 2 4 3 * +
  *             (2 + 4) * 3 => 2 4 + 3 *
  * @param expression in linked list format
  * @return String expression in postfix format, numbers are separated by
  * @throws SyntaxErrorException when input is not formatted correctly
  * an empty space
  */
  public static String postfix(LinkedList<String> input) throws SyntaxErrorException {
    String output = "";
    Stack<Character> stack = new Stack<Character>();

    // iterates through character array
    for (int i = 0; i < input.size(); i++) {
        // checks for PEDMAS
        String current = input.get(i);
        char firstChar = current.charAt(0);
        // not a negative number or multi-digit number; check for precedence
        if (current.length() == 1 && precedence(firstChar) > 0) {
            // stack has higher priority than current, add top to output
            while (!stack.empty() && precedence(stack.peek()) >= precedence(firstChar)) {
                output += stack.pop() + " "; // space out numbers & operators
            }
            stack.push(firstChar);
        }
        // reached a number
        else if (current.length() > 1 || isDigit(firstChar)) {
            output += current + " ";
        }
        else if (firstChar == OPEN_PARENTHESIS) {
            stack.push(firstChar);
        }
        else if (firstChar == CLOSED_PARENTHESIS) {
            char operator = stack.pop();
            while (operator != OPEN_PARENTHESIS) {
                output += operator + " ";
                if (stack.empty()) {
                    throw new SyntaxErrorException();
                }
                operator = stack.pop();
            }
        }
    }
    while (!stack.empty()) {
        if (stack.peek() == OPEN_PARENTHESIS) {
            throw new SyntaxErrorException();
        }
        output += stack.pop() + " ";
    }
    return output;
  }

 /**
  * Method name: parseString
  * Description: evaluates postfix expression
  * @param: expression in postfix format
  * @return: evaluated mathematical expression
  */
  public static double parseString(String expression) {
    String split[] = expression.split(" "); // separates numbers and operators
    Stack<Double> stack = new Stack<Double>();

    // iterates through each operator and number
    for (int i = 0; i < split.length; i++) {
      System.out.println("reading " + split[i]);
      // reached an operator
      if (split[i].equals("+") || split[i].equals("-") || split[i].equals("*")
        || split[i].equals("/")) {
          // evaluates quantity
          double num2 = stack.pop();
          double num1 = stack.pop();
          try {
            double ans = evaluate(num1, num2, split[i]);
            stack.push(ans);
          }
          catch (DivisionByZeroException e) { // division by 0
            System.out.println(e.getMessage());
            System.exit(1);
          }
      }
      // reached a numerical value
      else {
        stack.push(Double.parseDouble(split[i]));
      }
    }
    double answer = stack.pop();
    // final answer is located in stack
    while (!stack.empty()) {
       stack.pop();
    }
    // error, missing operator or invalid expression
    return answer;
  }

 /**
  * Method name: main
  * Description: checks for number of arguments and prints out evaluated
  * expression if arg is valid; otherwise prints out argument is invalid
  */
  public static void main(String[] args) {
    String arg = "";
    // no in-line command given, ask for an expression
    if (args.length == 0) {
      System.out.println("Enter a mathematical expression.");
      Scanner sc = new Scanner(System.in);
      arg  = sc.nextLine();
    }

    // expression given
    else {
      for (String str : args) {
        arg += str;
      }
    }
    // evaluates expression
    try {
      String postfixFormat = postfix(simpleFormat(arg));
      System.out.println("post: " + postfixFormat);
      double ans = parseString(postfixFormat);
      System.out.println(arg + " = " + ans);
      System.exit(0);
    }
    catch (InvalidCharacterException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
    catch (SyntaxErrorException e) {
      System.out.println(e.getMessage() );
      System.exit(1);
    }
  }
}
