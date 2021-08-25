/**
 * Filename: InvalidCharacterException.java
 * Usage: throws an exception when expression contains an invalid character
 * Description: this file contains an exception for when the argument contains
 * an invalid character
 */

 public class InvalidCharacterException extends Exception {
   private static final String EXCEPTION_MSG = "%s is Invalid";

   private String invalidChar;
   /**
    * Constructor creates a new exception when there is an invalid non-digit
    * or non operator character
    * @param c, invalid character
    */
    public InvalidCharacterException(String c) {
      super(String.format(EXCEPTION_MSG, c));
      this.invalidChar = c;
    }

    /**
     * Method name: toString
     * Description: overrides toString method to throw Exception
     * @return String with error message containing which invalid character
     */
     @Override
     public String toString() {
       return String.format(EXCEPTION_MSG, this.invalidChar);
     }
 }
