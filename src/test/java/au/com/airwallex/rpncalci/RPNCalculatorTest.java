package au.com.airwallex.rpncalci;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

import org.junit.Test;

public class RPNCalculatorTest {

	@Test
	public void testAddition() {
		String testInputForAdditioning = "1 1 +";
		RPNCalculator calci = new RPNCalculator();
		calci.calculate(testInputForAdditioning);
		Stack<BigDecimal> stack = calci.getOperandStack();

		assertEquals("stack size is 1 ", 1, stack.size());
		assertEquals("stack value is 2", "2", stack.pop().setScale(10, RoundingMode.DOWN).stripTrailingZeros().toPlainString());
	}
	
	@Test
	public void testSubtraction() {
		String testInputForAdditioning = "5 2 -";
		RPNCalculator calci = new RPNCalculator();
		calci.calculate(testInputForAdditioning);
		Stack<BigDecimal> stack = calci.getOperandStack();

		assertEquals("stack size is 1 ", 1, stack.size());
		assertEquals("stack value is 3", "3", stack.pop().setScale(10, RoundingMode.DOWN).stripTrailingZeros().toPlainString());
	}
	
	@Test
	public void testMultiplication() {
		String testInputForAdditioning = "4 1 *";
		RPNCalculator calci = new RPNCalculator();
		calci.calculate(testInputForAdditioning);
		Stack<BigDecimal> stack = calci.getOperandStack();

		assertEquals("stack size is 1 ", 1, stack.size());
		assertEquals("stack value is 4", "4", stack.pop().setScale(10, RoundingMode.DOWN).stripTrailingZeros().toPlainString());
	}
	
	@Test
	public void testDivision() {
		String testInputForAdditioning = "7 12 2 /";
		String a = "*";
		String testInput1 = "4 /";
		RPNCalculator calci = new RPNCalculator();
		calci.calculate(testInputForAdditioning);
		calci.calculate(a);
		calci.calculate(testInput1);
		Stack<BigDecimal> stack = calci.getOperandStack();

		assertEquals("stack size is 1 ", 1, stack.size());
		assertEquals("stack value is 10.5", "10.5", stack.pop().setScale(10, RoundingMode.DOWN).stripTrailingZeros().toPlainString());
	}
	
	@Test
	public void testUndo() {
		String testInputForAdditioning = "2 4 undo";
		RPNCalculator calci = new RPNCalculator();
		calci.calculate(testInputForAdditioning);
		Stack<BigDecimal> stack = calci.getOperandStack();

		assertEquals("stack size is 1 ", 1, stack.size());
		assertEquals("stack value is 2", "2", stack.pop().setScale(10, RoundingMode.DOWN).stripTrailingZeros().toPlainString());
	}
	
	@Test
	public void testSqrt() {
		String testInputForAdditioning = "2 2 sqrt";
		RPNCalculator calci = new RPNCalculator();
		calci.calculate(testInputForAdditioning);
		Stack<BigDecimal> stack = calci.getOperandStack();

		assertEquals("stack size is 2 ", 2, stack.size());
		assertEquals("stack value is 1.4142135623", "1.4142135623", stack.pop().setScale(10, RoundingMode.DOWN).stripTrailingZeros().toPlainString());
	}
	
	@Test
	public void testClear() {
		String testInputForAdditioning = "2 4 clear";
		RPNCalculator calci = new RPNCalculator();
		calci.calculate(testInputForAdditioning);
		Stack<BigDecimal> stack = calci.getOperandStack();

		assertEquals("stack size is 0 ", 0, stack.size());
	}
	
	@Test
	public void testMultipleUndo() {
		String testInputForAdditioning = "5 4 3 2 undo undo *";
		RPNCalculator calci = new RPNCalculator();
		calci.calculate(testInputForAdditioning);
		Stack<BigDecimal> stack = calci.getOperandStack();

		assertEquals("stack value is 20 ", "20", stack.pop().setScale(10, RoundingMode.DOWN).stripTrailingZeros().toPlainString());
	}
	
	@Test
	public void testRandom() {
		String testInputForAdditioning = "7 12 2 /";
		RPNCalculator calci = new RPNCalculator();
		calci.calculate(testInputForAdditioning);
		Stack<BigDecimal> stack = calci.getOperandStack();

		assertEquals("stack value is 6 ", "6", stack.pop().setScale(10, RoundingMode.DOWN).stripTrailingZeros().toPlainString());
	}
	
	@Test
	public void testInvalid() {
		String testInputForAdditioning = "1 2 3 * 5 + * * 6 5";
		RPNCalculator calci = new RPNCalculator();
		calci.calculate(testInputForAdditioning);
		Stack<BigDecimal> stack = calci.getOperandStack();

		assertEquals("stack size is 1 ", 1, stack.size());
		System.out.println(calci.getErrorMessage());
		assertNotNull("Errormessage is non empty ", calci.getErrorMessage());
	}
}
