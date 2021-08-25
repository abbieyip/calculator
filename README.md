# Calculator

> A calculator program that takes in a user input as a string, evaluates the expression, and prints out the answer. 

FileList 
----------------
Calculator.java <br>
calculatorTest.java <br>
DivisionByZeroException.java <br>
InvalidCharacterException.java <br>
README.md <br>
SyntaxErrorException.java<br>

Compilation 
----------------
javac Calculator.java
<br>
## Usage 
+ java Calculator [expression] <br>
+ java Calculator <br>

## Assumptions
- All numbers in the expression are within the range of a double.
- Evaluated expression is within the range of a double.

## Error cases
- Expressions cannot start with a nonnegative operator.
- Expressions cannot end with any operator.
- Expressions cannot contain non-digit characters or operators that are not +, -, *, /, (, or ). 
- Expressions cannot have more than two operators in series. The second operator may only be -.
- Expressions cannot have unbalanced parentheses. 
- Parentheses must have at least one value.
- Division by zero is invalid.

## Examples <br>
java Calculator <br>
> Enter a mathematical expression in infix notation. <br>

(24.3 + 4) / 78 <br>

> (24.3+4)/78 = 0.3628205128205128 <br>
<hr>

java Calculator (58   * 3.2 + (45 --23)) 
>(58*3.2+(45--23)) = 253.60000000000002 <br>
<hr>

java Calculator hello
> h is Invalid

<hr>
java Calculator (()) <br>

> Syntax Error.

