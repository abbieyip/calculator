/**
 * Filename: InvalidInfixOperatorException.java
 * Usage: throws an exception when expression is not expressed in infix notation
 * Description: this file contains an exception for when any expression is not
 * in infix notation
 */

 public class InvalidInfixOperatorException extends Exception {
   private static final String EXCEPTION_MSG = "Invalid operator location.";

   /**
    * Constructor creates a new exception when division by zero is attempted
    * @param NONE
    * @return NONE
    */
    public InvalidInfixOperatorException() {
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
