/**
 * Filename: SyntaxErrorException.java
 * Usage: throws an exception when there are two operators in a row,
 * a non-negative operator at the beginning of the expression, an operator
 * at the end of the expression, or unbalanced parentheses.
 * Description: this file contains an exception for when an argument has a
 * syntax error. This excludes operators followed by a '-'
 */

 public class SyntaxErrorException extends Exception {
   private static final String EXCEPTION_MSG = "Syntax Error.";

   /**
    * Constructor creates a new exception when division by zero is attempted
    * @param NONE
    * @return NONE
    */
    public SyntaxErrorException() {
      super(EXCEPTION_MSG);
    }

    /**
     * Method name: toString
     * Description: overrides toString method to throw Exception
     * @param NONE
     * @return String with error message
     */
     @Override
     public String toString() {
       return EXCEPTION_MSG;
     }
 }
