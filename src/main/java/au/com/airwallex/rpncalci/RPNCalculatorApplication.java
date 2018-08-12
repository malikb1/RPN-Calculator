package au.com.airwallex.rpncalci;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author malikb1
 *
 */
public class RPNCalculatorApplication {

	/**
	 * @param args
	 * Main application class to initiate the RPM calculator.
	 */
	public static void main(String[] args) {

		// present message to type single space separated input
		System.out.print("Welcome to the RPN calculator. Supported operations are : ");
		RPNConstants.SUPPORTED_OPERATIONS.stream().forEach(operators -> System.out.print(" " + operators + " "));
		System.out.print("... Please enter input seperated by space ... To Quit type Exit  \n");
		try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in))) {

			String userInput = null;
			RPNCalculator calci = new RPNCalculator();
			while (!(userInput = inputReader.readLine()).equalsIgnoreCase("Exit")) {
				calci.calculate(userInput);
				calci.printResult();
			}
		} catch (IOException ioe) {
			System.out.println("System error while reading input from console.");
		}
	}
}
