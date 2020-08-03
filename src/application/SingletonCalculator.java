package application;

import java.util.ArrayList;

public class SingletonCalculator {
	
	private static SingletonCalculator instance;
	
	private ArrayList<Double> numbers;
	private ArrayList<Character> operations;
	
	private SingletonCalculator() {
	}

	public static synchronized SingletonCalculator getCalculator() {
		if (instance == null) {
			instance = new SingletonCalculator();
		}
		
		return instance;
	}
	
	/**
	 * set the equation to be solved as a string
	 * @param equation is a string equation, consisting of doubles and simple operators (+*-/)
	 * @return a boolean indicating whether or not the equation was successfully parsed
	 */
	public boolean setEquation(String equation) {
		String[] strNums = equation.split("\\*|\\+|-|/"); // Delimiters of the basic operations
		numbers = new ArrayList<Double>(); // the numbers to be operated on
		operations = new ArrayList<Character>(); // the operations to be done
		int opLoc = 0; // the location in the equation of the operator (+,-,*,/)
		
		// loop through the equation, and separate the numbers from the operators, checking for invalid characters or formats
		for (int i = 0; i < strNums.length - 1; i++) {
			opLoc += strNums[i].length();
			operations.add(equation.charAt(opLoc));
			try {
				numbers.add(Double.parseDouble(strNums[i]));
			} catch(Exception e) {
				// if there is a 0 length "number", then there is an extra operator
				if(strNums[i].length() == 0) {
					strNums[i+1] = equation.charAt(opLoc) + strNums[i+1];
					operations.remove(i);
					opLoc-=1;
				} else { // if it can't convert to a double for any other reason, then the equation is invalid
					numbers.clear();
					operations.clear();
					return false;
				}
			}
			opLoc+=1;
		}
		
		// grab the last number from the string equation
		try {
			numbers.add(Double.parseDouble(strNums[strNums.length-1]));
		} catch(Exception e) { // if there is not a valid last number, then the equation is invalid
			numbers.clear();
			operations.clear();
			return false;
		}
		
		return true;
	}

	/**
	 * Based on the given equation, calculate a result, following order of operations
	 * @return a double result of the equation
	 */
	public double calculate() {
		double result = -1;
		int index, indexMult, indexDiv, indexAdd, indexSubt;
		
		// search for * and / operators and work them in order of appearance
		while (operations.contains('*') || operations.contains('/')) {
			indexMult = operations.indexOf('*');
			indexDiv = operations.indexOf('/');
			index = Math.min(indexMult, indexDiv);
			if (index < 0) {
				index = Math.max(indexMult, indexDiv);
			}
			
			if(index == indexMult) {
				result = numbers.get(index) * numbers.get(index+1);
			} else {
				result = numbers.get(index) / numbers.get(index+1);
			}
			
			numbers.remove(index+1); // remove one of the numbers 
			numbers.set(index, result); // and replace the other with the result
			operations.remove(index); // and remove the operator
		}
		
		// search for + and - operators and work them in order of appearance
		while (operations.contains('+') || operations.contains('-')) {
			indexAdd = operations.indexOf('+');
			indexSubt = operations.indexOf('-');
			index = Math.min(indexAdd, indexSubt);
			if (index < 0) {
				index = Math.max(indexAdd, indexSubt);
			}
			
			if(index == indexAdd) {
				result = numbers.get(index) + numbers.get(index+1);
			} else {
				result = numbers.get(index) - numbers.get(index+1);
			}
			numbers.remove(index+1);
			numbers.set(index, result);
			operations.remove(index);
		}
		
		if (!numbers.isEmpty()) {
			result = numbers.get(0);
		}
		
		return result;
	}

	public double logResult() {
		return Math.log(calculate());
	}
}
