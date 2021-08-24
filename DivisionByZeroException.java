/**
 * Filename: DivisionByZeroException.java
 * Usage: throws an exception when attempting to divide by zero
 * Description: this file contains an exception for when any number is divided
 * by zero.
 */

 public class DivisionByZeroException extends Exception {
   private static final String EXCEPTION_MSG = "Invalid division by zero.";

   /**
    * Constructor creates a new exception when division by zero is attempted
    * @param NONE
    * @return NONE
    */
    public DivisionByZeroException() {
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
