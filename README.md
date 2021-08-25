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

Note: when running on linux, in-line command must be written in between quotation marks <br>
 e.g. java Calculator "1 + 2"

## Assumptions
- All numbers in the expression are within the range of a double.
- Evaluated expression is within the range of a double.

## Error Cases/Expression Guidelines
- Expressions must be written in infix format.
- Expressions cannot start with a nonnegative operator.
- Expressions cannot end with any operator.
- Expressions cannot contain non-digit characters or operators that are not +, -, *, /, (, or ). 
- Expressions cannot have more than two operators in series. The second operator may only be -.
- Expressions cannot have unbalanced parentheses. 
- Parentheses must have at least one value.
- Division by zero is invalid.

## Design Notes
1. Input validation <br>
Check user input for expression. If no input was given, prompt user for an expression.
2. Check format <br>
Throws an error and exits out of program if expression does not meet expression guidelines. <br>
3. Infix to postfix <br>
Converts expression into postfix notation to determine priority. <br>
4. Evaluate postfix <br>
Returns the answer of the evaluated postfix expression.

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

