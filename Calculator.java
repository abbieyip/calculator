/**
 * Filename: Calculator.java
 * Compilation: javac Calculator.java
 * Usage: java Calculator [mathematical expression]
 *        java Calculator
 * Description: Calculates [mathematical expression] and prints the answer to
 * the terminal; if argument is invalid (i.e. contains variables or does not
 * have balanced parentheses), throws an error
 *
 * Assumptions:
 *  1. Parentheses are balanced
 *  2. No invalid characters (only digits and operators)
 */

import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;

public class Calculator {

  private static final char OPEN_PARENTHESIS = '(';
  private static final char CLOSED_PARENTHESIS = ')';
  private static final char[] numbers = {'0', '1', '2', '3', '4', '5', '6',
    '7', '8', '9', '.'};

 /**
  * Method name: evaluate
  * Description: completes the arithmetic of two numbers using the given
  * operator
  * @param: two numbers and an operator
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
     for (char num : numbers) {
       if (character == num) {
         return true;
       }
     }
     return false;
   }

 /**
  * Method name: simpleFormat
  * Description: Helper method to convert double negatives into addition and
  * remove white spaces
  * @param: String mathematical expression
  * @return: expression in linked list format without any double negatives or
  * spaces
  */
  public static LinkedList<Character> simpleFormat(String input)
    throws InvalidCharacterException, DoubleOperatorException,
    InvalidInfixOperatorException {
      String str = input.replaceAll("\\s", "");
      System.out.println(str);
      char[] charArray = str.toCharArray();
      LinkedList<Character> list = new LinkedList<Character>();

      for (int i = 0; i < charArray.length; i++) {
        // checks for consecutives
        if (i < charArray.length - 1) {
          // replaces double negatives
          if (charArray[i] == '-' && charArray[i + 1] == '-') {
            list.add('+');
          }
          // checks for valid characters and operators
          else if (precedence(charArray[i]) > 0
            && precedence(charArray[i + 1]) > 0) {
              throw new DoubleOperatorException();
          }
          // checks for double decimals
          else if (charArray[i] == '.' && charArray[i + 1] == '.') {
            System.out.println("double decimal invalid ");
            System.exit(1);
          }
        }
        // valid operator or digit
        if (precedence(charArray[i]) >= 0 || isDigit(charArray[i])) {
          list.add(charArray[i]);
        }
        else { // invalid
          throw new InvalidCharacterException(Character.toString(charArray[i]));
        }
      }
      // checks for operator location
      if (precedence(charArray[charArray.length - 1]) > 0
        || precedence(charArray[0]) > 0) {
          throw new InvalidInfixOperatorException();
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
  * @param: expression in linked list format
  * @return: String expression in postfix format, numbers are separated by
  * an empty space
  */
  public static String postfix(LinkedList<Character> input) {
    String output = "";
    Stack<Character> stack = new Stack<>();

    // iterates through character array
    for (int i = 0; i < input.size(); i++) {
      System.out.println("JFASKLJFLA " + input.get(i));
      // checks for PEDMAS
      if (precedence(input.get(i)) > 0) {
        // stack has higher priority than current, add top to str
        while (!stack.empty()
          && precedence(stack.peek()) >= precedence(input.get(i))) {
            output += stack.pop() + " ";
        }
        stack.push(input.get(i));
      }

      else if (isDigit(input.get(i))) {
        // number may be more than one digit long
        while (i < input.size() && isDigit(input.get(i))) {
          output += input.get(i);
          i++;
        }
        output += " ";
        i--; // reached operator, backstep one
      }

      else if (input.get(i) == OPEN_PARENTHESIS) {
        stack.push(input.get(i));
      }

      // reached end of parentheses, must check for priority
      else if (input.get(i) == CLOSED_PARENTHESIS) {
        char operator = stack.pop();
        // calculates quantity within parentheses
        while (operator != OPEN_PARENTHESIS) {
          output += operator + " ";
          operator = stack.pop();
        }
      }
    }

    // adds lowest priority numbers to str
    while (!stack.empty()) {
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
    catch (DoubleOperatorException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
    catch (InvalidInfixOperatorException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
  }
}
