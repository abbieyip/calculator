import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.LinkedList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class calculatorTest {

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  /**
   * Method name: testIntegerArithmetic
   * Description: tests basic integers based on the evaluate method
   * @throws DivisionByZeroException when dividing by zero
   */
  @Test
  public void testIntegerArithmetic() throws DivisionByZeroException {
    try {
      // addition
      assertEquals(3, Calculator.evaluate(2, 1, "+"), 0);
      assertEquals(-5, Calculator.evaluate(-3,-2,"+"), 0);
      assertEquals(-25, Calculator.evaluate(-30,5,"+"), 0);
      assertEquals(2, Calculator.evaluate(5,-3,"+"), 0);

      // subtraction
      assertEquals(10, Calculator.evaluate(-3,-13,"-"), 0);
      assertEquals(16, Calculator.evaluate(3,-13,"-"), 0);
      assertEquals(-18, Calculator.evaluate(-15,3,"-"), 0);
      assertEquals(2, Calculator.evaluate(4,2,"-"), 0);

      // multiplication
      assertEquals(-15, Calculator.evaluate(-3,5,"*"), 0);
      assertEquals(-10, Calculator.evaluate(2,-5,"*"), 0);
      assertEquals(0, Calculator.evaluate(0,1,"*"), 0);
      assertEquals(16, Calculator.evaluate(4,4,"*"), 0);
      assertEquals(9, Calculator.evaluate(-3,-3,"*"), 0);

      // division
      assertEquals(-1, Calculator.evaluate(10,-10,"/"), 0);
      assertEquals(0.5, Calculator.evaluate(2,4,"/"), 0);
      assertEquals(-2.5, Calculator.evaluate(-5,2,"/"), 0);
      assertEquals(4, Calculator.evaluate(-20,-5,"/"), 0);
      assertEquals(0, Calculator.evaluate(0,-21,"/"), 0);

      // division by zero
      Calculator.evaluate(2, 0, "/");
      exception.expect(DivisionByZeroException.class);
      Calculator.evaluate(0, 0, "/");
      exception.expect(DivisionByZeroException.class);
      Calculator.evaluate(-1, 0, "/");
      exception.expect(DivisionByZeroException.class);
    }
    catch (DivisionByZeroException e) {
    }
  }

  /**
   * Method name: testDoubleArithmetic
   * Description: tests decimal numbers based on the evaluate method; delta is nonzero
   * due to size of double
   * @throws DivisionByZeroException when dividing by zero
   */
  @Test
  public void testDoubleArithmetic() throws DivisionByZeroException {
    try {
      // addition
      assertEquals(3.0, Calculator.evaluate(2., 1, "+"), 0.0000001);
      assertEquals(-6, Calculator.evaluate(-3.5,-2.5,"+"), 0.0000001);

      assertEquals(-25, Calculator.evaluate(-30.1,5.1,"+"), 0.0000001);
      assertEquals(2.54, Calculator.evaluate(5.75,-3.21,"+"), 0.0000001);

      // subtraction
      assertEquals(9.952, Calculator.evaluate(-3.9,-13.852,"-"), 0.0000001);
      assertEquals(16.7, Calculator.evaluate(3.5,-13.2,"-"), 0.0000001);
      assertEquals(-18, Calculator.evaluate(-15,3.0,"-"), 0.0000001);
      assertEquals(1.7, Calculator.evaluate(4.1,2.4,"-"), 0.0000001);

      // multiplication
      assertEquals(-17.0625, Calculator.evaluate(-3.25,5.25,"*"), 0.0000001);
      assertEquals(-11.445, Calculator.evaluate(2.1,-5.45,"*"), 0.0000001);
      assertEquals(0, Calculator.evaluate(0,1,"*"), 0.0000001);
      assertEquals(17, Calculator.evaluate(4,4.25,"*"), 0.0000001);
      assertEquals(11.4, Calculator.evaluate(-3,-3.8,"*"), 0.0000001);

      // division
      assertEquals(-1, Calculator.evaluate(10,-10,"/"), 0.0000001);
      assertEquals(2/4.25, Calculator.evaluate(2,4.25,"/"), 0.0000001);
      assertEquals(-2.5, Calculator.evaluate(-5,2,"/"), 0.0000001);
      assertEquals(100, Calculator.evaluate(-20,-0.2,"/"), 0.0000001);
      assertEquals(0, Calculator.evaluate(0,-21,"/"), 0.0000001);

      // division by zero
      Calculator.evaluate(2.6, 0.0, "/");
      exception.expect(DivisionByZeroException.class);
      Calculator.evaluate(-0.1, 0, "/");
      exception.expect(DivisionByZeroException.class);
      Calculator.evaluate(-0.89, 0, "/");
      exception.expect(DivisionByZeroException.class);
    }
    catch (DivisionByZeroException e) {
    }
  }

  /**
   * Method name: testPrecedence
   * Description: tests if character is valid by returning a
   * value greater than 0
   */
  @Test
  public void testPrecedence() {
    // valid symbols
    assertEquals(1, Calculator.precedence('+'));
    assertEquals(1, Calculator.precedence('-'));
    assertEquals(2, Calculator.precedence('*'));
    assertEquals(2, Calculator.precedence('/'));
    assertEquals(0, Calculator.precedence('('));
    assertEquals(0, Calculator.precedence(')'));

    // invalid
    assertEquals(-1, Calculator.precedence('$'));
    assertEquals(-1, Calculator.precedence('4'));
    assertEquals(-1, Calculator.precedence('>'));
    assertEquals(-1, Calculator.precedence('='));

  }

  /**
   * Method name: testIsDigit
   * Description: returns true if character is a digit or if
   * it is a period, otherwise returns false
   */
  @Test
  public void testIsDigit() {
    assertTrue(Calculator.isDigit('1'));
    assertTrue(Calculator.isDigit('2'));
    assertTrue(Calculator.isDigit('3'));
    assertTrue(Calculator.isDigit('4'));
    assertTrue(Calculator.isDigit('5'));
    assertTrue(Calculator.isDigit('6'));
    assertTrue(Calculator.isDigit('7'));
    assertTrue(Calculator.isDigit('8'));
    assertTrue(Calculator.isDigit('9'));
    assertTrue(Calculator.isDigit('0'));

    assertFalse(Calculator.isDigit('+'));
    assertFalse(Calculator.isDigit('-'));
    assertFalse(Calculator.isDigit('*'));
    assertFalse(Calculator.isDigit('/'));
    assertFalse(Calculator.isDigit('%'));
    assertFalse(Calculator.isDigit('@'));
  }

  /**
   * Method name: testSimpleFormat
   * Description: Tests if simpleFormat changes the user input
   * into a valid infix expression
   * @throws InvalidCharacterException when a non-digit or non-operator character
   * is encountered
   * @throws SyntaxErrorException when there are double operators or leading/trailing
   * operators
   */
  @Test
  public void testSimpleFormat() throws InvalidCharacterException,
          SyntaxErrorException {
    try {
      // spacing test
      LinkedList<String> test1 = new LinkedList<>(Arrays.asList("1", "+", "2"));

      assertEquals(test1, Calculator.simpleFormat("1+2"));
      assertEquals(test1, Calculator.simpleFormat("1 + 2"));
      assertEquals(test1, Calculator.simpleFormat(" 1     +     2"));
      assertEquals(test1, Calculator.simpleFormat("1+      2"));

      // double negatives
      assertEquals(test1, Calculator.simpleFormat("1 -- 2"));
      assertEquals(test1, Calculator.simpleFormat("1--2"));
      assertEquals(test1, Calculator.simpleFormat("1 -      - 2"));

      LinkedList<String> test2 = new LinkedList<>(Arrays.asList("5", "*","-4"));
      assertEquals(test2, Calculator.simpleFormat("5*-4"));
      assertEquals(test2, Calculator.simpleFormat("5*    -4"));
      assertEquals(test2, Calculator.simpleFormat("5*-     4"));

      // long expressions
      LinkedList<String> test3 = new LinkedList<>(Arrays.asList("1", "+", "2", "*", "5", "/", "8"));
      assertEquals(test3, Calculator.simpleFormat("1+2*5/8"));
      assertEquals(test3, Calculator.simpleFormat("1    +2*5    /8"));
      assertEquals(test3, Calculator.simpleFormat("1--2*5/8"));
      assertEquals(test3, Calculator.simpleFormat("1+      2*    5/  8"));

      // negative expressions
      LinkedList<String> test4 = new LinkedList<>(Arrays.asList("-1", "/", "5","+","3"));
      assertEquals(test4, Calculator.simpleFormat("-1/5--3"));
      LinkedList<String> test5 = new LinkedList<>(Arrays.asList("-1", "*", "-2","-","4"));
      assertEquals(test5, Calculator.simpleFormat("-1*-2-4"));

      // larger numbers
      LinkedList<String> test6 = new LinkedList<>(Arrays.asList("-24.5","/","-65.102","*","(","12.1", "-", "10.2", ")"));
      assertEquals(test6, Calculator.simpleFormat("-24.5/-65.102*(12.1-10.2)"));
      assertEquals(test6, Calculator.simpleFormat("-24.5  /- 65.   102* (12.1-10.2 ) "));

      // nested parentheses
      LinkedList<String> test7 = new LinkedList<>(Arrays.asList("(", "1.24", "+", "(", "-24.89", "/",
              "(" , "1", "-", "3.4",")",")",")"));
      assertEquals(test7,Calculator.simpleFormat("(1.24+(-24.89/(1-3.4)))"));
      assertEquals(test7,Calculator.simpleFormat("(1.24 + (- 24.89/(1-3.4) ) )"));

      // double operators
      // Calculator.simpleFormat("2+-+-4");
      // Calculator.simpleFormat("2 + -    +-4");
      // Calculator.simpleFormat("2++4");
      // Calculator.simpleFormat("2++-/4");

      // double decimal
      // Calculator.simpleFormat((".."));
      // Calculator.simpleFormat("1..");

      // invalid characters
      // Calculator.simpleFormat("cinnamon");
      // Calculator.simpleFormat("ab37591#");
      // exception.expect(InvalidCharacterException.class);

      // invalid operator location
      // Calculator.simpleFormat("45/");
      Calculator.simpleFormat("+1/78");
      exception.expect(SyntaxErrorException.class);
    }
    catch (InvalidCharacterException e) {
      System.out.println(e.getMessage());
    }
    catch (SyntaxErrorException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Method name: testPostfix
   * Description: checks if infix expression is correctly converted into postfix
   * @throws SyntaxErrorException when there are unbalanced parentheses
   */
  @Test
  public void testPostfix() throws SyntaxErrorException {
    try {
      assertEquals("1 2 + ", Calculator.postfix(new LinkedList<>(Arrays.asList("1","+","2"))));
      assertEquals("4 5 * 2 / ", Calculator.postfix(new LinkedList<>(Arrays.asList("4","*","5", "/", "2"))));
      assertEquals("-5 -8 + 11 2 * + ",
              Calculator.postfix(new LinkedList<>(Arrays.asList("-5","+","-8", "+", "11", "*", "2"))));
      assertEquals("4 2 - 3.5 * ",
              Calculator.postfix(new LinkedList<>(Arrays.asList("(","4","-", "2", ")", "*", "3.5"))));
      assertEquals("25 10 5 -29 / * - ",
              Calculator.postfix(new LinkedList<>(Arrays.asList("(", "25","-","10", "*", "(", "5", "/", "-29", ")", ")"))));
      assertEquals("0 ", Calculator.postfix(new LinkedList<>(Arrays.asList("(","(","(", "0", ")", ")", ")"))));

      // uneven parentheses
      // Calculator.postfix(new LinkedList<>(Arrays.asList("1","+","2", ")")));
      // Calculator.postfix(new LinkedList<>(Arrays.asList("(","5","+", "2")));
      // Calculator.postfix(new LinkedList<>(Arrays.asList("(","4","-", "2", ")", "*", ")")));
      Calculator.postfix(new LinkedList<>(Arrays.asList("(","(","(", "(", ")", ")", ")")));
      exception.expect(SyntaxErrorException.class);
    }
    catch (SyntaxErrorException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Method name: testParseString
   * Description: parses through postfix expression and evaluates to a double
   */
  @Test
  public void testParseString() {
    assertEquals(3.0, Calculator.parseString("1 2 + "), 0);
    assertEquals(10.0, Calculator.parseString("4 5 * 2 / "), 0);
    assertEquals(9.0, Calculator.parseString("-5 -8 + 11 2 * + "), 0);
    assertEquals(7.0, Calculator.parseString("4 2 - 3.5 * "), 0);
    assertEquals(12.111, Calculator.parseString("12.111 "), 0);
  }
}
