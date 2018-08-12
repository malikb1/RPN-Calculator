package au.com.airwallex.rpncalci;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Function;
import static au.com.airwallex.rpncalci.RPNConstants.addition;
import static au.com.airwallex.rpncalci.RPNConstants.subtraction;
import static au.com.airwallex.rpncalci.RPNConstants.multiplication;
import static au.com.airwallex.rpncalci.RPNConstants.division;

/**
 * @author malikb1
 *
 */
public class RPNCalculator {

	/**
	 *  stack to store calculator operands
	 */
	private Stack<BigDecimal> operandStack = new Stack<>();
	private Stack<String> recoveryStack = new Stack<>();
	int index;
	
	/**
	 * variable to store error messages in case of unsupported operation
	 */
	private String errorMessage =null;
	
	/**
	 *  lambda expression to handle valid operator or real numbers. Incase of valid number,
	 *  stack gets updated with the number
	 */
	private Function<String,Boolean> handleValidOperatorOrRealNumber = (str) -> {
		boolean validInput = true;
		try {
			BigDecimal decimalVal = new BigDecimal(str).setScale(15, RoundingMode.DOWN);
			operandStack.push(decimalVal);
			// insert the input string in restore stack to support undo operation 
			recoveryStack.push(str);
		} catch(NumberFormatException nfe) {
			validInput = false;
		}
		return (validInput || RPNConstants.SUPPORTED_OPERATIONS.stream()
				.anyMatch((supportedOperation) -> supportedOperation.equals(str.toUpperCase())));
	};
	
	/**
	 * @param userInput
	 * Perform calculator operation on the userInput from the console
	 * and update the errorMessages for the scenario when operation is not supported
	 */
	public void calculate(String userInput) {
		index=0;
		Arrays.asList(userInput.split("\\s")).stream().
		forEach(input -> {
			
			//check if errorMessage is not empty , abort the calculation and return
			if(null!=errorMessage)
				return;
			
			// switch operation based on input
			switch(input.toUpperCase()) {
			
			case("+"):
				index+=2;
				performBasicMath(userInput,input,addition);
				break;
			case "-":
				index+=2;
				performBasicMath(userInput,input,subtraction);
				break;
			case "*":
				index+=2;
				performBasicMath(userInput,input,multiplication);
				break;
			case "/":
				index+=2;
				performBasicMath(userInput,input,division);
				break;
			case "SQRT":
				index+=5;
				performSqrt(userInput,input);
				break;
			case "CLEAR":
				index+=6;
				operandStack.clear();
				errorMessage = null;
				break;
			case "UNDO":
				index+=5;
				undo();
				break;
			default:
				index+=( input.length() +1 );
				if(!handleValidOperatorOrRealNumber.apply(input)) {
					errorMessage = "operator " + input + " ( position: " + --index +  " ) : " + "Insufficient parameters";
				}
			}
			
		});
	}
	
	/**
	 * @param userInput
	 * @param input
	 * @param operation
	 * Perform basic operations and update the errorMessages for the scenario when arguments are insufficient 
	 */
	public void performBasicMath(String userInput,String input, BiFunction<BigDecimal, BigDecimal, BigDecimal> operation) {
		if(operandStack.size()<2) {
			errorMessage = "operator " + input + " ( position: " + --index +  " ) : " + "Insufficient parameters";
			return;
		}
		BigDecimal operand2 = operandStack.pop();
		BigDecimal operand1 = operandStack.pop();
		
		// insert operands in reverse order to restore in original order for undo operation
		recoveryStack.push(operand2.toPlainString());
		recoveryStack.push(operand1.toPlainString());
		// insert symbolic indication of operation other than sqrt
		recoveryStack.push(RPNConstants.MATHS_OPERATION_INDICATOR);
		
		operandStack.push(operation.apply(operand2,operand1));
		
	}
	/**
	 * @param userInput
	 * @param input
	 * Perform the square root operation and update the errorMessages for the scenario when arguments are insufficient 
	 */
	public void performSqrt(String userInput,String input) {
		if(operandStack.size()<1) {
			errorMessage = "operator " + input + " ( position: " + --index +  " ) : " + "Insufficient parameters";
			return;
		}
		BigDecimal operand = operandStack.pop();
		Double sqrtVal = Math.sqrt(operand.doubleValue());
		// insert operands in reverse order to restore in original order for undo operation
		recoveryStack.push(operand.toPlainString());
		// insert symbolic indication of sqrt operation
		recoveryStack.push(RPNConstants.SQRT_OPERATION_INDICATOR);
		
		operandStack.push(new BigDecimal(sqrtVal));
	}
	
	public void undo() {
		if(recoveryStack.size()>0) {
			String lastInput = recoveryStack.pop();
			
			if(lastInput.equalsIgnoreCase(RPNConstants.SQRT_OPERATION_INDICATOR)) {
				// in case of sqaure root, replace operandStack value with recoveryStack
				operandStack.pop();
				operandStack.push(new BigDecimal(recoveryStack.pop()));
			} else if(lastInput.equalsIgnoreCase(RPNConstants.MATHS_OPERATION_INDICATOR)) {
				// in case of basic maths operation , remove operand stack with last two inputs from recoveryStack
				operandStack.pop();
				operandStack.push(new BigDecimal(recoveryStack.pop()));
				operandStack.push(new BigDecimal(recoveryStack.pop()));
			} else {
				// in case of lastInput as operands, just remove the last entry from operand stack
				operandStack.pop();
			}
		}
		
	}
	
	/**
	 * Method to print the stack and errorMessage on processing each input line from console
	 */
	public void printResult() {
		if(null!=errorMessage) System.out.println(errorMessage);
		System.out.print("Stack: ");
		operandStack.stream().forEach(str -> System.out.print(RPNConstants.SEPERATOR + 
				str.setScale(10,RoundingMode.DOWN).stripTrailingZeros().toPlainString() 
				+ RPNConstants.SEPERATOR ));
		// start from new line
		System.out.println();
		
		//clear error messages for the next run
		errorMessage = null;

	}

	/**
	 * @return
	 */
	public Stack<BigDecimal> getOperandStack() {
		return operandStack;
	}

	/**
	 * @return
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	
}
