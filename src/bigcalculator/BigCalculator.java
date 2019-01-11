/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigcalculator;

import java.util.Scanner;

/**
 *
 * @author chenz
 */
public class BigCalculator {
    
    public static void main(String[] args) {
        String strNumber1, strNumber2, finalResult = "";
        Scanner scanner = new Scanner(System.in); 
        
        System.out.println("Please input the first integer (265 or longer digits):");        
        strNumber1 = scanner.nextLine();
        if (validateInput(strNumber1) == false) {
            System.out.println("Your input contains non-digit.");
            System.exit(1);
        }
        
        System.out.println("Please input the second integer (265 or longer digits):"); 
        strNumber2 = scanner.nextLine();
        
        System.out.println("Please select the operator(0:SUM, 1:SUBSTRACT, 2:MULTIPLE):");
        BigInteger.Operator operator = BigInteger.Operator.values() [ Integer.parseInt(scanner.nextLine()) ] ;
        
        switch( operator ) {
            case Sum:
                finalResult = BigInteger.Sum(strNumber1, strNumber2);
                break;
            case Substract:
                finalResult = BigInteger.Substract(strNumber1, strNumber2);
                break;
            case Multiple:
                finalResult = BigInteger.Multiple(strNumber1, strNumber2);
                break;                
        }
        System.out.println("The two big integers' calculating result is: " + finalResult);   
    }
    
    public static boolean validateInput(String strInput) {
        return strInput.matches("^[1-9]");
    }
}
