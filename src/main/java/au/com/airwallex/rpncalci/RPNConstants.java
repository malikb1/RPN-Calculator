package au.com.airwallex.rpncalci;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;

import com.google.common.collect.ImmutableList;

/**
 * @author malikb1
 *
 */
public class RPNConstants {

	public static final ImmutableList<String> SUPPORTED_OPERATIONS = 
			  ImmutableList.of("+", "-","*","/","sqrt","undo", "clear");
	public final static String SEPERATOR = " ";

	public static BiFunction<BigDecimal, BigDecimal, BigDecimal> addition = (op1,op2) -> {
		return op1.add(op2);
	};
	public static BiFunction<BigDecimal, BigDecimal, BigDecimal> subtraction = (op1,op2) -> {
		return op2.subtract(op1);
	};
	public static BiFunction<BigDecimal, BigDecimal, BigDecimal> multiplication = (op1,op2) -> {
		return op1.multiply(op2);
	};
	public static BiFunction<BigDecimal, BigDecimal, BigDecimal> division = (op1,op2) -> {
		return op2.divide(op1,10,RoundingMode.HALF_UP);
	};
	public final static String MATHS_OPERATION_INDICATOR = "maths-operation-performed";
	public final static String SQRT_OPERATION_INDICATOR = "sqrt-operation-performed";

}
