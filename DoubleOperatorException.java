/**
 * Filename: DoubleOperatorException.java
 * Usage: throws an exception when there are two operators in a row
 * Description: this file contains an exception for when an argument has two
 * operators in a row. This excludes '--'
 */

 public class DoubleOperatorException extends Exception {
   private static final String EXCEPTION_MSG = "Invalid double operator.";

   /**
    * Constructor creates a new exception when division by zero is attempted
    * @param NONE
    * @return NONE
    */
    public DoubleOperatorException() {
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
