/**
 * Filename: Calculator.java
 * Compilation: javac Calculator.java
 * Usage: java Calculator [mathematical expression]
 *        java Calculator
 * Description: Calculates [mathematical expression] and prints the answer to
 * the terminal; if argument is invalid (i.e. contains variables or does not
 * have balanced parentheses), throws an error
 */

import java.util.Scanner;
import java.util.Stack;

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
  public static double evaluate(double num1, double num2, String operator) {
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
  * Method name: postfix
  * Description: changes infix expression to postfix expression based
  * on precedence
  *   Examples: 24 + 2 => 24 2 +
  *             2 + 4 * 3 => 2 4 3 * +
                (2 + 4) * 3 => 2 4 + 3 *
  * @param: expression in char[] format
  * @return: String expression in postfix format, numbers are separated by
  * an empty space
  */
  public static String postfix(char[] input) {
    String output = "";
    Stack<Character> stack = new Stack<>();

    // iterates through character array
    for (int i = 0; i < input.length ; i++) {
      // checks for PEDMAS
      if (precedence(input[i]) > 0) {
        // stack has higher priority than current, add top to str
        while (!stack.empty()
          && precedence(stack.peek()) >= precedence(input[i])) {
            output += stack.pop() + " ";
        }
        stack.push(input[i]);
      }

      else if (isDigit(input[i])) {
        // number may be more than one digit long
        while (i < input.length && isDigit(input[i])) {
          output += input[i];
          i++;
        }
        output += " ";
        i--; // reached operator, backstep one
      }

      else if (input[i] == OPEN_PARENTHESIS) {
        stack.push(input[i]);
      }

      // reached end of parentheses, must check for priority
      else if (input[i] == CLOSED_PARENTHESIS) {
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
  * Method name: removeDoubleNegative
  * Description: Helper method to convert double negatives into addition
  * @param: String mathematical expression
  * @return: expression in char array format without any double negatives
  */
  public static char[] removeDoubleNegative(String input) {
    char[] charArray = input.toCharArray();
    for (int i = 0; i < charArray.length - 1; i++) {
      if (charArray[i] == '-' && charArray[i + 1] == '-') {
        charArray[i] = ' ';
        charArray[i + 1] = '+';
      }
    }
    return charArray;
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
      System.out.println(split[i]);
      // reached an operator
      if (split[i].equals("+") || split[i].equals("-") || split[i].equals("*")
        || split[i].equals("/")) {
          // evaluates quantity
          double num2 = stack.pop();
          double num1 = stack.pop();
          double ans = evaluate(num1, num2, split[i]);
          stack.push(ans);
      }

      // reached a numerical value
      else {
        stack.push(Double.parseDouble(split[i]));
      }
    }

    // final answer is located in stack
    if (stack.size() == 1) {
      return stack.pop();
    }
    // error, missing operator or invalid expression
    return 0;
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
    else if (args.length == 1) {
      arg = args[0];
    }

    // evaluates expression
    System.out.println("OUTPUT: " + postfix(removeDoubleNegative(arg)));
    String postfixFormat = postfix(removeDoubleNegative(arg));
    double ans = parseString(postfixFormat);
    System.out.println(arg + " = " + ans);
  }
}
