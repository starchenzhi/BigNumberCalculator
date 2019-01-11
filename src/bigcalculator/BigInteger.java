/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigcalculator;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author chenz
 */
public class BigInteger {
    public enum Operator {
        Sum,
        Substract,
        Multiple,
    }
    
    private final static int DIGIT_LENGTH = 3;
    /*
    * Sum the two big integers from the arguments.
    */
    public static String Sum(String strNumber1, String strNumber2) {        
        //Split the two big integers from the arguments to groups.
        ArrayList<String> lstNumber1 = SplitStringToGroups(strNumber1);
        ArrayList<String> lstNumber2 = SplitStringToGroups(strNumber2);
        final int maxGroupCount = Math.max(lstNumber1.size(), lstNumber2.size());
        ArrayList<String> lstResult = new ArrayList<>(maxGroupCount + 1);
        int carryOut = 0;
        
        for (int index = 0; index < maxGroupCount; index++) {            
            int number1 = index >= lstNumber1.size() ? 0 : Integer.parseInt(lstNumber1.get(index));
            int number2 = index >= lstNumber2.size() ? 0 : Integer.parseInt(lstNumber2.get(index));
            
            int sumResult = number1 + number2 + carryOut;            
            String strResult = String.format("%0"+DIGIT_LENGTH+"d", sumResult);
            if (strResult.length() == DIGIT_LENGTH) { //The sum doesn't have data carry out.
                carryOut = 0;
                lstResult.add(strResult);
            } else if(strResult.length() > DIGIT_LENGTH) { //The sum has data carry out.
                carryOut = Integer.parseInt(strResult.substring(0, 1)) ;
                lstResult.add(strResult.substring(1));
            } 
        }
        
        if (carryOut > 0) {
            lstResult.add(String.valueOf(carryOut));
        }
        
        Collections.reverse(lstResult);
        StringBuilder sbResultBuilder = new StringBuilder();
        lstResult.forEach((strNumber) -> {
            sbResultBuilder.append(strNumber);
        });             
        
        //Remove the leading 0s then return the result.
        return sbResultBuilder.toString().replaceFirst("^0+(?!$)", "");
    }
    
    public static String Substract(String strNumber1, String strNumber2) {    
        //Compare the two numbers' value, swap their position if Number2 is larger than Number1.
        boolean negative = false;
        if (strNumber1.length() < strNumber2.length()) {
            negative = true;
        } else if (strNumber1.length() == strNumber2.length()) {
            for (int index = 0; index < strNumber1.length(); index++) {
                if (Integer.parseInt(String.valueOf(strNumber1.charAt(index))) < Integer.parseInt(String.valueOf(strNumber2.charAt(index))) ) {
                    negative = true;
                    break;
                }
            }
        }
        
        if (negative) {
            String temp = strNumber1;
            strNumber1 = strNumber2;
            strNumber2 = temp;
        }
        
        ArrayList<String> lstNumber1 = SplitStringToGroups(strNumber1);
        ArrayList<String> lstNumber2 = SplitStringToGroups(strNumber2);
        int maxGroupCount = Math.max(lstNumber1.size(), lstNumber2.size());
        ArrayList<String> lstResult = new ArrayList<>(maxGroupCount);
        int carryOut = 0;
        
        for (int index = 0; index < maxGroupCount; index++) {
            int number1 = index >= lstNumber1.size() ? 0 : Integer.parseInt(lstNumber1.get(index));
            int number2 = index >= lstNumber2.size() ? 0 : Integer.parseInt(lstNumber2.get(index));
            number1 -= carryOut;
            
            if (number1 < number2) {
                number1 = number1 + (int)Math.pow(10, DIGIT_LENGTH);
                carryOut = 1;
            }
            
            int result = number1 - number2;
            lstResult.add(String.format("%0"+DIGIT_LENGTH+"d", result));
        }
        
        Collections.reverse(lstResult);
        StringBuilder sbResuBuilder = new StringBuilder();
        lstResult.forEach((strResult) -> {
            sbResuBuilder.append(strResult);
        });
        
        String finalResult = sbResuBuilder.toString().replaceFirst("^0+(?!$)", "");
        if (negative) {
            finalResult = "-" + finalResult;
        }
        
        return finalResult;
    }
    
    public static String Multiple(String strNumber1, String strNumber2) { 
        ArrayList<String> lstNumbers = new ArrayList<>(strNumber2.length());
        
        for (int index = strNumber2.length() - 1; index >=0 ; index--) {
            int loopCount = Integer.parseInt(String.valueOf(strNumber2.charAt(index)));
            
            String tempResult = "0";
            for (int i = 0; i < loopCount; i++) {
               tempResult = Sum(strNumber1, tempResult);
            }
            
            int weight =  (int)Math.pow(10, strNumber2.length() - index - 1);
            tempResult = tempResult + String.valueOf(weight).substring(1);
            lstNumbers.add(tempResult);
        }
        
        String result = "0";
        for (String lstNumber : lstNumbers) {
            result = Sum(lstNumber, result);
        }
        
        return  result;
    }

    /*
    * Split a big integer (string) to groups, each group contains same length digits.
    */
    public static ArrayList<String> SplitStringToGroups( String strNumber ) {       
        final int groupCount = (int)Math.ceil(strNumber.length() * 1.0 / DIGIT_LENGTH ) ;
        int processedGroupCount = 0;
        final int firstGroupLengh = strNumber.length() % DIGIT_LENGTH;
        int count = 0;
        ArrayList<String> lstNumbers = new ArrayList<>( groupCount );
        StringBuilder sbNumberBuilder = new StringBuilder(DIGIT_LENGTH );
        
        //Process the first group.
        if (firstGroupLengh>0) {
            String firstGroup = strNumber.substring(0, firstGroupLengh);
            lstNumbers.add(firstGroup);
        }    
        
        //Process the rest of groups.
        for (int index = firstGroupLengh; index < strNumber.length(); index++) {            
            if (count < DIGIT_LENGTH ) {
                sbNumberBuilder.append(strNumber.charAt(index));
                count++;
            } 
            
            if (count == DIGIT_LENGTH ) {
                lstNumbers.add( sbNumberBuilder.toString() );
                processedGroupCount++;
                count = 0;
                if ( processedGroupCount < groupCount ) {
                    sbNumberBuilder = new StringBuilder(DIGIT_LENGTH);
                }
            }            
        }
        
        Collections.reverse( lstNumbers );        
        return lstNumbers;
    }
}
